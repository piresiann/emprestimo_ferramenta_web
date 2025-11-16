package com.trabalho.servlet;

import com.trabalho.model.Emprestimo;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/relatorio/emprestimo")
public class RelatorioEmprestimo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Emprestimo emprestimo = new Emprestimo();
            ArrayList<Emprestimo> listaEmprestimo = emprestimo.getAllEmprestimos();

            int itensPorPagina = 5;
            int paginaAtual = 1;

            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                paginaAtual = Integer.parseInt(pageParam);
            }

            int inicio = (paginaAtual - 1) * itensPorPagina;
            int fim = Math.min(inicio + itensPorPagina, listaEmprestimo.size());

            ArrayList<Emprestimo> emprestimoPaginados = new ArrayList<>(listaEmprestimo.subList(inicio, fim));

            int totalPaginas = (int) Math.ceil((double) listaEmprestimo.size() / itensPorPagina);

            request.setAttribute("listaEmprestimos", emprestimoPaginados);
            request.setAttribute("paginaAtual", paginaAtual);
            request.setAttribute("totalPaginas", totalPaginas);

            request.getRequestDispatcher("/jsp/RelatorioEmprestimo.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Erro ao gerar relatório: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erro inesperado: " + e.getMessage());
        }
    }
}
