<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Empréstimo de Ferramentas</title>
    <link rel="stylesheet" href="../css/LayoutBase.css">
    <link rel="stylesheet" href="../css/LayoutRelatorio.css">
    <link rel="stylesheet" href="../css/GerenciarEmprestimo.css">
    <link rel="icon" type="image/png" href="../Icone.png">
</head>
<body>

<%@ include file="NavBar.jsp" %>

<div class="content">
    <section class="hero tabela-container">
        <h2>Gerenciar Empréstimos</h2>

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
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="emprestimo" items="${listaEmprestimos}">
                    <tr data-id="${emprestimo.id}">
                        <td>${emprestimo.nomeAmigo}</td>
                        <td>${emprestimo.ferramenta}</td>
                        <td>${emprestimo.dataEmprestimo}</td>
                        <td>${emprestimo.dataDevolucao}</td>
                        <td>
                            <button type="button" class="btn-editar" onclick="editarEmprestimo(${emprestimo.id})">Editar</button>
                            <button type="button" class="btn-excluir" onclick="abrirModalExclusao(${emprestimo.id})">Excluir</button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="paginacao">
                <c:if test="${paginaAtual > 1}">
                    <a href="${pageContext.request.contextPath}/gerenciar/emprestimo?page=${paginaAtual - 1}">🡰</a>
                </c:if>

                <span style="margin: 0 10px;">${paginaAtual} de ${totalPaginas}</span>

                <c:if test="${paginaAtual < totalPaginas}">
                    <a href="${pageContext.request.contextPath}/gerenciar/emprestimo?page=${paginaAtual + 1}">🡲</a>
                </c:if>
            </div>
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

<div id="modalConfirmacao" class="modal">
    <div class="modal-content">
        <p>Deseja realmente excluir este empréstimo?</p>
        <div class="modal-buttons">
            <button id="confirmarExclusao">Sim</button>
            <button id="cancelarExclusao">Não</button>
        </div>
    </div>
</div>

<div id="modalEdicao" class="modal">
    <div class="modal-content">
        <h3>Editar Empréstimo</h3>

        <form id="formEditarEmprestimo" method="post" action="${pageContext.request.contextPath}/gerenciar/emprestimo">
            <input type="hidden" id="idEditar" name="idEditar">
            <input type="hidden" name="acao" value="editar">

            <div class="campo-data">
                <label for="dataEmprestimoEditar">Data do Empréstimo:</label>
                <input type="date" id="dataEmprestimoEditar" name="dataEmprestimoEditar" onkeydown="return false;" required>
            </div>

            <div class="campo-data">
                <label for="dataDevolucaoEditar">Data da Devolução:</label>
                <input type="date" id="dataDevolucaoEditar" name="dataDevolucaoEditar" onkeydown="return false;" required>
            </div>

            <div class="modal-buttons">
                <button type="submit">Salvar</button>
                <button type="button" id="cancelarEdicao">Cancelar</button>
            </div>
        </form>
    </div>
</div>

<footer class="footer">© 2025</footer>

<script src="../js/Navegar.js"></script>
<script>const contextPath = "${pageContext.request.contextPath}";</script>
<script src="../js/GerenciarEmprestimo.js"></script>

</body>
</html>