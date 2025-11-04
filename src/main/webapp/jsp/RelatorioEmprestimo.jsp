<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Relatório de Empréstimo</title>
    <link rel="stylesheet" href="../css/LayoutBase.css">
    <link rel="stylesheet" href="../css/LayoutRelatorio.css">
    <link rel="icon" type="image/png" href="../Icone.png">

</head>
<body>

<%@ include file="NavBar.jsp" %>

<div class="content">
    <section class="hero tabela-container">
        <h2>Relatório de Empréstimos</h2>

        <c:if test="${empty listaEmprestimos}">
            <p style="text-align: center; color: #666;">Nenhum Empréstimo cadastrado.</p>
        </c:if>

        <c:if test="${not empty listaEmprestimos}">
            <table>
                <thead>
                <tr>
                    <th>Amigo</th>
                    <th>Ferramenta</th>
                    <th>Data do Empréstimo</th>
                    <th>Data da Devolução</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="emprestimo" items="${listaEmprestimos}">
                    <tr>
                        <td>${emprestimo.nomeAmigo}</td>
                        <td>${emprestimo.ferramenta}</td>
                        <td>${emprestimo.dataEmprestimo}</td>
                        <td>${emprestimo.dataDevolucao}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="paginacao">
                <c:if test="${paginaAtual > 1}">
                    <a href="${pageContext.request.contextPath}/relatorio/emprestimo?page=${paginaAtual - 1}">🡰</a>
                </c:if>

                <span style="margin: 0 10px;">${paginaAtual} de ${totalPaginas}</span>

                <c:if test="${paginaAtual < totalPaginas}">
                    <a href="${pageContext.request.contextPath}/relatorio/emprestimo?page=${paginaAtual + 1}">🡲</a>
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
