document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");

    const amigoSelect = document.getElementById("amigo");
    const ferramentaSelect = document.getElementById("ferramenta");
    const dataDevolucaoInput = document.getElementById("data_devolucao");

    const amigoMsg = document.getElementById("msg-amigo");
    const ferramentaMsg = document.getElementById("msg-ferramenta");
    const dataMsg = document.getElementById("msg-data");


    form.addEventListener("submit", function (event) {
        let valid = true;

        amigoMsg.textContent = "";
        ferramentaMsg.textContent = "";
        dataMsg.textContent = "";

        if (amigoSelect.value === "") {
            amigoMsg.textContent = "Selecione um amigo.";
            valid = false;
        }

        if (ferramentaSelect.value === "") {
            ferramentaMsg.textContent = "Selecione uma ferramenta.";
            valid = false;
        }

        const dataDevolucao = dataDevolucaoInput.value;
        if (dataDevolucao === "") {
            dataMsg.textContent = "Informe a data de devolução.";
            valid = false;
        } else {
            const hoje = new Date();
            hoje.setHours(0, 0, 0, 0);
            const devolucao = new Date(dataDevolucao + "T00:00:00");
            if (devolucao < hoje) {
                dataMsg.textContent = "A devolução não pode ser antes de hoje.";
                valid = false;
            }
        }

        if (!valid) event.preventDefault();
    });

    amigoSelect.addEventListener("focus", () => amigoMsg.textContent = "");
    ferramentaSelect.addEventListener("focus", () => ferramentaMsg.textContent = "");
    dataDevolucaoInput.addEventListener("focus", () => dataMsg.textContent = "");
});