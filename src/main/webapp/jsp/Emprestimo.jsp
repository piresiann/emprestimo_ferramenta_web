<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Empréstimo de Ferramentas</title>
    <link rel="stylesheet" href="../css/LayoutBase.css">
    <link rel="stylesheet" href="../css/CadastrarEmprestimo.css">
    <link rel="icon" type="image/png" href="../Icone.png">
</head>
<body>

<%@ include file="NavBar.jsp" %>

<div class="content">
    <section class="hero" style="padding: 20px 40px">
        <form action="<%= request.getContextPath() %>/cadastrar/emprestimo/servlet" method="post">
            <h1 class="titulo" style="margin: 0">Registrar Empréstimo</h1>

            <label for="amigo">Amigo:</label>
            <select id="amigo" name="amigo_id" required>
                <option value="">Selecione um amigo</option>
                <c:forEach var="amigo" items="${requestScope.amigos}">
                    <option value="${amigo.id}">${amigo.nome}</option>
                </c:forEach>
            </select>
            <span id="msg-amigo" class="erro"></span>

            <label for="ferramenta">Ferramenta:</label>
            <select id="ferramenta" name="ferramenta_id" required>
                <option value="">Selecione uma ferramenta</option>
                <c:forEach var="ferramenta" items="${requestScope.ferramentas}">
                    <option value="${ferramenta.id}">${ferramenta.nome}</option>
                </c:forEach>
            </select>
            <span id="msg-ferramenta" class="erro"></span>

            <label for="data_devolucao">Data de Devolução:</label>
            <input type="date" id="data_devolucao" name="data_devolucao" required>
            <span id="msg-data" class="erro"></span>

            <div class="button-group">
                <button type="button" onclick="window.location.href='/v1/home'">Voltar</button>
                <input type="submit" value="Registrar">
            </div>
        </form>
    </section>

    <c:if test="${not empty sessionScope.mensagemSucesso}">
        <div class="mensagem mensagem-sucesso">${sessionScope.mensagemSucesso}</div>
        <c:remove var="mensagemSucesso" scope="session" />
    </c:if>

    <c:if test="${not empty sessionScope.mensagemErro}">
        <div class="mensagem mensagem-erro">${sessionScope.mensagemErro}</div>
        <c:remove var="mensagemErro" scope="session" />
    </c:if>

</div>

<footer class="footer">© 2025</footer>
<script src="../js/Navegar.js"></script>
<script src="../js/CadastrarEmprestimoValidacao.js"></script>

</body>
</html>