document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById("modalEdicao");
    const formEdicao = document.getElementById("formEdicao");
    const editNomeInput = document.getElementById("edit-nome");
    const editTelefoneInput = document.getElementById("edit-telefone");
    const editIdInput = document.getElementById("edit-id");

    const nomeMsg = document.getElementById("msg-edit-nome");
    const telefoneMsg = document.getElementById("msg-edit-telefone");


    window.abrirModalEdicao = function (id, nome, telefone) {
        editIdInput.value = id;
        editNomeInput.value = nome;
        editTelefoneInput.value = telefone;
        nomeMsg.textContent = "";
        telefoneMsg.textContent = "";
        modal.style.display = "block";
    }


    window.fecharModalEdicao = function () {
        modal.style.display = "none";
    }


    window.confirmarExclusao = function (id) {
        if (confirm("Tem certeza que deseja excluir este amigo?")) {

            const form = document.createElement('form');
            form.method = 'POST';
            form.action = formEdicao.getAttribute('action');

            const urlParams = new URLSearchParams(window.location.search);
            const pageParam = urlParams.get('page') || '1';

            const actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'action';
            actionInput.value = 'excluir';
            form.appendChild(actionInput);

            const idInput = document.createElement('input');
            idInput.type = 'hidden';
            idInput.name = 'id';
            idInput.value = id;
            form.appendChild(idInput);


            const pageInput = document.createElement('input');
            pageInput.type = 'hidden';
            pageInput.name = 'page';
            pageInput.value = pageParam;
            form.appendChild(pageInput);

            document.body.appendChild(form);
            form.submit();
        }
    }

    window.onclick = function(event) {
        if (event.target === modal) {
            fecharModalEdicao();
        }
    }


    editTelefoneInput.addEventListener("input", function (e) {
        let value = e.target.value.replace(/\D/g, "");
        if (value.length > 11) value = value.slice(0, 11);

        if (value.length > 0) value = "(" + value;
        if (value.length > 3) value = value.slice(0, 3) + ") " + value.slice(3);
        if (value.length > 10) value = value.slice(0, 10) + "-" + value.slice(10);

        e.target.value = value;
    });

    formEdicao.addEventListener("submit", function (event) {
        let valid = true;

        nomeMsg.textContent = "";
        telefoneMsg.textContent = "";

        const nome = editNomeInput.value.trim();
        const telefoneNumeros = editTelefoneInput.value.replace(/\D/g, "");

        if (nome.length < 3) {
            nomeMsg.textContent = "Mínimo 3 caracteres.";
            valid = false;
        } else {

            const nomeRegex = /^[A-Za-zÀ-ÿ\s]+$/;
            if (!nomeRegex.test(nome)) {
                nomeMsg.textContent = "O nome não deve conter números ou caracteres especiais.";
                valid = false;
            }
        }

        if (telefoneNumeros.length !== 11) {
            telefoneMsg.textContent = "Número inválido (DDD + 9 dígitos).";
            valid = false;
        }

        if (!valid) {
            event.preventDefault();
        }
    });

    editNomeInput.addEventListener("focus", () => nomeMsg.textContent = "");
    editTelefoneInput.addEventListener("focus", () => telefoneMsg.textContent = "");
});