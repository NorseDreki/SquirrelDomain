# SquirrelDomain
An example of programming work done for Android using TDD and built with "Ports and Adapters" architecture and RxJava.

>**Note:**
The repository contains only a part of real project; its purpose is to showcase development approach and provide a complete standalone buildable sample covered with tests.

##Key Principles
- Project is developed using **TDD** which means tests are written first and drive the whole process. Mockito and Hamcrest do help a lot and are primary tools when writing expectations and assertions. Tests are mostly written in a "mockist" way rather than a "classicist" way (Martin Fowler's [terminology](http://martinfowler.com/articles/mocksArentStubs.html)).

- The whole project is structured according to the [**Ports & Adapters**](http://alistair.cockburn.us/Hexagonal+architecture) architecture. It strictly follows the principle "dependencies should point inwards". This allows to have a set of loosely coupled modules maintaining high cohesion. For example, this repository illustrates how easy it was to drop other modules and have only the "domain" module whilst keeping it buildable and covered with tests. Also, such a modular structure allows to keep Android dependencies off domain business: the "domain" module uses "java" plugin only, and tests are also "plain JUnit" running on JVM; even Robolectric is not needed to achieve this.

- The project heavily uses [**RxJava**](https://github.com/ReactiveX/RxJava) as a primary gateway to Functional Reactive Programming. All data streams are obtained and manipulated using RxJava. There is also an example of using custom Transformers to encapsulate repeatable transformation tasks.

- Most of the development is done using the [**Kotlin**](kotlinlang.org) language which is a modern JVM language. It is less verbose, supports closures (which makes it easy to deal with RxJava), type inference, ensures null safety, supports data classes, etc. Not only does this allow to introduce less bugs but also greatly improves speed of development.
