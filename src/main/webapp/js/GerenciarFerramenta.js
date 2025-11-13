let idFerramenta = null;

document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".editar-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = btn.dataset.id;
            const nome = btn.dataset.nome;
            const marca = btn.dataset.marca;
            const status = btn.dataset.status;

            editarFerramenta(id, nome, marca, status);
        });
    });
});


function abrirModalExclusao(id) {
    idFerramenta = id;
    document.getElementById("modalConfirmacao").style.display = "flex";
}

document.getElementById("confirmarExclusao").addEventListener("click", function () {
    const form = document.createElement("form");
    form.method = "post";
    form.action = `${contextPath}/gerenciar/ferramenta`;

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
});

document.getElementById("cancelarExclusao").addEventListener("click", function () {
    document.getElementById("modalConfirmacao").style.display = "none";
    idFerramenta = null;
});

function editarFerramenta(id) {
    idFerramenta = id;
    const linha = document.querySelector(`tr[data-id="${id}"]`);
    const nome = linha.children[0].innerText.trim();
    const marca = linha.children[1].innerText.trim();
    const aquisicao = linha.children[2].innerText.trim();

    document.getElementById("idEditar").value = id;
    document.getElementById("nomeEditar").value = nome;
    document.getElementById("marcaEditar").value = marca;
    document.getElementById("aquisicaoEditar").value = aquisicao;

    document.getElementById("modalEdicao").style.display = "flex";
}

document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById("modalEdicao");
    const cancelarBtn = document.getElementById("cancelarEdicao");

    cancelarBtn.addEventListener("click", function () {
        modal.style.display = "none";
        idFerramenta = null;
    });
});
