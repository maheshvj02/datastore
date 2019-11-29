package org.assignment.datastore.filedatastore.domain;

import org.assignment.datastore.model.Attributes;

import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

public class BootTimeKeyIndexer implements Runnable{

    private RandomAccessFile randomAccessFile;
    private Map<String, Attributes>  keyIndex;

    public BootTimeKeyIndexer(RandomAccessFile randomAccessFile, Map<String,Attributes>  keyIndex){
        super();
        this.randomAccessFile=randomAccessFile;
        this.keyIndex=keyIndex;
    }

    @Override
    public void run() {
        String line;
        try {
            long sum=0;
            int i=0;
            while ((line = this.randomAccessFile.readLine()) != null&&!"".equals(line)) {
             int firstIndex=line.indexOf("~");
              String key=line.substring(0,firstIndex).trim();
              if("".equals(key)){
                  i++;
                  continue;
              }
             this.keyIndex.put(line.substring(0,firstIndex).trim(),new Attributes(i,Long.parseLong(line.substring(firstIndex+1,line.indexOf("~",firstIndex+1)).trim())));
             i++;
            }
            this.keyIndex.entrySet().stream().forEach(System.out::println);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
