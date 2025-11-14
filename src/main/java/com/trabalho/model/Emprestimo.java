package com.trabalho.model;

import com.trabalho.dao.EmprestimoDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Emprestimo {

    private int id;
    private String nomeAmigo;
    private String ferramenta;
    private String dataEmprestimo;
    private String dataDevolucao;
    private int codigoFerramenta;
    private StatusEmprestimo status;
    private final EmprestimoDAO dao;

    public Emprestimo() {
        this.dao = new EmprestimoDAO();
    }

    public Emprestimo(int id, String nomeAmigo, String ferramenta, String dataEmprestimo, String dataDevolucao, StatusEmprestimo status, int codigoFerramenta) {
        this.id = id;
        this.nomeAmigo = nomeAmigo;
        this.ferramenta = ferramenta;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.status = status;
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

    public int getCodigoFerramenta() {
        return codigoFerramenta;
    }

    public void setCodigoFerramenta(int codigoFerramenta) {
        this.codigoFerramenta = codigoFerramenta;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
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
                ", status='" + status + '\'' +
                ", codigoFerramenta='" + codigoFerramenta + '\'' +
                '}';
    }

    public ArrayList<Emprestimo> getAllEmprestimos() throws SQLException {
        return dao.getEmprestimoList(false);
    }

    public ArrayList<Emprestimo> getEmprestimosAtivos() throws SQLException {
        return dao.getEmprestimoList(true);
    }

    public boolean insertEmprestimo(String nomeAmigo, String ferramenta, String dataEmprestimo, String dataDevolucao, StatusEmprestimo status, int codigoFerramenta) throws SQLException {
        int id = this.maiorID() + 1;
        String hoje = LocalDate.now().toString();
        Emprestimo emprestimo = new Emprestimo(id, nomeAmigo, ferramenta, hoje, dataDevolucao, status,codigoFerramenta);

        dao.insertEmprestimo(emprestimo);
        return true;
    }

    public boolean updateStatus(int id) {
        dao.updateStatus(id);
        return true;
    }

    public boolean updateEmprestimoBD(int id, String nomeAmigo, String ferramenta, String dataEmprestimo, String dataDevolucao, StatusEmprestimo status, int codigoFerramenta) {
        Emprestimo emprestimo = new Emprestimo(id, nomeAmigo, ferramenta, dataEmprestimo, dataDevolucao, status, codigoFerramenta);
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