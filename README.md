# Exposed Playground

Exercises that test your SQL skills and teach you Exposed :)

## Setup

* This project requires Java 21 (if you're having problems with this, check 
  [Gradle JVM selection in IDEA](https://www.jetbrains.com/help/idea/gradle-jvm-selection.html))
* An in-memory database (H2) is created when running the tests
* Run [PcCompanyExercisesTest#exercise00](src/test/kotlin/com/example/pc/PcCompanyExercisesTest.kt) 
  to make sure everything is set up correctly and works.

## PC Company

Want to know what it's like to write queries for a website that sells computers? 
Do the exercises in [PcCompanyExercises](src/main/kotlin/com/example/pc/PcCompanyExercises.kt)!
* See schema in [PcCompanySchema](src/main/kotlin/com/example/pc/PcCompanySchema.kt)
* You can test your solutions by running [PcCompanyExercisesTest](src/test/kotlin/com/example/pc/PcCompanyExercisesTest.kt)
* If you get stuck, have a look at the plain SQL solutions in [sql-solutions/pc](src/main/resources/sql-solutions/pc)
  and try to implement them with Exposed.

These exercises are taken from [sql-ex](https://sql-ex.ru/?Lang=1) and date back to 2002.
