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

const form = document.getElementById("edit-form");
form.addEventListener("submit", (e) => {
    if (!verifyName()){
        e.preventDefault();
    }
})

const saveBtn = document.getElementById("save-btn");
saveBtn.addEventListener("click", (e) => {
    if (!verifyName()){
        e.preventDefault();
    }
})