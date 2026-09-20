const WATCH_LATER_KEY = "watchLaterVideos";
const VIDEO_API = "/api/videos";

const watchVideoList = document.getElementById("watchVideoList");
const watchEmpty = document.getElementById("watchEmpty");
const playlistCount = document.getElementById("playlistCount");
const videoCount = document.getElementById("videoCount");
const profileButton = document.getElementById("profileBtn");
const profileMenu = document.getElementById("profileMenu");
const createButton = document.getElementById("createBtn");
const createMenu = document.getElementById("createMenu");
const notificationButton = document.getElementById("notificationBtn");
const notificationPopup = document.getElementById("notificationPopup");

let apiVideos = [];

function getSavedVideoIds() {
    try {
        const saved = JSON.parse(
            localStorage.getItem(WATCH_LATER_KEY) || "[]"
        );

        return Array.isArray(saved)
            ? saved.map((video) => String(video.id))
            : [];
    } catch (error) {
        console.error("Unable to read Watch Later videos:", error);
        return [];
    }
}

function getThumbnail(video) {
    return video.thumbnail || "/images/default-thumbnail.jpg";
}

function renderVideos(videos) {
    watchVideoList.innerHTML = "";
    playlistCount.textContent = videos.length;
    videoCount.textContent = videos.length;
    watchEmpty.hidden = videos.length > 0;

    videos.forEach(function (video) {
        const row = document.createElement("article");
        row.className = "watch-video-row";
        row.tabIndex = 0;

        row.innerHTML = `
            <span class="drag-handle" aria-hidden="true">=</span>
            <div class="watch-video-thumbnail">
                <img src="${getThumbnail(video)}" alt="${video.title || "Video"}"
                    onerror="this.onerror=null; this.src='/images/default-thumbnail.jpg';">
                <span>${video.duration || ""}</span>
            </div>
            <div class="watch-video-info">
                <h3>${video.title || "Untitled video"}</h3>
                <p>${video.channel || "Unknown channel"}</p>
                <p>${video.views || "0 views"}${video.uploaded ? ` • ${video.uploaded}` : ""}</p>
            </div>
        `;

        const openVideo = function () {
            window.location.href =
                `/video?id=${encodeURIComponent(video.id)}`;
        };

        row.addEventListener("click", openVideo);
        row.addEventListener("keydown", function (event) {
            if (event.key === "Enter" || event.key === " ") {
                event.preventDefault();
                openVideo();
            }
        });

        watchVideoList.appendChild(row);
    });
}

async function loadVideosFromAPI() {
    try {
        const response = await fetch(VIDEO_API);

        if (!response.ok) {
            throw new Error(`Video API failed: ${response.status}`);
        }

        const data = await response.json();

        if (!Array.isArray(data)) {
            throw new Error("Video API did not return an array.");
        }

        apiVideos = data;

        const savedIds = getSavedVideoIds();
        const savedVideos = savedIds.length > 0
            ? savedIds
                .map((id) => apiVideos.find((video) => String(video.id) === id))
                .filter(Boolean)
            : apiVideos;

        renderVideos(
            savedVideos.length > 0
                ? savedVideos
                : apiVideos
        );
    } catch (error) {
        console.error("Unable to load Watch Later videos:", error);
        renderVideos([]);
    }
}

function playVideo(video) {
    if (video) {
        window.location.href =
            `/video?id=${encodeURIComponent(video.id)}`;
    }
}

document.getElementById("playAllBtn").addEventListener("click", function () {
    playVideo(apiVideos[0]);
});

document.getElementById("shuffleBtn").addEventListener("click", function () {
    const randomVideo =
        apiVideos[Math.floor(Math.random() * apiVideos.length)];

    playVideo(randomVideo);
});

document.getElementById("clearBtn").addEventListener("click", function () {
    if (confirm("Clear all Watch Later videos?")) {
        localStorage.removeItem(WATCH_LATER_KEY);
        renderVideos(apiVideos);
    }
});

profileButton.addEventListener("click", function (event) {
    event.stopPropagation();
    profileMenu.classList.toggle("show");
});

profileMenu.addEventListener("click", function (event) {
    event.stopPropagation();
});

document.addEventListener("click", function () {
    profileMenu.classList.remove("show");
    if (createMenu) createMenu.classList.remove("show");
    if (notificationPopup) notificationPopup.classList.remove("show");
});

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
        profileMenu.classList.remove("show");
        if (createMenu) createMenu.classList.remove("show");
    });
    const closeNotification = document.getElementById("closeNotification");
    if (closeNotification) {
        closeNotification.addEventListener("click", function () {
            notificationPopup.classList.remove("show");
        });
    }
}

document.getElementById("logoutBtn").addEventListener("click", function () {
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("username");
    localStorage.removeItem("userEmail");
    localStorage.removeItem("userId");
});

const username = localStorage.getItem("username");
const email = localStorage.getItem("userEmail");

if (username) {
    document.getElementById("profileUsername").textContent = username;
}

if (email) {
    document.getElementById("profileEmail").textContent = email;
}

if (localStorage.getItem("theme") === "dark") {
    document.body.classList.add("dark-mode");
}

loadVideosFromAPI();
