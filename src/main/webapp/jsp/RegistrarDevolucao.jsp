<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Registrar Devolução</title>
    <link rel="stylesheet" href="../css/LayoutBase.css">
    <link rel="stylesheet" href="../css/RegistrarDevolucao.css">
    <link rel="icon" type="image/png" href="../Icone.png">
</head>
<body>

<%@ include file="NavBar.jsp" %>

<div class="content">
    <section class="hero tabela-container">
        <h2>Registrar Devolução</h2>

        <c:if test="${empty listaEmprestimos}">
            <p style="text-align: center; color: #666;">Nenhum empréstimo ativo para devolução.</p>
        </c:if>

        <c:if test="${not empty listaEmprestimos}">
            <table>
                <thead>
                <tr>
                    <th>Amigo</th>
                    <th>Ferramenta</th>
                    <th>Data Empréstimo</th>
                    <th>Data Devolução</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="emp" items="${listaEmprestimos}">
                    <tr>
                        <td>${emp.nomeAmigo}</td>
                        <td>${emp.ferramenta}</td>
                        <td>${emp.dataEmprestimo}</td>
                        <td>${emp.dataDevolucao}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/registrar/devolucao/servlet" method="post" style="display:inline;">
                                <input type="hidden" name="idEmprestimo" value="${emp.id}">
                                <button type="submit">Registrar Devolução</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
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
</body>
</html>
