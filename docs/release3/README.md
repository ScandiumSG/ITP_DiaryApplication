 <div id="top"></div>

 <!-- PROJECT TITLE -->
<br />
<div align="center">
  <img src="../logo/logo.svg" alt="Logo" width="250" height="250">
  <h1>Release 3</h1>
  <p>
    <a href="https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2172/gr2172"><strong>Back to Root</strong></a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-project-3">About Project 3</a>
    </li>
    <li>
      <a href="#headless-testing">Headless Testing</a>
    </li>
    <li><a href="#rest-api">Rest-API</a></li>
    <li><a href="#deployment">Deployment</a></li>
    <li><a href="#work-schedule-and-habits">Work schedule and habits</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

<!-- ABOUT PROJECT 3 -->
</br>

## Intro

In this readme folder are more specifics related to project three described. The combination of the heavily weight of this assignment and four curious students set together, have we spent hundreds of hours creating the final programming related product for this course. It’s been an interesting journey. 

<!-- HEADLESS TESTING-->

## Headless

During project 3 all members developed a curiosity about headless testing, how it works and how it may be included in the CI and Jacoco report. A great amount of extra time has therefor been spent trying to implement headless testing. We managed to test the application headless as the result. However, we didn't manage to create or find a monocle library the docker-image could use to make it readable to Gitlab. Even though we didn't reach our goal did the process teach us a fair amount about how headless testing works and what it takes to create necessary properties it depend on.

Another technology we tried to use in order to implement headless testing was [selenium](https://www.selenium.dev/). By simply running a Selenium test using a headless browser that operates as your typical browser, but without a user interface, making it to automated testing. We tried to make the docker image to download two browsers (chrome and firefox) intending to open the application on a VM from GitLab. We did get closer to the final goal. However, one issue stopped us reaching it. The maven surefire dependency had conflicts.

As Phantom and Karma also had some issues with gitpod and are easier to use with JAR we had to accept the failure and rather keep going on the actual project. Our ideal approache

<img src="selenium-screenshot.png" alt="Logo" width="650" height="500">

<!-- REST API -->

## Rest API

In this release we implemented a REST-api, which saves the diaries the user creates to the server. The server currently is quite simple, only supporting GET to retrieve diaries and POST to update the server. We still store diaries locally, as a simple kind of cache. The backend starts automatically when the repository is opened in gitpod. To read more about how the API functions and what commands are needed to start it, visit the [readme.md](../../diary/readme.md) in diary. Using the provided installers to run application would not allow interaction with the REST API, as the REST API does not have a permanent, publicly available server to run from.

<!-- DEPLOYMENT-->

## Shippable Program

The local version of the diary application can now be provided as a java runtime or as a installable version.

Use of the installable version allows anyone to utilize the diary application without having to install java and maven previously. The saved diary files are stored in a users home directory, within a diary folder. However as mentioned in [Rest API](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2172/gr2172/-/tree/develop/docs/release3#rest-api) installed version can't retrieve or store data on a remote server.

To install the diary application simply run the installation medium as specified in the [diary readme](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2172/gr2172/-/tree/develop/diary#using-runtime-and-installer), the required runtime will then be available to start the application.

<!-- WORK SCHEDULE AND HABITS-->

## Work Habits

All members have been working almost daily. With multiply sleepless nights and long hours have the project been worked on almost continuously every day, every week. 

<br/>

As well as working hours is coordination and planning been executed by two to three meeting each week. This have surely benefitted us in organizing and working with a proper efficiency. We considered to track working hours but decided too not to. We must admit to regret that decision a bit. I would been fun to actual show you that we are not joking about continuously working together and individually. It’s been fun to make this project a bit extra.

<!-- CONTACT -->

## Contact

</br>
<!-- Alphabetical first-name order -->

-   Jakob Lien - [@jlien11](https://github.com/jlien11) - jakobli@stud.ntnu.no
-   Lars Overskeid - [@Lars-over](https://github.com/Lars-over) - larsover@stud.ntnu.no
-   Sebastian Veum - [@nazgul735](https://github.com/nazgul735) - sebasv@stud.ntnu.no
-   Stian Gaustad - [@StianKGaustad](https://github.com/StianKGaustad) - stiankg@stud.ntnu.no

<!-- Alphabetical last-name order -->
<!--
-   Stian Gaustad - [@StianKGaustad](https://github.com/StianKGaustad) - stiankg@stud.ntnu.no
-   Jakob Lien - [@jlien11](https://github.com/jlien11) - jakobli@stud.ntnu.no
-   Lars Overskeid - [@Lars-over](https://github.com/Lars-over) - larsover@stud.ntnu.no
-   Sebastian Veum - [@nazgul735](https://github.com/nazgul735) - sebasv@stud.ntnu.no
-->
</br>

**Project Link:** [Diary](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2172/gr2172)

<div align="right">
  <a href="#top">back to top</a>
</div>
