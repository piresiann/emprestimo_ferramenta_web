let idFerramenta = null;

function abrirModalExclusao(id) {
    idFerramenta = id;
    const modal = document.getElementById("modalConfirmacao");
    if (modal) modal.style.display = "flex";
}

function fecharModalExclusao() {
    const modal = document.getElementById("modalConfirmacao");
    if (modal) modal.style.display = "none";
    idFerramenta = null;
}

function confirmarExclusaoSubmit() {
    const form = document.createElement("form");
    form.method = "post";
    form.action = `${contextPath}/gerenciar/ferramenta/servlet`;

    const inputId = document.createElement("input");
    inputId.type = "hidden";
    inputId.name = "idExcluir";
    inputId.value = idFerramenta;

    const acao = document.createElement("input");
    acao.type = "hidden";
    acao.name = "acao";
    acao.value = "excluir";

    form.appendChild(inputId);
    form.appendChild(acao);
    document.body.appendChild(form);
    form.submit();
}

function editarFerramenta(id) {
    idFerramenta = id;
    const linha = document.querySelector(`tr[data-id="${id}"]`);
    if (!linha) {
        console.warn("Linha da ferramenta não encontrada para id:", id);
        return;
    }

    const nome = linha.children[0] ? linha.children[0].innerText.trim() : "";
    const marca = linha.children[1] ? linha.children[1].innerText.trim() : "";
    const aquisicaoTexto = linha.children[2] ? linha.children[2].innerText.trim() : "";

    const nomeInput = document.getElementById("nome");
    const marcaInput = document.getElementById("marca");
    const aquisicaoInput = document.getElementById("aquisicao");

    if (nomeInput) nomeInput.value = nome;
    if (marcaInput) marcaInput.value = marca;
    if (aquisicaoInput) aquisicaoInput.value = aquisicaoTexto;

    let inputId = document.getElementById("idEditarFerramenta");
    if (!inputId) {
        inputId = document.createElement("input");
        inputId.type = "hidden";
        inputId.id = "idEditarFerramenta";
        inputId.name = "idEditar";
        const form = document.querySelector("#modalEdicao form");
        if (form) form.appendChild(inputId);
    }
    inputId.value = id;

    const modal = document.getElementById("modalEdicao");
    if (modal) modal.style.display = "flex";
}

document.addEventListener("DOMContentLoaded", function () {
    const btnConfirmarExclusao = document.getElementById("confirmarExclusao");
    const btnCancelarExclusao = document.getElementById("cancelarExclusao");

    if (btnConfirmarExclusao) btnConfirmarExclusao.addEventListener("click", confirmarExclusaoSubmit);
    if (btnCancelarExclusao) btnCancelarExclusao.addEventListener("click", fecharModalExclusao);

    const form = document.querySelector("#modalEdicao form");
    const nomeInput = document.getElementById("nome");
    const marcaInput = document.getElementById("marca");
    const aquisicaoInput = document.getElementById("aquisicao");

    const nomeMsg = document.getElementById("msg-nome");
    const marcaMsg = document.getElementById("msg-marca");
    const aquisicaoMsg = document.getElementById("msg-aquisicao");

    if (!form || !nomeInput || !marcaInput || !aquisicaoInput) return;

    aquisicaoInput.addEventListener("input", function (e) {
        let valor = e.target.value.replace(/\D/g, "");
        if (valor === "") {
            e.target.value = "";
            return;
        }
        valor = (parseInt(valor, 10) / 100).toFixed(2);
        valor = valor.replace(".", ",").replace(/\B(?=(\d{3})+(?!\d))/g, ".");
        e.target.value = "R$ " + valor;
    });

    form.addEventListener("submit", function (event) {
        let valid = true;
        nomeMsg.textContent = "";
        marcaMsg.textContent = "";
        aquisicaoMsg.textContent = "";

        const nome = nomeInput.value.trim();
        const marca = marcaInput.value.trim();
        const aquisicao = aquisicaoInput.value.trim();
        const nomeRegex = /^[A-Za-zÀ-ÿ\s]+$/;

        if (nome.length < 3 || nome.length > 35) {
            nomeMsg.textContent = "Mínimo 3 caracteres.";
            valid = false;
        } else if (!nomeRegex.test(nome)) {
            nomeMsg.textContent = "Digite apenas letras.";
            valid = false;
        }

        if (marca.length < 3 || marca.length > 15) {
            marcaMsg.textContent = "Mínimo 3 caracteres.";
            valid = false;
        } else if (!nomeRegex.test(marca)) {
            marcaMsg.textContent = "Digite apenas letras.";
            valid = false;
        }

        if (aquisicao === "" || !aquisicao.startsWith("R$")) {
            aquisicaoMsg.textContent = "Informe o valor de aquisição.";
            valid = false;
        } else {
            const valorNumerico = parseFloat(aquisicao.replace("R$", "").replace(/\./g, "").replace(",", "."));
            if (isNaN(valorNumerico) || valorNumerico <= 0) {
                aquisicaoMsg.textContent = "Valor inválido.";
                valid = false;
            }
        }

        if (!valid) event.preventDefault();
    });

    const btnCancelarEdicao = document.getElementById("cancelarEdicao");
    if (btnCancelarEdicao) {
        btnCancelarEdicao.addEventListener("click", function () {
            const modal = document.getElementById("modalEdicao");
            if (modal) modal.style.display = "none";
            idFerramenta = null;
            nomeMsg.textContent = "";
            marcaMsg.textContent = "";
            aquisicaoMsg.textContent = "";
        });
    }

    nomeInput.addEventListener("focus", () => nomeMsg.textContent = "");
    marcaInput.addEventListener("focus", () => marcaMsg.textContent = "");
    aquisicaoInput.addEventListener("focus", () => aquisicaoMsg.textContent = "");
});
