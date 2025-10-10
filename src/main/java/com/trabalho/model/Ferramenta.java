package com.trabalho.model;

import java.math.BigDecimal;
import java.util.*;
import com.trabalho.dao.FerramentaDAO;
import java.sql.SQLException;

public class Ferramenta {

    private int id;
    private String nome;
    private String status;
    private String marca;
    private BigDecimal custoAquisicao;
    private final FerramentaDAO dao;

    public Ferramenta() {
        this.dao = new FerramentaDAO();
    }

    public Ferramenta(int id, String nome, String status, String marca, BigDecimal custoAquisicao) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.marca = marca;
        this.custoAquisicao = custoAquisicao;
        this.dao = new FerramentaDAO();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
     public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public BigDecimal getCustoAquisicao() {
        return custoAquisicao;
    }

    public void setCustoAquisicao(BigDecimal custoAquisicao) {
        this.custoAquisicao = custoAquisicao;
    }

    @Override
    public String toString() {
        return "\n Id: " + this.getId()
                + "\n Nome da Ferramenta: " + this.getNome()
                + "\n Marca: " + this.getMarca()
                + "\n Custo de Aquisição: " + this.getCustoAquisicao()
                + "\n Status: " + this.getStatus();
    }

    public ArrayList getAllFerramentas() throws SQLException {
        return dao.getAllFerramentas();
    }

    public boolean insertFerramenta(String nome, String status, String marca, BigDecimal custoAquisicao) throws SQLException {
        int id = this.maiorID() + 1;
        Ferramenta ferramenta = new Ferramenta(id, nome, status, marca, custoAquisicao);

        dao.insertFerramenta(ferramenta);
        return true;
    }

    public boolean deleteFerramentaById(int id) throws SQLException {
        dao.deleteFerramentaById(id);
        return true;
    }

    public boolean updateFerramentasBD(int id, String nome, String status, String marca, BigDecimal custoAquisicao) throws SQLException {
        Ferramenta ferramenta = new Ferramenta(id, nome, status, marca, custoAquisicao);
        dao.updateferramentaById(ferramenta);
        return true;
    }

    public Ferramenta getFerrametaById(int id) throws SQLException {
        dao.getFerramentaById(id);
        return null;
    }

    public int maiorID() throws SQLException {

        return dao.maiorID();
    }
}