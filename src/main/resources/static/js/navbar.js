const changeNavLinkColor = () => {
    const path = window.location.pathname;
    const slashIndex = path.indexOf("/", 1);
    const currentPath = slashIndex !== -1 ? path.substring(0, slashIndex) : path;

    const navlinks = document.querySelectorAll(".navbar a");
    navlinks.forEach((el) => {
        if (el.getAttribute("href").startsWith(currentPath)) {
            el.classList.add("active");
        }

    });
}

document.addEventListener("DOMContentLoaded", changeNavLinkColor)