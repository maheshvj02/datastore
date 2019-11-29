package org.assignment.datastore.filedatastore;


import org.assignment.datastore.DataStore;
import org.assignment.datastore.filedatastore.domain.BootTimeKeyIndexer;
import org.assignment.datastore.filedatastore.domain.DeleteOldRecords;
import org.assignment.datastore.filedatastore.exception.FileDataStoreException;
import org.assignment.datastore.filedatastore.util.FileHandlerUtil;
import org.assignment.datastore.filedatastore.util.ValidationUtil;
import org.assignment.datastore.model.Attributes;
import org.assignment.datastore.model.DataStoreKey;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class FileDataStore implements DataStore {

    private String filePath="/datastore.txt";
    private static FileChannel fc;
    private FileLock lock;

    private RandomAccessFile randomAccessFile;
    private LinkedBlockingQueue<String> queue;
    private Map<String, Attributes> keyIndex=new HashMap<>();

    public FileDataStore(String filePath) throws IOException, InterruptedException {
        this.filePath=filePath;
        initialize(this.filePath);
    }
    public FileDataStore() throws IOException, InterruptedException {
        initialize(this.filePath);
    }

    public void initialize(String fileName) throws IOException, InterruptedException {
        this.randomAccessFile = new RandomAccessFile(fileName, "rw");
        this.fc=this.randomAccessFile.getChannel();
        this.lock=this.fc.lock();
        addShutdownHook();
        this.queue = new LinkedBlockingQueue<>(Integer.MAX_VALUE);
        Thread thread=new Thread(new BootTimeKeyIndexer(randomAccessFile,keyIndex));
        thread.start();
        thread.join();


    }

    @Override
    public Boolean createData(DataStoreKey key, Object object) throws IOException, FileDataStoreException {
        ValidationUtil.validateKey(key);
        ValidationUtil.validateObject(object);
        String line=new String(FileHandlerUtil.getOutput(key,object));
        if(FileHandlerUtil.isKeyExpired(line)){
            deleteData(key.getKey());
        }
        if(keyIndex.get(key.getKey())!=null){
            throw new FileDataStoreException("Key Already Exist");
        }
        keyIndex.put(key.getKey(),new Attributes(keyIndex.size(),key.getTimeToLive()));
        this.randomAccessFile.seek(randomAccessFile.length());
        if(this.randomAccessFile.length()!=0) {
            this.randomAccessFile.write("\n".getBytes());
        }
        this.randomAccessFile.write(line.getBytes());
        return true;
    }

    @Override
    public Object readData(String key) throws IOException, FileDataStoreException {

        long expiryTime= LocalDateTime.now().plusYears(100).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Attributes attribute=keyIndex.get(key);
        if(attribute==null){
            throw new FileDataStoreException("Invalid key:"+key);
        }
        long index=attribute.getIndex();
        if(index!=0) {
            this.randomAccessFile.seek((this.keyIndex.get(key).getIndex()) * 16433);
        }else{
            this.randomAccessFile.seek(0);
        }
        byte[] data=new byte[16433];
        this.randomAccessFile.read(data);

        String line=new String(data);
        if(FileHandlerUtil.isKeyExpired(line)){
            deleteData(key);
            throw new FileDataStoreException("Invalid key:"+key);

        }
//    32+1+14+1+16384 +1=16433

        return line;
    }

    @Override
    public Boolean deleteData(String key) throws FileDataStoreException, IOException {
        Attributes attribute=keyIndex.get(key);
        if(attribute==null){
            throw new FileDataStoreException("Invalid key:"+key);
        }
        keyIndex.remove(key);
        long index=attribute.getIndex();

        if(index!=0) {
            this.randomAccessFile.seek((attribute.getIndex()) * 16433);
        }else{
            this.randomAccessFile.seek(0);
        }
        if(this.randomAccessFile.length()!=0) {
            this.randomAccessFile.write("\n".getBytes());
        }
        this.randomAccessFile.write(FileHandlerUtil.defaultEmpty.getBytes());

        return true;
    }

    private void addShutdownHook(){
        Thread deleteThread=new Thread(new DeleteOldRecords(keyIndex,randomAccessFile));
        Runtime.getRuntime().addShutdownHook(deleteThread);
    }
}
