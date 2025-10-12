<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="navbar">
    <div class="dropdown">
        <button class="dropbtn">Cadastrar ▾</button>
        <div class="dropdown-content">
            <a href="<%= request.getContextPath() %>/cadastrar/amigo">Cadastrar Amigo</a>
            <a href="<%= request.getContextPath() %>/cadastrar/ferramenta">Cadastrar Ferramenta</a>
        </div>
    </div>

    <div class="dropdown">
        <button class="dropbtn">Empréstimo e Devolução ▾</button>
        <div class="dropdown-content">
            <a href="<%= request.getContextPath() %>/emprestimo">Registrar Empréstimo</a>
            <a href="<%= request.getContextPath() %>/devolucao">Registrar Devolução</a>
        </div>
    </div>

    <div class="dropdown">
        <button class="dropbtn">Gerenciar ▾</button>
        <div class="dropdown-content">
            <a href="<%= request.getContextPath() %>/gerenciar/amigo">Gerenciar Amigos</a>
            <a href="<%= request.getContextPath() %>/gerenciar/ferramenta">Gerenciar Ferramentas</a>
            <a href="<%= request.getContextPath() %>/gerenciar/emprestimo">Gerenciar Empréstimos</a>
        </div>
    </div>

    <div class="dropdown">
        <button class="dropbtn">Relatório ▾</button>
        <div class="dropdown-content">
            <a href="<%= request.getContextPath() %>/relatorio/amigo">Relatório de Amigos</a>
            <a href="<%= request.getContextPath() %>/relatorio/ferramenta">Relatório de Ferramentas</a>
            <a href="<%= request.getContextPath() %>/relatorio/emprestimo">Relatório de Empréstimos</a>
        </div>
    </div>
</div>
