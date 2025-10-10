package com.trabalho.dao;

import com.trabalho.config.ConfigDb;
import com.trabalho.model.Amigo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AmigoDAO {

    public static ArrayList<Amigo> amigosList = new ArrayList<>();
    public ConfigDb conexao;

    public AmigoDAO() {
        this.conexao = new ConfigDb();
    }

    public int maiorID() throws SQLException {
        int maiorID = 0;
        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                ResultSet res = stmt.executeQuery("SELECT MAX(id) id FROM amigo");
                res.next();
                maiorID = res.getInt("id");
            }

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }

        return maiorID;
    }

    public ArrayList getAllAmigos() throws SQLException {

        amigosList.clear();

        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                ResultSet res = stmt.executeQuery("SELECT * FROM amigo");
                while (res.next()) {

                    int id = res.getInt("id");
                    String nome = res.getString("nome");
                    String telefone = res.getString("telefone");

                    Amigo amigo = new Amigo(id, nome, telefone);

                    amigosList.add(amigo);
                }
            }

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }

        return amigosList;
    }

    public boolean InsertAmigo(Amigo amigo) throws SQLException {
        String sql = "INSERT INTO amigo(id,nome,telefone) VALUES(?,?,?)";

        try {
            try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
                stmt.setInt(1, amigo.getId());
                stmt.setString(2, amigo.getNome());
                stmt.setString(3, amigo.getTelefone());

                stmt.execute();
            }

            return true;

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    public boolean DeleteAmigoById(int id) {
        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                stmt.executeUpdate("DELETE FROM amigo WHERE id = " + id);
            }

        } catch (SQLException erro) {
        }

        return true;
    }

    public boolean UpdateAmigoById(Amigo amigo) throws SQLException {

        String sql = "UPDATE amigo set nome = ? , telefone = ? WHERE id = ?";

        try {
            try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
                stmt.setString(1, amigo.getNome());
                stmt.setString(2, amigo.getTelefone());
                stmt.setInt(3, amigo.getId());

                stmt.execute();
            }

            return true;

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    public Amigo getAmigoById(int id) throws SQLException {

        Amigo amigo = new Amigo();
        amigo.setId(id);

        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                ResultSet res = stmt.executeQuery("SELECT * FROM amigo WHERE id = " + id);
                res.next();

                amigo.setNome(res.getString("nome"));
                amigo.setTelefone(res.getString("telefone"));
            }

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return amigo;
    }
}