package com.medical.dao;

public class DAOException extends RuntimeException {

    private static final long serialVersionUID = 9164530242774772495L;

    public DAOException( String message ) {
        super( message );
    }

    public DAOException( String message, Throwable cause ) {
        super( message, cause );
    }

    public DAOException( Throwable cause ) {
        super( cause );
    }
}
