document.addEventListener('DOMContentLoaded', () => {
    const usernameInput = document.getElementById('usernameInput');
    const passwordInput = document.getElementById('passwordInput');
    const roleInput = document.getElementById('roleInput');

    function editProfile() {
        profileDisplay.style.username = usernameInput.value;
        profileDisplay.style.password = passwordInput.value;
        profileDisplay.style.role = roleInput.value;
    }
    
    usernameInput.addEventListener('input', editProfile);
    passwordInput.addEventListener('input', editProfile);
    roleInput.addEventListener('input', editProfile);
});