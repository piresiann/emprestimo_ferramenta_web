<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Empréstimo de Ferramentas</title>
    <link rel="stylesheet" href="../css/CadastrarFerramenta.css">
    <link rel="stylesheet" href="../css/LayoutBase.css">
    <link rel="icon" type="image/png" href="../Icone.png">
</head>
<body>

<%@ include file="NavBar.jsp" %>

<div class="content">
    <section class="hero" style="padding: 20px 40px">
        <form action="<%= request.getContextPath() %>/cadastrar/ferramenta/servlet" method="post">
            <h1 class="titulo" style="margin: 0">Cadastrar Ferramenta</h1>

            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" maxlength="35" autocomplete="off" required>
            <span id="msg-nome" class="erro"></span>

            <label for="marca">Marca:</label>
            <input type="text" id="marca" name="marca" maxlength="15" autocomplete="off" required>
            <span id="msg-marca" class="erro"></span>

            <label for="aquisicao">Valor Aquisição:</label>
            <input type="text" id="aquisicao" name="aquisicao" maxlength="13" autocomplete="off" required>
            <span id="msg-aquisicao" class="erro"></span>

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
<script src="../js/CadastrarFerramentaValidacao.js"></script>

</body>
</html>