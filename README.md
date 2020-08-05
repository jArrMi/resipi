# Resepi - Sample Reference Project
Pronouns like `ˈresəˌpē`, is a sample application showcasing how to use clean architecture and MVVM on Android using RxJava & RxAndroid.

# 1. Architecture

## 1.1 Clean Architecture
By using Clean Architecture we can achieve the following benefits:

* Separation of code in different layers with **assigned responsibilities** making it easier for further modification.
* High level of abstraction
* **Loose coupling** between the code
* **Testable** application

The Clean Architecture consists on three main layers:

* **PRESENTATION LAYER:** You can find in this layer the graphical interface that captures the user's event, validate user's entries and shows the results.
Common components in this layer are: Activites, Fragments, Dialogs and ViewModels/Presenters.
* **DOMAIN LAYER:** This layer contains **use cases** which are the components in charge of handling the business logic of your application.
* **DATA LAYER:** This layer are responsible for managing the data access via either remote or local sources.
Common components in this layer are: Model Classes, Repositories with Local and Remote Data Sources, Web services and Local Storage.

![Clean Architecture](https://cdn-images-1.medium.com/max/800/1*a-AUcEVdyRJhIepo9JyJBw.png)


## 1.2 Single Activity & Navigation Architecture
This project follow a single Activity pattern and Jetpack Navigation in order to follow these best practices:

* Use activities as entry points into your app's UI. When a user goes to launch your app, their launching only one activity.
* Share data between screens by using the activity scope
* Use the Navigation Architecture Component to make navigating between destinations even easier.
* Navigate between destinations with the new gradle plugin, Safe arguments passed.
* Extract your business logic, don't test at the destination level!
* FragmentScenario for testing and verifying fragments.
* Mock the NavController to confirm that you're calling the right navigate calls.
* Inject dependencies with FragmentFactory.
* Use multiple tasks when appropriate.
* Don't contort your app architecture. Do what's right for your app.

![Single Activity](https://i.imgur.com/xFwEsIe.png)

![Jetpack Navigation](https://developer.android.com/images/topic/libraries/architecture/navigation-design-graph-top-level.png)


# 2. Dependencies

* **Kotlin:** Kotlin libraries as primary app programming language
    * See: [Kotlin](https://kotlinlang.org/)
* **AndroidX:** Libraries used as major improvement to the original Android Support Library. AndroidX ships separately from the Android OS and provides backwards-compatibility across Android releases.
    * See: [AndroidX](https://developer.android.com/jetpack/androidx)
* **ViewModel** Libraries handling viewModel classes designed to store and manage UI-related data in a lifecycle conscious way.
    * See: [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* **LiveData** Libraries handling LiveData as lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
    * See: [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* **Room:** Persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
    * See: [Room](https://developer.android.com/topic/libraries/architecture/room)
* **Koin:** A pragmatic lightweight dependency injection framework for Kotlin developers.
    * See: [Koin](https://insert-koin.io/)
* **MultiDex:** Library used when your app and the libraries it references exceed 65,536 method.
    * See: [MultiDex](https://developer.android.com/studio/build/multidex)
* **Navigation:** Library which helps you implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer. The Navigation component also ensures a consistent and predictable user experience.
    * See: [Navigation](https://developer.android.com/guide/navigation)
* **Databinding**:  is a support library that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
    * See [Databinding](https://developer.android.com/topic/libraries/data-binding)
* **Picasso:** Picasso allows for hassle-free image loading in your application—often in one line of code!
    * See: [Picasso](https://square.github.io/picasso/)

# References

* Single Activity: Why, When, and How:
[Android Dev Summit 18](https://www.youtube.com/watch?v=2k8x8V77CrU)

* [Android Jetpack Libraries](https://developer.android.com/jetpack)

* [Google Samples Android Architecture](https://github.com/googlesamples/android-architecturex)

* Clean Architecture:
    * [Uncle Bob](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

* Getting on the same page with Paging 3 by [@FMuntenescu](https://www.twitter.com/⁦FMuntenescu): [Getting on the same page with Paging 3](https://android-developers.googleblog.com/2020/07/getting-on-same-page-with-paging-3.html)