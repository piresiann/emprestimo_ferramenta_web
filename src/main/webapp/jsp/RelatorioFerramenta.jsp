<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Relatório de Amigos</title>
    <link rel="stylesheet" href="../css/LayoutBase.css">
    <link rel="stylesheet" href="../css/LayoutRelatorio.css">
    <link rel="icon" type="image/png" href="../Icone.png">

</head>
<body>

<%@ include file="NavBar.jsp" %>

<div class="content">
    <section class="hero tabela-container">
        <h2>Relatório de Ferramentas</h2>

        <c:if test="${empty listaFerramentas}">
            <p style="text-align: center; color: #666;">Nenhuma Ferramenta cadastrada.</p>
        </c:if>

        <c:if test="${not empty listaFerramentas}">
            <table>
                <thead>
                <tr>
                    <th>Nome</th>
                    <th>Marca</th>
                    <th>Aquisição</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="ferramenta" items="${listaFerramentas}">
                    <tr>
                        <td>${ferramenta.nome}</td>
                        <td>${ferramenta.marca}</td>
                        <td><fmt:formatNumber value="${ferramenta.custoAquisicao}" type="currency" currencySymbol="R$" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td>${ferramenta.status}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="paginacao">
                <c:if test="${paginaAtual > 1}">
                    <a href="${pageContext.request.contextPath}/relatorio/ferramenta?page=${paginaAtual - 1}">🡰</a>
                </c:if>

                <span style="margin: 0 10px;">${paginaAtual} de ${totalPaginas}</span>

                <c:if test="${paginaAtual < totalPaginas}">
                    <a href="${pageContext.request.contextPath}/relatorio/ferramenta?page=${paginaAtual + 1}">🡲</a>
                </c:if>
            </div>
        </c:if>

        <a href="../home" class="btn-voltar">Voltar</a>
    </section>
</div>

<footer class="footer">© 2025</footer>
<script src="../js/Navegar.js"></script>

</body>
</html>
