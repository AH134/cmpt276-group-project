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

const form = document.getElementById("edit-form");
form.addEventListener("submit", (e) => {
    if (!verifyName() || !verifyEmail()) {
        e.preventDefault();
    }
})

const saveBtn = document.getElementById("save-btn");
saveBtn.addEventListener("click", (e) => {
    if (!verifyName() || !verifyEmail()) {
        e.preventDefault();
    }
})