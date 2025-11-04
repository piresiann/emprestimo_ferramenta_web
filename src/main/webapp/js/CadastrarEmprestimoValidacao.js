document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");

    const amigoSelect = document.getElementById("amigo");
    const ferramentaSelect = document.getElementById("ferramenta");
    const dataEmprestimoInput = document.getElementById("data_emprestimo");
    const dataDevolucaoInput = document.getElementById("data_devolucao");

    const amigoMsg = document.getElementById("msg-amigo");
    const ferramentaMsg = document.getElementById("msg-ferramenta");
    const dataMsg = document.getElementById("msg-data");

    // ======== Validação do formulário ========
    form.addEventListener("submit", function (event) {
        let valid = true;

        // Limpa mensagens anteriores
        amigoMsg.textContent = "";
        ferramentaMsg.textContent = "";
        dataMsg.textContent = "";

        const dataEmprestimo = dataEmprestimoInput.value;
        const dataDevolucao = dataDevolucaoInput.value;

        // ======= Validação do Amigo =======
        if (amigoSelect.value === "") {
            amigoMsg.textContent = "Selecione um amigo.";
            valid = false;
        }

        // ======= Validação da Ferramenta =======
        if (ferramentaSelect.value === "") {
            ferramentaMsg.textContent = "Selecione uma ferramenta.";
            valid = false;
        }

        // ======= Validação das Datas =======
        if (dataEmprestimo === "") {
            dataMsg.textContent = "Informe a data do empréstimo.";
            valid = false;
        } else if (dataDevolucao === "") {
            dataMsg.textContent = "Informe a data de devolução.";
            valid = false;
        } else {
            const dataInicio = new Date(dataEmprestimo);
            const dataFim = new Date(dataDevolucao);

            if (dataFim < dataInicio) {
                dataMsg.textContent = "A devolução não pode ser antes do empréstimo.";
                valid = false;
            }
        }

        if (!valid) {
            event.preventDefault();
        }
    });

    // ======== Limpa mensagens ao focar no campo ========
    amigoSelect.addEventListener("focus", () => amigoMsg.textContent = "");
    ferramentaSelect.addEventListener("focus", () => ferramentaMsg.textContent = "");
    dataEmprestimoInput.addEventListener("focus", () => dataMsg.textContent = "");
    dataDevolucaoInput.addEventListener("focus", () => dataMsg.textContent = "");
});