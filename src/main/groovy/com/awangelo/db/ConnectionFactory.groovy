package com.awangelo.db

import groovy.sql.Sql

class ConnectionFactory {
    private static Sql sqlInstance

    static Sql getSql() {
        if (sqlInstance == null) {
            String url = System.getenv('LINKETINDER_DB_URL') ?: 'jdbc:postgresql://localhost:5432/linketinder'
            String user = System.getenv('LINKETINDER_DB_USER') ?: 'admin'
            String pass = System.getenv('LINKETINDER_DB_PASS') ?: 'admin'
            String driver = System.getenv('LINKETINDER_DB_DRIVER') ?: 'org.postgresql.Driver'

            sqlInstance = Sql.newInstance(url, user, pass, driver)
        }
        return sqlInstance
    }

    static void close() {
        try {
            sqlInstance?.close()
        } finally {
            sqlInstance = null
        }
    }
}
