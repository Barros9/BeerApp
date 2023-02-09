# BeerApp

A sample Android native app that shows a list of beers using the [Punk API](https://punkapi.com/). The app is based
on [Android developers architecture](https://developer.android.com/jetpack/guide?gclsrc=ds&gclsrc=ds)
developed with [Jetpack Compose](https://developer.android.com/jetpack/compose).

The app follows an implementation of mudularazition by features:
* Features
  - *detail*, it shows the detail page
  - *home*, it shows the main page
* Libraries
  - *beer*, it exposes the information related beers and takes care of managing online and offline logic 
  - *navigator*, it provides a way to navigate between pages
  - *ui*, it contains Compose theme configuration

## Medium

This app is mentioned in the following articles:

* [Android app modularization with Clean Architecture](https://barros9.medium.com/android-app-modularization-with-clean-architecture-9aa2e135a99a)

## Libraries

* The network API requests are made using [Retrofit](https://github.com/square/retrofit) and the data are
  stored with [Room](https://developer.android.com/training/data-storage/room)
* The Dependency Injection module is based
  on [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)

## Gradle
The Gradle build is implemented with Gradle’s Kotlin DSL, an alternative syntax to the traditional Groovy DSL.

## Thanks


<div>Icons made by <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
