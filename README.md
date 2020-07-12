# WeatherApp
![](https://github.com/mrea1/WeatherApp/workflows/Android%20CI/badge.svg)

This is a simple Android app written in Kotlin that displays the current weather for a input location.

Here Destination [Weather API](https://developer.here.com/documentation/destination-weather/dev_guide/topics/resource-report.html) is used to fetch the current weather information

#### Libraries used:
  - [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) for easy MVVM implementation
  - [Material Components](https://github.com/material-components/material-components-android) for beautiful Material UI
  - [Timber](https://github.com/JakeWharton/timber) for logging
  - [Retrofit](https://github.com/square/retrofit) for REST communication (duh)
  - [Moshi](https://github.com/square/moshi) for Kotlin-friendly deserialization
  - [Koin](https://github.com/InsertKoinIO/koin) for dead-simple Dependency Injection
  - [Lottie](https://github.com/airbnb/lottie-android) for smooth weather animations
  - [Coil](https://github.com/airbnb/lottie-android) for modern network Image fetching using Kotlin coroutines
  - [Mockk](https://github.com/mockk/mockk) for Kotlin-friendly mocking in unit tests

## Architecture
This app follows [Clean architecture by Uncle Bob](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

The code is divided into 3 main layers: Data, Domain, and Presentation 

### Data

#### Networking
The Data layer is responsible for fetching data from the API and supplying it to the Domain layer.

Retrofit is used with Moshi to fetch and deserialize the response from the API. A set of response objects (DTOs) are included in this layer. They map 1-1 to the API contract

A OkHttp Interceptor handles authentication with a simple API Key-based system. 

[OAuth2.0](https://developer.here.com/documentation/authentication/dev_guide/topics/token.html) could be a future improvement

#### Repository
A simple in-memory repository is used for holding the data returned by the API.

The repository supports synchronous and asynchronous fetching, as well as refreshing from the API.

A simple `Mapper` class is used to map from the response objects (DTOs) to the domain entities

### Domain

The domain layer is responsible for handling data flow and business logic

A set of `Entities` are used in this layer. These models more accurately map to the app business requirements

`UseCase`s are included in this layer. They use a suspend function to allow the presentation layer to interface via Kotlin coroutines


### Presentation

The presentation layer is responsible for displaying the data returned from the Domain layer to the user.

The MVVM pattern is used here to divide the UI logic from the Android framework. The `View` (Activity currently) is designed to be as "dumb" as possible.

The ViewModel exposes a `UiState` LiveData stream that continually emits based on external events like user input and network responses. The UiState should be a nearly direct representation of the UI on the screen. Some "sub-UiStates" were created to standardize this approach (ImageUiState, AnimationUiState, TextInputUiState). This approach was inspired by [Jetpack Compose](https://developer.android.com/jetpack/compose)

`viewModelScope` CoroutineScope is used to easily handle cancelling network calls based on the Android Activity lifecycle

DataBinding is leveraged to minimize logic and boilerplate in the View. Most data from the ViewModel's UiState is directly mapped to the Android `View`s

#### Animations 
Some animations were added using Lottie to portray the current weather conditions. A small set of animations were added; this could be expanded to include all possible weather conditions

To view the various animations available, **pull out the debug drawer** from the very left side of the screen


## Continuous Integration (CI)
Github Actions is used to build and run unit tests on each commit on the `master` branch

**See this build** for an example
https://github.com/mrea1/WeatherApp/runs/862106667

Future enhancements could include:
  - Continuous Delivery (CD) using a framework like Firebase App Distribution or HockeyApp (now Visual Studio App Center)
  - Automated UI testing using a framework like Waldo or Appium

## Testing
A small set of unit tests are included. The architecture in the app allows for easy unit testing.

JUnit 4 is used currently. A future improvement could be to use JUnit 5.

Mockk is used to mock dependencies in unit tests. It works well with coroutines, in contrast to Mockito

UI testing with Espresso is not really needed here, since the bulk of UI logic is easily unit tested on the ViewModel layer

