package com.example.projet_carte.exception;

public enum ErrorCodes {

    APPRENANT_NOT_FOUND(1000),
    APPRENANT_NOT_VALID(1001),
    APPRENANT_ALREADY_IN_USE(1002),
    BAD_CREDENTIALS(2000),

    VISITEUR_NOT_VALID(10000),

    ADMIN_NOT_FOUND(3000),
    ADMIN_NOT_VALID(3001),
    ADMIN_ALREADY_IN_USE(3002),

    SUPERVISEUR_NOT_FOUND(4000),
    SUPERVISEUR_NOT_VALID(4001),
    SUPERVISEUR_ALREADY_IN_USE(4002);

    private int code;

    ErrorCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
