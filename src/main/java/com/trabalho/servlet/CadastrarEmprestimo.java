package com.trabalho.servlet;

import com.trabalho.dao.AmigoDAO;
import com.trabalho.dao.EmprestimoDAO;
import com.trabalho.dao.FerramentaDAO;
import com.trabalho.model.Amigo;
import com.trabalho.model.Emprestimo;
import com.trabalho.model.Ferramenta;

import com.trabalho.model.StatusEmprestimo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/cadastrar/emprestimo", "/cadastrar/emprestimo/servlet"})
public class CadastrarEmprestimo extends HttpServlet {

    private final AmigoDAO amigoDAO = new AmigoDAO();
    private final FerramentaDAO ferramentaDAO = new FerramentaDAO();
    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ArrayList<Amigo> amigos = amigoDAO.getAllAmigos();
            ArrayList<Ferramenta> ferramentas = ferramentaDAO.getFerramentasDisponiveis(); // apenas disponíveis
            request.setAttribute("amigos", amigos);
            request.setAttribute("ferramentas", ferramentas);
        } catch (SQLException e) {
            request.getSession().setAttribute("mensagemErro", "Erro ao carregar dados: " + e.getMessage());
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Emprestimo.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            int amigoId = Integer.parseInt(request.getParameter("amigo_id"));
            int ferramentaId = Integer.parseInt(request.getParameter("ferramenta_id"));
            String dataEmprestimo = request.getParameter("data_emprestimo");
            String dataDevolucao = request.getParameter("data_devolucao");

            Amigo amigo = amigoDAO.getAmigoById(amigoId);
            Ferramenta ferramenta = ferramentaDAO.getFerramentaById(ferramentaId);

            if (amigo == null || ferramenta == null) {
                throw new Exception("Amigo ou Ferramenta não encontrado.");
            }

            if (!"Disponível".equalsIgnoreCase(ferramenta.getStatus())) {
                throw new Exception("A ferramenta selecionada não está disponível.");
            }

            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setId(emprestimoDAO.maiorID() + 1);
            emprestimo.setNomeAmigo(amigo.getNome());
            emprestimo.setFerramenta(ferramenta.getNome());
            emprestimo.setDataEmprestimo(dataEmprestimo);
            emprestimo.setDataDevolucao(dataDevolucao);
            emprestimo.setCodigoFerramenta(ferramentaId);
            emprestimo.setStatus(StatusEmprestimo.ATIVO);

            ferramenta.setStatus("Indisponível");
            ferramenta.updateFerramentasBD(
                    ferramenta.getId(),
                    ferramenta.getNome(),
                    ferramenta.getStatus(),
                    ferramenta.getMarca(),
                    ferramenta.getCustoAquisicao()
            );

            emprestimoDAO.insertEmprestimo(emprestimo);

            session.setAttribute("mensagemSucesso", "Empréstimo registrado com sucesso!");
        } catch (Exception e) {
            session.setAttribute("mensagemErro", "Erro ao registrar empréstimo: " + e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/cadastrar/emprestimo");
    }
}