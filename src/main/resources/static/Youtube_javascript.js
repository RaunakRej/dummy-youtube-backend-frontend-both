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
    return JSON.parse(localStorage.getItem(WATCH_HISTORY_KEY)) || [];
  } catch (error) {
    console.error("Error reading watch history:", error);
    return [];
  }
}

function saveWatchHistory(history) {
  try {
    localStorage.setItem(WATCH_HISTORY_KEY, JSON.stringify(history));
  } catch (error) {
    console.error("Error saving watch history:", error);
  }
}

function addToWatchHistory(videoId) {
  if (localStorage.getItem(WATCH_HISTORY_PAUSED_KEY) === "true") {
    console.log("Watch history is paused.");
    return;
  }

  let history = getWatchHistory();

  videoId = String(videoId);

  // Remove duplicate
  history = history.filter((id) => String(id) !== videoId);

  // Add newest video at beginning
  history.unshift(videoId);

  // Maximum 100 history records
  history = history.slice(0, 100);

  saveWatchHistory(history);

  console.log("Watch history updated:", history);
}

// =====================================================
// LIKED VIDEOS
// =====================================================

function getLikedVideoIds() {
  try {
    return JSON.parse(localStorage.getItem(LIKED_VIDEOS_KEY)) || [];
  } catch (error) {
    console.error("Error reading liked videos:", error);

    return [];
  }
}

function saveLikedVideoIds(ids) {
  try {
    localStorage.setItem(LIKED_VIDEOS_KEY, JSON.stringify(ids));
  } catch (error) {
    console.error("Error saving liked videos:", error);
  }
}

function updateLikedVideosCount() {
  const likedVideoIds = getLikedVideoIds();
  const count = likedVideoIds.length;

  const countElements = [
    "likedVideosCount",
    "likedVideoCount",
    "liked-count",
    "likedCount",
  ];

  countElements.forEach((id) => {
    const element = document.getElementById(id);

    if (element) {
      element.textContent = count;
    }
  });

  console.log("Liked videos count:", count);
}

// =====================================================
// GET VIDEO BY ID
// =====================================================

function getVideoById(id) {
  return videos.find((video) => String(video.id) === String(id));
}

// =====================================================
// LOAD VIDEOS FROM SPRING BOOT API
// =====================================================

