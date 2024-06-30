document.addEventListener("DOMContentLoaded", function () {
    fetch('/api/users')
        .then(response => response.json())
        .then(data => {
            const userTableBody = document.getElementById('userTableBody');
            data.forEach(user => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.role}</td>
                    <td>$${user.balance}</td>
                    <td>${user.posts}</td>
                    <td>${user.plants}</td>
                `;
                userTableBody.appendChild(row);
            });
        });
});
