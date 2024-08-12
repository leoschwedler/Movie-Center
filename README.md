# 🎬 MoviesApp - TheMovieDB Client

## 🌟 Overview
MoviesApp is an Android application designed to fetch and display movies from The Movie Database (TMDb) using Retrofit. The app offers a smooth and engaging user experience with a Home screen showcasing top-rated, popular, and upcoming movies, and a Search feature for finding specific titles. With a focus on clean architecture, the app leverages coroutines for asynchronous operations, and Picasso for efficient image loading.

## 🛠️ Project Structure
The project is organized to efficiently manage the retrieval and display of movie data from TMDb:

### Key Components

#### `MainActivity`
- **Manages the main navigation and UI setup of the app.**
- Integrates an `AnimatedBottomBar` for seamless navigation between the `HomeFragment` and `SearchFragment`.
- Initializes and sets up the fragments on launch.

#### `HomeFragment`
- **Displays lists of top-rated, popular, and upcoming movies.**
- Uses a `ViewPager` for showcasing top-rated movies in a banner at the top.
- Features horizontal `RecyclerView`s for popular and upcoming movies, with infinite scrolling support.
- Navigates to `DetailActivity` when a movie is selected.

#### `SearchFragment`
- **Allows users to search for movies.**
- Utilizes a `RecyclerView` with a `GridLayoutManager` to display search results.
- Handles user input to query TMDb for matching movies.
- Supports click events to navigate to `DetailActivity` for more details on selected movies.

#### `MoviesAdapter`
- **Handles the display of movie data in `RecyclerView`s.**
- Manages the loading of movie posters using Picasso.
- Handles item clicks to trigger navigation to `DetailActivity`.

#### `BannerAdapter`
- **Manages the display of top-rated movies in a `ViewPager`.**
- Facilitates smooth swiping between movie banners.

#### `RetrofitService`
- **Manages network operations with TMDb using Retrofit.**
- Configures `OkHttpClient` with appropriate timeouts and API key handling via `AuthInterceptor`.
- Provides an instance of `TheMovieDBAPI` to perform API requests.

#### `TheMovieDBAPI`
- **Defines API endpoints for TMDb.**
- Contains methods for retrieving top-rated, popular, and upcoming movies, as well as searching for movies by query.

## 🚀 Implemented Features
- **Home Screen**: Displays top-rated, popular, and upcoming movies in a user-friendly layout.
- **Movie Search**: Allows users to search for movies and view results in a grid format.
- **Movie Details**: Displays detailed information about a selected movie, including a larger poster and more in-depth details.
- **Infinite Scrolling**: Supports infinite scrolling in `RecyclerView`s for popular and upcoming movies.
- **Image Loading**: Efficiently loads and caches images using Picasso.
- **Navigation**: Seamless navigation between Home and Search screens using an animated bottom navigation bar.

## 📷 Screenshots
<img src="https://github.com/user-attachments/assets/e21614ad-04d0-4d86-a9a0-a35d2d8f9117" width="200"/>
<img src="https://github.com/user-attachments/assets/7380b1d6-1d5f-4c36-828b-81891ec90d1e" width="200"/>
<img src="https://github.com/user-attachments/assets/f73e3600-8c52-45f9-8bee-c36f541af652" width="200"/>
<img src="https://github.com/user-attachments/assets/f91af287-9b9e-4287-9306-1f583f54f799" width="200"/>

## 🛠️ Dependencies
- `com.squareup.retrofit2:retrofit:2.11.0`: For handling network requests and communication with the TMDb API.
- `com.squareup.retrofit2:converter-gson:2.11.0`: For converting JSON responses into Kotlin objects.
- `com.squareup.picasso:picasso:2.8`: For image loading and caching.
- `nl.joery.animatedbottombar:library:1.1.0`: For creating an animated bottom navigation bar.

## 📌 License
This project is open-source under the MIT License.
