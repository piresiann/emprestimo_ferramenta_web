package com.trabalho.servlet;

import com.trabalho.model.Ferramenta;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet("/cadastrar/ferramenta/servlet")
public class CadastrarFerramenta extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nome = request.getParameter("nome");
        String marca = request.getParameter("marca");
        String aquisicaoStr = request.getParameter("aquisicao");
        String status = "Disponível";

        BigDecimal valorAquisicao = BigDecimal.ZERO;

        try {
            if (aquisicaoStr != null && !aquisicaoStr.isBlank()) {
                String valorLimpo = aquisicaoStr
                        .replace("R$", "")
                        .replace(".", "")
                        .replace(",", ".")
                        .trim();

                valorAquisicao = new BigDecimal(valorLimpo);
            }

            Ferramenta ferramenta = new Ferramenta();
            ferramenta.insertFerramenta(nome, marca, status, valorAquisicao);

            request.getSession().setAttribute("mensagemSucesso", "Ferramenta cadastrada com sucesso!");
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("mensagemErro", "Valor de aquisição inválido.");
        } catch (SQLException e) {
            request.getSession().setAttribute("mensagemErro", "Erro ao cadastrar ferramenta.");
        }

        response.sendRedirect(request.getContextPath() + "/cadastrar/ferramenta");
    }
}
