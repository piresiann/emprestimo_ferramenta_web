package com.trabalho.servlet;

import com.trabalho.model.Amigo;
import com.trabalho.model.Emprestimo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/cadastrar/amigo", "/cadastrar/amigo/servlet"})
public class CadastrarAmigo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/jsp/CadastrarAmigo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nome = request.getParameter("nome");
        String telefone = request.getParameter("telefone");

        Amigo amigo = new Amigo();

        try {
            amigo.insertAmigo(nome, telefone);
            request.getSession().setAttribute("mensagemSucesso", "Amigo cadastrado com sucesso!");
        } catch (SQLException e) {
            request.getSession().setAttribute("mensagemErro", "Erro ao cadastrar amigo.");
        }

        response.sendRedirect(request.getContextPath() + "/cadastrar/amigo");
    }
}
