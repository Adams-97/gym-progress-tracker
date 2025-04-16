package org.project.db;

interface DataStore {
    int getConnection();
    int write(String sql);

}
