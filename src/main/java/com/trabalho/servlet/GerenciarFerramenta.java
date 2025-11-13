package com.trabalho.servlet;

import com.trabalho.model.Ferramenta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/gerenciar/ferramenta", "/gerenciar/ferramenta/servlet"})
public class GerenciarFerramenta extends HttpServlet {

    private final Ferramenta ferramenta = new Ferramenta();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Ferramenta ferramenta = new Ferramenta();
            List<Ferramenta> listaFerramentas = ferramenta.getFerramentasDisponivel();

            int itensPorPagina = 5;
            int paginaAtual = 1;

            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                paginaAtual = Integer.parseInt(pageParam);
            }

            int inicio = (paginaAtual - 1) * itensPorPagina;
            int fim = Math.min(inicio + itensPorPagina, listaFerramentas.size());

            List<Ferramenta> ferramentasPaginadas = new ArrayList<>(listaFerramentas.subList(inicio, fim));

            int totalPaginas = (int) Math.ceil((double) listaFerramentas.size() / itensPorPagina);

            request.setAttribute("listaFerramentas", ferramentasPaginadas);
            request.setAttribute("paginaAtual", paginaAtual);
            request.setAttribute("totalPaginas", totalPaginas);

            request.getRequestDispatcher("/jsp/GerenciarFerramenta.jsp").forward(request, response);

        } catch (SQLException | ServletException e) {
            e.printStackTrace();
            response.getWriter().println("Erro ao listar ferramentas: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String acao = request.getParameter("acao");

        if ("excluir".equals(acao)) {
            try {
                int idExcluir = Integer.parseInt(request.getParameter("idExcluir"));

                Ferramenta ferramentaUpdate = ferramenta.getFerrametaById(idExcluir);
                ferramentaUpdate.setStatus("Indisponível");

                ferramentaUpdate.updateFerramentasBD(ferramentaUpdate.getId(), ferramentaUpdate.getNome(), ferramentaUpdate.getStatus(), ferramentaUpdate.getMarca(), ferramentaUpdate.getCustoAquisicao());

                request.getSession().setAttribute("mensagemSucesso", "Ferramenta excluída com sucesso!");
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
                String nome = request.getParameter("nomeEditar");
                String marca = request.getParameter("marcaEditar");
                System.out.println(request.getParameter("marcaEditar"));
                System.out.println(request.getParameter("aquisicaoEditar"));
                BigDecimal valor = BigDecimal.valueOf(Long.parseLong(request.getParameter("aquisicaoEditar")));
                String status = "Disponível";

                Ferramenta ferramentasadasdasd = new Ferramenta(idEditar, nome, status, marca, valor);
                System.out.println(ferramentasadasdasd);
                ferramenta.updateFerramentasBD(idEditar, nome, status, marca, valor);

                request.getSession().setAttribute("mensagemSucesso", "Ferramenta editada com sucesso!");
            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("mensagemErro", "Erro ao editar ferramenta.");
            }

            response.sendRedirect(request.getContextPath() + "/gerenciar/ferramenta");
        }
    }
}
