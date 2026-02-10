package com.awangelo.db

import groovy.sql.Sql

interface IDatabaseConnection {
    Sql getSql()
    void close()
}

