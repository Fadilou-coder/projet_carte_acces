package com.example.projet_carte.exception;

public enum ErrorCodes {

    APPRENANT_NOT_FOUND(1000),
    APPRENANT_NOT_VALID(1001),
    APPRENANT_ALREADY_IN_USE(1002),
    BAD_CREDENTIALS(2000),

    VISITEUR_NOT_VALID(10000),
    ;
    private int code;

    ErrorCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
