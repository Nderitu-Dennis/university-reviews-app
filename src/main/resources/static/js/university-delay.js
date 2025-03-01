document.addEventListener("DOMContentLoaded", function () {
  console.log("DOM fully loaded!");

  // Force a 3-second delay before scrolling
  setTimeout(function () {
    console.log("3 seconds passed. Checking URL hash...");

    if (window.location.hash === "#overview-section") {
      console.log("Hash detected! Scrolling to section...");

      const section = document.getElementById("overview-section");
      if (section) {
        section.scrollIntoView({ behavior: "smooth" });
        console.log("Scrolled to overview-section!");
      } else {
        console.log("overview-section not found!");
      }
    } else {
      console.log("No matching hash in URL.");
    }
  }, 10000); // 3 seconds delay
});
