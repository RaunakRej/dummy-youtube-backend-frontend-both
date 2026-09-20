const loginForm = document.getElementById("loginForm");
const usernameInput = document.getElementById("username");
const passwordInput = document.getElementById("password");
const loginButton = document.getElementById("loginButton");
const loginMessage = document.getElementById("loginMessage");
const togglePassword = document.getElementById("togglePassword");

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

    let result;

    try {

        result = responseText
            ? JSON.parse(responseText)
            : null;

    } catch (parseError) {

        console.error(
            "JSON parsing error:",
            parseError
        );

        throw new Error(
            "The server returned an invalid response."
        );
    }

    console.log("Login API response:", result);
    console.log("HTTP status:", response.status);

    // =================================================
    // LOGIN FAILED
    // =================================================

    if (!response.ok) {

        throw new Error(
            typeof result === "string"
                ? result
                : "Invalid username or password."
        );
    }

    // =================================================
    // LOGIN SUCCESSFUL
    // =================================================

    console.log("Login successful!");

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
    if (result.email) {

        localStorage.setItem(
            "userEmail",
            result.email
        );
    }

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
