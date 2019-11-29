package org.assignment.datastore.filedatastore.exception;

import org.assignment.datastore.exception.DataStoreException;

public class FileDataStoreException extends DataStoreException {

    public FileDataStoreException(String message) {
        super(message);
    }

    public FileDataStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileDataStoreException(Throwable cause) {
        super(cause);
    }

    public FileDataStoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
