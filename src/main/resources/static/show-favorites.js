const favoritesList = document.getElementById("favoritesList");

// Fetch data
fetch("books/favorites")
    .then(response => response.json())
    .then(favorites => {
        //If the "loans" array is empty, an error should be generated
        if (favorites.length === 0) {
            favoritesList.innerHTML = `<p class="empty">🔍 No favorite books</p>`;
            return;
        }
        displayFavorites(favorites);

    })
    .catch(error => {
        console.error('Error when loading JSON:', error);
        favoritesList.innerHTML = `<p class="error">⚠️ Failed to load favorite books</p>`;
    });

function displayFavorites(favorites) {
    favorites.forEach(favorite => {
        favoritesList.innerHTML += `
<div class="book-row">
 <img src="https://images.emojiterra.com/google/noto-emoji/unicode-16.0/color/share/1fa75.jpg" alt="Book cover image">
 <div>
    <p class="row-title">${favorite.title}</p>
    <p class="row-meta">${favorite.authorName} · ${favorite.year} · ${favorite.category}</p>
 </div>
 ${favorite.isAvailable === 'Available'
            ? `<span class="badge available">${favorite.isAvailable}</span>`
            : `<span class="badge taken">${favorite.isAvailable}</span>`}
         
          
</div>
${favorite.isAvailable === 'Available'
            ? '<button class="button primary take-button">Take</button>'
            : '<button class="button gray take-button is-taken">Reserve</button>'}
            `;

    });
}
