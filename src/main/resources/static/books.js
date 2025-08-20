const booksGrid = document.getElementById("booksGrid");

    // Fetch data
    fetch("/books")
        .then(response => response.json())
        .then(books => {
            //If the "books" array is empty, an error should be generated
            if (books.length === 0) {
                booksGrid.innerHTML = `<p class="empty">🔍 No books found</p>`;
                return;
            }
            myDisplay(books);

        })
        .catch(error => {
            console.error('Error when loading JSON:', error);
            booksGrid.innerHTML = `<p class="error">⚠️ Failed to load books</p>`;
        });


function myDisplay(books) {
    books.forEach(book => {
            booksGrid.innerHTML += `                
    <article class="book-card">
      <div class="cover">
        <img class="ratio" src="https://img.freepik.com/free-vector/hand-drawn-flat-stack-books_23-2149331314.jpg" alt="Book cover image">
        <button class="favorite-button" aria-label="Add to favorites" title="Add to favorites">
          <svg viewBox="0 0 24 24" fill="currentColor" aria-hidden="true"><path d="M12.001 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 6.01 3.99 4 6.5 4c1.74 0 3.41.81 4.5 2.09C12.09 4.81 13.76 4 15.5 4 18.01 4 20 6.01 20 8.5c0 3.78-3.4 6.86-8.55 11.54l-1.449 1.31z"/></svg>
        </button>
      </div>
      <div class="book-info">
        <h3 class="book-title">${book.title}</h3>
        <p class="book-meta">${book.authorName} · ${book.year} · ${book.category}</p>
        <div class="actions">
        ${book.isAvailable === 'Available'
                ? `<span class="badge available">${book.isAvailable}</span>`
                : `<span class="badge taken">${book.isAvailable}</span>`}
         
          ${book.isAvailable === 'Available'
                ? '<button class="button primary take-button">Take</button>'
                : '<button class="button gray take-button is-taken">Reserve</button>'}
        </div>
      </div>
    </>
            `;
    });
}

