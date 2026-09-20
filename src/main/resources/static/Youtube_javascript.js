// =====================================================
// RTube - YouTube JavaScript
// =====================================================

console.log("RTube frontend loaded.");

// =====================================================
// API CONFIGURATION
// =====================================================

const API_URL = "/api/videos";
const SEARCH_API_URL = "/api/videos/search";

// =====================================================
// GLOBAL VARIABLES
// =====================================================

let videos = [];

// =====================================================
// LOCAL STORAGE KEYS
// =====================================================

const LIKED_VIDEOS_KEY = "likedVideoIds";
const WATCH_HISTORY_KEY = "watchHistory";
const WATCH_HISTORY_PAUSED_KEY = "watchHistoryPaused";

// =====================================================
// DEFAULT THUMBNAIL
// =====================================================

const DEFAULT_THUMBNAIL = "/images/default-thumbnail.jpg";

// =====================================================
// DOM ELEMENTS
// =====================================================

const searchInput = document.getElementById("searchInput");
const searchBtn = document.getElementById("searchBtn");
const spinner = document.getElementById("spinner");
const clearBtn = document.getElementById("clearBtn");

// =====================================================
// WATCH HISTORY
// =====================================================

function getWatchHistory() {

    try {

        return JSON.parse(
            localStorage.getItem(WATCH_HISTORY_KEY)
        ) || [];

    } catch (error) {

        console.error(
            "Error reading watch history:",
            error
        );

        return [];
    }
}


function saveWatchHistory(history) {

    try {

        localStorage.setItem(
            WATCH_HISTORY_KEY,
            JSON.stringify(history)
        );

    } catch (error) {

        console.error(
            "Error saving watch history:",
            error
        );
    }
}


function addToWatchHistory(videoId) {

    if (
        localStorage.getItem(
            WATCH_HISTORY_PAUSED_KEY
        ) === "true"
    ) {

        console.log(
            "Watch history is paused."
        );

        return;
    }

    let history = getWatchHistory();

    videoId = String(videoId);

    history = history.filter(
        (id) => String(id) !== videoId
    );

    history.unshift(videoId);

    history = history.slice(0, 100);

    saveWatchHistory(history);

    console.log(
        "Watch history updated:",
        history
    );
}

// =====================================================
// LIKED VIDEOS
// =====================================================

function getLikedVideoIds() {

    try {

        return JSON.parse(
            localStorage.getItem(
                LIKED_VIDEOS_KEY
            )
        ) || [];

    } catch (error) {

        console.error(
            "Error reading liked videos:",
            error
        );

        return [];
    }
}


function saveLikedVideoIds(ids) {

    try {

        localStorage.setItem(
            LIKED_VIDEOS_KEY,
            JSON.stringify(ids)
        );

    } catch (error) {

        console.error(
            "Error saving liked videos:",
            error
        );
    }
}


function updateLikedVideosCount() {

    const likedVideoIds =
        getLikedVideoIds();

    const count =
        likedVideoIds.length;

    const countElements = [

        "likedVideosCount",
        "likedVideoCount",
        "liked-count",
        "likedCount",
        "likedVideosCountProfile"

    ];

    countElements.forEach((id) => {

        const element =
            document.getElementById(id);

        if (element) {

            element.textContent = count;
        }
    });

    console.log(
        "Liked videos count:",
        count
    );
}

// =====================================================
// GET VIDEO BY ID
// =====================================================

function getVideoById(id) {

    return videos.find(
        (video) =>
            String(video.id) === String(id)
    );
}

// =====================================================
// LOAD VIDEOS FROM SPRING BOOT API
// =====================================================

