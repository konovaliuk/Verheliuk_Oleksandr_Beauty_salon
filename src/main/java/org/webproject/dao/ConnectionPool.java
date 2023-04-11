package org.webproject.dao;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariConfig;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ConnectionPool {
    private static HikariDataSource ds;

    static {
        try{
            initDataSource();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    private static void initDataSource() throws NamingException {
        Context initContext = new InitialContext();
        Context envContext = (Context)initContext.lookup("java:/comp/env");
        ds = (HikariDataSource)envContext.lookup("jdbc/myDs");
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void closePool() {
        ds.close();
    }
}
