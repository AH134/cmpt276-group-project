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
    fetch(`/setRole?role=${role}`, { method: 'POST' }) // Example of setting role via POST request
        .then(response => {
            if (response.ok) {
                // Redirect to the respective dashboard based on role
                if (role === 'grower') {
                    window.location.href = '/growerdashboard';
                } else if (role === 'sponsor') {
                    window.location.href = '/sponsordashboard';
                }
            } else {
                console.error('Failed to set role.');
            }
        })
        .catch(error => console.error('Error setting role:', error));
}

// JavaScript to handle menu toggle and closing on outside click
document.addEventListener('DOMContentLoaded', function() {
    var menu = document.getElementById('menu');
    var hamburgerIcon = document.getElementById('hamburgerIcon');

    hamburgerIcon.addEventListener('click', function() {
        menu.classList.toggle('open');
    });

    // Close menu on outside click
    document.addEventListener('click', function(event) {
        var isClickInside = hamburgerIcon.contains(event.target) || menu.contains(event.target);
        if (!isClickInside) {
            menu.classList.remove('open');
        }
    });
});




function showPopup() {
    document.getElementById('popupMessage').style.display = 'block';
}

function closePopup() {
    document.getElementById('popupMessage').style.display = 'none';
}
function goToProfile() {
    window.location.href = '/profile';
}