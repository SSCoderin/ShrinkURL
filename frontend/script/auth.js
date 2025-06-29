document.addEventListener("DOMContentLoaded", () => {
    const registerForm = document.getElementById("registerForm");
    if (registerForm) {
        registerForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const formData = new FormData(registerForm);
            const data = {
                username: formData.get("username"),
                password: formData.get("password"),
                confirmPassword: formData.get("confirmPassword")
            };

            if (data.password !== data.confirmPassword) {
                alert("Passwords do not match!");
                return;
            }

            const res = await fetch("http://localhost:8080/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });

            const result = await res.json();
            alert(result.message);
            if (res.ok) window.location.href = "/login";
        });
    }


    const loginForm = document.getElementById("loginForm");
    if (loginForm) {
        loginForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const formData = new FormData(loginForm);
            const data = {
                email: formData.get("email"),
                password: formData.get("password")
            };

            const res = await fetch("http://localhost:8080/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });

            const result = await res.json();
            alert(result.message);

            if (res.ok) {
                localStorage.setItem("token", result.token);
                window.location.href = "/index";
            }
        });
    }
});