async function loadVideosFromAPI() {

    console.log(
        "Fetching videos from Spring Boot API..."
    );

    if (spinner) {

        spinner.style.display = "block";
    }

    try {

        const response =
            await fetch(API_URL);

        console.log(
            "API response status:",
            response.status
        );

        if (!response.ok) {

            throw new Error(
                `API request failed: ${response.status}`
            );
        }

        const data =
            await response.json();

        console.log(
            "Videos received from API:",
            data
        );

        if (!Array.isArray(data)) {

            throw new Error(
                "API response is not an array."
            );
        }

        videos = data;

        // Restore like status
        const likedVideoIds =
            getLikedVideoIds();

        videos.forEach((video) => {

            video.liked =
                likedVideoIds.includes(
                    String(video.id)
                );
        });

        updateLikedVideosCount();

        console.log(
            "Calling renderVideos()..."
        );

        renderVideos();

        const requestedSection =
            new URLSearchParams(window.location.search).get("section");

        const sectionId =
            requestedSection === "trending"
                ? "trending-section"
                : requestedSection === "music"
                    ? "music-section"
                    : requestedSection === "movies"
                        ? "movies"
                        : "recommended-section";

        showOnlySection(sectionId);

    } catch (error) {

        console.error(
            "Error loading videos:",
            error
        );

        showAPIError(
            "Unable to load videos from server."
        );

    } finally {

        if (spinner) {

            spinner.style.display = "none";
        }
    }
}

// =====================================================
// API ERROR
// =====================================================

function showAPIError(message) {

    console.error(message);

    const containers = [

        document.getElementById(
            "recommendedVideos"
        ),

        document.getElementById(
            "trendingVideos"
        ),

        document.getElementById(
            "musicVideos"
        ),

        document.getElementById(
            "movieVideos"
        )
    ];

    containers.forEach((container) => {

        if (container) {

            container.innerHTML = `

                <div style="
                    padding:20px;
                    text-align:center;
                    color:#ff4444;
                    font-size:16px;
                ">

                    ${message}

                </div>

            `;
        }
    });
}

// =====================================================
// SAFE THUMBNAIL
// =====================================================

function getSafeThumbnail(video) {

    if (!video) {

        return DEFAULT_THUMBNAIL;
    }

    const thumbnail =
        String(
            video.thumbnail || ""
        ).trim();

    if (!thumbnail) {

        return DEFAULT_THUMBNAIL;
    }

    // Reject all placeholder URLs
    if (
        thumbnail.toLowerCase().includes(
            "placeholder"
        )
    ) {

        return DEFAULT_THUMBNAIL;
    }

    return thumbnail;
}

// =====================================================
// CREATE VIDEO CARD
// =====================================================

function createVideoCard(video) {

    const isLiked =
        video.liked === true;

    const thumbnail =
        getSafeThumbnail(video);

    const category =
        video.category ||
        "Recommended";

    const title =
        video.title ||
        "Untitled Video";

    const channel =
        video.channel ||
        "Unknown Channel";

    const views =
        video.views ||
        "0 views";

    const uploaded =
        video.uploaded ||
        "";

    const duration =
        video.duration ||
        "";

    return `

        <div class="video-card">

            <article
                data-id="${video.id}"
                class="video-article"
            >

                <div class="thumbnail-container">

                    <img
                        src="${thumbnail}"
                        alt="${title}"
                        loading="lazy"
                        onerror="
                            this.onerror=null;
                            this.src='${DEFAULT_THUMBNAIL}';
                        "
                    >

                    <span class="dur">
                        ${duration}
                    </span>

                </div>


                <div class="video-info">

                    <h3>
                        ${title}
                    </h3>

                    <p>
                        ${channel}
                    </p>

                    <p>
                        ${views}
                        •
                        ${uploaded}
                    </p>

                    <p class="video-category">
                        ${category}
                    </p>

                    <button
                        class="like-btn ${isLiked ? "liked" : ""}"
                        data-id="${video.id}"
                        type="button"
                    >
                        ${isLiked ? "♥ Liked" : "♡ Like"}
                    </button>

                </div>

            </article>

        </div>

    `;
}

// =====================================================
// GET NORMALIZED CATEGORY
// =====================================================

function getVideoCategory(video) {

    const rawCategory =
        video.category ||
        video.videoCategory ||
        video.type ||
        "recommended";

    return String(rawCategory)
        .trim()
        .toLowerCase();
}

// =====================================================
// RENDER VIDEOS
// =====================================================

