# Popcorn Demo Application

### Project

Build `./gradlew build`

Run tests: `./gradlew testDebugUnitTest`


### Architecture
The architecture of the application is MVVM, where the following layers can be found:
Data layer:
* Remote Source -> communication with the API.
* Local Source -> Shared Preference for the suggestion.
* Since there are no other persistent data in the application than the suggestion list, there is no repository or other persisting layer due to lack of data.
* The Remote and Local Sources are used by the use case layer which defines uses-case where each of them are for one single and simple task.
View Layer:
* ViewModel -> contains the user interactions and data handling for the views, they use only the use-case layer.
* View -> observes the view model and displays the received data and signals to view model the user actions.

### Libraries and Dependencies

#### Dependency Injection

For managing the dependencies, the application uses Koin, which is a "lightweight dependency injection framework for Kotlin developers". Compared to other DI frameworks the reason for choosing it was that it's easy to setup, has no proxy, no code generation, no reflection.

#### UI

For the UI the app uses the Material Components for Android which helps developers to comply more easily to the Material Design Guidelines.
For navigation between screens, the new Navigation Components was used which 

#### Networking

For communicating with the search API, the application uses Retrofit library, while for JSON serialization, the app uses Moshi. For networking and for other background operations, the demo app uses Coroutines.

#### Other
For Image loading, the app uses Glide.

For unit testing the following libraries were used: Mockito, Mockito-Kotlin, MockWebserver and the test implementations for the other used libraries (koin, coroutine).

### Time
The application was done in around 1 day, maybe a little bit more (2 half-days) plus the project setup before this.
