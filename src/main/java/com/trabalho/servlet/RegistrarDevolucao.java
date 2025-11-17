package com.trabalho.servlet;

import com.trabalho.dao.EmprestimoDAO;
import com.trabalho.dao.FerramentaDAO;
import com.trabalho.model.Emprestimo;
import com.trabalho.model.Ferramenta;
import com.trabalho.model.StatusFerramenta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/registrar/devolucao", "/registrar/devolucao/servlet"})
public class RegistrarDevolucao extends HttpServlet {

    private final Emprestimo emprestimo = new Emprestimo();
    private final Ferramenta ferramenta = new Ferramenta();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            ArrayList<Emprestimo> emprestimosAtivos = emprestimo.getEmprestimosAtivos();

            int itensPorPagina = 5;
            int paginaAtual = 1;

            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                try {
                    paginaAtual = Integer.parseInt(pageParam);
                } catch (NumberFormatException ignored) {
                    paginaAtual = 1;
                }
            }

            int totalRegistros = emprestimosAtivos.size();
            int totalPaginas = (int) Math.ceil((double) totalRegistros / itensPorPagina);
            if (totalPaginas == 0) {
                totalPaginas = 1;
            }
            if (paginaAtual < 1) {
                paginaAtual = 1;
            } else if (paginaAtual > totalPaginas) {
                paginaAtual = totalPaginas;
            }

            int inicio = (paginaAtual - 1) * itensPorPagina;
            int fim = Math.min(inicio + itensPorPagina, totalRegistros);

            ArrayList<Emprestimo> pagina = new ArrayList<>(emprestimosAtivos.subList(inicio, fim));

            request.setAttribute("listaEmprestimos", pagina);
            request.setAttribute("paginaAtual", paginaAtual);
            request.setAttribute("totalPaginas", totalPaginas);

            request.getRequestDispatcher("/jsp/RegistrarDevolucao.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Erro ao carregar empréstimos para devolução: " + e.getMessage());
        } catch (ServletException e) {
            e.printStackTrace();
            throw new IOException(e);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erro inesperado ao carregar empréstimos para devolução.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("idEmprestimo");
        try {
            int idEmprestimo = Integer.parseInt(idParam);

            Emprestimo emp = emprestimo.getEmprestimoById(idEmprestimo);

            boolean statusAtualizado = emprestimo.updateStatus(idEmprestimo);

            if (!statusAtualizado) {
                request.getSession().setAttribute("mensagemErro", "Erro ao atualizar status da devolução.");
                response.sendRedirect(request.getContextPath() + "/registrar/devolucao");
                return;
            }
            System.out.println(emp);
            System.out.println(emp.getCodigoFerramenta());
            if (emp != null && emp.getCodigoFerramenta() != null) {
                System.out.println("entrou");
                int idFerramenta = Integer.parseInt(emp.getCodigoFerramenta());
                System.out.println("idFerramenta: " + idFerramenta);
                Ferramenta ferramentaUpdate = ferramenta.getFerramentaById(idFerramenta);
                System.out.println(ferramentaUpdate);
                if (ferramentaUpdate != null) {
                    ferramentaUpdate.setStatus(StatusFerramenta.DISPONIVEL);
                    System.out.println(ferramentaUpdate.getStatus());
                    ferramenta.updateFerramentasBD(ferramentaUpdate);
                }
            }

            request.getSession().setAttribute("mensagemSucesso", "Devolução registrada com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagemErro", "Erro ao registrar devolução.");
        }

        response.sendRedirect(request.getContextPath() + "/registrar/devolucao");
    }
}