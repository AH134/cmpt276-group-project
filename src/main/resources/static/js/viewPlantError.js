document.addEventListener("DOMContentLoaded", () => {
    setTimeout(() => {
        const errorDiv = document.getElementById("error-container");
        if (errorDiv) {
            errorDiv.classList.remove("d-flex");
            errorDiv.style.display = "none";
        }
    }, 3000);
})