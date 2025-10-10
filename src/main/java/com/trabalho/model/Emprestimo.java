package com.trabalho.model;

import java.util.*;
import com.trabalho.dao.EmprestimoDAO;
import java.sql.SQLException;

public class Emprestimo {

    private int id;
    private String nomeAmigo;
    private String ferramenta;
    private String dataEmprestimo;
    private String dataDevolucao;
    private String codigoFerramenta;
    private final EmprestimoDAO dao;

    public Emprestimo() {
        this.dao = new EmprestimoDAO(); 
    }

    public Emprestimo(int id, String nomeAmigo, String ferramenta, String dataEmprestimo, String dataDevolucao, String codigoFerramenta) {
        this.id = id;
        this.nomeAmigo = nomeAmigo;
        this.ferramenta = ferramenta;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.codigoFerramenta = codigoFerramenta;
        this.dao = new EmprestimoDAO();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeAmigo() {
        return nomeAmigo;
    }

    public void setNomeAmigo(String nomeAmigo) {
        this.nomeAmigo = nomeAmigo;
    }

    public String getFerramenta() {
        return ferramenta;
    }

    public void setFerramenta(String ferramenta) {
        this.ferramenta = ferramenta;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getCodigoFerramenta() {
        return codigoFerramenta;
    }

    public void setCodigoFerramenta(String codigoFerramenta) {
        this.codigoFerramenta = codigoFerramenta;
    }

    public EmprestimoDAO getDao() {
        return dao;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", nomeAmigo='" + nomeAmigo + '\'' +
                ", ferramenta='" + ferramenta + '\'' +
                ", dataEmprestimo='" + dataEmprestimo + '\'' +
                ", dataDevolucao='" + dataDevolucao + '\'' +
                ", codigoFerramenta='" + codigoFerramenta + '\'' +
                '}';
    }

    public ArrayList getEmprestimos() {
        return dao.getEmprestimoList();
    }

    public boolean insertEmprestimo(String nomeAmigo, String ferramenta, String dataEmprestimo, String dataDevolucao, String codigoFerramenta) throws SQLException {
        int id = this.maiorID() + 1;
        Emprestimo emprestimo = new Emprestimo(id, nomeAmigo, ferramenta, dataEmprestimo, dataDevolucao, codigoFerramenta);

        dao.insertEmprestimo(emprestimo);
        return true;
    }

    public boolean deleteEmprestimoBD(int id) {
        dao.deleteEmprestimoById(id);
        return true;
    }

    public boolean updateEmprestimoBD(int id, String nomeAmigo, String ferramenta, String dataEmprestimo, String dataDevolucao, String codigoFerramenta) {
        Emprestimo emprestimo = new Emprestimo(id, nomeAmigo, ferramenta, dataEmprestimo, dataDevolucao, codigoFerramenta);
        dao.updateEmprestimoById(emprestimo);
        return true;
    }

    public Emprestimo getEmprestimoById(int id) {
        dao.getEmprestimoById(id);
        return null;
    }

    public int maiorID() throws SQLException {

        return dao.maiorID();
    }
}