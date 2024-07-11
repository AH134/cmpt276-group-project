document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('search-input');
    const filterButton = document.getElementById('filter-btn');
    const priceFilter = document.getElementById('price-filter');
    const cityFilter = document.getElementById('city-filter');

    searchInput.addEventListener('keypress', function (event) {
        if (event.key === 'Enter') {
            searchPlants();
        }
    });

    filterButton.addEventListener('click', function () {
        fetchPlants();
    });

    function searchPlants() {
        const query = document.getElementById('search-input').value;
        fetch(`/api/plants/search?query=${query}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                const plantGrid = document.getElementById('plant-grid');
                plantGrid.innerHTML = '';
                if (Array.isArray(data) && data.length > 0) {
                    data.forEach(plant => {
                        plantGrid.appendChild(createPlantCard(plant));
                    });
                } else {
                    plantGrid.innerHTML = '<p>No plants found</p>';
                }
            })
            .catch(error => {
                console.error('Error fetching plant data:', error);
            });
    }

    function fetchPlants() {
        const query = buildQuery();
        fetch(query)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                const filteredPlants = filterPlants(data);
                displayPlants(filteredPlants);
            })
            .catch(error => {
                console.error('Error fetching plant data:', error);
            });
    }

    function buildQuery() {
        const priceFilterValue = priceFilter.value;
        const cityFilterValue = cityFilter.value;

        let query = `/api/plants`;

        if (priceFilterValue !== 'all') 
            query += `?price=${priceFilterValue}`;

        if (cityFilterValue !== 'all') 
            query += `?city=${cityFilterValue}`;

        return query;
    }

    function filterPlants(plants) {
        const priceFilterValue = priceFilter.value;

        if (priceFilterValue === 'all')
            return plants;

        const [minPrice, maxPrice] = priceFilterValue.split('-').map(Number);

        return plants.filter(plant => {
            return (plant.price >= minPrice && plant.price <= maxPrice);
        });
    }

    function displayPlants(plants) {
        const plantGrid = document.getElementById('plant-grid');
        plantGrid.innerHTML = '';

        if (Array.isArray(plants) && plants.length > 0) {
            plants.forEach(plant => {
                plantGrid.appendChild(createPlantCard(plant));
            });
        } else
            plantGrid.innerHTML = '<p>No plants found</p>';
    }

    function createPlantCard(plant) {
        const card = document.createElement('div');
        card.classList.add('plant-card');

        const name = document.createElement('h2');
        name.textContent = plant.name;

        const price = document.createElement('p');
        price.textContent = `Price: $${plant.price}`;

        card.appendChild(name);
        card.appendChild(price);

        return card;
    }

    fetchPlants();
});

//active link
document.addEventListener('DOMContentLoaded', function () {
    const navLinks = document.querySelectorAll('nav a');
    const currentPage = window.location.pathname.split('/').pop();

    navLinks.forEach(link => {
        if (link.getAttribute('href') === currentPage) {
            link.classList.add('active');
        }
    });
});

