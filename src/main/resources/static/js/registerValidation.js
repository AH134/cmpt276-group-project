// https://www.youtube.com/watch?v=hAqglX3Jm7Y

const APP = {
    init(){
        APP.addListeners();
    },
    addListeners(){
        let form = document.getElementsByTagName("form")[0];
        let username = document.getElementById("username");
        let email = document.getElementById("email");
        let password = document.getElementById("password1");
        let password2 = document.getElementById("password2");

        username.addEventListener('change', APP.testUsername);
        email.addEventListener('change', APP.testEmail);
        password.addEventListener('input', APP.testPassword);
        password2.addEventListener('input', APP.testPassword2);

        username.addEventListener('invalid', APP.fail);
        email.addEventListener('invalid', APP.fail);
        password.addEventListener('invalid', APP.fail);
        password2.addEventListener('invalid', APP.fail);
    },
    testUsername(evt){
        let username = evt.target;
        username.parentElement.querySelector('.errorMsg').textContent = '';
        username.setCustomValidity('');
        let state = username.checkValidity();

        if(state){
            const userReg = /^\S+$/;
            if(userReg.test(username.value) == false){
                username.setCustomValidity('No spaces allowed');
                username.reportValidity();
            }
        }
    },
    testEmail(evt){
        let email = evt.target;
        email.parentElement.querySelector('.errorMsg').textContent = '';
        email.setCustomValidity('');
        let state = email.checkValidity();

        if(state){
        }
    },
    testPassword(evt){
        let password = evt.target;
        let pass1 = password.value;
        let pass2 = document.getElementById("password2").value;
        password.parentElement.querySelector('.errorMsg').textContent = '';
        document.getElementById("password2").parentElement.querySelector('.errorMsg').textContent = '';
        password.setCustomValidity('');
        let state = password.checkValidity();

        if(state){
            const passReg = /^\S+$/;
            if(pass1 != pass2){
                password.setCustomValidity('Passwords do not match');
                password.reportValidity();
            }
            else if(passReg.test(pass1) == false){
                password.setCustomValidity('No spaces allowed');
                password.reportValidity();
            }
        }
    },
    testPassword2(evt){
        let password2 = evt.target;
        let pass1 = document.getElementById("password1").value;
        let pass2 = password2.value;
        document.getElementById("password1").parentElement.querySelector('.errorMsg').textContent = '';
        password2.parentElement.querySelector('.errorMsg').textContent = '';
        password2.setCustomValidity('');
        let state = password2.checkValidity();

        if(state){
            const passReg = /^\S+$/;
            if(pass1 != pass2){
                password2.setCustomValidity('Passwords do not match');
                password2.reportValidity();
            }
            else if(passReg.test(pass2) == false){
                password2.setCustomValidity('No spaces allowed');
                password2.reportValidity();
            }
        }
    },
    fail(evt){
        let field = evt.target;
        switch(field.id){
            case 'username':
                let username = field.parentElement.querySelector('.errorMsg');
                username.textContent = 'No spaces allowed';
                break;
            case 'email':
                let email = field.parentElement.querySelector('.errorMsg');
                email.textContent = 'Incorrect email address';
                break;
            case 'password1':
                let password = field.parentElement.querySelector('.errorMsg');
                password.textContent = 'Passwords do not match and/or no spaces allowed';
                break;
            case 'password2':
                let password2 = field.parentElement.querySelector('.errorMsg');
                password2.textContent = 'Passwords do not match and/or no spaces allowed';
                break;
        }
    }
};
document.addEventListener('DOMContentLoaded', APP.init);