document.addEventListener("DOMContentLoaded", () => {
    const path = window.location.pathname;
    console.log(path)
    const slashIndex = path.indexOf("/", 1);
    const currentPath = slashIndex !== -1 ? path.substring(0, slashIndex) : path;

    const navlinks = document.querySelectorAll(".navbar a");
    navlinks.forEach((el) => {
        if (el.getAttribute("href").startsWith(currentPath)) {
            el.classList = "text-decoration-none fs-5 active";
        }

    });
})

const hamburgerBtn = document.getElementById("hamburger-btn");
hamburgerBtn.addEventListener("click", () => {
    const menu = document.getElementById("hamburger-menu");
    const menuStatus = menu.dataset.menuStatus;
    if (menuStatus === "closed") {
        menu.style.display = "block";
        menu.dataset.menuStatus = "open";
    } else {
        menu.style.display = "none";
        menu.dataset.menuStatus = "closed";
    }
})