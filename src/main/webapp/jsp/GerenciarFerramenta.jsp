<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciar Ferramentas</title>
    <link rel="stylesheet" href="../css/LayoutBase.css">
    <link rel="stylesheet" href="../css/LayoutRelatorio.css">
    <link rel="stylesheet" href="../css/GerenciarFerramenta.css">
    <link rel="icon" type="image/png" href="../Icone.png">
</head>
<body>

<%@ include file="NavBar.jsp" %>

<div class="content">
    <section class="hero tabela-container">
        <h2>Gerenciar Ferramentas</h2>

        <c:if test="${empty listaFerramentas}">
            <p style="text-align: center; color: #666;">Nenhuma ferramenta cadastrada.</p>
        </c:if>

        <c:if test="${not empty listaFerramentas}">
            <table>
                <thead>
                <tr>
                    <th>Nome</th>
                    <th>Marca</th>
                    <th>Valor Aquisição</th>
                    <th>Status</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="ferramenta" items="${listaFerramentas}">
                    <tr data-id="${ferramenta.id}">
                        <td>${ferramenta.nome}</td>
                        <td>${ferramenta.marca}</td>
                        <td>${ferramenta.custoAquisicao}</td>
                        <td>${ferramenta.status}</td>
                        <td>
                            <button type="button" class="btn-editar editar-btn"
                                    data-id="${ferramenta.id}"
                                    data-nome="${ferramenta.nome}"
                                    data-marca="${ferramenta.marca}"
                                    data-custo="${ferramenta.custoAquisicao}"
                                    data-status="${ferramenta.status}">
                                Editar
                            </button>

                            <button type="button" class="btn-excluir" onclick="abrirModalExclusao(${ferramenta.id})">
                                Excluir
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>


            </table>

            <div class="paginacao">
                <c:if test="${paginaAtual > 1}">
                    <a href="${pageContext.request.contextPath}/gerenciar/ferramenta?page=${paginaAtual - 1}">🡰</a>
                </c:if>

                <span style="margin: 0 10px;">${paginaAtual} de ${totalPaginas}</span>

                <c:if test="${paginaAtual < totalPaginas}">
                    <a href="${pageContext.request.contextPath}/gerenciar/ferramenta?page=${paginaAtual + 1}">🡲</a>
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

<!-- MODAL DE EXCLUSÃO -->
<div id="modalConfirmacao" class="modal">
    <div class="modal-content">
        <p>Deseja realmente excluir esta ferramenta?</p>
        <div class="modal-buttons">
            <button id="confirmarExclusao">Sim</button>
            <button id="cancelarExclusao">Não</button>
        </div>
    </div>
</div>

<!-- MODAL DE EDIÇÃO -->
<div id="modalEdicao" class="modal">
    <div class="modal-content">
        <h3>Editar Ferramenta</h3>

        <form id="formEditarFerramenta" method="post" action="${pageContext.request.contextPath}/gerenciar/ferramenta">
            <input type="hidden" id="idEditar" name="idEditar">
            <input type="hidden" name="acao" value="editar">

            <label for="nomeEditar">Nome:</label>
            <input type="text" id="nomeEditar" name="nomeEditar" maxlength="35" required>

            <label for="marcaEditar">Marca:</label>
            <input type="text" id="marcaEditar" name="marcaEditar" maxlength="15" required>

            <label for="aquisicaoEditar">Valor Aquisição:</label>
            <input type="text" id="aquisicaoEditar" name="aquisicaoEditar" maxlength="13" autocomplete="off" required>

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
<script src="../js/GerenciarFerramenta.js"></script>

</body>
</html>
