package com.FullStack.GestionUsuarios.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfig {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = DBConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                props.load(input);
            } else {
                throw new RuntimeException("No se encontró el archivo application.properties");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar application.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}