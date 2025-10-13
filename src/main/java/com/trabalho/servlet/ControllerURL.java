package com.trabalho.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/home", "/cadastrar/amigo", "/cadastrar/ferramenta"})
public class ControllerURL extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();
        String destino = null;

        switch (path) {
            case "/home":
                destino = "/Home.html";
                break;

            case "/cadastrar/amigo":
                destino = "/jsp/CadastrarAmigo.jsp";
                break;

            case "/cadastrar/ferramenta":
                destino = "/jsp/CadastrarFerramenta.jsp";
                break;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
        dispatcher.forward(request, response);
    }
}
