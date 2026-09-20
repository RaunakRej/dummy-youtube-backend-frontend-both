```javascript
/* =====================================================
   VIDEO DETAILS PAGE
   Loads video information from Spring Boot API
===================================================== */

// =====================================================
// GET VIDEO ID FROM URL
// =====================================================

const params = new URLSearchParams(window.location.search);

let rawId = params.get("id");


// =====================================================
// FIX VIDEO ID
// =====================================================

let id = rawId;

if (id && id.includes("=")) {
    id = id.split("=").pop();
}

if (id) {
    id = id.trim();
}

console.log("=================================");
console.log("VIDEO DETAILS PAGE");
console.log("Raw ID:", rawId);
console.log("Clean Video ID:", id);

```javascript
// =====================================================
// SPRING BOOT API URL
// =====================================================

const API_BASE_URL = "http://localhost:8080/api/videos";

let API_URL = "";

if (id) {
    API_URL = `${API_BASE_URL}/${encodeURIComponent(id)}`;
}

console.log("API URL:", API_URL);
```



// =====================================================
// GLOBAL VARIABLES
// =====================================================

let titleElement;
let playerElement;
let viewsElement;
let uploadedElement;
let channelElement;
let descriptionElement;


// =====================================================
// INITIALIZE PAGE
// =====================================================

document.addEventListener("DOMContentLoaded", function () {

    titleElement = document.getElementById("title");
    playerElement = document.getElementById("player");
    viewsElement = document.getElementById("views");
    uploadedElement = document.getElementById("uploaded");
    channelElement = document.getElementById("channel");
    descriptionElement = document.getElementById("description");

    console.log("DOM elements loaded:");
    console.log("title:", titleElement);
    console.log("player:", playerElement);
    console.log("views:", viewsElement);
    console.log("uploaded:", uploadedElement);
    console.log("channel:", channelElement);
    console.log("description:", descriptionElement);

    setupShareButton();

    loadVideo();
});


// =====================================================
// LOAD VIDEO FROM SPRING BOOT API
// =====================================================

async function loadVideo() {

    if (!id) {

        console.error("Video ID is missing.");

        showError("Video ID is missing.");

        return;
    }

    try {

        console.log("Calling API:");
        console.log(API_URL);

        const response = await fetch(API_URL);

        console.log(
            "API response status:",
            response.status
        );

        if (!response.ok) {

            throw new Error(
                `Video API error: ${response.status}`
            );
        }

        const video = await response.json();

        console.log("=================================");
        console.log("VIDEO RECEIVED FROM API");
        console.log(video);
        console.log("=================================");


        // =================================================
        // CHECK VIDEO
        // =================================================

        if (!video || video.id == null) {

            console.error(
                "Video not found for ID:",
                id
            );

            showError("Video not found.");

            return;
        }


        // =================================================
        // DISPLAY VIDEO
        // =================================================

        displayVideo(video);

    } catch (error) {

        console.error(
            "Error loading video:",
            error
        );

        showError(
            "Unable to load this video. Please make sure your Spring Boot server is running on port 8080."
        );
    }
}


// =====================================================
// DISPLAY VIDEO
// =====================================================

function displayVideo(video) {

    console.log("Displaying video:", video);


    // =================================================
    // TITLE
    // =================================================

    if (titleElement) {

        const title =
            video.title || "Untitled Video";

        titleElement.textContent = title;

        document.title = title;

        console.log("VIDEO TITLE:", title);
    }


    // =================================================
    // CHANNEL
    // =================================================

    if (channelElement) {

        channelElement.textContent =
            video.channel || "Unknown Channel";
    }


    // =================================================
    // VIEWS
    // =================================================

    if (viewsElement) {

        viewsElement.textContent =
            video.views || "0 views";
    }


    // =================================================
    // UPLOADED
    // =================================================

    if (uploadedElement) {

        uploadedElement.textContent =
            video.uploaded || "";
    }


    // =================================================
    // DESCRIPTION
    // =================================================

    if (descriptionElement) {

        descriptionElement.textContent =
            video.description ||
            "No description available.";
    }


    // =================================================
    // VIDEO PLAYER
    // =================================================

    if (playerElement) {

        const videoURL =
            convertToEmbedURL(video.videoUrl);

        console.log(
            "Original video URL:",
            video.videoUrl
        );

        console.log(
            "Converted embed URL:",
            videoURL
        );

        if (videoURL) {

            playerElement.src = videoURL;

        } else {

            console.error(
                "Video URL is missing."
            );

            playerElement.removeAttribute("src");
        }
    }


    // =================================================
    // WATCH HISTORY
    // =================================================

    saveToWatchHistory(video);


    // =================================================
    // LIKE
    // =================================================

    setupLikeButton(video);


    // =================================================
    // SAVE
    // =================================================

    setupSaveButton(video);


    // =================================================
    // SUBSCRIBE
    // =================================================

    setupSubscribeButton(video);
}


// =====================================================
// CONVERT YOUTUBE URL TO EMBED URL
// =====================================================

function convertToEmbedURL(url) {

    if (!url) {

        console.error(
            "convertToEmbedURL(): URL is empty"
        );

        return "";
    }

    url = String(url).trim();


    // =================================================
    // YOUTUBE EMBED URL
    // =================================================

    if (url.includes("youtube.com/embed/")) {

        return url;
    }


    // =================================================
    // YOUTU.BE URL
    // =================================================

    if (url.includes("youtu.be/")) {

        try {

            const videoId =
                url
                    .split("youtu.be/")[1]
                    .split("?")[0]
                    .split("&")[0];

            if (videoId) {

                return `https://www.youtube.com/embed/${videoId}`;
            }

        } catch (error) {

            console.error(
                "Invalid youtu.be URL:",
                error
            );
        }
    }


    // =================================================
    // YOUTUBE WATCH URL
    // =================================================

    if (url.includes("youtube.com/watch")) {

        try {

            const youtubeURL =
                new URL(url);

            const videoId =
                youtubeURL.searchParams.get("v");

            if (videoId) {

                return `https://www.youtube.com/embed/${videoId}`;
            }

        } catch (error) {

            console.error(
                "Invalid YouTube URL:",
                error
            );
        }
    }


    // =================================================
    // RETURN ORIGINAL URL
    // =================================================

    return url;
}


