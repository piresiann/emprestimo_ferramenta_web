<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciar Amigos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/LayoutBase.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/LayoutTabela.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/LayoutModal.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ModaisAmigo.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/Icone.png">
</head>
<body>

<%@ include file="NavBar.jsp" %>

<div class="content">
    <section class="hero tabela-container">
        <h2>Gerenciar Amigos</h2>

        <c:if test="${empty listaAmigos}">
            <p style="text-align: center; color: #666;">Nenhum amigo cadastrado.</p>
        </c:if>

        <c:if test="${not empty listaAmigos}">
            <table>
                <thead>
                <tr>
                    <th>Nome</th>
                    <th>Telefone</th>
                    <th>Ações</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="amigo" items="${listaAmigos}">
                    <tr data-id="${amigo.id}">
                        <td>${amigo.nome}</td>
                        <td>${amigo.telefone}</td>

                        <td>
                            <button type="button" class="btn-editar"
                                    onclick="abrirModalEdicao(${amigo.id}, '${amigo.nome}', '${amigo.telefone}')">
                                Editar
                            </button>

                            <button type="button" class="btn-excluir"
                                    onclick="abrirModalExclusao(${amigo.id})">
                                Excluir
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="paginacao">
                <c:if test="${paginaAtual > 1}">
                    <a href="${pageContext.request.contextPath}/gerenciar/amigo/servlet?page=${paginaAtual - 1}">🡰</a>
                </c:if>

                <span style="margin: 0 10px;">${paginaAtual} de ${totalPaginas}</span>

                <c:if test="${paginaAtual < totalPaginas}">
                    <a href="${pageContext.request.contextPath}/gerenciar/amigo/servlet?page=${paginaAtual + 1}">🡲</a>
                </c:if>
            </div>

            <a href="../home" class="btn-voltar">Voltar</a>
        </c:if>
    </section>

    <c:if test="${not empty sessionScope.mensagemSucesso}">
        <div class="mensagem mensagem-sucesso">${sessionScope.mensagemSucesso}</div>
        <c:remove var="mensagemSucesso" scope="session"/>
    </c:if>

    <c:if test="${not empty sessionScope.mensagemErro}">
        <div class="mensagem mensagem-erro">${sessionScope.mensagemErro}</div>
        <c:remove var="mensagemErro" scope="session"/>
    </c:if>

</div>

<div id="modalExclusao" class="modal">
    <div class="modal-content">
        <p>Deseja realmente excluir este amigo?</p>
        <div class="modal-buttons">
            <button id="confirmarExclusao">Sim</button>
            <button id="cancelarExclusao">Não</button>
        </div>
    </div>
</div>

<div id="modalEdicao" class="modal">
    <div class="modal-content">
        <h3>Editar Amigo</h3>

        <form id="formEdicao" action="${pageContext.request.contextPath}/gerenciar/amigo/servlet" method="post">
            <input type="hidden" name="action" value="editar">
            <input type="hidden" id="edit-id" name="id">
            <input type="hidden" name="page" value="${paginaAtual}">

            <div class="campo-nome">
                <label for="edit-nome">Nome:</label>
                <input type="text" id="edit-nome" name="nome" maxlength="30" required autocomplete="off">
            </div>

            <div class="campo-telefone">
                <label for="edit-telefone">Telefone:</label>
                <input type="text" id="edit-telefone" name="telefone" maxlength="15" placeholder="(xx) xxxxx-xxxx" required autocomplete="off">
            </div>

            <div class="modal-buttons">
                <button type="submit">Salvar</button>
                <button type="button" onclick="fecharModalEdicao()">Cancelar</button>
            </div>
        </form>
    </div>
</div>

<footer class="footer">© 2025</footer>

<script src="${pageContext.request.contextPath}/js/Navegar.js"></script>
<script>const contextPath = "${pageContext.request.contextPath}";</script>
<script src="${pageContext.request.contextPath}/js/GerenciarAmigo.js"></script>

</body>
</html>