function renderVideos() {

    console.log("=================================");
    console.log("renderVideos() called");
    console.log("Total videos:", videos.length);
    console.log("=================================");

    const recommendedContainer =
        document.getElementById(
            "recommendedVideos"
        );

    const trendingContainer =
        document.getElementById(
            "trendingVideos"
        );

    const musicContainer =
        document.getElementById(
            "musicVideos"
        );

    const movieContainer =
        document.getElementById(
            "movieVideos"
        );

    // Clear containers

    if (recommendedContainer) {

        recommendedContainer.innerHTML = "";
    }

    if (trendingContainer) {

        trendingContainer.innerHTML = "";
    }

    if (musicContainer) {

        musicContainer.innerHTML = "";
    }

    if (movieContainer) {

        movieContainer.innerHTML = "";
    }

    if (!videos || videos.length === 0) {

        console.warn(
            "No videos available."
        );

        if (recommendedContainer) {

            recommendedContainer.innerHTML = `

                <div style="
                    padding:30px;
                    text-align:center;
                    font-size:18px;
                ">

                    No videos found.

                </div>

            `;
        }

        return;
    }

    // =================================================
    // RENDER ALL VIDEOS
    // =================================================

    videos.forEach((video) => {

        const category =
            getVideoCategory(video);

        console.log(
            "VIDEO:",
            video.id,
            "| TITLE:",
            video.title,
            "| CATEGORY:",
            category
        );

        const card =
            createVideoCard(video);

        // =================================================
        // TRENDING
        // =================================================

        if (
            category === "trending" ||
            category.includes("trending")
        ) {

            console.log(
                " -> TRENDING:",
                video.title
            );

            if (trendingContainer) {

                trendingContainer.insertAdjacentHTML(
                    "beforeend",
                    card
                );
            }

        }

        // =================================================
        // MUSIC
        // =================================================

        else if (
            category === "music" ||
            category === "song" ||
            category === "songs" ||
            category.includes("music") ||
            category.includes("song")
        ) {

            console.log(
                " -> MUSIC:",
                video.title
            );

            if (musicContainer) {

                musicContainer.insertAdjacentHTML(
                    "beforeend",
                    card
                );
            }

        }

        // =================================================
        // MOVIES
        // =================================================

        else if (
            category === "movie" ||
            category === "movies" ||
            category === "film" ||
            category === "films" ||
            category.includes("movie") ||
            category.includes("film")
        ) {

            console.log(
                " -> MOVIES:",
                video.title
            );

            if (movieContainer) {

                movieContainer.insertAdjacentHTML(
                    "beforeend",
                    card
                );
            }

        }

        // =================================================
        // RECOMMENDED / HOME
        // =================================================

        else {

            console.log(
                " -> RECOMMENDED:",
                video.title
            );

            if (recommendedContainer) {

                recommendedContainer.insertAdjacentHTML(
                    "beforeend",
                    card
                );
            }
        }

    });

    attachVideoEvents();

    updateLikedVideosCount();

    console.log(
        "Recommended cards:",
        recommendedContainer
            ? recommendedContainer.children.length
            : 0
    );

    console.log(
        "Trending cards:",
        trendingContainer
            ? trendingContainer.children.length
            : 0
    );

    console.log(
        "Music cards:",
        musicContainer
            ? musicContainer.children.length
            : 0
    );

    console.log(
        "Movie cards:",
        movieContainer
            ? movieContainer.children.length
            : 0
    );

    console.log(
        "Total video cards:",
        document.querySelectorAll(
            ".video-card"
        ).length
    );

    console.log(
        "Video rendering completed."
    );
}

// =====================================================
// ATTACH VIDEO EVENTS
// =====================================================

function attachVideoEvents() {

    const articles =
        document.querySelectorAll(
            "article[data-id]"
        );

    console.log(
        "Attaching events to:",
        articles.length,
        "videos"
    );

    articles.forEach((article) => {

        // =================================================
        // OPEN VIDEO
        // =================================================

        article.addEventListener(
            "click",
            function (event) {

                if (
                    event.target.closest(
                        ".like-btn"
                    )
                ) {

                    return;
                }

                const videoId =
                    article.dataset.id;

                console.log(
                    "Opening video:",
                    videoId
                );

                addToWatchHistory(
                    videoId
                );

                window.location.href =
                    `/video?id=${videoId}`;
            }
        );

        // =================================================
        // LIKE BUTTON
        // =================================================

        const likeButton =
            article.querySelector(
                ".like-btn"
            );

        if (likeButton) {

            likeButton.addEventListener(
                "click",
                async function (event) {

                    event.preventDefault();
                    event.stopPropagation();

                    const videoId =
                        likeButton.dataset.id;

                    await toggleLike(
                        videoId,
                        likeButton
                    );
                }
            );
        }

        // =================================================
        // HOVER
        // =================================================

        article.addEventListener(
            "mouseenter",
            function () {

                article.style.transform =
                    "translateY(-3px)";
            }
        );

        article.addEventListener(
            "mouseleave",
            function () {

                article.style.transform =
                    "translateY(0)";
            }
        );
    });
}

