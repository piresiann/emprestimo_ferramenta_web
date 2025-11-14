package com.trabalho.servlet;

import com.trabalho.model.Emprestimo;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet(urlPatterns = {"/gerenciar/emprestimo", "/gerenciar/emprestimo/servlet"})
public class GerenciarEmprestimo extends HttpServlet {

    private final Emprestimo emprestimo = new Emprestimo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            ArrayList<Emprestimo> listaEmprestimo = emprestimo.getEmprestimosAtivos();

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

            request.getRequestDispatcher("/jsp/GerenciarEmprestimo.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Erro ao gerar relatório: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erro inesperado: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String acao = request.getParameter("acao");

        if ("excluir".equals(acao)) {
            try {
                int idExcluir = Integer.parseInt(request.getParameter("idExcluir"));
                Emprestimo existente = emprestimo.getDao().getEmprestimoById(idExcluir);

                if (existente != null && existente.getCodigoFerramenta() != null) {
                    int idFerramenta = Integer.parseInt(existente.getCodigoFerramenta());

                    com.trabalho.dao.FerramentaDAO ferramentaDAO = new com.trabalho.dao.FerramentaDAO();
                    com.trabalho.model.Ferramenta ferramenta = ferramentaDAO.getFerramentaById(idFerramenta);
                    if (ferramenta != null) {
                        ferramenta.setStatus("Disponível");
                        ferramentaDAO.updateferramentaById(ferramenta);
                    }
                }

                boolean removido = emprestimo.getDao().deleteEmprestimoById(idExcluir);

                if (removido) {
                    request.getSession().setAttribute("mensagemSucesso", "Empréstimo excluído com sucesso!");
                } else {
                    request.getSession().setAttribute("mensagemErro", "Não foi possível excluir o empréstimo.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("mensagemErro", "Erro ao excluir empréstimo.");
            }

            response.sendRedirect(request.getContextPath() + "/gerenciar/emprestimo");
            return;
        }

        if ("editar".equals(acao)) {
            try {
                int idEditar = Integer.parseInt(request.getParameter("idEditar"));
                String dataEmprestimo = request.getParameter("dataEmprestimoEditar");
                String dataDevolucao = request.getParameter("dataDevolucaoEditar");

                Emprestimo existente = emprestimo.getDao().getEmprestimoById(idEditar);

                emprestimo.updateEmprestimoBD(
                        idEditar,
                        existente.getNomeAmigo(),
                        existente.getFerramenta(),
                        dataEmprestimo,
                        dataDevolucao,
                        existente.getStatus(),
                        existente.getCodigoFerramenta()
                );

                request.getSession().setAttribute("mensagemSucesso", "Empréstimo editado com sucesso!");
            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("mensagemErro", "Erro ao editar empréstimo.");
            }

            response.sendRedirect(request.getContextPath() + "/gerenciar/emprestimo");
        }
    }
}