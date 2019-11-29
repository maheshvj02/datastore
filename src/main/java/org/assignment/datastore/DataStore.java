package org.assignment.datastore;

import org.assignment.datastore.exception.DataStoreException;
import org.assignment.datastore.filedatastore.exception.FileDataStoreException;
import org.assignment.datastore.model.DataStoreKey;

import javax.xml.bind.ValidationException;
import java.io.IOException;

/**
 * Data Store to maintain key value Pairs.
 * It will support create,read and delete
 */
public interface DataStore {

  /**
   * To create a Record in DataStore
   * @param key
   * @param object
   * @return
   * @throws IOException
   * @throws DataStoreException
   */
  Boolean createData(DataStoreKey key, Object object) throws IOException, DataStoreException;

  /**
   * To read a Record from DataStore
   * @param key
   * @return
   * @throws IOException
   * @throws DataStoreException
   */
  Object readData(String key) throws IOException, DataStoreException;

  /**
   * To Delete a Record from DataStore
   * @param key
   * @return
   * @throws DataStoreException
   * @throws IOException
   */
  Boolean deleteData(String key) throws DataStoreException, IOException;


}
