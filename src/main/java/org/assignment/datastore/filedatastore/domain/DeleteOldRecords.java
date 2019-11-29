package org.assignment.datastore.filedatastore.domain;

import org.assignment.datastore.filedatastore.util.FileHandlerUtil;
import org.assignment.datastore.model.Attributes;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;

public class DeleteOldRecords implements Runnable {
    private RandomAccessFile randomAccessFile;
    private Map<String, Attributes> keyIndex;
    private String tempFilePath="tempFile.txt";

    public DeleteOldRecords(Map<String, Attributes> keyIndex,RandomAccessFile randomAccessFile){
        this.keyIndex=keyIndex;
        this.randomAccessFile=randomAccessFile;
    }

    @Override
    public void run() {
        String line=null;
        try {
            long sum=0;
            int i=0;
            RandomAccessFile outputFile = new RandomAccessFile(tempFilePath, "rw");
            outputFile.setLength(0);
            randomAccessFile.seek(0);
            while ((line = this.randomAccessFile.readLine()) != null&&!"".equals(line)) {
                outputFile.seek(outputFile.length());
                int firstIndex=line.indexOf("~");
                String key=line.substring(0,firstIndex).trim();
                if("".equals(key)|| FileHandlerUtil.isKeyExpired(line)){
                i++;
                continue;
            }
            if(outputFile.length()!=0) {
                outputFile.write("\n".getBytes());
            }
                outputFile.write(line.getBytes());
                this.keyIndex.put(line.substring(0,firstIndex).trim(),new Attributes(i,Long.parseLong(line.substring(firstIndex+1,line.indexOf("~",firstIndex+1)).trim())));
                i++;
            }
            line="";
            randomAccessFile.setLength(0);
            outputFile.seek(0);
            randomAccessFile.seek(0);
            while ((line = outputFile.readLine()) != null&&!"".equals(line)) {
                if(this.randomAccessFile.length()!=0) {
                    this.randomAccessFile.write("\n".getBytes());
                }
                randomAccessFile.write(line.getBytes());
            }
            outputFile.setLength(0);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        System.gc();
    }


}
