document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");

    const nomeInput = document.getElementById("nome");
    const marcaInput = document.getElementById("marca");
    const aquisicaoInput = document.getElementById("aquisicao");

    const nomeMsg = document.getElementById("msg-nome");
    const marcaMsg = document.getElementById("msg-marca");
    const aquisicaoMsg = document.getElementById("msg-aquisicao");

    // ======== Máscara de valor monetário com "R$" ========
    aquisicaoInput.addEventListener("input", function (e) {
        let valor = e.target.value.replace(/\D/g, ""); // remove tudo que não for número

        if (valor === "") {
            e.target.value = "";
            return;
        }

        // Converte para formato com centavos
        valor = (parseInt(valor, 10) / 100).toFixed(2);

        // Substitui ponto por vírgula e adiciona separadores de milhar
        valor = valor
            .replace(".", ",")
            .replace(/\B(?=(\d{3})+(?!\d))/g, ".");

        e.target.value = "R$ " + valor;
    });

    // ======== Validação do formulário ========
    form.addEventListener("submit", function (event) {
        let valid = true;

        // Limpa mensagens anteriores
        nomeMsg.textContent = "";
        marcaMsg.textContent = "";
        aquisicaoMsg.textContent = "";

        const nome = nomeInput.value.trim();
        const marca = marcaInput.value.trim();
        const aquisicao = aquisicaoInput.value.trim();

        const nomeRegex = /^[A-Za-zÀ-ÿ\s]+$/;

        // ======= Validação do Nome =======
        if (nome.length < 3 || nome.length > 35) {
            nomeMsg.textContent = "Mínimo 3 caracteres.";
            valid = false;
        } else if (!nomeRegex.test(nome)) {
            nomeMsg.textContent = "Digite apenas letras.";
            valid = false;
        }

        // ======= Validação da Marca =======
        if (marca.length < 3 || marca.length > 15) {
            marcaMsg.textContent = "Mínimo 3 caracteres.";
            valid = false;
        } else if (!nomeRegex.test(marca)) {
            marcaMsg.textContent = "Digite apenas letras.";
            valid = false;
        }

        // ======= Validação do Valor de Aquisição =======
        if (aquisicao === "" || !aquisicao.startsWith("R$")) {
            aquisicaoMsg.textContent = "Informe o valor de aquisição.";
            valid = false;
        } else {
            // Remove o R$ e converte para número
            const valorNumerico = parseFloat(aquisicao.replace("R$", "").replace(/\./g, "").replace(",", "."));
            if (isNaN(valorNumerico) || valorNumerico <= 0) {
                aquisicaoMsg.textContent = "Valor inválido.";
                valid = false;
            }
        }

        if (!valid) {
            event.preventDefault();
        }
    });

    // ======== Limpa mensagens ao focar no campo ========
    nomeInput.addEventListener("focus", () => nomeMsg.textContent = "");
    marcaInput.addEventListener("focus", () => marcaMsg.textContent = "");
    aquisicaoInput.addEventListener("focus", () => aquisicaoMsg.textContent = "");
});
