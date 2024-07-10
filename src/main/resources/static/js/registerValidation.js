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
        p1Err.innerText = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character";
        isValidPwd = false;
    }

    if (!pwdRegex.test(p2.value)) {
        p2Err.innerText = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character";
        isValidPwd = false;
    }

    if (p1.value !== p2.value) {
        p2Err.innerText = "Passwords do not match.";
        isValidPwd = false;
    }

    return isValidPwd;
}
const form = document.getElementById("register-form");
form.addEventListener("submit", (e) => {
    if (!verifyPwd()) {
        e.preventDefault();
    }
})


const signUpBtn = document.getElementById("signup-btn");
signUpBtn.addEventListener("click", (e) => {
    if (!verifyPwd()) {
        e.preventDefault();
    }
})