<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciar Amigos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/LayoutBase.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/GerenciarAmigo.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/LayoutRelatorio.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/Icone.png">
</head>
<body>

<%@ include file="NavBar.jsp" %>

<div class="content">
    <section class="hero" style="padding: 20px 40px">
        <h1 class="titulo">Gerenciar Amigos</h1>


        <div class="tabela-container">
            <c:choose>
                <c:when test="${not empty listaAmigos}">
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
                            <tr data-id="${amigo.id}" data-nome="${amigo.nome}" data-telefone="${amigo.telefone}">
                                <td>${amigo.nome}</td>
                                <td>${amigo.telefone}</td>
                                <td>
                                    <button class="editar" onclick="abrirModalEdicao(${amigo.id}, '${amigo.nome}', '${amigo.telefone}')">Editar</button>
                                    <button class="excluir" onclick="confirmarExclusao(${amigo.id})">Excluir</button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <div class="paginacao">
                        <c:if test="${paginaAtual > 1}">
                            <a href="${pageContext.request.contextPath}/gerenciar/amigo/servlet?page=${paginaAtual - 1}">🡰</a>
                        </c:if>

                        <span>${paginaAtual} de ${totalPaginas}</span>

                        <c:if test="${paginaAtual < totalPaginas}">
                            <a href="${pageContext.request.contextPath}/gerenciar/amigo/servlet?page=${paginaAtual + 1}">🡲</a>
                        </c:if>
                    </div>

                </c:when>
                <c:otherwise>
                    <p>Nenhum amigo cadastrado.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </section>

    <c:if test="${not empty sessionScope.mensagemSucesso}">
        <div class="mensagem mensagem-sucesso">${sessionScope.mensagemSucesso}</div>
        <c:remove var="mensagemSucesso" scope="session"/>
    </c:if>

    <c:if test="${not empty sessionScope.mensagemErro}">
        <div class="mensagem mensagem-erro">${sessionScope.mensagemErro}</div>
        <c:remove var="mensagemErro" scope="session"/>
    </c:if>

    <div id="modalEdicao" class="modal">
        <div class="modal-content">
            <form id="formEdicao" action="${pageContext.request.contextPath}/gerenciar/amigo/servlet" method="post" class="edicao-form">
                <h3>Editar Amigo</h3>
                <input type="hidden" name="action" value="editar">
                <input type="hidden" id="edit-id" name="id">
                <input type="hidden" name="page" value="${paginaAtual}">

                <label for="edit-nome">Nome:</label>
                <input type="text" id="edit-nome" name="nome" maxlength="30" autocomplete="off" required>
                <span id="msg-edit-nome" class="erro"></span>

                <label for="edit-telefone">Telefone:</label>
                <input type="text" id="edit-telefone" name="telefone" maxlength="15" placeholder="(xx) xxxxx-xxxx" autocomplete="off" required>
                <span id="msg-edit-telefone" class="erro"></span>

                <div class="form-botoes">
                    <button type="button" class="cancelar" onclick="fecharModalEdicao()">Cancelar</button>
                    <button type="submit" class="salvar">Salvar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div id="modalExclusao" class="modal">
    <div class="modal-content">
        <span class="fechar-modal" onclick="fecharModalExclusao()">&times;</span>

        <h3 style="text-align: center; margin-top: 10px;">Confirmar Exclusão</h3>
        <p style="text-align: center; margin: 20px 0; font-size: 1.1em;">
            Tem certeza que deseja excluir este amigo?
        </p>

        <input type="hidden" id="delete-id">

        <div class="confirm-botoes">
            <button type="button" id="btnConfirmarExclusao" class="btn-sim">Sim</button>
            <button type="button" id="btnCancelarExclusao" class="btn-nao">Não</button>
        </div>
    </div>
</div>



<footer class="footer">© 2025</footer>
<script src="${pageContext.request.contextPath}/js/Navegar.js"></script>
<script src="${pageContext.request.contextPath}/js/GerenciarAmigo.js"></script>
</body>
</html>
