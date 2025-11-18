package com.trabalho.dao;

import com.trabalho.config.ConfigDb;
import com.trabalho.model.Amigo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Classe responsável pelo acesso e manipulação dos dados da entidade {@link Amigo}
 * no banco de dados. Implementa operações CRUD e consultas auxiliares.
 */
public class AmigoDAO {

    /** Lista estática para armazenar amigos recuperados do banco. */
    public static ArrayList<Amigo> amigosList = new ArrayList<>();

    /** Objeto de conexão com o banco de dados. */
    public ConfigDb conexao;

    /**
     * Construtor padrão que inicializa a conexão com o banco.
     */
    public AmigoDAO() {
        this.conexao = new ConfigDb();
    }

    /**
     * Retorna o maior ID presente na tabela amigo.
     *
     * @return maior ID encontrado
     * @throws SQLException em caso de erro na consulta SQL
     */
    public int maiorID() throws SQLException {
        int maiorID = 0;

        try (Statement stmt = conexao.getConexao().createStatement()) {
            ResultSet res = stmt.executeQuery("SELECT MAX(id) id FROM amigo");
            res.next();
            maiorID = res.getInt("id");
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }

        return maiorID;
    }

    /**
     * Recupera todos os amigos cadastrados.
     *
     * @return lista de amigos
     * @throws SQLException em caso de falha no acesso ao banco
     */
    public ArrayList<Amigo> getAllAmigos() throws SQLException {
        amigosList.clear();

        try (Statement stmt = conexao.getConexao().createStatement()) {
            ResultSet res = stmt.executeQuery("SELECT * FROM amigo");

            while (res.next()) {
                int id = res.getInt("id");
                String nome = res.getString("nome");
                String telefone = res.getString("telefone");

                Amigo amigo = new Amigo(id, nome, telefone);
                amigosList.add(amigo);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }

        return amigosList;
    }

    /**
     * Insere um novo amigo no banco de dados.
     *
     * @param amigo objeto contendo os dados do amigo
     * @return true caso seja inserido com sucesso
     * @throws SQLException em caso de erro na operação
     */
    public boolean insertAmigo(Amigo amigo) throws SQLException {
        String sql = "INSERT INTO amigo(id,nome,telefone) VALUES(?,?,?)";

        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, amigo.getId());
            stmt.setString(2, amigo.getNome());
            stmt.setString(3, amigo.getTelefone());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    /**
     * Exclui um amigo pelo ID.
     *
     * @param id identificador a excluir
     * @return true sempre
     */
    public boolean deleteAmigoById(int id) {
        try (Statement stmt = conexao.getConexao().createStatement()) {
            stmt.executeUpdate("DELETE FROM amigo WHERE id = " + id);
        } catch (SQLException erro) {
        }
        return true;
    }

    /**
     * Atualiza os dados de um amigo.
     *
     * @param amigo objeto contendo as informações atualizadas
     * @return true caso executado com sucesso
     * @throws SQLException caso ocorra erro de banco
     */
    public boolean updateAmigoById(Amigo amigo) throws SQLException {
        String sql = "UPDATE amigo set nome = ? , telefone = ? WHERE id = ?";

        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setString(1, amigo.getNome());
            stmt.setString(2, amigo.getTelefone());
            stmt.setInt(3, amigo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    /**
     * Busca um amigo no banco pelo ID.
     *
     * @param id identificador do amigo
     * @return objeto Amigo preenchido
     * @throws SQLException caso ocorra erro de consulta
     */
    public Amigo getAmigoById(int id) throws SQLException {
        Amigo amigo = new Amigo();
        amigo.setId(id);

        try (Statement stmt = conexao.getConexao().createStatement()) {
            ResultSet res = stmt.executeQuery("SELECT * FROM amigo WHERE id = " + id);
            res.next();

            amigo.setNome(res.getString("nome"));
            amigo.setTelefone(res.getString("telefone"));

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }

        return amigo;
    }
}
