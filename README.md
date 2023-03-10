# Movies Assignment

Movies Assignment: An Android app using jetpack compose and MVVM with clean architecture approach.

App features :

<ul style="list-style-type:circle">
  <li>List of movies including two section: 
    <ul style="list-style-type:disc">
       <li>Playing now</li>
       <li>Most popular</li>
    </ul>
 </li>
 <li>Movie detail - information about specific movie.</li>
</ul>

# Movie Listing Screen

<img width="200" alt="list" src="https://user-images.githubusercontent.com/127376760/224245209-989dc52a-aa57-4d53-bf6f-8d67ab503517.png">

# Bottom sheet for movie detail

<img width="200" alt="detail" src="https://user-images.githubusercontent.com/127376760/224245013-1d8c5fbb-9485-439f-b4dc-670f3ce4db36.png">

## Architecture 

Clean architecture is a software design pattern that separates the concerns of an application into independent layers that are easy to maintain
and higly testable.

<ul style="list-style-type:circle">
  <li>Better separation of concerns. Each Api request has its own use case and repository.</li>
  <li>Each feature can be developed independently from other features.</li>
</ul>

## Project structure

 <ul style="list-style-type:circle">
  <li>App - This layer include the app modules. Modules are way to create objects of third party classes, interfaces and abstrat classes using hilt. </li>
  <li>Data - Data layer contains the repository implementation, data source and data models. Repositories are responsible for fetching and storing data from
  data source. Data source is actual source of data such as web service. Data models are the objects that represents tha data retrieved from data sources.
  </li>
  <li>Domain - This layer contains the core business logic of the application. it include the model, repo, use cases. This layer is independent of any 
  framework and can be tested independently.</li>
  <li>Presentation - This layer contains the user interface of the application and handling user inputs. This layer also inclues the view model which 
  is responsible for managing UI state and surviving configuration changes.</li>
</ul>

# Library used in this applicaiton

<ul style="list-style-type:circle">
  <li><a href="https://developer.android.com/jetpack/compose?gclid=EAIaIQobChMIpKSisODQ_QIVQUorCh2xYQTZEAAYASAAEgK0r_D_BwE&gclsrc=aw.ds">Jetpack Compose</a></li>
  <li><a href="https://developer.android.com/training/dependency-injection/hilt-android">Hilt dependency injection</a></li>
  <li><a href="https://coil-kt.github.io/coil/compose/">Coil Compose</a></li>
   <li><a href="https://square.github.io/retrofit/">Retrofit</a></li>
</ul>

#TODO

<ul style="list-style-type:circle">
  <li>Unit tests.</li>
</ul>





