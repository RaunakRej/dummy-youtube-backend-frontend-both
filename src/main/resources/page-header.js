(function () {
    const profileButton = document.getElementById("profileBtn");
    const profileMenu = document.getElementById("profileMenu");
    const createButton = document.getElementById("createBtn");
    const createMenu = document.getElementById("createMenu");
    const notificationButton = document.getElementById("notificationBtn");
    const notificationPopup = document.getElementById("notificationPopup");

    function closeMenus() {
        if (profileMenu) profileMenu.classList.remove("show");
        if (createMenu) createMenu.classList.remove("show");
        if (notificationPopup) notificationPopup.classList.remove("show");
    }

    if (profileButton && profileMenu) {
        profileButton.addEventListener("click", function (event) {
            event.stopPropagation();
            profileMenu.classList.toggle("show");
            if (createMenu) createMenu.classList.remove("show");
            if (notificationPopup) notificationPopup.classList.remove("show");
        });
        profileMenu.addEventListener("click", function (event) {
            event.stopPropagation();
        });
    }

    if (createButton && createMenu) {
        createButton.addEventListener("click", function (event) {
            event.stopPropagation();
            createMenu.classList.toggle("show");
        });
        document.getElementById("uploadVideoBtn").addEventListener("click", function () {
            window.location.href = "/upload";
        });
        document.getElementById("goLiveBtn").addEventListener("click", function () {
            alert("Live streaming is not available in this demo.");
        });
        document.getElementById("createPostBtn").addEventListener("click", function () {
            alert("Creating posts is not available in this demo.");
        });
    }

    if (notificationButton && notificationPopup) {
        notificationButton.addEventListener("click", function (event) {
            event.stopPropagation();
            notificationPopup.classList.toggle("show");
            if (profileMenu) profileMenu.classList.remove("show");
            if (createMenu) createMenu.classList.remove("show");
        });
        document.getElementById("closeNotification").addEventListener("click", closeMenus);
    }

    const logoutButton = document.getElementById("logoutBtn");
    if (logoutButton) {
        logoutButton.addEventListener("click", function () {
            localStorage.removeItem("isLoggedIn");
            localStorage.removeItem("username");
            localStorage.removeItem("userEmail");
            localStorage.removeItem("userId");
        });
    }

    document.addEventListener("click", closeMenus);
})();
