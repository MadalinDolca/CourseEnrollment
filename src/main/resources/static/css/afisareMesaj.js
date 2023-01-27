function afisareMesaj(alerta, text, cooldown = 3000, actiune = {}) {
    alerta.html(text) // adauga mesajul
    alerta.show() // afiseaza alerta

    setTimeout(() => {
        alerta.hide() // ascunde mesajul
        actiune() // executa functia
    }, cooldown)
}