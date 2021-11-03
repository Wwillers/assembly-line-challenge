# Assembly Line Challenge
![<Wwillers>](https://img.shields.io/circleci/build/github/Wwillers/assembly-line-challenge)
[![CircleCI](https://badgen.net/badge/icon/circleci?icon=circleci&label)](https://https://circleci.com/)  
[![Docker](https://badgen.net/badge/icon/docker?icon=docker&label)](https://https://docker.com/)
[![Maven](https://badgen.net/badge/icon/maven?icon=maven&label)](https://https://maven.apache.org/)
  

## Problem Statement - Assembly Line

We need to adjust our assembly lines using the below constraints.
there are so many possibilities, you should write a program to organize this production.

Constraints:
- Don't use plugins utilities or frameworks for date calculations (e.g. Joda-Time)
- Use input.txt as the system input data file.
- The production has multiple assembly lines and each one has the morning, lunch and afternoon periods.
- Each period has multiple steps of the production process. Obviously the lunch period doesn't have them.
- The morning period begins at 9:00 am and must finish by 12:00 noon, for lunch.
- The afternoon period begins at 1:00 pm and must finish in time for the labor gymnastics activities.
- The labor gymnastics activities can start no earlier than 4:00 pm and no later than 5:00 pm.
- The production step titles don't have numbers in it.
- All the numbers in the production step titles are the step time in minutes or the word "maintenance" which one represents a 5 minutes of technical pause.
- It won't have interval between the process steps

## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/java/technologies/downloads/#java11)
- [Maven](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.assemblyline.assemblyLine.AssemblyLineApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

### Under construction
