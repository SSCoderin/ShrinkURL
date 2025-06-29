let customAlias = "";

document.addEventListener("DOMContentLoaded", () => {
  const longUrlInput = document.getElementById("longUrlInput");
  const shortenBtn = document.getElementById("shortenBtn");
  const shortUrlOutput = document.getElementById("shortUrlOutput");
  const copyBtn = document.getElementById("copyBtn");
  const customUrlBtn = document.getElementById("customUrlBtn");

  customUrlBtn.addEventListener("click", async () => {
    const token = localStorage.getItem("token");

    if (!token) {
      alert("Please login first to use custom URL.");
      window.location.href = "/login";
      return;
    }

    try {
      const check = await fetch("http://localhost:8080/checktoken", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
        }
      });

      if (!check.ok) {
        alert(" Invalid or expired token. Please login again.");
        return;
      }

      const alias = prompt("Enter a custom alias (e.g., my-link):");
      if (alias && alias.trim() !== "") {
        customAlias = alias.trim();
        shortenBtn.click()
        alert(`Custom alias set: ${customAlias}`);
      }

    } catch (error) {
      console.error("Token check failed:", error);
      alert("Error verifying login. Try again.");
    }
  });

  shortenBtn.addEventListener("click", async () => {
    const longUrl = longUrlInput.value.trim();
    if (!longUrl) {
      alert("Please enter a valid URL to shorten.");
      return;
    }

    try {
      const payload = {
        longUrl: longUrl,
        customAlias: customAlias
      };

      const response = await fetch("http://localhost:8080/shorten", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
      });

      const result = await response.json();

      if (response.ok) {
        shortUrlOutput.value = result.shortUrl;
        customAlias = ""; 
      } else {
        alert(result.message || "Failed to shorten URL.");
      }

    } catch (error) {
      console.error("Shorten error:", error);
      alert("Something went wrong while shortening the URL.");
    }
  });

  copyBtn.addEventListener("click", () => {
    if (shortUrlOutput.value) {
      navigator.clipboard.writeText(shortUrlOutput.value).then(() => {
        alert("Short URL copied!");
      });
    }
  });
});
