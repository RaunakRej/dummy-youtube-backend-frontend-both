(function () {
  const username = localStorage.getItem("username");
  const email = localStorage.getItem("userEmail");
  document.querySelectorAll("[data-profile-name]").forEach((element) => {
    element.textContent = username || "User";
  });
  document.querySelectorAll("[data-profile-email]").forEach((element) => {
    element.textContent = email || "Email not available";
  });
  const profileHandle = (username || "User")
    .trim()
    .toLowerCase()
    .replace(/[^a-z0-9_]+/g, "");
  document.querySelectorAll("[data-profile-handle]").forEach((element) => {
    element.textContent = `@${profileHandle || "user"}`;
  });

  const fallbackPicture =
    "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100'%3E%3Crect width='100' height='100' rx='50' fill='%23e5e5e5'/%3E%3Ccircle cx='50' cy='36' r='18' fill='%23757575'/%3E%3Cpath d='M16 94c2-22 16-34 34-34s32 12 34 34' fill='%23757575'/%3E%3C/svg%3E";
  const profilePictures = document.querySelectorAll("[data-user-profile-picture]");
  profilePictures.forEach((image) => {
    image.src = fallbackPicture;
  });

  const userId = localStorage.getItem("userId");
  if (!userId) {
    return;
  }

  if (profilePictures.length === 0) {
    return;
  }

  fetch(`/api/auth/profile/${encodeURIComponent(userId)}/picture`, {
    cache: "no-store",
  })
    .then((response) => {
      if (response.status === 404) {
        return null;
      }
      if (!response.ok) {
        throw new Error(`Profile picture request failed: ${response.status}`);
      }
      return response.blob();
    })
    .then((picture) => {
      if (!picture) {
        return;
      }

      const pictureUrl = URL.createObjectURL(picture);
      profilePictures.forEach((image) => {
        image.src = pictureUrl;
      });
    })
    .catch((error) => {
      console.error("Unable to load profile picture:", error);
    });
})();
