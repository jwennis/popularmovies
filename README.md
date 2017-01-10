# Popular Movies

Android Developer Nanodegree Project #2

Popular Movies is an app that demonstrates the use of some common Android design patterns and techniques, as described below. Using [TheMovieDB.org API](https://developers.themoviedb.org/3/getting-started), the app entry point displays a grid of currently popular and historically top rated movie posters. Clicking a movie launches the detail view, displaying information about the movie, including trailers and reviews.

<img src="https://raw.githubusercontent.com/jwennis/popularmovies/master/screens/2017-01-10%2019.15.37.png" width="300" />
<img src="https://raw.githubusercontent.com/jwennis/popularmovies/master/screens/2017-01-10%2019.16.05.png" width="300" />

Below are the project description and requirements.

## Stage 1:  Main Discovery Screen, A Details View, and Settings

In this stage you’ll build the core experience of your movies app.
Your app will:
* Upon launch, present the user with a grid arrangement of movie posters.
* Allow your user to change sort order via a setting:
 * The sort order can be by most popular, or by top rated
* Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
 * original title movie poster 
 * image thumbnail
 * A plot synopsis (called overview in the api)
 * user rating (called vote_average in the api)
 * release date

## Stage 2: Trailers, Reviews, and Favorites

In this stage you’ll add additional functionality to the app you built in Stage 1.
* You’ll add more information to your movie details view:
* You’ll allow users to view and play trailers ( either in the youtube app or a web browser).
* You’ll allow users to read reviews of a selected movie.
* You’ll also allow users to mark a movie as a favorite in the details view by tapping a button(star). This is for a local movies collection that you will maintain and does not require an API request*.
* You’ll modify the existing sorting criteria for the main view to include an additional pivot to show their favorites collection.
Lastly, you’ll optimize your app experience for tablet.

## Specification

**User Interface - Layout**
* UI contains an element (e.g., a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated.
* Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.
* UI contains a screen for displaying the details for a selected movie.
* Movie Details layout contains title, release date, movie poster, vote average, and plot synopsis.
* Movie Details layout contains a section for displaying trailer videos and user reviews.

**User Interface - Function**
* When a user changes the sort criteria (most popular, highest rated, and favorites) the main view gets updated correctly.
* When a movie poster thumbnail is selected, the movie details screen is launched.
* When a trailer is selected, app uses an Intent to launch the trailer.
* In the movies detail screen, a user can tap a button(for example, a star) to mark it as a Favorite.

**Network API Implementation**
* In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.
* App requests for related videos for a selected movie via the /movie/{id}/videos endpoint in a background thread and displays those details when the user selects a movie.
* App requests for user reviews for a selected movie via the /movie/{id}/reviews endpoint in a background thread and displays those details when the user selects a movie.

**Data Persistence**
* App saves a "Favorited" movie to SharedPreferences or a database using the movie’s id.
* When the "favorites" setting option is selected, the main view displays the entire favorites collection based on movie IDs stored in SharedPreferences or a database.

**General Project Guidelines**
* App conforms to common standards found in the Android Nanodegree General Project Guidelines. 

**Optional**
* Implement a content provider to store favorite movie details with a database such as SQLite. Store the title, poster, synopsis, user rating, and release date and display them even when offline.
* Implement sharing functionality to allow the user to share the first trailer’s YouTube URL from the movie details screen.
