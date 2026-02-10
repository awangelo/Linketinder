package com.awangelo.db

import groovy.sql.Sql

class PostgreSQLConnection implements IDatabaseConnection {
    private static PostgreSQLConnection instance
    private Sql sqlInstance

    private PostgreSQLConnection() {}

    static PostgreSQLConnection getInstance() {
        if (instance == null) {
            synchronized (PostgreSQLConnection.class) {
                if (instance == null) {
                    instance = new PostgreSQLConnection()
                }
            }
        }
        instance
    }

    @Override
    Sql getSql() {
        if (sqlInstance == null) {
            String url = System.getenv('LINKETINDER_DB_URL') ?: 'jdbc:postgresql://localhost:5432/linketinder'
            String user = System.getenv('LINKETINDER_DB_USER') ?: 'admin'
            String pass = System.getenv('LINKETINDER_DB_PASS') ?: 'admin'
            String driver = 'org.postgresql.Driver'

            sqlInstance = Sql.newInstance(url, user, pass, driver)
        }
        sqlInstance
    }

    @Override
    void close() {
        try {
            sqlInstance?.close()
        } finally {
            sqlInstance = null
        }
    }
}

