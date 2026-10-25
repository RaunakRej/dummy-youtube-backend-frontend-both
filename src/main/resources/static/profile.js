/* =====================================================
   RTUBE - PROFILE PAGE
   ===================================================== */

const profileKeys = {
  subscriptions: "subscriptions",
  liked: "likedVideoIds",
  watchLater: "watchLaterVideos",
  history: "watchHistory",
};

/* =====================================================
   READ LOCAL STORAGE LIST COUNT
===================================================== */

function readListCount(key) {
  try {
    const value = JSON.parse(localStorage.getItem(key) || "[]");

    return Array.isArray(value) ? value.length : 0;
  } catch (error) {
    console.error(`Unable to read ${key} from local storage:`, error);
    return 0;
  }
}

/* =====================================================
   READ WATCH HISTORY COUNT
===================================================== */

function readHistoryCount() {
  try {
    const history = JSON.parse(
      localStorage.getItem(profileKeys.history) || "[]",
    );

    return Array.isArray(history) ? history.length : 0;
  } catch (error) {
    console.error("Unable to read watch history from local storage:", error);

    return 0;
  }
}

function readSavedWatchLaterIds() {
  try {
    const saved = JSON.parse(
      localStorage.getItem(profileKeys.watchLater) || "[]",
    );

    if (!Array.isArray(saved)) {
      return [];
    }

    return saved
      .map((item) => {
        if (item && typeof item === "object" && item.id != null) {
          return String(item.id);
        }

        return item == null ? null : String(item);
      })
      .filter((id) => id !== null);
  } catch (error) {
    console.error("Unable to read Watch Later videos from local storage:", error);
    return [];
  }
}

/* =====================================================
   RENDER PROFILE IDENTITY
===================================================== */

function renderProfileIdentity() {
  const username = localStorage.getItem("username") || "RTube User";

  const email = localStorage.getItem("userEmail") || "Email not provided";

  // Create profile handle
  const handle = username
    .trim()
    .toLowerCase()
    .replace(/[^a-z0-9_]+/g, "");

  // Display username
  document.getElementById("profileName").textContent = username;

  // Display @handle
  document.getElementById("profileHandle").textContent = `@${handle || "user"}`;

  // Display email
  document.getElementById("profileEmail").textContent = email;

  // Subscriptions
  document.getElementById("subscriptionsCount").textContent = readListCount(
    profileKeys.subscriptions,
  );

  // Watch History
  document.getElementById("historyCount").textContent = readHistoryCount();
}

let profilePicturePreviewUrl = null;

async function loadProfilePicture() {
  const userId = localStorage.getItem("userId");
  if (!userId) {
    return;
  }

  try {
    const response = await fetch(
      `/api/auth/profile/${encodeURIComponent(userId)}/picture`,
      { cache: "no-store" },
    );

    if (response.status === 404) {
      setProfilePicturePreview(null);
      return;
    }
    if (!response.ok) {
      throw new Error(`Profile picture request failed: ${response.status}`);
    }

    const picture = await response.blob();
    if (profilePicturePreviewUrl) {
      URL.revokeObjectURL(profilePicturePreviewUrl);
    }
    profilePicturePreviewUrl = URL.createObjectURL(picture);
    setProfilePicturePreview(profilePicturePreviewUrl);
  } catch (error) {
    console.error("Unable to load profile picture:", error);
    setProfilePicturePreview(null);
  }
}

function setProfilePicturePreview(imageUrl) {
  const previewImages = [
    document.getElementById("profileAvatarImage"),
    document.getElementById("editProfileAvatarImage"),
  ];
  const fallbacks = [
    document.getElementById("profileAvatarFallback"),
    document.getElementById("editProfileAvatarFallback"),
  ];

  previewImages.forEach((image) => {
    image.hidden = !imageUrl;
    if (imageUrl) {
      image.src = imageUrl;
    } else {
      image.removeAttribute("src");
    }
  });
  fallbacks.forEach((fallback) => {
    fallback.hidden = Boolean(imageUrl);
  });
}

