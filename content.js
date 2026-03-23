let debounceTimer = null;

function procesarTokens(textoGenerado) {
    const palabras = textoGenerado.trim().split(/\s+/).length;
    const tokensCalculados = Math.ceil(palabras * 1.3);

    if (debounceTimer) {
        clearTimeout(debounceTimer);
    }

    debounceTimer = setTimeout(() => {
        console.log("Reporte final generado. Tokens de salida: " + tokensCalculados);
        
        chrome.runtime.sendMessage({
            accion: "REGISTRAR_CONSUMO",
            tokens_salida: tokensCalculados,
            tokens_entrada: 15
        });
    }, 2000);
}

const observer = new MutationObserver((mutations) => {
    const elementosRespuesta = document.querySelectorAll('.markdown');
    if (elementosRespuesta.length > 0) {
        const ultimaRespuesta = elementosRespuesta[elementosRespuesta.length - 1];
        if (ultimaRespuesta && ultimaRespuesta.innerText) {
            procesarTokens(ultimaRespuesta.innerText);
        }
    }
});

observer.observe(document.body, { childList: true, subtree: true });
