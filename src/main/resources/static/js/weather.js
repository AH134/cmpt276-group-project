const apiKey = "97fe4738603e09df64b3e21eaee1d185";
const getWeather = async (city) => {
    const apiUrl = `https://api.openweathermap.org/data/2.5/weather?units=metric&q=${city}&appid=${apiKey}`;
    const res = await fetch(apiUrl);
    const data = await res.json();

    const weather = data.weather[0];
    const mainWeather = weather.main;
    const icon = `https://openweathermap.org/img/wn/${weather.icon}.png`
    const temp = data.main.temp;

    return {
        mainWeather,
        icon,
        temp,
    }
}

document.addEventListener("DOMContentLoaded", async () => {
    const weatherDiv = document.getElementById("weather-container");

    const city = weatherDiv.dataset.city;
    const weather = await getWeather(city);
    weatherDiv.innerHTML = `
    <div class="d-flex align-items-center">
        <img src="${weather.icon}" alt="weather-icon">
        <p class="m-0">${weather.mainWeather}</p>
        &nbsp;
        &nbsp;
        <p class="m-0">${weather.temp}°C</p>
    </div>
        `;
})