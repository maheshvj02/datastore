package org.assignment.datastore.filedatastore.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assignment.datastore.filedatastore.exception.FileDataStoreException;
import org.assignment.datastore.model.DataStoreKey;


public class ValidationUtil {

    public static Boolean validateKey(DataStoreKey key) throws FileDataStoreException {
        if(key==null||key.getKey()==null||key.getKey().length()==0){
            throw new FileDataStoreException("Invalid Key");
        }else if(key.getKey().length()>32){
            throw new FileDataStoreException("Maximum key length is 32 char ");
        }

        return true;
    }

    public static Boolean validateObject(Object value) throws JsonProcessingException, FileDataStoreException {
        if(FileHandlerUtil.getObjectSizeInKB(value)>16){
            throw new FileDataStoreException("Maximum Value size is 16 KB ");
        }
        return true;
    }


}
