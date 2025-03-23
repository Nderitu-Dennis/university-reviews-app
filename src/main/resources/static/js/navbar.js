document.addEventListener("DOMContentLoaded", function() {
    const menuToggle = document.getElementById("menu-toggle");
    const mobileMenu = document.getElementById("mobile-menu");
    const navLinks = document.querySelectorAll("#mobile-menu a"); // Select all links in the mobile menu

    if (menuToggle && mobileMenu) {
        // Toggle menu on button click
        menuToggle.addEventListener("click", function() {
            mobileMenu.classList.toggle("hidden");
            mobileMenu.classList.toggle("block");
        });

        // Close menu when a link is clicked
        navLinks.forEach(link => {
            link.addEventListener("click", function() {
                mobileMenu.classList.add("hidden");
                mobileMenu.classList.remove("block");
            });
        });

        // Fix: Reset menu when resizing back to desktop size
        window.addEventListener("resize", function() {
            if (window.innerWidth >= 1024) { // Tailwind's lg breakpoint (1024px)
                mobileMenu.classList.add("hidden");
                mobileMenu.classList.remove("block");
            }
        });
    }
});
