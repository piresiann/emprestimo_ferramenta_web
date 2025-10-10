package com.trabalho.model;

import java.util.*;
import com.trabalho.dao.AmigoDAO;
import java.sql.SQLException;

public class Amigo {

    private int id;
    private String nome;
    private String telefone;

    private final AmigoDAO dao;

    public Amigo() {
        this.dao = new AmigoDAO(); 
    }

    public Amigo(int id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.dao = new AmigoDAO();
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "\n Id do Amigo: " + this.getId()
                + "\n Nome do Amigo: " + this.getNome()
                + "\n Telefone: " + this.getTelefone();
    }

    public ArrayList getAllAmigos() throws SQLException {
        return dao.getAllAmigos();
    }

    public boolean InsertAmigo(String nome, String telefone) throws SQLException {
        int id = this.maiorID() + 1;
        Amigo amigo = new Amigo(id, nome, telefone);

        dao.InsertAmigo(amigo);
        return true;
    }

    public boolean DeleteAmigoById(int id) {
        dao.DeleteAmigoById(id);
        return true;
    }

    public boolean UpdateAmigoById(int id, String nome, String telefone) throws SQLException {
        Amigo amigo = new Amigo(id, nome, telefone);
        dao.UpdateAmigoById(amigo);
        return true;
    }

    public Amigo getAmigoById(int id) throws SQLException {
        dao.getAmigoById(id);
        return null;
    }

    public int maiorID() throws SQLException {
        return dao.maiorID();
    }
}