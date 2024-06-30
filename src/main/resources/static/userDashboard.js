document.addEventListener('DOMContentLoaded', function() {
    showPopup();
    document.addEventListener('click', function(event) {
        const popup = document.getElementById('popupMessage');
        if (!popup.contains(event.target) && event.target !== document.querySelector('.message-btn button')) {
            closePopup();
        }
    });
});

function selectRole(role) {
    window.location.href = `/homePage?role=${role}`;
}

function goToProfile() {
    window.location.href = '/profile';
}

function showPopup() {
    document.getElementById('popupMessage').style.display = 'block';
}

function closePopup() {
    document.getElementById('popupMessage').style.display = 'none';
}
