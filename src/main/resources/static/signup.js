const signupForm = document.getElementById("signupForm");
const signupUsernameInput = document.getElementById("signupUsername");
const signupEmailInput = document.getElementById("signupEmail");
const signupPasswordInput = document.getElementById("signupPassword");
const confirmSignupPasswordInput = document.getElementById("confirmSignupPassword");
const signupButton = document.getElementById("signupButton");
const signupMessage = document.getElementById("signupMessage");

signupForm.addEventListener("submit", async function (event) {
  event.preventDefault();
  signupMessage.textContent = "";

  const username = signupUsernameInput.value.trim();
  const email = signupEmailInput.value.trim();
  const password = signupPasswordInput.value;
  const confirmPassword = confirmSignupPasswordInput.value;

  if (!username) {
    signupMessage.textContent = "Enter a username.";
    signupUsernameInput.focus();
    return;
  }

  if (username.length > 80) {
    signupMessage.textContent = "Username must be 80 characters or fewer.";
    signupUsernameInput.focus();
    return;
  }

  if (!signupEmailInput.checkValidity()) {
    signupMessage.textContent = "Enter a valid email address.";
    signupEmailInput.focus();
    return;
  }

  if (password.length < 8 || new TextEncoder().encode(password).length > 72) {
    signupMessage.textContent = "Password must be at least 8 characters and no more than 72 bytes.";
    signupPasswordInput.focus();
    return;
  }

  if (password !== confirmPassword) {
    signupMessage.textContent = "The passwords do not match.";
    confirmSignupPasswordInput.focus();
    return;
  }

  signupButton.disabled = true;
  signupButton.textContent = "Creating account...";

  try {
    const response = await fetch("/api/auth/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, email, password }),
    });

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
          : result?.message || "Unable to create your account.",
      );
    }

    signupMessage.classList.add("success-message");
    signupMessage.textContent = "Account created! Redirecting you to sign in...";
    signupForm.reset();
    window.setTimeout(() => {
      window.location.assign("/login?accountCreated=1");
    }, 1200);
  } catch (error) {
    console.error("Sign-up failed:", error);
    signupMessage.classList.remove("success-message");
    signupMessage.textContent = error.message || "Unable to create your account.";
  } finally {
    signupButton.disabled = false;
    signupButton.textContent = "Sign up";
  }
});
