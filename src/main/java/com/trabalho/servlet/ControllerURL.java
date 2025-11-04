package com.trabalho.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/home", "/cadastrar/amigo", "/cadastrar/ferramenta", "/relatorio/amigo", "/relatorio/ferramenta", "/relatorio/emprestimo"})
public class ControllerURL extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();
        String destino = null;

        switch (path) {
            case "/home":
                destino = "/jsp/Home.jsp";
                break;

            case "/cadastrar/amigo":
                destino = "/jsp/CadastrarAmigo.jsp";
                break;

            case "/cadastrar/ferramenta":
                destino = "/jsp/CadastrarFerramenta.jsp";
                break;

            case "/relatorio/amigo":
                request.getRequestDispatcher("/relatorio/amigo/servlet").forward(request, response);
                return;

            case "/relatorio/ferramenta":
                request.getRequestDispatcher("/relatorio/ferramenta/servlet").forward(request, response);
                return;

            case "/relatorio/emprestimo":
                request.getRequestDispatcher("/relatorio/emprestimo/servlet").forward(request, response);
                return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
        dispatcher.forward(request, response);
    }
}
