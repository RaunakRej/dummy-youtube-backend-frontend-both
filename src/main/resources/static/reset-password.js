const requestResetForm = document.getElementById("requestResetForm");
const confirmResetForm = document.getElementById("confirmResetForm");
const resetTitle = document.getElementById("resetTitle");
const resetSubtitle = document.getElementById("resetSubtitle");
const resetBackLink = document.getElementById("resetBackLink");
const resetCompleteMessage = document.getElementById("resetCompleteMessage");
const resetEmailInput = document.getElementById("resetEmail");
const newPasswordInput = document.getElementById("newPassword");
const confirmNewPasswordInput = document.getElementById("confirmNewPassword");
const requestResetButton = document.getElementById("requestResetButton");
const confirmResetButton = document.getElementById("confirmResetButton");
const resetRequestMessage = document.getElementById("resetRequestMessage");
const resetConfirmMessage = document.getElementById("resetConfirmMessage");

const pageUrl = new URL(window.location.href);
const resetToken = pageUrl.searchParams.get("token");

if (resetToken) {
    confirmResetForm.hidden = false;
    requestResetForm.hidden = true;
    resetTitle.textContent = "Choose a new password";
    resetSubtitle.textContent = "Enter and confirm your new password.";
    window.history.replaceState(null, "", pageUrl.pathname);
}

function showFormMessage(element, message, isSuccess) {
    element.textContent = message;
    element.classList.toggle("success-message", isSuccess);
}

async function getResponseMessage(response) {
    const responseText = await response.text();
    if (!responseText) {
        return "";
    }

    try {
        const result = JSON.parse(responseText);
        if (typeof result === "string") {
            return result;
        }
        return typeof result.message === "string" ? result.message : "";
    } catch {
        return responseText;
    }
}

requestResetForm.addEventListener("submit", async function (event) {
    event.preventDefault();
    showFormMessage(resetRequestMessage, "", false);

    if (!resetEmailInput.checkValidity()) {
        showFormMessage(resetRequestMessage, "Enter a valid email address.", false);
        resetEmailInput.focus();
        return;
    }

    requestResetButton.disabled = true;
    requestResetButton.textContent = "Sending...";

    try {
        const response = await fetch("/api/auth/password-reset/request", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email: resetEmailInput.value.trim() }),
        });
        const message = await getResponseMessage(response);
        if (!response.ok) {
            throw new Error(message || "Unable to send a password reset email.");
        }

        showFormMessage(
            resetRequestMessage,
            message || "If an account exists for that email, password reset instructions will be sent.",
            true,
        );
    } catch (error) {
        console.error("Password reset request failed:", error);
        showFormMessage(
            resetRequestMessage,
            error.message || "Unable to send a password reset email.",
            false,
        );
    } finally {
        requestResetButton.disabled = false;
        requestResetButton.textContent = "Send reset link";
    }
});

confirmResetForm.addEventListener("submit", async function (event) {
    event.preventDefault();
    showFormMessage(resetConfirmMessage, "", false);

    const newPassword = newPasswordInput.value;
    if (newPassword.length < 8 || new TextEncoder().encode(newPassword).length > 72) {
        showFormMessage(
            resetConfirmMessage,
            "Password must be at least 8 characters and no more than 72 bytes.",
            false,
        );
        newPasswordInput.focus();
        return;
    }

    if (newPassword !== confirmNewPasswordInput.value) {
        showFormMessage(resetConfirmMessage, "The passwords do not match.", false);
        confirmNewPasswordInput.focus();
        return;
    }

    confirmResetButton.disabled = true;
    confirmResetButton.textContent = "Resetting...";

    try {
        const response = await fetch("/api/auth/password-reset/confirm", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ token: resetToken, newPassword }),
        });
        const message = await getResponseMessage(response);
        if (!response.ok) {
            throw new Error(message || "Unable to reset your password.");
        }

        confirmResetForm.hidden = true;
        resetCompleteMessage.textContent = message;
        resetCompleteMessage.hidden = false;
        resetBackLink.hidden = false;
    } catch (error) {
        console.error("Password reset failed:", error);
        showFormMessage(
            resetConfirmMessage,
            error.message || "Unable to reset your password.",
            false,
        );
    } finally {
        confirmResetButton.disabled = false;
        confirmResetButton.textContent = "Reset password";
    }
});

if (resetToken) {
    resetBackLink.hidden = true;
}
