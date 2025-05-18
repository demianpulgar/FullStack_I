package com.FullStack.GestionUsuarios.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            DBConfig.get("db.url"),
            DBConfig.get("db.username"),
            DBConfig.get("db.password")
        );
    }
}