// =====================================================
// WATCH HISTORY
// =====================================================

const WATCH_HISTORY_KEY = "watchHistory";

const MAX_HISTORY_ITEMS = 100;


function saveToWatchHistory(video) {

    if (!video || video.id == null) {

        return;
    }

    try {

        let history =
            JSON.parse(
                localStorage.getItem(
                    WATCH_HISTORY_KEY
                )
            ) || [];


        history =
            history.filter(
                item =>
                    String(item.id) !==
                    String(video.id)
            );


        history.unshift({

            id: video.id,

            title: video.title,

            channel: video.channel,

            views: video.views,

            uploaded: video.uploaded,

            duration: video.duration,

            thumbnail: video.thumbnail,

            category: video.category,

            videoUrl: video.videoUrl,

            watchedAt:
                new Date().toISOString()
        });


        history =
            history.slice(
                0,
                MAX_HISTORY_ITEMS
            );


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


// =====================================================
// LIKE BUTTON
// =====================================================

function setupLikeButton(video) {

    const likeButton =
        document.getElementById("likeBtn");

    if (!likeButton) {

        return;
    }


    updateLikeButton(
        video,
        likeButton
    );


    likeButton.onclick =
        async function () {

            try {

                if (video.liked) {

                    await unlikeVideo(
                        video.id,
                        likeButton,
                        video
                    );

                } else {

                    await likeVideo(
                        video.id,
                        likeButton,
                        video
                    );
                }

            } catch (error) {

                console.error(
                    "Like error:",
                    error
                );
            }
        };
}


// =====================================================
// LIKE VIDEO
// =====================================================

async function likeVideo(
    videoId,
    button,
    video
) {

    const response =
        await fetch(
            `http://localhost:8080/api/videos/${videoId}/like`,
            {
                method: "POST"
            }
        );


    if (!response.ok) {

        throw new Error(
            "Unable to like video."
        );
    }


    video.liked = true;


    button.innerText =
        "👍 Liked";


    button.classList.add(
        "liked"
    );


    updateLikedVideoStorage(
        videoId,
        true
    );
}


// =====================================================
// UNLIKE VIDEO
// =====================================================

async function unlikeVideo(
    videoId,
    button,
    video
) {

    const response =
        await fetch(
            `http://localhost:8080/api/videos/${videoId}/like`,
            {
                method: "DELETE"
            }
        );


    if (!response.ok) {

        throw new Error(
            "Unable to unlike video."
        );
    }


    video.liked = false;


    button.innerText =
        "👍 Like";


    button.classList.remove(
        "liked"
    );


    updateLikedVideoStorage(
        videoId,
        false
    );
}


// =====================================================
// UPDATE LIKE BUTTON
// =====================================================

function updateLikeButton(
    video,
    button
) {

    const likedVideos =
        JSON.parse(
            localStorage.getItem(
                "likedVideoIds"
            )
        ) || [];


    const isLiked =
        likedVideos.some(
            likedId =>
                String(likedId) ===
                String(video.id)
        );


    video.liked = isLiked;


    if (isLiked) {

        button.innerText =
            "👍 Liked";

        button.classList.add(
            "liked"
        );

    } else {

        button.innerText =
            "👍 Like";

        button.classList.remove(
            "liked"
        );
    }
}


// =====================================================
// UPDATE LIKED VIDEOS STORAGE
// =====================================================

function updateLikedVideoStorage(
    videoId,
    liked
) {

    let likedVideos =
        JSON.parse(
            localStorage.getItem(
                "likedVideoIds"
            )
        ) || [];


    if (liked) {

        if (
            !likedVideos.some(
                id =>
                    String(id) ===
                    String(videoId)
            )
        ) {

            likedVideos.push(
                videoId
            );
        }

    } else {

        likedVideos =
            likedVideos.filter(
                id =>
                    String(id) !==
                    String(videoId)
            );
    }


    localStorage.setItem(
        "likedVideoIds",
        JSON.stringify(
            likedVideos
        )
    );
}


// =====================================================
// SHARE BUTTON
// =====================================================

function setupShareButton() {

    const shareButton =
        document.getElementById(
            "shareBtn"
        );


    if (!shareButton) {

        return;
    }


    shareButton.onclick =
        async function () {

            const shareURL =
                window.location.href;


            try {

                if (
                    navigator.share
                ) {

                    await navigator.share({

                        title:
                            document.title,

                        url:
                            shareURL
                    });

                } else {

                    await navigator.clipboard.writeText(
                        shareURL
                    );

                    alert(
                        "Video link copied!"
                    );
                }

            } catch (error) {

                console.error(
                    "Share error:",
                    error
                );
            }
        };
}


// =====================================================
// SAVE BUTTON
// =====================================================

function setupSaveButton(video) {

    const saveButton =
        document.getElementById(
            "saveBtn"
        );


    if (!saveButton) {

        return;
    }


    const WATCH_LATER_KEY =
        "watchLaterVideos";


    let savedVideos =
        JSON.parse(
            localStorage.getItem(
                WATCH_LATER_KEY
            )
        ) || [];


    const alreadySaved =
        savedVideos.some(
            item =>
                String(item.id) ===
                String(video.id)
        );


    updateSaveButton(
        saveButton,
        alreadySaved
    );


    saveButton.onclick =
        function () {

            let saved =
                JSON.parse(
                    localStorage.getItem(
                        WATCH_LATER_KEY
                    )
                ) || [];


            const exists =
                saved.some(
                    item =>
                        String(item.id) ===
                        String(video.id)
                );


            if (exists) {

                saved =
                    saved.filter(
                        item =>
                            String(item.id) !==
                            String(video.id)
                    );


                updateSaveButton(
                    saveButton,
                    false
                );

            } else {

                saved.push(video);


                updateSaveButton(
                    saveButton,
                    true
                );
            }


            localStorage.setItem(
                WATCH_LATER_KEY,
                JSON.stringify(saved)
            );
        };
}


// =====================================================
// UPDATE SAVE BUTTON
// =====================================================

function updateSaveButton(
    button,
    saved
) {

    if (saved) {

        button.innerText =
            "✓ Saved";

    } else {

        button.innerText =
            "💾 Save";
    }
}


// =====================================================
// SUBSCRIBE BUTTON
// =====================================================

function setupSubscribeButton(video) {

    const subscribeButton =
        document.getElementById(
            "subscribeBtn"
        );


    if (!subscribeButton) {

        return;
    }


    const SUBSCRIPTIONS_KEY =
        "subscriptions";


    const channel =
        video.channel ||
        "Unknown Channel";


    let subscriptions =
        JSON.parse(
            localStorage.getItem(
                SUBSCRIPTIONS_KEY
            )
        ) || [];


    const subscribed =
        subscriptions.includes(
            channel
        );


    updateSubscribeButton(
        subscribeButton,
        subscribed
    );


    subscribeButton.onclick =
        function () {

            let currentSubscriptions =
                JSON.parse(
                    localStorage.getItem(
                        SUBSCRIPTIONS_KEY
                    )
                ) || [];


            if (
                currentSubscriptions.includes(
                    channel
                )
            ) {

                currentSubscriptions =
                    currentSubscriptions.filter(
                        item =>
                            item !== channel
                    );


                updateSubscribeButton(
                    subscribeButton,
                    false
                );

            } else {

                currentSubscriptions.push(
                    channel
                );


                updateSubscribeButton(
                    subscribeButton,
                    true
                );
            }


            localStorage.setItem(
                SUBSCRIPTIONS_KEY,
                JSON.stringify(
                    currentSubscriptions
                )
            );
        };
}


// =====================================================
// UPDATE SUBSCRIBE BUTTON
// =====================================================

function updateSubscribeButton(
    button,
    subscribed
) {

    if (subscribed) {

        button.innerText =
            "✓ Subscribed";


        button.style.background =
            "#333";


        button.style.color =
            "white";

    } else {

        button.innerText =
            "Subscribe";


        button.style.background =
            "white";


        button.style.color =
            "black";
    }
}


// =====================================================
// ERROR MESSAGE
// =====================================================

function showError(message) {

    if (titleElement) {

        titleElement.innerText =
            "Unable to load video";
    }


    if (descriptionElement) {

        descriptionElement.innerText =
            message;
    }


    if (playerElement) {

        playerElement.removeAttribute("src");
    }
}
```
