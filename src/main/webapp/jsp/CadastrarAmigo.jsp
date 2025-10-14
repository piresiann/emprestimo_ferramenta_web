<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Empréstimo de Ferramentas</title>
    <link rel="stylesheet" href="../css/CadastrarAmigo.css">
    <link rel="stylesheet" href="../css/LayoutBase.css">
    <link rel="icon" type="image/png" href="../Icone.png">
</head>
<body>

<%@ include file="NavBar.jsp" %>

<div class="content">
    <section class="hero" style="padding: 20px 40px">
        <form action="<%= request.getContextPath() %>/cadastrar/amigo/servlet" method="post">
            <h1 class="titulo" style="margin: 0">Cadastrar Amigo</h1>

            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" maxlength="30" autocomplete="off" required>
            <span id="msg-nome" class="erro"></span>

            <label for="telefone">Telefone:</label>
            <input type="text" id="telefone" name="telefone" maxlength="15" placeholder="(xx) xxxxx-xxxx" autocomplete="off" required>
            <span id="msg-telefone" class="erro"></span>

            <div class="button-group">
                <button type="button" onclick="window.location.href='/v1/home'">Voltar</button>
                <input type="submit" value="Cadastrar">
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
<script src="../js/CadastrarAmigoValidacao.js"></script>

</body>
</html>