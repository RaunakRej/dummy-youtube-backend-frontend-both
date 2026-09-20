const settingsMenu = document.getElementById("settingsMenu");
const sections = document.querySelectorAll(".settings-section");
const settingsSearch = document.getElementById("settingsSearch");
const profileButton = document.getElementById("profileBtn");
const profileMenu = document.getElementById("profileMenu");
const logoutButton = document.getElementById("settingsLogout");

function applySettingsTheme(theme) {
    const darkMode = theme === "dark"
        || (theme === "system"
            && window.matchMedia("(prefers-color-scheme: dark)").matches);

    document.body.classList.toggle("dark-mode", darkMode);
}

function getSavedSettings() {
    try {
        return JSON.parse(
            localStorage.getItem("rtubeSettings") || "{}"
        );
    } catch (error) {
        console.error("Unable to load saved settings:", error);
        return {};
    }
}

const savedSettings = getSavedSettings();
const savedTheme = savedSettings.theme || localStorage.getItem("theme") || "system";

applySettingsTheme(savedTheme);

const savedUsername = localStorage.getItem("username");
const savedEmail = localStorage.getItem("userEmail");
const settingsUsername = document.getElementById("settingsUsername");
const settingsEmail = document.getElementById("settingsEmail");

if (savedUsername && settingsUsername) {
    settingsUsername.textContent = savedUsername;
}

if (savedEmail && settingsEmail) {
    settingsEmail.textContent = savedEmail;
}

document.querySelectorAll('input[name="theme"]').forEach(function (radio) {
    radio.checked = radio.value === savedTheme;

    radio.addEventListener("change", function () {
        const settings = getSavedSettings();
        settings.theme = radio.value;
        localStorage.setItem("rtubeSettings", JSON.stringify(settings));
        localStorage.setItem("theme", radio.value);
        applySettingsTheme(radio.value);
    });
});

settingsMenu.querySelectorAll("li").forEach(function (item) {
    item.addEventListener("click", function () {
        settingsMenu.querySelectorAll("li").forEach(function (menuItem) {
            menuItem.classList.remove("active");
        });

        sections.forEach(function (section) {
            section.classList.remove("active-section");
        });

        item.classList.add("active");
        const section = document.getElementById(item.dataset.section);

        if (section) {
            section.classList.add("active-section");
        }
    });
});

settingsSearch.addEventListener("input", function () {
    const query = settingsSearch.value.trim().toLowerCase();

    settingsMenu.querySelectorAll("li").forEach(function (item) {
        item.style.display = item.textContent.toLowerCase().includes(query)
            ? ""
            : "none";
    });
});

profileButton.addEventListener("click", function (event) {
    event.stopPropagation();
    profileMenu.classList.toggle("is-open");
});

profileMenu.addEventListener("click", function (event) {
    event.stopPropagation();
});

document.addEventListener("click", function () {
    profileMenu.classList.remove("is-open");
});

logoutButton.addEventListener("click", function () {
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("username");
    localStorage.removeItem("userEmail");
    localStorage.removeItem("userId");
});
