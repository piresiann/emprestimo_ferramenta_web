package com.trabalho.servlet;

import com.trabalho.model.Amigo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/gerenciar/amigo", "/gerenciar/amigo/servlet"})
public class GerenciarAmigo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Amigo amigo = new Amigo();
            ArrayList<Amigo> listaAmigos = amigo.getAllAmigos();

            int itensPorPagina = 5;
            int totalAmigos = listaAmigos.size();
            int totalPaginas = (int) Math.ceil((double) totalAmigos / itensPorPagina);

            int paginaAtual = 1;
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                try {
                    paginaAtual = Integer.parseInt(pageParam);
                    if (paginaAtual < 1) paginaAtual = 1;
                    if (paginaAtual > totalPaginas && totalPaginas > 0) paginaAtual = totalPaginas;
                } catch (NumberFormatException e) {
                    paginaAtual = 1;
                }
            } else if (totalAmigos > 0) {
                paginaAtual = 1;
            }

            int inicio = (paginaAtual - 1) * itensPorPagina;
            int fim = Math.min(inicio + itensPorPagina, totalAmigos);

            ArrayList<Amigo> amigosPaginados = new ArrayList<>();

            if (totalAmigos > 0 && inicio < fim && inicio < totalAmigos) {
                amigosPaginados = new ArrayList<>(listaAmigos.subList(inicio, fim));
            }

            request.setAttribute("listaAmigos", amigosPaginados);
            request.setAttribute("paginaAtual", paginaAtual);
            request.setAttribute("totalPaginas", totalPaginas);

            request.getRequestDispatcher("/jsp/GerenciarAmigo.jsp").forward(request, response);
        } catch (SQLException | ServletException e) {
            e.printStackTrace();
            response.getWriter().println("Erro: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String pageToRedirect = request.getParameter("page");

        if (pageToRedirect == null || pageToRedirect.isEmpty()) {
            pageToRedirect = "1";
        }

        try {
            Amigo amigo = new Amigo();

            if ("excluir".equals(action)) {

                int id = Integer.parseInt(request.getParameter("id"));
                amigo.deleteAmigoById(id);
                request.getSession().setAttribute("mensagemSucesso", "Amigo excluído com sucesso.");

            } else if ("editar".equals(action)) {

                int id = Integer.parseInt(request.getParameter("id"));
                String nome = request.getParameter("nome");
                String telefone = request.getParameter("telefone");


                if (nome.matches(".*\\d.*")) {
                    request.getSession().setAttribute("mensagemErro", "Erro ao editar: O nome não pode conter números.");
                }


                if (nome != null && !nome.trim().isEmpty() && telefone != null && !telefone.trim().isEmpty() && !nome.matches(".*\\d.*")) {
                    amigo.updateAmigoById(id, nome, telefone);
                    request.getSession().setAttribute("mensagemSucesso", "Amigo atualizado com sucesso.");
                } else if (!nome.matches(".*\\d.*")) {
                    request.getSession().setAttribute("mensagemErro", "Erro ao editar: Nome e Telefone são obrigatórios.");
                }
            }

            response.sendRedirect(request.getContextPath() + "/gerenciar/amigo/servlet?page=" + pageToRedirect);
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagemErro", "Erro interno ao processar ação: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/gerenciar/amigo/servlet?page=" + pageToRedirect);
        }
    }
}