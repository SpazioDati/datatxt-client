package com.spaziodati.datatxt;

/**
 * @author Michele Mostarda (mostarda@fbk.eu)
 */
public class DataTXTClientException extends Exception {

    private final int status;

    public DataTXTClientException(int status, String error) {
        super(error);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
