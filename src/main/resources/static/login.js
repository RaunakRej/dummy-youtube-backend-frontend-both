const loginForm = document.getElementById("loginForm");
const usernameInput = document.getElementById("username");
const passwordInput = document.getElementById("password");
const loginButton = document.getElementById("loginButton");
const loginMessage = document.getElementById("loginMessage");
const togglePassword = document.getElementById("togglePassword");
const loginErrorPopup = document.getElementById("loginErrorPopup");
const closeLoginPopup = document.getElementById("closeLoginPopup");
const loginPopupMessage = document.getElementById("loginPopupMessage");
const accountCreatedPopup = document.getElementById("accountCreatedPopup");
const closeAccountCreatedPopup = document.getElementById("closeAccountCreatedPopup");
const forgotPasswordLink = document.getElementById("forgotPasswordLink");
const forgotPasswordPopup = document.getElementById("forgotPasswordPopup");
const forgotPasswordForm = document.getElementById("forgotPasswordForm");
const forgotPasswordEmail = document.getElementById("forgotPasswordEmail");
const forgotPasswordMessage = document.getElementById("forgotPasswordMessage");
const sendResetLinkButton = document.getElementById("sendResetLinkButton");
const closeForgotPasswordPopup = document.getElementById("closeForgotPasswordPopup");

const loginUrl = new URL(window.location.href);
if (loginUrl.searchParams.get("accountCreated") === "1") {
    loginUrl.searchParams.delete("accountCreated");
    window.history.replaceState(null, "", `${loginUrl.pathname}${loginUrl.search}${loginUrl.hash}`);
    accountCreatedPopup.hidden = false;
    closeAccountCreatedPopup.focus();
}

function hideAccountCreatedPopup() {
    accountCreatedPopup.hidden = true;
}

closeAccountCreatedPopup.addEventListener("click", function () {
    hideAccountCreatedPopup();
    usernameInput.focus();
});

accountCreatedPopup.addEventListener("click", function (event) {
    if (event.target === accountCreatedPopup) {
        hideAccountCreatedPopup();
    }
});

function showForgotPasswordMessage(message, isSuccess) {
    forgotPasswordMessage.textContent = message;
    forgotPasswordMessage.classList.toggle("success-message", isSuccess);
}

function hideForgotPasswordPopup() {
    forgotPasswordPopup.hidden = true;
    forgotPasswordForm.reset();
    showForgotPasswordMessage("", false);
    sendResetLinkButton.disabled = false;
    sendResetLinkButton.textContent = "Send reset link";
}

forgotPasswordLink.addEventListener("click", function (event) {
    event.preventDefault();
    forgotPasswordPopup.hidden = false;
    forgotPasswordEmail.focus();
});

closeForgotPasswordPopup.addEventListener("click", function () {
    hideForgotPasswordPopup();
    forgotPasswordLink.focus();
});

forgotPasswordPopup.addEventListener("click", function (event) {
    if (event.target === forgotPasswordPopup) {
        hideForgotPasswordPopup();
        forgotPasswordLink.focus();
    }
});

forgotPasswordForm.addEventListener("submit", async function (event) {
    event.preventDefault();
    showForgotPasswordMessage("", false);

    if (!forgotPasswordEmail.checkValidity()) {
        showForgotPasswordMessage("Enter a valid email address.", false);
        forgotPasswordEmail.focus();
        return;
    }

    sendResetLinkButton.disabled = true;
    sendResetLinkButton.textContent = "Sending...";

    try {
        const response = await fetch("/api/auth/password-reset/request", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email: forgotPasswordEmail.value.trim() }),
        });
        const responseText = await response.text();
        let message = "";

        if (responseText) {
            try {
                const result = JSON.parse(responseText);
                message = typeof result === "string"
                    ? result
                    : (typeof result.message === "string" ? result.message : "");
            } catch {
                message = responseText;
            }
        }

        if (!response.ok) {
            throw new Error(message || "Unable to send a password reset email.");
        }

        showForgotPasswordMessage(
            message || "If an account exists for that email, password reset instructions will be sent.",
            true,
        );
    } catch (error) {
        console.error("Password reset request failed:", error);
        showForgotPasswordMessage(
            error.message || "Unable to send a password reset email.",
            false,
        );
    } finally {
        sendResetLinkButton.disabled = false;
        sendResetLinkButton.textContent = "Send reset link";
    }
});

function hideLoginPopup() {
    loginErrorPopup.hidden = true;
}

closeLoginPopup.addEventListener("click", function () {
    hideLoginPopup();
    passwordInput.focus();
});

loginErrorPopup.addEventListener("click", function (event) {
    if (event.target === loginErrorPopup) {
        hideLoginPopup();
    }
});

