package com.medical.forms;

public class FormValidationException extends Exception {

    private static final long serialVersionUID = 4140341618040045218L;

    public FormValidationException( String message ) {
        super( message );
    }
}