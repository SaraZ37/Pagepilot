const borrowedList = document.getElementById("borrowedList");

// Fetch data
fetch("api/loans")
    .then(response => response.json())
    .then(loans => {
        //If the "loans" array is empty, an error should be generated
        if (loans.length === 0) {
            borrowedList.innerHTML = `<p class="empty">🔍 No borrowed books</p>`;
            return;
        }
        displayBorrowed(loans);

    })
    .catch(error => {
        console.error('Error when loading JSON:', error);
        borrowedList.innerHTML = `<p class="error">⚠️ Failed to load borrowed books</p>`;
    });


function displayBorrowed(loans) {
    loans.forEach(loan => {
        borrowedList.innerHTML += `
<div class="book-row">
  <img src="https://img.freepik.com/premium-vector/blue-3d-book-vector-icon-hardcover-educational-literature_92753-6691.jpg" alt="Book cover image">
  <div>
    <p class="row-title">${loan.bookTitle}</p>
    <p class="row-meta">${loan.bookAuthor}</p>
  </div>
  <span class="badge dates">Borrowed: ${loan.borrowedDate} → Due to date: ${loan.dueDate}</span>
</div>
<button class="button accent-600 take-button">Return</button>
            `;

    });
}