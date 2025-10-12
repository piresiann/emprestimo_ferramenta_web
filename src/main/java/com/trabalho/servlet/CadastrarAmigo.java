package com.trabalho.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/cadastrar/amigo/servlet")
public class CadastrarAmigo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");

        System.out.println("Nome: " + nome + ", Email: " + email);
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
