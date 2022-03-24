# Movies

Introduction
-------------

### Building
You can open the project in Android studio and press run.
### Testing
The project uses both instrumentation tests that run on the device
and local unit tests that run on your computer.

#### Device Tests
##### Database Tests
The project creates an in memory database for each database test but still
runs them on the device.
To run both of them and generate a coverage report, you can run:

`./gradlew fullCoverageReport` (requires a connected device or an emulator)

#### Local Unit Tests
##### ViewModel Tests
Each ViewModel is tested using local unit tests with mock Repository
implementations.
##### Repository Tests
Each Repository is tested using local unit tests with mock database.


### Libraries
* [Android Support Library][support-lib]
* [Android Architecture Components][arch]
* [Android Data Binding][data-binding]
* [Hilt][hilt] for dependency injection
* [mockito][mockito] for mocking in tests

#### TMDB API_KEY
Create a private key on [The Movie Database (TMDB)][tmdb] to get access on TMDB APIs 
or use API_KEY="d13430285a5e48c0e10532e9e3914cab" for a quick start.

[mockwebserver]: https://github.com/square/okhttp/tree/master/mockwebserver
[support-lib]: https://developer.android.com/topic/libraries/support-library/index.html
[arch]: https://developer.android.com/arch
[data-binding]: https://developer.android.com/topic/libraries/data-binding/index.html
[hilt]: https://developer.android.com/training/dependency-injection/hilt-android
[mockito]: http://site.mockito.org
[tmdb]: https://www.themoviedb.org