let idEmprestimo = null;

function abrirModalExclusao(id) {
    idEmprestimo = id;
    document.getElementById("modalConfirmacao").style.display = "flex";
}

document.getElementById("confirmarExclusao").addEventListener("click", function () {
    const form = document.createElement("form");
    form.method = "post";
    form.action = `${contextPath}/gerenciar/emprestimo`;

    const inputId = document.createElement("input");
    inputId.type = "hidden";
    inputId.name = "idExcluir";
    inputId.value = idEmprestimo;

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
    idEmprestimo = null;
});

function editarEmprestimo(id) {
    idEmprestimo = id;
    const linha = document.querySelector(`tr[data-id="${id}"]`);
    const dataEmprestimoTexto = linha.children[2].innerText.trim();
    const dataDevolucaoTexto = linha.children[3].innerText.trim();

    const [diaEmp, mesEmp, anoEmp] = dataEmprestimoTexto.split('/');
    const [diaDev, mesDev, anoDev] = dataDevolucaoTexto.split('/');

    document.getElementById("idEditar").value = id;
    document.getElementById("dataEmprestimoEditar").value = `${anoEmp}-${mesEmp}-${diaEmp}`;
    document.getElementById("dataDevolucaoEditar").value = `${anoDev}-${mesDev}-${diaDev}`;

    document.getElementById("modalEdicao").style.display = "flex";
}

document.addEventListener("DOMContentLoaded", function () {
    const dataEmprestimo = document.getElementById("dataEmprestimoEditar");
    const dataDevolucao = document.getElementById("dataDevolucaoEditar");
    const formEdicao = document.getElementById("formEditarEmprestimo");
    const btnCancelar = document.getElementById("cancelarEdicao");

    if (!dataEmprestimo || !dataDevolucao || !formEdicao) return;

    const erroEmprestimo = document.createElement("small");
    const erroDevolucao = document.createElement("small");
    erroEmprestimo.classList.add("erro-data");
    erroDevolucao.classList.add("erro-data");

    dataEmprestimo.insertAdjacentElement("afterend", erroEmprestimo);
    dataDevolucao.insertAdjacentElement("afterend", erroDevolucao);

    function limparErros() {
        erroEmprestimo.textContent = "";
        erroDevolucao.textContent = "";
        dataEmprestimo.classList.remove("input-erro");
        dataDevolucao.classList.remove("input-erro");
    }

    formEdicao.addEventListener("submit", function (event) {
        limparErros();
        let erro = false;

        const emprestimo = new Date(dataEmprestimo.value);
        const devolucao = new Date(dataDevolucao.value);

        if (dataEmprestimo.value && dataDevolucao.value) {
            if (emprestimo > devolucao) {
                erroDevolucao.textContent = "A data de devolução não pode ser antes do empréstimo.";
                dataDevolucao.classList.add("input-erro");
                erro = true;
            }
        }

        if (erro) event.preventDefault();
    });

    btnCancelar.addEventListener("click", function () {
        document.getElementById("modalEdicao").style.display = "none";
        idEmprestimo = null;
        limparErros();
    });
});