document.addEventListener("keydown", function (event) {
    if (event.key === "Escape" && !loginErrorPopup.hidden) {
        hideLoginPopup();
        passwordInput.focus();
    }

    if (event.key === "Escape" && !accountCreatedPopup.hidden) {
        hideAccountCreatedPopup();
        usernameInput.focus();
    }

    if (event.key === "Escape" && !forgotPasswordPopup.hidden) {
        hideForgotPasswordPopup();
        forgotPasswordLink.focus();
    }
});

function showLoginMessage(message) {
  loginMessage.textContent = message;
}

// =====================================================
// SHOW / HIDE PASSWORD
// =====================================================

togglePassword.addEventListener("click", function () {
    const isPassword = passwordInput.type === "password";

    passwordInput.type = isPassword ? "text" : "password";

    togglePassword.textContent = isPassword ? "Hide" : "Show";

    togglePassword.setAttribute(
        "aria-label",
        isPassword ? "Hide password" : "Show password"
    );
});

// =====================================================
// LOGIN
// =====================================================

loginForm.addEventListener("submit", async function (event) {
    event.preventDefault();

showLoginMessage("");

const username = usernameInput.value.trim();
const password = passwordInput.value;

// =================================================
// CHECK EMPTY FIELDS
// =================================================

if (!username || !password) {
    showLoginMessage(
        "Enter both your username and password."
    );
    return;
}

// Disable button while login is processing
loginButton.disabled = true;
loginButton.textContent = "Signing in...";

try {

    // =================================================
    // CALL LOGIN API
    // =================================================

    const response = await fetch("/api/auth/login", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            username: username,
            password: password
        })
    });

    // =================================================
    // READ SERVER RESPONSE
    // =================================================

    const responseText = await response.text();
    console.log("HTTP status:", response.status);

    // =================================================
    // LOGIN FAILED
    // =================================================

    if (!response.ok) {
        let errorText = responseText;

        try {
            const errorResult = responseText
                ? JSON.parse(responseText)
                : null;

            if (typeof errorResult === "string") {
                errorText = errorResult;
            } else if (errorResult && typeof errorResult.message === "string") {
                errorText = errorResult.message;
            }
        } catch {
            // The login endpoint returns plain-text errors for rejected credentials.
        }

        if (response.status === 400
            || response.status === 401
            || response.status === 403
            || /invalid username or password|invalid credentials/i.test(errorText)) {
            showLoginMessage("");
            loginPopupMessage.textContent =
                "Incorrect password. Please check your password and try again.";
            loginErrorPopup.hidden = false;
            closeLoginPopup.focus();
            return;
        }

        throw new Error(errorText || "Unable to sign in. Please try again.");
    }

    let result;

    try {
        result = responseText ? JSON.parse(responseText) : null;
    } catch (parseError) {
        console.error("JSON parsing error:", parseError);
        throw new Error("The server returned an invalid response.");
    }

    console.log("Login API response:", result);

    // =================================================
    // LOGIN SUCCESSFUL
    // =================================================

    console.log("Login successful!");

    localStorage.removeItem("username");
    localStorage.removeItem("userEmail");
    localStorage.removeItem("userId");

    // Save login status
    localStorage.setItem(
        "isLoggedIn",
        "true"
    );

    // Save username
    localStorage.setItem(
        "username",
        result.username || username
    );

    // Save email
    localStorage.setItem("userEmail", result.email || "");

    // Save user ID
    if (result.id) {

        localStorage.setItem(
            "userId",
            result.id
        );
    }

    // =================================================
    // VERIFY LOCAL STORAGE
    // =================================================

    console.log(
        "isLoggedIn:",
        localStorage.getItem("isLoggedIn")
    );

    console.log(
        "username:",
        localStorage.getItem("username")
    );

    console.log(
        "userEmail:",
        localStorage.getItem("userEmail")
    );

    console.log(
        "userId:",
        localStorage.getItem("userId")
    );

    // =================================================
    // SHOW SUCCESS MESSAGE
    // =================================================

    showLoginMessage(
        "Login successful! Redirecting..."
    );

    // =================================================
    // REDIRECT TO RTUBE HOME PAGE
    // =================================================

    setTimeout(function () {

        window.location.href = "/";

    }, 800);

} catch (error) {

    // =================================================
    // LOGIN ERROR
    // =================================================

    console.error(
        "Login failed:",
        error
    );

    if (error.message === "Failed to fetch") {

        showLoginMessage(
            "Unable to connect to the server. Start the Spring Boot application and try again."
        );

    } else {

        showLoginMessage(
            error.message
        );
    }

} finally {

    // Re-enable button
    loginButton.disabled = false;
    loginButton.textContent = "Sign in";
}
});
