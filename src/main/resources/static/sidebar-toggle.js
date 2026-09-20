(function () {
    const menuButton = document.getElementById("menuBtn")
        || document.getElementById("homeBtn");

    if (!menuButton) {
        return;
    }

    menuButton.addEventListener("click", function () {
        document.body.classList.toggle("sidebar-collapsed");
        menuButton.setAttribute(
            "aria-label",
            document.body.classList.contains("sidebar-collapsed")
                ? "Expand navigation"
                : "Collapse navigation"
        );
    });
})();
