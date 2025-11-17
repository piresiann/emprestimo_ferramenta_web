package com.trabalho.dao;

import com.trabalho.config.ConfigDb;
import com.trabalho.model.Ferramenta;
import com.trabalho.model.StatusFerramenta;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FerramentaDAO {

    public static ArrayList<Ferramenta> ferramentasList = new ArrayList<>();
    public ConfigDb conexao;

    public FerramentaDAO() {
        this.conexao = new ConfigDb();
    }

    public int maiorID() throws SQLException {

        int maiorID = 0;
        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                ResultSet res = stmt.executeQuery("SELECT MAX(id) id FROM ferramenta");
                res.next();
                maiorID = res.getInt("id");
            }

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }

        return maiorID;
    }

    public ArrayList getAllFerramentas(boolean apenasDiponiveis) throws SQLException {

        ferramentasList.clear();

        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                String sql = "SELECT * FROM ferramenta";

                if (apenasDiponiveis) {
                    sql += " WHERE status = 'Disponível'";
                }

                ResultSet res = stmt.executeQuery(sql);
                while (res.next()) {

                    int id = res.getInt("id");
                    String nome = res.getString("nome");
                    StatusFerramenta status = StatusFerramenta.statusEnum(res.getString("status"));
                    String marca = res.getString("marca");
                    BigDecimal custoAquisicao = res.getBigDecimal("custo_aquisicao");

                    Ferramenta ferramenta = new Ferramenta(id, nome, status, marca, custoAquisicao);

                    ferramentasList.add(ferramenta);
                }
            }

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }

        return ferramentasList;
    }

    public ArrayList<Ferramenta> getFerramentasDisponiveis() throws SQLException {
        ArrayList<Ferramenta> disponiveis = new ArrayList<>();

        String sql = "SELECT * FROM ferramenta WHERE status = 'Disponível'";

        try (Statement stmt = conexao.getConexao().createStatement();
             ResultSet res = stmt.executeQuery(sql)) {

            while (res.next()) {
                int id = res.getInt("id");
                String nome = res.getString("nome");
                StatusFerramenta status = StatusFerramenta.statusEnum(res.getString("status"));
                String marca = res.getString("marca");
                BigDecimal custoAquisicao = res.getBigDecimal("custo_aquisicao");

                Ferramenta ferramenta = new Ferramenta(id, nome, status, marca, custoAquisicao);
                disponiveis.add(ferramenta);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }

        return disponiveis;
    }

    public boolean insertFerramenta(Ferramenta ferramenta) {
        String sql = "INSERT INTO ferramenta(id,nome,status,marca,custo_aquisicao) VALUES(?,?,?,?,?)";

        try {
            try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
                stmt.setInt(1, ferramenta.getId());
                stmt.setString(2, ferramenta.getNome());
                stmt.setString(3, ferramenta.getStatus().getDescricao());
                stmt.setString(4, ferramenta.getMarca());
                stmt.setBigDecimal(5, ferramenta.getCustoAquisicao());

                stmt.execute();
            }

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

    public boolean deleteFerramentaById(int id) throws SQLException {
        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                stmt.executeUpdate("DELETE FROM ferramenta WHERE id = " + id);
            }

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }

        return true;
    }

    public boolean updateferramentaById(Ferramenta ferramenta) throws SQLException {

        String sql = "UPDATE ferramenta set nome = ?, status = ? ,marca = ? ,custo_aquisicao = ? WHERE id = ?";

        try {
            try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
                stmt.setString(1, ferramenta.getNome());
                stmt.setString(2, ferramenta.getStatus().getDescricao());
                stmt.setString(3, ferramenta.getMarca());
                stmt.setBigDecimal(4, ferramenta.getCustoAquisicao());
                stmt.setInt(5, ferramenta.getId());

                stmt.execute();
            }

            return true;

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    public Ferramenta getFerramentaById(int id) throws SQLException {

        Ferramenta ferramenta = new Ferramenta();

        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                ResultSet res = stmt.executeQuery("SELECT * FROM ferramenta WHERE id = " + id);
                res.next();

                ferramenta.setId(res.getInt("id"));
                ferramenta.setNome(res.getString("nome"));
                ferramenta.setStatus(StatusFerramenta.statusEnum(res.getString("status")));
                ferramenta.setMarca(res.getString("marca"));
                ferramenta.setCustoAquisicao(res.getBigDecimal("custo_aquisicao"));
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return ferramenta;
    }
}