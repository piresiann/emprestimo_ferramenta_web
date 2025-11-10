package com.trabalho.dao;

import com.trabalho.config.ConfigDb;
import com.trabalho.model.Emprestimo;
import com.trabalho.model.StatusEmprestimo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EmprestimoDAO {

    public static ArrayList<Emprestimo> emprestimoList = new ArrayList<>();
    public ConfigDb conexao;

    public EmprestimoDAO() {
        this.conexao = new ConfigDb();
    }

    public int maiorID() throws SQLException {

        int maiorID = 0;
        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                ResultSet res = stmt.executeQuery("SELECT MAX(id) id FROM emprestimo");
                res.next();
                maiorID = res.getInt("id");
            }

        } catch (SQLException ex) {
        }

        return maiorID;
    }

    public ArrayList<Emprestimo> getEmprestimoList(boolean apenasAtivos) {
        emprestimoList.clear();

        try (Statement stmt = conexao.getConexao().createStatement()) {

            String sql = """
            SELECT id, nome_amigo, 
                   REGEXP_REPLACE(ferramenta, '[0-9]', '') AS ferramenta, 
                   DATE_FORMAT(data_emprestimo, '%d/%m/%Y') AS data_emprestimo, 
                   DATE_FORMAT(data_devolucao, '%d/%m/%Y') AS data_devolucao,
                   status,
                   codigo_ferramenta
            FROM emprestimo
        """;

            if (apenasAtivos) {
                sql += " WHERE status = 'Ativo'";
            }

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                int id = res.getInt("id");
                String nomeAmigo = res.getString("nome_amigo");
                String ferramenta = res.getString("ferramenta");
                String dataEmprestimoStr = res.getString("data_emprestimo");
                String dataDevolucaoStr = res.getString("data_devolucao");
                StatusEmprestimo status = StatusEmprestimo.statusEnum(res.getString("status"));
                String codFerramenta = res.getString("codigo_ferramenta");

                SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                Date dataEmprestimo = inputDateFormat.parse(dataEmprestimoStr);
                Date dataDevolucao = inputDateFormat.parse(dataDevolucaoStr);

                String dataEmprestimoFormatada = outputDateFormat.format(dataEmprestimo);
                String dataDevolucaoFormatada = outputDateFormat.format(dataDevolucao);

                Emprestimo emprestimo = new Emprestimo(
                        id,
                        nomeAmigo,
                        ferramenta,
                        dataEmprestimoFormatada,
                        dataDevolucaoFormatada,
                        status,
                        codFerramenta
                );

                emprestimoList.add(emprestimo);
            }

        } catch (SQLException | ParseException ex) {
            ex.printStackTrace();
        }

        return emprestimoList;
    }

    public boolean insertEmprestimo(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimo (id,nome_amigo,ferramenta,data_emprestimo,data_devolucao,status,codigo_ferramenta) VALUES(?,?,?,NOW(),?,?,?)";

        try {
            try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
                stmt.setInt(1, emprestimo.getId());
                stmt.setString(2, emprestimo.getNomeAmigo());
                stmt.setString(3, emprestimo.getFerramenta());
                stmt.setString(4, emprestimo.getDataDevolucao());
                stmt.setString(5, emprestimo.getStatus().getDescricao());
                stmt.setString(6, emprestimo.getCodigoFerramenta());

                stmt.execute();
            }

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

    public boolean deleteEmprestimoById(int id) {
        String sql = "UPDATE emprestimo SET status = 'Devolvido' WHERE id = " + id;
        try (Statement stmt = conexao.getConexao().createStatement()) {
            int linhasAfetadas = stmt.executeUpdate(sql);
            return linhasAfetadas > 0;
        } catch (SQLException erro) {
            erro.printStackTrace();
            return false;
        }
    }

    public boolean updateEmprestimoById(Emprestimo emprestimo) {

        String sql = "UPDATE emprestimo set nome_amigo = ? , ferramenta = ? , data_emprestimo = ? , data_devolucao = ?  WHERE id = ?";

        try {
            try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
                stmt.setString(1, emprestimo.getNomeAmigo());
                stmt.setString(2, emprestimo.getFerramenta());
                stmt.setString(3, emprestimo.getDataEmprestimo());
                stmt.setString(4, emprestimo.getDataDevolucao());
                stmt.setInt(5, emprestimo.getId());

                stmt.execute();
            }

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    public Emprestimo getEmprestimoById(int id) {

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId(id);

        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                ResultSet res = stmt.executeQuery("SELECT * FROM emprestimo WHERE id = " + id);
                res.next();

                emprestimo.setNomeAmigo(res.getString("nome_amigo"));
                emprestimo.setFerramenta(res.getString("ferramenta"));
                emprestimo.setDataEmprestimo(res.getString("data_emprestimo"));
                emprestimo.setDataDevolucao(res.getString("data_devolucao"));
                emprestimo.setCodigoFerramenta(res.getString("codigo_ferramenta"));
            }

        } catch (SQLException erro) {
        }
        return emprestimo;
    }
}