// =====================================================
// LIKE / UNLIKE VIDEO
// =====================================================

async function toggleLike(
    videoId,
    likeButton
) {

    let likedVideoIds =
        getLikedVideoIds();

    const isCurrentlyLiked =
        likedVideoIds.includes(
            String(videoId)
        );

    try {

        let response;

        if (isCurrentlyLiked) {

            response = await fetch(
                `${API_URL}/${videoId}/like`,
                {
                    method: "DELETE"
                }
            );

        } else {

            response = await fetch(
                `${API_URL}/${videoId}/like`,
                {
                    method: "POST"
                }
            );
        }

        if (!response.ok) {

            throw new Error(
                `Like API failed: ${response.status}`
            );
        }

        // Update local storage

        if (isCurrentlyLiked) {

            likedVideoIds =
                likedVideoIds.filter(
                    (id) =>
                        String(id) !==
                        String(videoId)
                );

            likeButton.classList.remove(
                "liked"
            );

            likeButton.textContent =
                "♡ Like";

        } else {

            likedVideoIds.push(
                String(videoId)
            );

            likeButton.classList.add(
                "liked"
            );

            likeButton.textContent =
                "♥ Liked";
        }

        saveLikedVideoIds(
            likedVideoIds
        );

        updateLikedVideosCount();

        const video =
            getVideoById(videoId);

        if (video) {

            video.liked =
                !isCurrentlyLiked;
        }

        console.log(
            "Like status updated:",
            videoId,
            !isCurrentlyLiked
        );

    } catch (error) {

        console.error(
            "Error updating like:",
            error
        );

        alert(
            "Unable to update like. Please try again."
        );
    }
}

// =====================================================
// SEARCH VIDEOS
// =====================================================

async function searchVideos() {

    const query =
        searchInput
            ? searchInput.value.trim()
            : "";

    if (!query) {

        loadVideosFromAPI();

        return;
    }

    console.log(
        "Searching for:",
        query
    );

    if (spinner) {

        spinner.style.display =
            "block";
    }

    try {

        const url =
            `${SEARCH_API_URL}?keyword=${encodeURIComponent(query)}`;

        console.log(
            "Search API URL:",
            url
        );

        const response =
            await fetch(url);

        if (!response.ok) {

            throw new Error(
                `Search failed: ${response.status}`
            );
        }

        const data =
            await response.json();

        console.log(
            "Search results:",
            data
        );

        if (!Array.isArray(data)) {

            throw new Error(
                "Search API did not return an array."
            );
        }

        videos = data;

        const likedVideoIds =
            getLikedVideoIds();

        videos.forEach((video) => {

            video.liked =
                likedVideoIds.includes(
                    String(video.id)
                );
        });

        renderVideos();

    } catch (error) {

        console.error(
            "Search error:",
            error
        );

        showAPIError(
            "Unable to search videos."
        );

    } finally {

        if (spinner) {

            spinner.style.display =
                "none";
        }
    }
}

// =====================================================
// SEARCH BUTTON
// =====================================================

if (searchBtn) {

    searchBtn.addEventListener(
        "click",
        searchVideos
    );
}

// =====================================================
// SEARCH ENTER KEY
// =====================================================

if (searchInput) {

    searchInput.addEventListener(
        "keydown",
        function (event) {

            if (event.key === "Enter") {

                event.preventDefault();

                searchVideos();
            }
        }
    );

    searchInput.addEventListener(
        "input",
        function () {

            if (
                searchInput.value.trim() === ""
            ) {

                loadVideosFromAPI();
            }
        }
    );
}

// =====================================================
// CLEAR SEARCH
// =====================================================

