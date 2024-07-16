const form = document.getElementById("filter-form");

const prevPageBtn = document.getElementById("prev-page");
if (prevPageBtn !== null) {
    prevPageBtn.addEventListener("click", (e) => {
        e.preventDefault();

        const prevPageNo = prevPageBtn.dataset.prevPageNo;
        if (prevPageNo < 0) {
            return;
        }

        const pageNoInput = document.createElement("input");
        pageNoInput.setAttribute("type", "hidden");
        pageNoInput.setAttribute("name", "pageNo");
        pageNoInput.setAttribute("value", prevPageNo)
        form.appendChild(pageNoInput);
        form.submit();
    })
}

const nextPageBtn = document.getElementById("next-page");
if (nextPageBtn !== null) {
    nextPageBtn.addEventListener("click", (e) => {
        e.preventDefault();

        const nextPageNo = nextPageBtn.dataset.nextPageNo;
        console.log(nextPageNo)

        const pageNoInput = document.createElement("input");
        pageNoInput.setAttribute("type", "hidden");
        pageNoInput.setAttribute("name", "pageNo");
        pageNoInput.setAttribute("value", nextPageNo)
        form.appendChild(pageNoInput);
        form.submit();
    })
}

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

document.addEventListener("DOMContentLoaded", () => {
    const url = new URLSearchParams(window.location.search)
    const selectedProvince = url.get("province");

    const cities = citiesByProvince[selectedProvince];
    if (selectedProvince === "all" || selectedProvince === null) {
        return;
    }

    cities.forEach(city => {
        const option = document.createElement("option");
        option.value = city;
        option.textContent = city;
        if (city === url.get("city")) {
            option.selected = true;
        }
        citySelect.appendChild(option);
    });

})