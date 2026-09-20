(function () {
    const input = document.getElementById("searchInput");
    const button = document.getElementById("searchBtn");

    if (!input || !button) {
        return;
    }

    function filterCurrentPage() {
        const query = input.value.trim().toLowerCase();
        const rows = document.querySelectorAll(
            ".watch-video-row, .channel-page article, .video-item"
        );

        if (rows.length === 0) {
            window.location.href = query
                ? "/?search=" + encodeURIComponent(query)
                : "/";
            return;
        }

        rows.forEach(function (row) {
            row.hidden = query !== ""
                && !row.textContent.toLowerCase().includes(query);
        });
    }

    button.addEventListener("click", filterCurrentPage);
    input.addEventListener("keydown", function (event) {
        if (event.key === "Enter") {
            event.preventDefault();
            filterCurrentPage();
        }
    });
    input.addEventListener("input", function () {
        if (document.querySelector(".watch-video-row, .channel-page article, .video-item")) {
            filterCurrentPage();
        }
    });
})();
