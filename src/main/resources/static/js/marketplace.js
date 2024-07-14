document.addEventListener('DOMContentLoaded', () => {
    displayPlants();
});

function displayPlants() {
    fetch(`/api/plants`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const plantGrid = document.getElementById('plant-grid');
            plantGrid.innerHTML = '';

            if (data.length > 0) {
                data.forEach(plant => {
                    plantGrid.appendChild(createPlantCard(plant));
                });
            } else
                plantGrid.innerHTML = '<p>No plants found</p>';
        })
        .catch(error => {
            console.error('Error fetching plant data:', error);
        });
}

function createPlantCard(plant) {
    const plantCard = document.createElement('div');
    plantCard.className = 'plant-card';
    plantCard.innerHTML = `
        <h2>${plant.name}</h2>
        <p>Price: $${plant.price}</p>
    `;
    return plantCard;
}

document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('search-input');
    const filterButton = document.getElementById('filter-btn');
    const priceFilter = document.getElementById('price-filter');
    const provinceFilter = document.getElementById('province-filter');
    const cityFilter = document.getElementById('city-filter');

    searchInput.addEventListener('keypress', function (event) {
        if (event.key === 'Enter') {
            searchPlants();
        }
    });

    filterButton.addEventListener('click', function () {
        searchPlants();
    });

    function searchPlants() {
        const query = document.getElementById('search-input').value.toLowerCase();
        const priceFilterValue = priceFilter.value;
        const provinceFilterValue = provinceFilter.value;
        const cityFilterValue = cityFilter.value;

        fetch(`/api/plants`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {

                let filteredPlants = data;

                if (query) {
                    filteredPlants = filteredPlants.filter(plant =>
                        plant.name.toLowerCase().includes(query)
                    );
                }

                //Price filter
                if (priceFilterValue !== 'all') {
                    const [minPrice, maxPrice] = priceFilterValue.split('-').map(Number);
                    filteredPlants = filteredPlants.filter(plant =>
                        plant.price >= minPrice && plant.price < maxPrice
                    );
                }

                //Province filter
                if (provinceFilterValue !== 'all') {
                    filteredPlants = filteredPlants.filter(plant =>
                        plant.province === provinceFilterValue
                    );
                }

                //City filter
                if (cityFilterValue !== 'all') {
                    filteredPlants = filteredPlants.filter(plant =>
                        plant.city === cityFilterValue
                    );
                }

                //Update the UI
                const plantGrid = document.getElementById('plant-grid');
                plantGrid.innerHTML = '';

                if (filteredPlants.length > 0) {
                    filteredPlants.forEach(plant => {
                        plantGrid.appendChild(createPlantCard(plant));
                    });
                } else
                    plantGrid.innerHTML = '<p>No plants found</p>';

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
                const filteredPlants = filteredPlants(data);
                displayPlants(filteredPlants);
            })
            .catch(error => {
                console.error('Error fetching plant data:', error);
            });
    }

    fetchPlants();
});

//Province and city selection
const citiesByProvince = {
    AB: ["Calgary", "Edmonton"],
    BC: ["Vancouver", "Burnaby", "Surrey"],
    MB: ["Winnipeg", "Brandon"],
    NB: ["Moncton", "Saint John"],
    NL: ["Corner Brook", "St. John's"],
    NS: ["Halifax", "Sydney"],
    ON: ["Toronto", "Ottawa", "Mississauga", "Brampton"],
    PE: ["Charlottetown"],
    QC: ["Montreal", "Quebec"],
    SK: ["Saskatoon", "Regina"],
    NT: ["Yellowknife"],
    NU: ["Iqaluit"],
    YT: ["Whitehorse"]
};

const provinceSelect = document.getElementById("province-filter");
const citySelect = document.getElementById("city-filter");

provinceSelect.addEventListener("change", function () {
    const selectedProvince = provinceSelect.value;

    const cities = citiesByProvince[selectedProvince];
    cities.forEach(city => {
        const option = document.createElement("option");
        option.value = city;
        option.textContent = city;
        citySelect.appendChild(option);
    });
});

//Active link
document.addEventListener('DOMContentLoaded', function () {
    const navLinks = document.querySelectorAll('nav a');
    const current = window.location.pathname.split('/').pop();

    navLinks.forEach(link => {
        if (link.getAttribute('href') === current) {
            link.classList.add('active');
        }
    });

    //Page scroll 
    // let currentPage = 1;

    // function prevPage() {
    //     if (currentPage > 1) {
    //         currentPage--;
    //         updatePageInfo();
    //         //loadPageData(currentPage);
    //     }
    // }

    // function nextPage() {
    //     let totalPage = 10; //replace or remove
    //     if (currentPage < totalPage) {
    //         currentPage++;
    //         updatePageInfo();
    //         //loadPageData(currentPage);
    //     }
    // }

    // function updatePageInfo() {
    //     document.getElementById("page-info").textContent = `Page ${currentPage}`;
    // }

    // document.getElementById("prev-page-btn").addEventListener("click", prevPage);
    // document.getElementById("next-page-btn").addEventListener("click", nextPage);

});
