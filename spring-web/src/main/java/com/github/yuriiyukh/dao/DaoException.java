package com.github.yuriiyukh.dao;

public class DaoException extends Exception {

    private static final long serialVersionUID = 8316637156921080230L;

    public DaoException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public DaoException(String errorMessage) {
        super(errorMessage);
    }
}
