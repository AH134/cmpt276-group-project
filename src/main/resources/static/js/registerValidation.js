const verifyPwd = () => {
    const p1 = document.getElementById("password1");
    const p2 = document.getElementById("password2");
    const p1Err = document.getElementById("error-pwd1");
    const p2Err = document.getElementById("error-pwd2");

    p1Err.innerText = "";
    p2Err.innerText = "";

    const pwdRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    let isValidPwd = true;

    if (!pwdRegex.test(p1.value)) {
        p1Err.innerText = "Password must contain at least: eight characters, one lowercase letter, one uppercase letter, one digit, and one special character (@$!%*?&).";
        isValidPwd = false;
    }

    if (!pwdRegex.test(p2.value)) {
        p2Err.innerText = "Password must contain at least: eight characters, one lowercase letter, one uppercase letter, one digit, and one special character (@$!%*?&).";
        isValidPwd = false;
    }

    if (p1.value !== p2.value) {
        p2Err.innerText = "Passwords do not match.";
        isValidPwd = false;
    }

    return isValidPwd;
}
const verifyName = () => {
    const name = document.getElementById("username");
    const nameErr = document.getElementById("error-name");
    nameErr.innerText = "";
    let isValidName = true;

    if (name.value == "") {
        nameErr.innerText = "Username cannot be empty.";
        isValidName = false;
    }

    return isValidName;

}
const verifyEmail = () => {
    const email = document.getElementById("email");
    const emailErr = document.getElementById("error-email");
    emailErr.innerText = "";
    let isValidEmail = true;

    if (email.value == "") {
        emailErr.innerText = "Email cannot be empty.";
        isValidEmail = false;
    }

    return isValidEmail;
}
const form = document.getElementById("register-form");
form.addEventListener("submit", (e) => {
    if (!verifyName() || !verifyEmail() || !verifyPwd()){
        e.preventDefault();
    }
})

const signUpBtn = document.getElementById("signup-btn");
signUpBtn.addEventListener("click", (e) => {
    if (!verifyName() || !verifyEmail() || !verifyPwd()){
        e.preventDefault();
    }
})

const togglePass = document.querySelectorAll("#togglePass");
const pwd1 = document.querySelector("#password1");
const pwd2 = document.querySelector("#password2");
togglePass.forEach(function(pass){
    pass.addEventListener("click", (e) => {
        const type = pwd1.getAttribute("type") == "password" 
            ? "text" : "password";
        pwd1.setAttribute("type", type);
        pwd2.setAttribute("type", type);
        togglePass[0].classList.toggle("bi-eye");
        togglePass[1].classList.toggle("bi-eye");
    })
});