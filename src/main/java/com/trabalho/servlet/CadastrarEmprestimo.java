package com.trabalho.servlet;

import com.trabalho.model.Amigo;
import com.trabalho.model.Emprestimo;
import com.trabalho.model.Ferramenta;
import com.trabalho.model.StatusFerramenta;
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

    private final Amigo amigo = new Amigo();
    private final Ferramenta ferramenta = new Ferramenta();
    private final Emprestimo emprestimo = new Emprestimo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ArrayList<Amigo> amigos = amigo.getAllAmigos();
            ArrayList<Ferramenta> ferramentas = ferramenta.getFerramentasDisponiveis();
            request.setAttribute("amigos", amigos);
            request.setAttribute("ferramentas", ferramentas);
        } catch (SQLException e) {
            request.getSession().setAttribute("mensagemErro", "Erro ao carregar dados: " + e.getMessage());
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/RegistrarEmprestimo.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        try {
            int amigoId = Integer.parseInt(request.getParameter("amigo_id"));
            int ferramentaId = Integer.parseInt(request.getParameter("ferramenta_id"));
            String dataDevolucao = request.getParameter("data_devolucao");

            Amigo amigoInsert = amigo.getAmigoById(amigoId);
            Ferramenta ferramentaInsert = ferramenta.getFerramentaById(ferramentaId);


            if (amigo == null || ferramenta == null) {
                throw new Exception("Amigo ou Ferramenta não encontrado.");
            }

            if (ferramentaInsert.getStatus() != null && !StatusFerramenta.DISPONIVEL.equals(ferramentaInsert.getStatus())) {
                throw new Exception("A ferramenta selecionada não está disponível para empréstimo.");
            }

            emprestimo.setId(emprestimo.maiorID() + 1);
            emprestimo.setNomeAmigo(amigoInsert.getNome());
            emprestimo.setFerramenta(ferramentaInsert.getNome());
            emprestimo.setDataDevolucao(dataDevolucao);
            emprestimo.setCodigoFerramenta(String.valueOf(ferramentaId));
            emprestimo.setStatus(StatusEmprestimo.ATIVO);

            emprestimo.insertEmprestimo(emprestimo.getNomeAmigo(), emprestimo.getFerramenta(), emprestimo.getDataDevolucao(), emprestimo.getStatus(), emprestimo.getCodigoFerramenta());

            Ferramenta ferramentaEmprestada = ferramenta.getFerramentaById(ferramentaId);
            ferramentaEmprestada.setStatus(StatusFerramenta.INDISPONIVEL);

            ferramenta.updateFerramentasBD(ferramentaEmprestada);

            session.setAttribute("mensagemSucesso", "Empréstimo registrado com sucesso!");
        } catch (Exception e) {
            session.setAttribute("mensagemErro", "Erro ao registrar empréstimo: " + e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/cadastrar/emprestimo");
    }
}