async function loadVideosFromAPI() {
  console.log("Fetching videos from Spring Boot API...");

  if (spinner) {
    spinner.style.display = "block";
  }

  try {
    const response = await fetch(API_URL);

    console.log("API response status:", response.status);

    if (!response.ok) {
      throw new Error(`API request failed: ${response.status}`);
    }

    const data = await response.json();

    console.log("Videos received from API:", data);

    if (!Array.isArray(data)) {
      throw new Error("API response is not an array.");
    }

    videos = data;

    // =================================================
    // RESTORE LIKE STATUS
    // =================================================

    const likedVideoIds = getLikedVideoIds();

    videos.forEach((video) => {
      video.liked = likedVideoIds.includes(String(video.id));
    });

    updateLikedVideosCount();

    // =================================================
    // RENDER VIDEOS
    // =================================================

    console.log("Calling renderVideos()...");

    renderVideos();
  } catch (error) {
    console.error("Error loading videos:", error);

    showAPIError("Unable to load videos from server.");
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
    document.getElementById("recommendedVideos"),
    document.getElementById("trendingVideos"),
    document.getElementById("musicVideos"),
    document.getElementById("movieVideos"),
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
// CREATE VIDEO CARD
// =====================================================

function createVideoCard(video) {
  const isLiked = video.liked === true;

  // ---------------------------------------------
  // Thumbnail
  // ---------------------------------------------

  const fallbackThumbnail = "https://via.placeholder.com/300x170?text=RTube";

  const thumbnail =
    video.thumbnail && String(video.thumbnail).trim() !== ""
      ? video.thumbnail
      : fallbackThumbnail;

  // ---------------------------------------------
  // Category
  // ---------------------------------------------

  const category = video.category || "Recommended";

  // ---------------------------------------------
  // Create HTML
  // ---------------------------------------------

  return `
        <div class="video-card">

            <article
                data-id="${video.id}"
                class="video-article"
            >

                <div class="thumbnail-container">

                    <img
                        src="${thumbnail}"
                        alt="${video.title || "Video"}"
                        loading="lazy"
                        onerror="
                            if (this.src !== '${fallbackThumbnail}') {
                                this.src='${fallbackThumbnail}';
                            }
                        "
                    >

                    <span class="dur">
                        ${video.duration || ""}
                    </span>

                </div>

                <div class="video-info">

                    <h3>
                        ${video.title || "Untitled Video"}
                    </h3>

                    <p>
                        ${video.channel || "Unknown Channel"}
                    </p>

                    <p>
                        ${video.views || "0 views"}
                        •
                        ${video.uploaded || ""}
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
// RENDER VIDEOS
// =====================================================

function renderVideos() {
  console.log("=================================");
  console.log("renderVideos() called");
  console.log("Total videos:", videos.length);
  console.log("=================================");

  // ---------------------------------------------
  // Get containers
  // ---------------------------------------------

  const recommendedContainer = document.getElementById("recommendedVideos");

  const trendingContainer = document.getElementById("trendingVideos");

  const musicContainer = document.getElementById("musicVideos");

  const movieContainer = document.getElementById("movieVideos");

  console.log("recommendedVideos:", recommendedContainer);

  console.log("trendingVideos:", trendingContainer);

  console.log("musicVideos:", musicContainer);

  console.log("movieVideos:", movieContainer);

  // ---------------------------------------------
  // Clear containers if available
  // ---------------------------------------------

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

  // ---------------------------------------------
  // Check if videos exist
  // ---------------------------------------------

  if (!videos || videos.length === 0) {
    console.warn("No videos available for rendering.");

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

  // ---------------------------------------------
  // Render every video
  // ---------------------------------------------

  videos.forEach((video) => {
    const category = String(video.category || "recommended")
      .trim()
      .toLowerCase();

    console.log(
      "Rendering video:",
      video.id,
      video.title,
      "Category:",
      category,
    );

    const card = createVideoCard(video);

    // -----------------------------------------
    // Trending
    // -----------------------------------------

    if (category.includes("trending")) {
      if (trendingContainer) {
        trendingContainer.insertAdjacentHTML("beforeend", card);
      }
    }

    // -----------------------------------------
    // Music
    // -----------------------------------------
    else if (category.includes("music") || category.includes("song")) {
      if (musicContainer) {
        musicContainer.insertAdjacentHTML("beforeend", card);
      }
    }

    // -----------------------------------------
    // Movies
    // -----------------------------------------
    else if (category.includes("movie") || category.includes("film")) {
      if (movieContainer) {
        movieContainer.insertAdjacentHTML("beforeend", card);
      }
    }

    // -----------------------------------------
    // Recommended
    // -----------------------------------------
    else {
      if (recommendedContainer) {
        recommendedContainer.insertAdjacentHTML("beforeend", card);
      }
    }
  });

  // ---------------------------------------------
  // Attach events
  // ---------------------------------------------

  attachVideoEvents();

  // ---------------------------------------------
  // Update liked count
  // ---------------------------------------------

  updateLikedVideosCount();

  // ---------------------------------------------
  // Debug information
  // ---------------------------------------------

  console.log(
    "Recommended cards:",
    recommendedContainer ? recommendedContainer.children.length : 0,
  );

  console.log(
    "Trending cards:",
    trendingContainer ? trendingContainer.children.length : 0,
  );

  console.log(
    "Music cards:",
    musicContainer ? musicContainer.children.length : 0,
  );

  console.log(
    "Movie cards:",
    movieContainer ? movieContainer.children.length : 0,
  );

  console.log(
    "Total video cards:",
    document.querySelectorAll(".video-card").length,
  );

  console.log("Video rendering completed.");
}

// =====================================================
// ATTACH VIDEO EVENTS
// =====================================================

function attachVideoEvents() {
  const articles = document.querySelectorAll("article[data-id]");

  console.log("Attaching events to:", articles.length, "videos");

  articles.forEach((article) => {
    // -----------------------------------------
    // Open video
    // -----------------------------------------

    article.addEventListener("click", function (event) {
      // Don't open video when clicking Like
      if (event.target.closest(".like-btn")) {
        return;
      }

      const videoId = article.dataset.id;

      console.log("Opening video:", videoId);

      addToWatchHistory(videoId);

      window.location.href = `video.html?id=${videoId}`;
    });

    // -----------------------------------------
    // Like button
    // -----------------------------------------

    const likeButton = article.querySelector(".like-btn");

    if (likeButton) {
      likeButton.addEventListener("click", async function (event) {
        event.preventDefault();
        event.stopPropagation();

        const videoId = likeButton.dataset.id;

        await toggleLike(videoId, likeButton);
      });
    }

    // -----------------------------------------
    // Hover effect
    // -----------------------------------------

    article.addEventListener("mouseenter", function () {
      article.style.transform = "translateY(-3px)";
    });

    article.addEventListener("mouseleave", function () {
      article.style.transform = "translateY(0)";
    });
  });
}

// =====================================================
// LIKE / UNLIKE VIDEO
// =====================================================

async function toggleLike(videoId, likeButton) {
  let likedVideoIds = getLikedVideoIds();

  const isCurrentlyLiked = likedVideoIds.includes(String(videoId));

  try {
    let response;

    // -----------------------------------------
    // UNLIKE
    // -----------------------------------------

    if (isCurrentlyLiked) {
      response = await fetch(`${API_URL}/${videoId}/like`, {
        method: "DELETE",
      });
    }

    // -----------------------------------------
    // LIKE
    // -----------------------------------------
    else {
      response = await fetch(`${API_URL}/${videoId}/like`, {
        method: "POST",
      });
    }

    if (!response.ok) {
      throw new Error(`Like API failed: ${response.status}`);
    }

    // -----------------------------------------
    // Update local storage
    // -----------------------------------------

    if (isCurrentlyLiked) {
      likedVideoIds = likedVideoIds.filter(
        (id) => String(id) !== String(videoId),
      );

      likeButton.classList.remove("liked");

      likeButton.textContent = "♡ Like";
    } else {
      likedVideoIds.push(String(videoId));

      likeButton.classList.add("liked");

      likeButton.textContent = "♥ Liked";
    }

    saveLikedVideoIds(likedVideoIds);

    updateLikedVideosCount();

    // Update global video object
    const video = getVideoById(videoId);

    if (video) {
      video.liked = !isCurrentlyLiked;
    }

    console.log("Like status updated:", videoId, !isCurrentlyLiked);
  } catch (error) {
    console.error("Error updating like:", error);

    alert("Unable to update like. Please try again.");
  }
}

// =====================================================
// SEARCH VIDEOS
// =====================================================

async function searchVideos() {
  const query = searchInput ? searchInput.value.trim() : "";

  if (!query) {
    loadVideosFromAPI();

    return;
  }

  console.log("Searching for:", query);

  if (spinner) {
    spinner.style.display = "block";
  }

  try {
    const url = `${SEARCH_API_URL}?keyword=${encodeURIComponent(query)}`;

    console.log("Search API URL:", url);

    const response = await fetch(url);

    if (!response.ok) {
      throw new Error(`Search failed: ${response.status}`);
    }

    const data = await response.json();

    console.log("Search results:", data);

    if (!Array.isArray(data)) {
      throw new Error("Search API did not return an array.");
    }

    videos = data;

    // Restore likes
    const likedVideoIds = getLikedVideoIds();

    videos.forEach((video) => {
      video.liked = likedVideoIds.includes(String(video.id));
    });

    renderVideos();
  } catch (error) {
    console.error("Search error:", error);

    showAPIError("Unable to search videos.");
  } finally {
    if (spinner) {
      spinner.style.display = "none";
    }
  }
}

// =====================================================
// SEARCH BUTTON
// =====================================================

if (searchBtn) {
  searchBtn.addEventListener("click", searchVideos);
}

// =====================================================
// SEARCH ENTER KEY
// =====================================================

if (searchInput) {
  searchInput.addEventListener("keydown", function (event) {
    if (event.key === "Enter") {
      event.preventDefault();

      searchVideos();
    }
  });
}

// =====================================================
// CLEAR SEARCH
// =====================================================

if (clearBtn) {
  clearBtn.addEventListener("click", function () {
    if (searchInput) {
      searchInput.value = "";
    }

    loadVideosFromAPI();
  });
}

// =====================================================
// SIDEBAR CATEGORY FILTER
// =====================================================

function showOnlySection(sectionId) {
  const sections = [
    "recommended-section",
    "trending-section",
    "music-section",
    "movies",
  ];

  sections.forEach((id) => {
    const section = document.getElementById(id);

    if (section) {
      section.style.display = id === sectionId ? "block" : "none";
    }
  });
}

// =====================================================
// SIDEBAR LINKS
// =====================================================

const sidebarLinks = document.querySelectorAll("aside a, aside button");

sidebarLinks.forEach((link) => {
  link.addEventListener("click", function () {
    const text = this.textContent.trim().toLowerCase();

    if (text.includes("home")) {
      showOnlySection("recommended-section");
    } else if (text.includes("trending")) {
      showOnlySection("trending-section");
    } else if (text.includes("music")) {
      showOnlySection("music-section");
    } else if (text.includes("movie")) {
      showOnlySection("movies");
    }
  });
});

// =====================================================
// WATCH LATER
// =====================================================

function getWatchLaterVideos() {
  try {
    return JSON.parse(localStorage.getItem("watchLaterVideos")) || [];
  } catch (error) {
    console.error("Error reading watch later:", error);

    return [];
  }
}

function saveWatchLaterVideos(ids) {
  localStorage.setItem("watchLaterVideos", JSON.stringify(ids));
}

function toggleWatchLater(videoId) {
  let ids = getWatchLaterVideos();

  videoId = String(videoId);

  if (ids.includes(videoId)) {
    ids = ids.filter((id) => String(id) !== videoId);
  } else {
    ids.push(videoId);
  }

  saveWatchLaterVideos(ids);

  console.log("Watch later videos:", ids);
}

// =====================================================
// SUBSCRIPTIONS
// =====================================================

function getSubscriptions() {
  try {
    return JSON.parse(localStorage.getItem("subscriptions")) || [];
  } catch (error) {
    return [];
  }
}

function saveSubscriptions(ids) {
  localStorage.setItem("subscriptions", JSON.stringify(ids));
}

function toggleSubscription(channel) {
  let subscriptions = getSubscriptions();

  if (subscriptions.includes(channel)) {
    subscriptions = subscriptions.filter((item) => item !== channel);
  } else {
    subscriptions.push(channel);
  }

  saveSubscriptions(subscriptions);

  console.log("Subscriptions:", subscriptions);
}

// =====================================================
// THEME
// =====================================================

function applyTheme(theme) {
  if (theme === "dark") {
    document.body.classList.add("dark-mode");
  } else {
    document.body.classList.remove("dark-mode");
  }
}

const savedTheme = localStorage.getItem("theme");

if (savedTheme) {
  applyTheme(savedTheme);
}

// =====================================================
// LOGOUT
// =====================================================

function logout() {
  localStorage.removeItem("isLoggedIn");

  localStorage.removeItem("username");

  localStorage.removeItem("userEmail");

  console.log("User logged out.");

  window.location.href = "youtube_mainhtml.html";
}

// =====================================================
// CREATE MENU
// =====================================================

function setupCreateMenu() {
  const createButton = document.getElementById("createButton");

  const createMenu = document.getElementById("createMenu");

  if (!createButton || !createMenu) {
    return;
  }

  createButton.addEventListener("click", function (event) {
    event.stopPropagation();

    createMenu.classList.toggle("show");
  });

  document.addEventListener("click", function () {
    createMenu.classList.remove("show");
  });
}

setupCreateMenu();

// =====================================================
// NOTIFICATION
// =====================================================

function setupNotifications() {
  const notificationButton = document.getElementById("notificationButton");

  const notificationPopup = document.getElementById("notificationPopup");

  const closeNotification = document.getElementById("closeNotification");

  if (notificationButton && notificationPopup) {
    notificationButton.addEventListener("click", function () {
      notificationPopup.classList.toggle("show");
    });
  }

  if (closeNotification) {
    closeNotification.addEventListener("click", function () {
      notificationPopup.classList.remove("show");
    });
  }
}

setupNotifications();

// =====================================================
// PROFILE MENU
// =====================================================

function setupProfileMenu() {
  const profileButton = document.getElementById("profileButton");

  const profileMenu = document.getElementById("profileMenu");

  if (!profileButton || !profileMenu) {
    return;
  }

  profileButton.addEventListener("click", function (event) {
    event.stopPropagation();

    profileMenu.classList.toggle("show");
  });

  document.addEventListener("click", function () {
    profileMenu.classList.remove("show");
  });
}

setupProfileMenu();

// =====================================================
// VOICE SEARCH
// =====================================================

function startVoiceSearch() {
  const SpeechRecognition =
    window.SpeechRecognition || window.webkitSpeechRecognition;

  if (!SpeechRecognition) {
    alert("Voice search is not supported in this browser.");

    return;
  }

  const recognition = new SpeechRecognition();

  recognition.lang = "en-IN";

  recognition.interimResults = false;

  recognition.maxAlternatives = 1;

  recognition.start();

  recognition.onresult = function (event) {
    const text = event.results[0][0].transcript;

    if (searchInput) {
      searchInput.value = text;
    }

    searchVideos();
  };

  recognition.onerror = function (event) {
    console.error("Voice search error:", event.error);
  };
}

// =====================================================
// VOICE SEARCH BUTTON
// =====================================================

const voiceSearchButton = document.getElementById("voiceSearchButton");

if (voiceSearchButton) {
  voiceSearchButton.addEventListener("click", startVoiceSearch);
}

// =====================================================
// INITIALIZE APPLICATION
// =====================================================

document.addEventListener("DOMContentLoaded", function () {
  console.log("DOM fully loaded.");

  updateLikedVideosCount();

  loadVideosFromAPI();
});
