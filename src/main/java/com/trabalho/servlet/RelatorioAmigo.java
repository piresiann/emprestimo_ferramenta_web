package com.trabalho.servlet;

import com.trabalho.model.Amigo;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/relatorio/amigo/servlet")
public class RelatorioAmigo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Amigo amigo = new Amigo();
            ArrayList<Amigo> listaAmigos = amigo.getAllAmigos();

            int itensPorPagina = 5;
            int paginaAtual = 1;

            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                paginaAtual = Integer.parseInt(pageParam);
            }

            int inicio = (paginaAtual - 1) * itensPorPagina;
            int fim = Math.min(inicio + itensPorPagina, listaAmigos.size());

            ArrayList<Amigo> amigosPaginados = new ArrayList<>(listaAmigos.subList(inicio, fim));

            int totalPaginas = (int) Math.ceil((double) listaAmigos.size() / itensPorPagina);

            request.setAttribute("listaAmigos", amigosPaginados);
            request.setAttribute("paginaAtual", paginaAtual);
            request.setAttribute("totalPaginas", totalPaginas);

            request.getRequestDispatcher("/jsp/RelatorioAmigo.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Erro ao gerar relatório: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erro inesperado: " + e.getMessage());
        }
    }
}
