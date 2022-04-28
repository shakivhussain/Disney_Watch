![](./Assets/poster.jpg)

# Disney Watch 🎥

A movies listing application to fetch movies from TMDB Api using Retrofit Library. Implemented MVVM Architecture and Latest Jetpack Components.

## Built With 🛠

- [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.

## Features 🚀

 - Explore the Upcoming, Popular and Top Rated Movies.
 - Get Detailed description of all the movies.
 - Bookmark your favorite movies.
 - Get movies trailer within the application.
 - Search for movies.

## Download 📥
- Download latest APK from [Click Here](https://www.dl.dropboxusercontent.com/s/7e46wqlzpc7t5l2/DisneyWatch.apk)

## Screenshots 📷 
![light_screenshot](Assets/screen_shot.jpg)

## Architecture 🗼

This project follows the famous MVVM architecture and best practices from Google's
[GithubBrowserSample](https://github.com/android/architecture-components-samples/tree/master/GithubBrowserSample)

![architecture](Assets/mvvm_architecture.png)

## Project Structure 📂

```
.
├── data
|   |
|   ├── api
|   |   ├── constants
|   |   |            └── ApiConstants.java
|   |   └── network
|   |              ├── ApiClient.java
|   |              └──  ApiServices.java
│   ├── model
│   │   ├── User.java
│   └── repository
│       └── UserRepository.java
├── ui
│   ├── adapter
│   |   ├── UserAdapter.java
│   ├── fragment
│   |   ├── HomeFragment.java
│   ├── activities
│   |   └── MainActivity.java
│   ├── viewmodel
│   |   ├── AppViewModel.java
└── utils
        └── AppConstant.java 
    
```

## Requirements 🎯 
- Android 6.0 and Above
- Min sdk version 23

## Permissions 💻
- Internet

