# GitHubStars
Sample Android app to show the most starred GitHub repositories.

Jetpack Compose + MVI, ViewModels and Repository with simple endpoint for GitHub REST API.

Used libraries:

* Koin - dependency injection
* Coil - download and cache images
* ktor-client - HTTP client with fine errors procession
* ktor-client-mock - showcase of HTTP requests and deserialization unit tests
* kotlinx-serialization - simplest way for JSON deserialization
* kotlinx-coroutines - for asynchronous network requests 

Project ready for KMM transformation, network and repository classes may be moved to shared module quite easily.