function toggleClearButton() {

    if (!clearBtn || !searchInput) {

        return;
    }

    clearBtn.style.display =
        searchInput.value.trim()
            ? "flex"
            : "none";
}

function clearSearch() {

    if (searchInput) {

        searchInput.value = "";
    }

    toggleClearButton();

    loadVideosFromAPI();
}

if (clearBtn) {

    clearBtn.addEventListener(
        "click",
        function () {

            clearSearch();
        }
    );
}

// =====================================================
// SHOW ONLY ONE SECTION
// =====================================================

function showOnlySection(sectionId) {

    const sections = [

        "recommended-section",
        "trending-section",
        "music-section",
        "movies"

    ];

    sections.forEach((id) => {

        const section =
            document.getElementById(id);

        if (section) {

            if (id === sectionId) {

                section.style.display =
                    "block";

            } else {

                section.style.display =
                    "none";
            }
        }
    });

    console.log(
        "Showing section:",
        sectionId
    );
}

// =====================================================
// SIDEBAR CATEGORY NAVIGATION
// =====================================================

function setupSidebarNavigation() {

    const sidebarLinks =
        document.querySelectorAll(
            "aside a, aside button, aside li"
        );

    console.log(
        "Sidebar links found:",
        sidebarLinks.length
    );

    sidebarLinks.forEach((link) => {

        link.addEventListener(
            "click",
            function (event) {

                const text =
                    this.textContent
                        .trim()
                        .toLowerCase();

                console.log(
                    "Sidebar clicked:",
                    text
                );

                // HOME

                if (
                    text.includes("home")
                ) {

                    event.preventDefault();

                    showOnlySection(
                        "recommended-section"
                    );

                    return;
                }

                // TRENDING

                if (
                    text.includes("trending")
                ) {

                    event.preventDefault();

                    showOnlySection(
                        "trending-section"
                    );

                    return;
                }

                // MUSIC

                if (
                    text.includes("music")
                ) {

                    event.preventDefault();

                    showOnlySection(
                        "music-section"
                    );

                    return;
                }

                // MOVIES

                if (
                    text.includes("movie")
                ) {

                    event.preventDefault();

                    showOnlySection(
                        "movies"
                    );

                    return;
                }
            }
        );
    });
}

// =====================================================
// WATCH LATER
// =====================================================

function getWatchLaterVideos() {

    try {

        return JSON.parse(
            localStorage.getItem(
                "watchLaterVideos"
            )
        ) || [];

    } catch (error) {

        console.error(
            "Error reading watch later:",
            error
        );

        return [];
    }
}


function saveWatchLaterVideos(ids) {

    localStorage.setItem(
        "watchLaterVideos",
        JSON.stringify(ids)
    );
}


function toggleWatchLater(videoId) {

    let ids =
        getWatchLaterVideos();

    videoId = String(videoId);

    if (ids.includes(videoId)) {

        ids = ids.filter(
            (id) =>
                String(id) !== videoId
        );

    } else {

        ids.push(videoId);
    }

    saveWatchLaterVideos(ids);

    console.log(
        "Watch later videos:",
        ids
    );
}

// =====================================================
// SUBSCRIPTIONS
// =====================================================

function getSubscriptions() {

    try {

        return JSON.parse(
            localStorage.getItem(
                "subscriptions"
            )
        ) || [];

    } catch (error) {

        console.error(
            "Error reading subscriptions:",
            error
        );

        return [];
    }
}


function saveSubscriptions(ids) {

    localStorage.setItem(
        "subscriptions",
        JSON.stringify(ids)
    );
}


function toggleSubscription(channel) {

    let subscriptions =
        getSubscriptions();

    if (
        subscriptions.includes(channel)
    ) {

        subscriptions =
            subscriptions.filter(
                (item) =>
                    item !== channel
            );

    } else {

        subscriptions.push(channel);
    }

    saveSubscriptions(
        subscriptions
    );

    console.log(
        "Subscriptions:",
        subscriptions
    );
}

// =====================================================
// THEME
// =====================================================

function applyTheme(theme) {

    if (theme === "dark") {

        document.body.classList.add(
            "dark-mode"
        );

    } else {

        document.body.classList.remove(
            "dark-mode"
        );
    }
}

