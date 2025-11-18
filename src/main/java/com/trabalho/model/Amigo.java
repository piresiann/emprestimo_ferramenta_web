package com.trabalho.model;

import com.trabalho.dao.AmigoDAO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Representa um amigo cadastrado no sistema, contendo dados pessoais e
 * métodos de acesso ao banco de dados por meio da classe {@link AmigoDAO}.
 * Esta classe encapsula operações relacionadas ao gerenciamento de amigos.
 */
public class Amigo {

    /** Identificador único do amigo. */
    private int id;

    /** Nome completo do amigo. */
    private String nome;

    /** Telefone de contato do amigo. */
    private String telefone;

    /** Instância do DAO responsável pelas operações de banco de dados. */
    private final AmigoDAO dao;

    /**
     * Construtor padrão que inicializa a instância do DAO.
     */
    public Amigo() {
        this.dao = new AmigoDAO();
    }

    /**
     * Construtor completo utilizado para inicializar todas as propriedades do objeto.
     *
     * @param id identificador do amigo.
     * @param nome nome do amigo.
     * @param telefone telefone do amigo.
     */
    public Amigo(int id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.dao = new AmigoDAO();
    }

    /**
     * Retorna o ID do amigo.
     *
     * @return id do amigo.
     */
    public int getId() {
        return id;
    }

    /**
     * Atualiza o ID do amigo.
     *
     * @param id novo ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome do amigo.
     *
     * @return nome do amigo.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Atualiza o nome do amigo.
     *
     * @param nome novo nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o telefone do amigo.
     *
     * @return telefone do amigo.
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Atualiza o telefone do amigo.
     *
     * @param telefone novo telefone.
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Retorna uma representação textual formatada do objeto.
     *
     * @return string com as informações básicas do amigo.
     */
    @Override
    public String toString() {
        return "\n Id do Amigo: " + this.getId()
                + "\n Nome do Amigo: " + this.getNome()
                + "\n Telefone: " + this.getTelefone();
    }

    /**
     * Obtém todos os amigos cadastrados no banco de dados.
     *
     * @return lista de amigos.
     * @throws SQLException caso haja erro na consulta.
     */
    public ArrayList getAllAmigos() throws SQLException {
        return dao.getAllAmigos();
    }

    /**
     * Insere um novo amigo no banco de dados.
     *
     * @param nome nome do amigo.
     * @param telefone telefone do amigo.
     * @return true se inserido com sucesso.
     * @throws SQLException caso ocorra falha ao inserir.
     */
    public boolean insertAmigo(String nome, String telefone) throws SQLException {
        int id = this.maiorID() + 1;
        Amigo amigo = new Amigo(id, nome, telefone);

        dao.insertAmigo(amigo);
        return true;
    }

    /**
     * Remove um amigo pelo ID.
     *
     * @param id identificador do amigo.
     * @return true se removido com sucesso.
     */
    public boolean deleteAmigoById(int id) {
        dao.deleteAmigoById(id);
        return true;
    }

    /**
     * Atualiza os dados de um amigo já existente.
     *
     * @param id identificador do amigo.
     * @param nome nome atualizado.
     * @param telefone telefone atualizado.
     * @return true se atualizado com sucesso.
     * @throws SQLException caso ocorra erro na operação.
     */
    public boolean updateAmigoById(int id, String nome, String telefone) throws SQLException {
        Amigo amigo = new Amigo(id, nome, telefone);
        dao.updateAmigoById(amigo);
        return true;
    }

    /**
     * Busca um amigo pelo ID.
     *
     * ⚠ Observação: este método sempre retorna null (provavelmente bug original).
     *
     * @param id identificador do amigo.
     * @return amigo encontrado ou null.
     * @throws SQLException caso haja erro na consulta.
     */
    public Amigo getAmigoById(int id) throws SQLException {
        return dao.getAmigoById(id);
    }

    /**
     * Retorna o maior ID atualmente registrado na tabela.
     *
     * @return maior ID.
     * @throws SQLException caso ocorra falha na consulta.
     */
    public int maiorID() throws SQLException {
        return dao.maiorID();
    }
}
