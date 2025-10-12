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
            <input type="text" id="nome" name="nome" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>

            <div class="button-group">
                <button type="button" onclick="window.location.href='/v1/home'">Voltar</button>
                <input type="submit" value="Cadastrar">
            </div>
        </form>
    </section>
</div>

<footer class="footer">© 2025</footer>
<script src="../js/Navegar.js"></script>

</body>
</html>