document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const nomeInput = document.getElementById("nome");
    const telefoneInput = document.getElementById("telefone");

    const nomeMsg = document.getElementById("msg-nome");
    const telefoneMsg = document.getElementById("msg-telefone");

    telefoneInput.addEventListener("input", function (e) {
        let value = e.target.value.replace(/\D/g, "");
        if (value.length > 11) value = value.slice(0, 11);

        if (value.length > 0) value = "(" + value;
        if (value.length > 3) value = value.slice(0, 3) + ") " + value.slice(3);
        if (value.length > 10) value = value.slice(0, 10) + "-" + value.slice(10);

        e.target.value = value;
    });

    form.addEventListener("submit", function (event) {
        let valid = true;

        nomeMsg.textContent = "";
        telefoneMsg.textContent = "";

        const nome = nomeInput.value.trim();
        const telefoneNumeros = telefoneInput.value.replace(/\D/g, "");

        if (nome.length < 3) {
            nomeMsg.textContent = "Mínimo 3 caracteres.";
            valid = false;
        } else {
            const nomeRegex = /^[A-Za-zÀ-ÿ\s]+$/;
            if (!nomeRegex.test(nome)) {
                nomeMsg.textContent = "Digite apenas letras.";
                valid = false;
            }
        }

        if (telefoneNumeros.length !== 11) {
            telefoneMsg.textContent = "Número inválido.";
            valid = false;
        }

        if (!valid) {
            event.preventDefault();
        }
    });

    nomeInput.addEventListener("focus", () => nomeMsg.textContent = "");
    telefoneInput.addEventListener("focus", () => telefoneMsg.textContent = "");
});