/* =====================================================
   LOAD PROFILE STATISTICS
===================================================== */

async function loadProfileStats() {
  const message = document.getElementById("profileStatsMessage");

  const likedCount = document.getElementById("likedCount");

  const watchLaterCount = document.getElementById("watchLaterCount");

  const savedWatchLaterIds = readSavedWatchLaterIds();
  watchLaterCount.textContent = savedWatchLaterIds.length;

  /*
   * Watch History is stored locally.
   */
  document.getElementById("historyCount").textContent = readHistoryCount();

  /*
   * Subscriptions are currently stored locally.
   */
  document.getElementById("subscriptionsCount").textContent = readListCount(
    profileKeys.subscriptions,
  );

  const results = await Promise.allSettled([
    fetch("/api/videos/liked").then(async (response) => {
      if (!response.ok) {
        throw new Error(`Liked videos API failed: ${response.status}`);
      }

      const videos = await response.json();
      if (!Array.isArray(videos)) {
        throw new Error("Liked videos API did not return a list.");
      }

      return videos;
    }),
    fetch("/api/videos").then(async (response) => {
      if (!response.ok) {
        throw new Error(`Videos API failed: ${response.status}`);
      }

      const videos = await response.json();
      if (!Array.isArray(videos)) {
        throw new Error("Videos API did not return a list.");
      }

      return videos;
    }),
  ]);

  const errors = [];

  if (results[0].status === "fulfilled") {
    likedCount.textContent = results[0].value.length;
  } else {
    console.error("Unable to load liked-video count:", results[0].reason);
    likedCount.textContent = readListCount(profileKeys.liked);
    errors.push("Liked Videos");
  }

  if (results[1].status === "fulfilled") {
    const apiVideos = results[1].value;
    const matchingSavedVideos = savedWatchLaterIds.filter((savedId) =>
      apiVideos.some((video) => String(video.id) === savedId),
    );

    watchLaterCount.textContent =
      matchingSavedVideos.length > 0
        ? matchingSavedVideos.length
        : apiVideos.length;
  } else {
    console.error("Unable to load Watch Later count:", results[1].reason);
    errors.push("Watch Later");
  }

  message.textContent = errors.length > 0
    ? `Could not load ${errors.join(" and ")} count from the API. Other profile counts use browser storage.`
    : "Liked Videos and Watch Later match the videos shown by the API; Watch History is stored in this browser.";
}

/* =====================================================
   DARK MODE
===================================================== */

const savedTheme = localStorage.getItem("theme");

document.body.classList.toggle("dark-mode", savedTheme === "dark");

/* =====================================================
   LOGIN PROTECTION
===================================================== */

if (localStorage.getItem("isLoggedIn") !== "true") {
  window.location.replace("/login");
} else {
  renderProfileIdentity();
  loadProfilePicture();
  loadProfileStats();
}

/* =====================================================
   EDIT PROFILE ELEMENTS
===================================================== */

const editProfileButton = document.getElementById("editProfileButton");

const editProfileForm = document.getElementById("editProfileForm");
const editProfileModal = document.getElementById("editProfileModal");
const closeEditProfileButton = document.getElementById("closeEditProfileButton");

const editUsername = document.getElementById("editUsername");

const editEmail = document.getElementById("editEmail");

const profilePictureInput = document.getElementById("profilePictureInput");

const currentPasswordInput = document.getElementById("currentPassword");
const newPasswordInput = document.getElementById("newPassword");
const confirmNewPasswordInput = document.getElementById("confirmNewPassword");
const changePasswordToggle = document.getElementById("changePasswordToggle");
const passwordChangeFields = document.getElementById("passwordChangeFields");

const cancelEditButton = document.getElementById("cancelEditButton");

