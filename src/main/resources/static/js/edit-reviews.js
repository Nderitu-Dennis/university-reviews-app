function openEditModal(button) {
    document.getElementById("editReviewId").value = button.getAttribute("data-review-id");
    document.getElementById("editTitle").value = button.getAttribute("data-title");
    document.getElementById("editReviewText").value = button.getAttribute("data-review-text");
    document.getElementById("editRating").value = button.getAttribute("data-rating");

    //  Get university name from the page
    let universityName = document.querySelector("input[name='reviewedUniversity']").value;

    //  Update the hidden field inside the modal
    document.getElementById("editReviewForm").querySelector("input[name='reviewedUniversity']").value = universityName;

    // Show the modal
    document.getElementById("editReviewModal").classList.remove("hidden");
}

function closeEditModal() {
    document.getElementById("editReviewModal").classList.add("hidden");
}
