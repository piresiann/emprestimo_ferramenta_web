package com.trabalho.servlet;

import com.trabalho.model.Amigo;
import com.trabalho.model.Ferramenta;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/relatorio/ferramenta/servlet")
public class RelatorioFerramenta extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Ferramenta ferramenta = new Ferramenta();
            ArrayList<Ferramenta> listaFerramenta = ferramenta.getAllFerramentas();

            int itensPorPagina = 5;
            int paginaAtual = 1;

            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                paginaAtual = Integer.parseInt(pageParam);
            }

            int inicio = (paginaAtual - 1) * itensPorPagina;
            int fim = Math.min(inicio + itensPorPagina, listaFerramenta.size());

            ArrayList<Ferramenta> ferramentasPaginadas = new ArrayList<>(listaFerramenta.subList(inicio, fim));

            int totalPaginas = (int) Math.ceil((double) listaFerramenta.size() / itensPorPagina);

            request.setAttribute("listaFerramentas", ferramentasPaginadas);
            request.setAttribute("paginaAtual", paginaAtual);
            request.setAttribute("totalPaginas", totalPaginas);

            request.getRequestDispatcher("/jsp/RelatorioFerramenta.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Erro ao gerar relatório: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erro inesperado: " + e.getMessage());
        }
    }
}
