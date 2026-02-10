package com.awangelo.db

import groovy.sql.Sql

class ConnectionFactory {
    private static IDatabaseConnection databaseConnection

    enum DatabaseType {
        POSTGRESQL
    }

    static IDatabaseConnection createConnection(DatabaseType type = DatabaseType.POSTGRESQL) {
        if (databaseConnection == null) {
            synchronized (ConnectionFactory.class) {
                if (databaseConnection == null) {
                    switch (type) {
                        case DatabaseType.POSTGRESQL:
                            databaseConnection = PostgreSQLConnection.getInstance()
                            break
                        default:
                            databaseConnection = PostgreSQLConnection.getInstance()
                    }
                }
            }
        }
        databaseConnection
    }

    static Sql getSql() {
        String dbTypeEnv = System.getenv('LINKETINDER_DB_TYPE')?.toUpperCase()
        DatabaseType type = DatabaseType.POSTGRESQL

        if (dbTypeEnv) {
            try {
                type = DatabaseType.valueOf(dbTypeEnv)
            } catch (IllegalArgumentException ignored) {
                println "Tipo de banco '${dbTypeEnv}' inválido. Usando PostgreSQL por padrão."
            }
        }

        createConnection(type).getSql()
    }

    static void close() {
        databaseConnection?.close()
        databaseConnection = null
    }
}