const profileMessage = document.getElementById("profileMessage");
const profileSaveMessage = document.getElementById("profileSaveMessage");
const chooseProfilePictureButton = document.getElementById("chooseProfilePictureButton");
const profileSuccessPopup = document.getElementById("profileSuccessPopup");
let profileSuccessPopupTimer;

function showProfileSuccessPopup() {
  window.clearTimeout(profileSuccessPopupTimer);
  profileSuccessPopup.textContent = "Updated the profile";
  profileSuccessPopup.hidden = false;
  requestAnimationFrame(() => profileSuccessPopup.classList.add("show"));
  profileSuccessPopupTimer = window.setTimeout(() => {
    profileSuccessPopup.classList.remove("show");
    window.setTimeout(() => {
      profileSuccessPopup.hidden = true;
    }, 200);
  }, 3000);
}

function closeProfileEditor() {
  editProfileModal.close();
  profilePictureInput.value = "";
  currentPasswordInput.value = "";
  changePasswordToggle.checked = false;
  passwordChangeFields.hidden = true;
  newPasswordInput.required = false;
  confirmNewPasswordInput.required = false;
  newPasswordInput.value = "";
  confirmNewPasswordInput.value = "";
  profileMessage.textContent = "";
  loadProfilePicture();
  editProfileButton.focus();
}

profilePictureInput.addEventListener("change", function () {
  const file = profilePictureInput.files[0];
  if (!file) {
    loadProfilePicture();
    return;
  }

  if (!["image/jpeg", "image/png", "image/gif", "image/webp"].includes(file.type)) {
    profilePictureInput.value = "";
    profileMessage.textContent = "Choose a JPEG, PNG, GIF, or WebP image.";
    loadProfilePicture();
    return;
  }

  if (file.size > 5 * 1024 * 1024) {
    profilePictureInput.value = "";
    profileMessage.textContent = "Profile pictures must be 5 MB or smaller.";
    loadProfilePicture();
    return;
  }

  if (profilePicturePreviewUrl) {
    URL.revokeObjectURL(profilePicturePreviewUrl);
  }
  profilePicturePreviewUrl = URL.createObjectURL(file);
  setProfilePicturePreview(profilePicturePreviewUrl);
});

chooseProfilePictureButton.addEventListener("click", function () {
  profilePictureInput.click();
});

changePasswordToggle.addEventListener("change", function () {
  passwordChangeFields.hidden = !changePasswordToggle.checked;
  newPasswordInput.required = changePasswordToggle.checked;
  confirmNewPasswordInput.required = changePasswordToggle.checked;

  if (changePasswordToggle.checked) {
    newPasswordInput.focus();
  } else {
    newPasswordInput.value = "";
    confirmNewPasswordInput.value = "";
  }
});

/* =====================================================
   OPEN EDIT PROFILE
===================================================== */

editProfileButton.addEventListener("click", function () {
  editUsername.value = localStorage.getItem("username") || "";

  editEmail.value = localStorage.getItem("userEmail") || "";
  profilePictureInput.value = "";
  currentPasswordInput.value = "";
  changePasswordToggle.checked = false;
  passwordChangeFields.hidden = true;
  newPasswordInput.required = false;
  confirmNewPasswordInput.required = false;
  newPasswordInput.value = "";
  confirmNewPasswordInput.value = "";

  profileMessage.textContent = "";
  profileSaveMessage.textContent = "";

  editProfileModal.showModal();

  editUsername.focus();
});

/* =====================================================
   CANCEL EDIT PROFILE
===================================================== */

cancelEditButton.addEventListener("click", function () {
  closeProfileEditor();
  profileSaveMessage.textContent = "";
});

closeEditProfileButton.addEventListener("click", closeProfileEditor);
editProfileModal.addEventListener("cancel", function (event) {
  event.preventDefault();
  closeProfileEditor();
});
editProfileModal.addEventListener("click", function (event) {
  if (event.target === editProfileModal) {
    closeProfileEditor();
  }
});

