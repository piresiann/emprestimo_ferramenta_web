package com.trabalho.Config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConfigDb {

    private static Connection connection = null;

    public static Connection getConexao() {
        if (connection != null) {
            return connection;
        }

        try (InputStream input = ConfigDb.class.getClassLoader().getResourceAsStream("application.properties")) {

            if (input == null) {
                System.out.println("Não foi possível encontrar o arquivo application.properties");
                return null;
            }

            Properties prop = new Properties();
            prop.load(input);

            String driver = prop.getProperty("db.driver");
            Class.forName(driver);

            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");

            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                System.out.println("Status: Conectado!");
            } else {
                System.out.println("Status: NÃO CONECTADO!");
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler application.properties: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }

        return connection;
    }
}
