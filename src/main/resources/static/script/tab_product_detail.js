/**
 * 
 */
 //xử lí tab product detail
document.addEventListener("DOMContentLoaded", function() {
    var navLinks = document.querySelectorAll(".nav-link");
    navLinks.forEach(function(navLink) {
        navLink.addEventListener("click", function(event) {
            event.preventDefault();
            var tabId = this.getAttribute("href");
            var tabPanes = document.querySelectorAll(".tab-pane");
            navLinks.forEach(function(navLink) {
                navLink.classList.remove("active");
            });
            tabPanes.forEach(function(tabPane) {
                tabPane.classList.remove("active");
            });
            this.classList.add("active");
            document.querySelector(tabId).classList.add("active");
        });
    });
});