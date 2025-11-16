let idAmigo = null;

function abrirModalExclusao(id) {
    idAmigo = id;
    document.getElementById("modalExclusao").style.display = "flex";
}

document.getElementById("confirmarExclusao").addEventListener("click", function () {

    const form = document.createElement("form");
    form.method = "post";
    form.action = `${contextPath}/gerenciar/amigo/servlet`;

    const inputId = document.createElement("input");
    inputId.type = "hidden";
    inputId.name = "id";
    inputId.value = idAmigo;

    const acao = document.createElement("input");
    acao.type = "hidden";
    acao.name = "action";
    acao.value = "excluir";

    const pagina = new URLSearchParams(window.location.search).get("page") || "1";
    const pageInput = document.createElement("input");
    pageInput.type = "hidden";
    pageInput.name = "page";
    pageInput.value = pagina;

    form.appendChild(inputId);
    form.appendChild(acao);
    form.appendChild(pageInput);

    document.body.appendChild(form);
    form.submit();
});

document.getElementById("cancelarExclusao").addEventListener("click", function () {
    document.getElementById("modalExclusao").style.display = "none";
    idAmigo = null;
});

function abrirModalEdicao(id, nome, telefone) {
    idAmigo = id;

    document.getElementById("edit-id").value = id;
    document.getElementById("edit-nome").value = nome;
    document.getElementById("edit-telefone").value = telefone;

    document.getElementById("modalEdicao").style.display = "flex";
}

function fecharModalEdicao() {
    document.getElementById("modalEdicao").style.display = "none";
    idAmigo = null;
}

window.onclick = function (event) {
    const modalEdicao = document.getElementById("modalEdicao");
    const modalExclusao = document.getElementById("modalExclusao");

    if (event.target === modalEdicao) {
        fecharModalEdicao();
    }
    if (event.target === modalExclusao) {
        document.getElementById("modalExclusao").style.display = "none";
    }
};

document.getElementById("edit-telefone").addEventListener("input", function (e) {
    let value = e.target.value.replace(/\D/g, "");

    if (value.length > 11) value = value.slice(0, 11);

    if (value.length > 0) value = "(" + value;
    if (value.length > 3) value = value.slice(0, 3) + ") " + value.slice(3);
    if (value.length > 10) value = value.slice(0, 10) + "-" + value.slice(10);

    e.target.value = value;
});

document.getElementById("formEdicao").addEventListener("submit", function (event) {

    const nome = document.getElementById("edit-nome");
    const telefone = document.getElementById("edit-telefone");

    const nomeMsg = document.getElementById("msg-edit-nome");
    const telefoneMsg = document.getElementById("msg-edit-telefone");

    nomeMsg.textContent = "";
    telefoneMsg.textContent = "";

    let valido = true;

    const nomeVal = nome.value.trim();
    if (nomeVal.length < 3) {
        nomeMsg.textContent = "Mínimo 3 caracteres.";
        valido = false;
    } else if (!/^[A-Za-zÀ-ÿ\s]+$/.test(nomeVal)) {
        nomeMsg.textContent = "O nome não deve conter números ou símbolos.";
        valido = false;
    }

    const numeros = telefone.value.replace(/\D/g, "");
    if (numeros.length !== 11) {
        telefoneMsg.textContent = "Número inválido: DDD + 9 dígitos.";
        valido = false;
    }

    if (!valido) event.preventDefault();
});

document.getElementById("edit-nome").addEventListener("focus", () => {
    document.getElementById("msg-edit-nome").textContent = "";
});

document.getElementById("edit-telefone").addEventListener("focus", () => {
    document.getElementById("msg-edit-telefone").textContent = "";
});