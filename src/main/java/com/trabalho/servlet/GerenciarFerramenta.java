package com.trabalho.servlet;

import com.trabalho.model.Ferramenta;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/gerenciar/ferramenta", "/gerenciar/ferramenta/servlet"})
public class GerenciarFerramenta extends HttpServlet {

    private final Ferramenta ferramenta = new Ferramenta();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            ArrayList<Ferramenta> listaFerramenta = ferramenta.getFerramentasDisponiveis();

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

            request.getRequestDispatcher("/jsp/GerenciarFerramenta.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Erro ao gerar relatório: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erro inesperado: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String acao = request.getParameter("acao");

        if ("excluir".equals(acao)) {
            try {
                int idExcluir = Integer.parseInt(request.getParameter("idExcluir"));

                boolean removido = false;

                if (idExcluir != 0) {
                    removido = ferramenta.deleteFerramentaById(idExcluir);
                }

                if (removido) {
                    request.getSession().setAttribute("mensagemSucesso", "Ferramenta excluída com sucesso!");
                } else {
                    request.getSession().setAttribute("mensagemErro", "Não foi possível excluir o ferramenta.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("mensagemErro", "Erro ao excluir ferramenta.");
            }

            response.sendRedirect(request.getContextPath() + "/gerenciar/ferramenta");
            return;
        }

        if ("editar".equals(acao)) {
            try {
                int idEditar = Integer.parseInt(request.getParameter("idEditar"));
                String nome = request.getParameter("nome");
                String marca = request.getParameter("marca");
                String aquisicaoStr = request.getParameter("aquisicao");
                BigDecimal valorAquisicao = BigDecimal.ZERO;

                if (aquisicaoStr != null && !aquisicaoStr.isBlank()) {
                    String valorLimpo = aquisicaoStr
                            .replace("R$", "")
                            .replace("\u00A0", "")  // remove no-break space
                            .replace(".", "")
                            .replace(",", ".")
                            .trim();

                    valorAquisicao = new BigDecimal(valorLimpo);
                }

                System.out.println(idEditar);
                Ferramenta existente = ferramenta.getFerramentaById(idEditar);
                System.out.println(existente);
                Ferramenta ferramentaUpdate = new Ferramenta(idEditar, nome, existente.getStatus(), marca, valorAquisicao);

                ferramenta.updateFerramentasBD(ferramentaUpdate);

                request.getSession().setAttribute("mensagemSucesso", "Ferramenta editada com sucesso!");
            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("mensagemErro", "Erro ao editar ferramenta.");
            }

            response.sendRedirect(request.getContextPath() + "/gerenciar/ferramenta");
        }
    }
}