function setupAppearanceMenu() {

    const appearanceButton =
        document.getElementById(
            "appearanceBtn"
        );

    const appearanceMenu =
        document.getElementById(
            "appearanceMenu"
        );

    const backButton =
        document.getElementById(
            "backAppearance"
        );

    if (
        !appearanceButton ||
        !appearanceMenu
    ) {

        return;
    }

    appearanceButton.addEventListener(
        "click",
        function (event) {

            event.stopPropagation();

            appearanceMenu.classList.add(
                "show"
            );
        }
    );

    appearanceMenu.addEventListener(
        "click",
        function (event) {

            event.stopPropagation();
        }
    );

    if (backButton) {

        backButton.addEventListener(
            "click",
            function () {

                appearanceMenu.classList.remove(
                    "show"
                );
            }
        );
    }

    appearanceMenu
        .querySelectorAll(
            'input[name="theme"]'
        )
        .forEach(function (themeInput) {

            themeInput.addEventListener(
                "change",
                function () {

                    const theme =
                        themeInput.value;

                    localStorage.setItem(
                        "theme",
                        theme
                    );

                    applyTheme(theme);
                }
            );
        });
}

setupAppearanceMenu();


const savedTheme =
    localStorage.getItem("theme");

if (savedTheme) {

    applyTheme(savedTheme);
}

// =====================================================
// LOGOUT
// =====================================================

function logout() {

    localStorage.removeItem(
        "isLoggedIn"
    );

    localStorage.removeItem(
        "username"
    );

    localStorage.removeItem(
        "userEmail"
    );

    localStorage.removeItem(
        "userId"
    );

    console.log(
        "User logged out."
    );

    window.location.href =
        "/login";
}

// =====================================================
// CREATE MENU
// =====================================================

function setupCreateMenu() {

    const createButton =
        document.getElementById(
            "createBtn"
        );

    const createMenu =
        document.getElementById(
            "createMenu"
        );

    const uploadVideoButton =
        document.getElementById(
            "uploadVideoBtn"
        );

    const goLiveButton =
        document.getElementById(
            "goLiveBtn"
        );

    const createPostButton =
        document.getElementById(
            "createPostBtn"
        );

    if (
        !createButton ||
        !createMenu
    ) {

        return;
    }

    if (uploadVideoButton) {

        uploadVideoButton.addEventListener(
            "click",
            function (event) {

                event.stopPropagation();

                window.location.href =
                    "/upload";
            }
        );
    }

    if (goLiveButton) {

        goLiveButton.addEventListener(
            "click",
            function () {

                alert(
                    "Live streaming is not available in this demo."
                );
            }
        );
    }

    if (createPostButton) {

        createPostButton.addEventListener(
            "click",
            function () {

                alert(
                    "Creating posts is not available in this demo."
                );
            }
        );
    }

    createButton.addEventListener(
        "click",
        function (event) {

            event.stopPropagation();

            createMenu.classList.toggle(
                "show"
            );
        }
    );

    document.addEventListener(
        "click",
        function () {

            createMenu.classList.remove(
                "show"
            );
        }
    );
}

setupCreateMenu();

// =====================================================
// NOTIFICATION
// =====================================================

function setupNotifications() {

    const notificationButton =
        document.getElementById(
            "notificationBtn"
        );

    const notificationPopup =
        document.getElementById(
            "notificationPopup"
        );

    const closeNotification =
        document.getElementById(
            "closeNotification"
        );

    if (
        notificationButton &&
        notificationPopup
    ) {

        notificationButton.addEventListener(
            "click",
            function () {

                notificationPopup.classList.toggle(
                    "show"
                );
            }
        );
    }

    if (closeNotification) {

        closeNotification.addEventListener(
            "click",
            function () {

                notificationPopup.classList.remove(
                    "show"
                );
            }
        );
    }
}

setupNotifications();

// =====================================================
// PROFILE MENU
// =====================================================

