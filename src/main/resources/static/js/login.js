const togglePass = document.querySelector("#togglePass");
const pwd = document.querySelector("#password");
togglePass.addEventListener("click", (e) => {
    const type = pwd.getAttribute("type") == "password" 
        ? "text" : "password";
    pwd.setAttribute("type", type);
    togglePass.classList.toggle("bi-eye");
})