/* =====================================================
   SAVE EDIT PROFILE
===================================================== */

editProfileForm.addEventListener("submit", async function (event) {
  event.preventDefault();

  const username = editUsername.value.trim();

  const email = editEmail.value.trim();
  const userId = localStorage.getItem("userId");
  const profilePicture = profilePictureInput.files[0];
  const newPassword = newPasswordInput.value;
  const confirmNewPassword = confirmNewPasswordInput.value;

  /* ---------------------------------------------
           VALIDATION
        --------------------------------------------- */

  if (!username) {
    profileMessage.textContent = "Username cannot be empty.";

    editUsername.focus();

    return;
  }

  if (!editUsername.checkValidity()) {
    profileMessage.textContent = "Please enter a valid username.";

    editUsername.focus();

    return;
  }

  if (!email || !editEmail.checkValidity()) {
    profileMessage.textContent = "Please enter a valid email address.";

    editEmail.focus();

    return;
  }

  if (!userId) {
    profileMessage.textContent = "Your account ID is missing. Please sign in again.";
    return;
  }

  if (changePasswordToggle.checked) {
    if (!newPassword || !confirmNewPassword) {
      profileMessage.textContent = "Enter and confirm your new password to change it.";
      (!newPassword ? newPasswordInput : confirmNewPasswordInput).focus();
      return;
    }
    if (newPassword !== confirmNewPassword) {
      profileMessage.textContent = "The new passwords do not match.";
      confirmNewPasswordInput.focus();
      return;
    }
    if (newPassword.length < 8 || newPassword.length > 72) {
      profileMessage.textContent = "New password must be between 8 and 72 characters.";
      newPasswordInput.focus();
      return;
    }
  }

  if (profilePicture && profilePicture.size > 5 * 1024 * 1024) {
    profileMessage.textContent = "Profile pictures must be 5 MB or smaller.";
    profilePictureInput.focus();
    return;
  }

  const saveButton = editProfileForm.querySelector('button[type="submit"]');
  saveButton.disabled = true;
  saveButton.textContent = "Saving...";

  try {
    const formData = new FormData();
    formData.append("currentPassword", currentPasswordInput.value);
    formData.append("username", username);
    formData.append("email", email);
    formData.append("newPassword", newPassword);
    formData.append("confirmNewPassword", confirmNewPassword);
    if (profilePicture) {
      formData.append("profilePicture", profilePicture);
    }

    const response = await fetch(
      `/api/auth/profile/${encodeURIComponent(userId)}`,
      {
        method: "PUT",
        body: formData,
      },
    );

    const responseText = await response.text();
    let result;
    try {
      result = responseText ? JSON.parse(responseText) : null;
    } catch {
      result = responseText;
    }

    if (!response.ok) {
      throw new Error(
        typeof result === "string"
          ? result
          : result?.message || "Unable to save profile changes.",
      );
    }

    localStorage.setItem("username", result.username);
    localStorage.setItem("userEmail", result.email);
    renderProfileIdentity();
    editProfileModal.close();
    profilePictureInput.value = "";
    currentPasswordInput.value = "";
    newPasswordInput.value = "";
    confirmNewPasswordInput.value = "";
    await loadProfilePicture();
    profileSaveMessage.textContent = "";
    showProfileSuccessPopup();
  } catch (error) {
    console.error("Unable to save profile to the database:", error);
    profileMessage.textContent = error.message || "Unable to save profile changes.";
  } finally {
    saveButton.disabled = false;
    saveButton.textContent = "Save";
  }
});

/* =====================================================
   SIGN OUT
===================================================== */

document.getElementById("signOutButton").addEventListener("click", function () {
  localStorage.removeItem("isLoggedIn");
  localStorage.removeItem("username");
  localStorage.removeItem("userEmail");
  localStorage.removeItem("userId");

  window.location.replace("/logout");
});
