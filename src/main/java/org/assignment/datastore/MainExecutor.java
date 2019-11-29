package org.assignment.datastore;

import org.assignment.datastore.filedatastore.FileDataStore;
import org.assignment.datastore.filedatastore.domain.DeleteOldRecords;
import org.assignment.datastore.filedatastore.exception.FileDataStoreException;
import org.assignment.datastore.model.DataStoreKey;

import javax.xml.bind.ValidationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainExecutor {
    public static void main(String args[]){
        try {
            DataStore dataStore=new FileDataStore("/home/maheshj/mahesh-j/backup/sprinklr/Temp/datastore2.txt");
            List<DataStoreKey> keyList=new ArrayList<>();

            for(int i=0;i<1;i++){
                keyList.add(new DataStoreKey(""+i,i));
            }
            for(int i=70;i<70;i++) {
                dataStore.createData(new DataStoreKey(""+i, 4), keyList);
            }
//            dataStore.readData("85");
            dataStore.deleteData("93");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (FileDataStoreException e) {
            e.printStackTrace();
        }
    }

}
