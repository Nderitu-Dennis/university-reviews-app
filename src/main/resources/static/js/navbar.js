document.addEventListener("DOMContentLoaded", function() {
    const menuToggle = document.getElementById("menu-toggle");
    const mobileMenu = document.getElementById("mobile-menu");

    if (menuToggle && mobileMenu) {
        // Toggle menu on button click
        menuToggle.addEventListener("click", function() {
            mobileMenu.classList.toggle("hidden");
            mobileMenu.classList.toggle("block");
        });

        //  Fix: Reset menu when resizing back to desktop size
        window.addEventListener("resize", function() {
            if (window.innerWidth >= 1024) { // Tailwind's lg breakpoint (1024px)
                mobileMenu.classList.add("hidden");
                mobileMenu.classList.remove("block");
            }
        });
    }
});
