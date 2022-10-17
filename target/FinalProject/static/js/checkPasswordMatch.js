function checkPasswordMatch(fieldConfirmPassword) {
    if (fieldConfirmPassword.value !== document.querySelector("#password").value) {
        let lang = sessionStorage.getItem('lang');
        let value = sessionStorage.getItem('value');

        if (lang === "uk_UA")
            fieldConfirmPassword.setCustomValidity(value);
        else
            fieldConfirmPassword.setCustomValidity(value);

    } else
        fieldConfirmPassword.setCustomValidity("");
}