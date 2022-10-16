package com.github.yuriiyukh.connectionmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import com.github.yuriiyukh.dao.DaoException;

public class ConnectionManager {

    private static final String CONNECTION_FAILED_ERRORMESSAGE = "Connection failed";
    private static final String DRIVER_NOT_FOUND_ERRORMESSAGE = "Driver not found";
    private String url;
    private String userName;
    private String password;
    private String driverUrl;

    public ConnectionManager(String driverUrl, String url, String userName, String password) {
        this.driverUrl = driverUrl;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public Connection getConnection() throws DaoException {
        try {
            Class.forName(driverUrl);
        } catch (ClassNotFoundException e) {
            throw new DaoException(DRIVER_NOT_FOUND_ERRORMESSAGE, e);
        }
        try {
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            throw new DaoException(CONNECTION_FAILED_ERRORMESSAGE, e);

        }
    }
}