function setupProfileMenu() {

    const profileButton =
        document.getElementById(
            "profileBtn"
        );

    const profileMenu =
        document.getElementById(
            "profileMenu"
        );

    if (
        !profileButton ||
        !profileMenu
    ) {

        return;
    }

    profileButton.addEventListener(
        "click",
        function (event) {

            event.stopPropagation();

            profileMenu.classList.toggle(
                "show"
            );
        }
    );

    document.addEventListener(
        "click",
        function () {

            profileMenu.classList.remove(
                "show"
            );
        }
    );
}

setupProfileMenu();

// =====================================================
// HELP MENU
// =====================================================

function setupHelpMenu() {

    const helpButton =
        document.getElementById(
            "helpBtn"
        );

    const helpMenu =
        document.getElementById(
            "helpMenu"
        );

    const closeHelp =
        document.getElementById(
            "closeHelp"
        );

    const helpSearch =
        document.getElementById(
            "helpSearch"
        );

    if (
        !helpButton ||
        !helpMenu
    ) {

        return;
    }

    function closeHelpMenu() {

        helpMenu.classList.remove(
            "is-open"
        );

        helpMenu.setAttribute(
            "aria-hidden",
            "true"
        );
    }

    helpButton.addEventListener(
        "click",
        function (event) {

            event.preventDefault();
            event.stopPropagation();

            const isOpen =
                helpMenu.classList.toggle(
                    "is-open"
                );

            helpMenu.setAttribute(
                "aria-hidden",
                String(!isOpen)
            );

            if (isOpen && helpSearch) {

                helpSearch.focus();
            }
        }
    );

    helpMenu.addEventListener(
        "click",
        function (event) {

            event.stopPropagation();
        }
    );

    if (closeHelp) {

        closeHelp.addEventListener(
            "click",
            closeHelpMenu
        );
    }

    if (helpSearch) {

        helpSearch.addEventListener(
            "input",
            function () {

                const query =
                    helpSearch.value
                        .trim()
                        .toLowerCase();

                helpMenu
                    .querySelectorAll(
                        ".help-item"
                    )
                    .forEach(function (item) {

                        item.hidden =
                            !item.textContent
                                .toLowerCase()
                                .includes(query);
                    });
            }
        );
    }

    document.addEventListener(
        "click",
        closeHelpMenu
    );

    document.addEventListener(
        "keydown",
        function (event) {

            if (
                event.key === "Escape"
            ) {

                closeHelpMenu();
            }
        }
    );
}

setupHelpMenu();

// =====================================================
// VOICE SEARCH
// =====================================================

function startVoiceSearch() {

    const SpeechRecognition =
        window.SpeechRecognition ||
        window.webkitSpeechRecognition;

    if (!SpeechRecognition) {

        alert(
            "Voice search is not supported in this browser."
        );

        return;
    }

    const recognition =
        new SpeechRecognition();

    recognition.lang =
        "en-IN";

    recognition.interimResults =
        false;

    recognition.maxAlternatives =
        1;

    recognition.start();

    recognition.onresult =
        function (event) {

            const text =
                event.results[0][0]
                    .transcript;

            if (searchInput) {

                searchInput.value =
                    text;
            }

            searchVideos();
        };

    recognition.onerror =
        function (event) {

            console.error(
                "Voice search error:",
                event.error
            );
        };
}

// =====================================================
// VOICE SEARCH BUTTON
// =====================================================

const voiceSearchButton =
    document.getElementById(
        "voiceBtn"
    );

if (voiceSearchButton) {

    voiceSearchButton.addEventListener(
        "click",
        startVoiceSearch
    );
}

// =====================================================
// INITIALIZE APPLICATION
// =====================================================

document.addEventListener(
    "DOMContentLoaded",
    function () {

        console.log(
            "DOM fully loaded."
        );

        updateLikedVideosCount();

        setupSidebarNavigation();

        const requestedSection =
            new URLSearchParams(window.location.search).get("section");
        const requestedSearch =
            new URLSearchParams(window.location.search).get("search");

        if (requestedSearch && searchInput) {
            searchInput.value = requestedSearch;
            searchVideos();
            return;
        }

        if (requestedSection === "trending") {
            showOnlySection("trending-section");
        } else if (requestedSection === "music") {
            showOnlySection("music-section");
        } else if (requestedSection === "movies") {
            showOnlySection("movies");
        } else {
            showOnlySection("recommended-section");
        }

        loadVideosFromAPI();
    }
);
