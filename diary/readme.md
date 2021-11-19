# REST-API

## How to run backend server

To run the backend server, simply navigate into the diary directory using `cd diary`, and then run `mvn jetty:run -f backend/pom.xml` to start the server. In gitpod this is done automatically, in the second terminal tab.

## The client server message format

Due to our requirements, the server expects and accepts very specific messages. For both GET and POST requests the server uses a url of \*/api/diary/filename, where filename is the name of the file, without the .json ending. For POST requests, the body of the requests is simply the content of the diary named in the url, represented as a string, though no validation is performed by the server. For GET requests, the body of the server reponse consists of several diarynames intertwined with several such diary strings(again, no validation), all separated by the string `%%NEXT%%`. For example it could be `DiaryName1%%NEXT%%DiaryContent1%%NEXT%%DiaryName2%%NEXT%%DiaryContent2`.

## Testing of the API

In this project we have implemented an integrationtest to test the API. In our case, we felt this was enough, as the API merely stores and retrieves static data, and is not stateful. The integration test is ofcourse run as part of mvn clean install.

## Storing local files

In our application we never delete local files, and the reason for this is twofold. First, we use the files stored to autocomplete filling in the username, so the user doesn't have to do so manually for every startup. Secondly, as we pull from the server every time the user logs in, the local diaries will never be in danger of beeing overwritten, as they only get written to when the user saves(locally and to the server). This allows the flexibility of logging in to the app on any computer, while not forgeting the user every logout.

## Current backend location

Currently the backend stores the diaries in the diary folder(the one this readme is in), as this is the default working directory. We could make them be stored in backend/../resources, but this would be subject to change for any actual deployment, and therefore we have let it stay user.dir.

## Security
As this is just a school project, we knew we didn't have time to implement comprehensive security measures. Despite this, we have managed to implement some fundamental security, which allows several different users to save their diaries to the same location, without being able to view eachothers diaries. Every diary filename is the user's username, pluss their user pin, pluss the diary's name. The client and server are both blissfully unaware that they are transporting usernames and pins, they only see strings, and when the user logs in, the client sends a get request to the server retrieving any files that start with a specific string, which is allways their username pluss pin. This same trick is used to have the user be able to select their username from the dropdown menu, read from any diary filenames stored locally. In an actual project, this would be changed. 

# Persistance - core.json

## Source code for persistance layer

The implentation of persistance is done using [GSON](https://github.com/google/gson). GSON is used to read and write objects to JSON files.

The reason GSON was selected for this project is that it provides a very simple to use, open-source, framework which easily converts to and from JSON files. GSON also have extensive support for java generics and can convert pre-existing objects to/from JSON as it does not require java annotations in the object class.

A downside is that GSON is not currently [under development](https://old.reddit.com/r/androiddev/comments/684flw/why_use_moshi_over_gson/dgx3gpm/?context=3), but in maintenance mode. New features to java is therefore unlikely to be supported by GSON in the future, but critical bugs and minor fixes do currently get implemented.

### _Why is the project not migrated to Moshi, the successor of GSON?_

GSON fullfill all requirements set for this project. The nature of the Diary project does not require better separation of possible exceptions thrown nor does it benefit from Kotlin support.

If GSON becomes insufficient in future development the project will migrate over to Moshi, but the developers see no reason to invest development time into this convertion at this time.

## File storage

The file storage is based on implicit storage, json files are stored in the src/main/resources folder of the project run.
This is done to ensure that the saved JSON entries are stored in a safe location that is easy and predictable to access for the project code. We also consider this approach advantageous to keep all facets of the project within one folder, making it easy to keep track of all associated files and remove project files when project done.

# Testing and code verification

## Unit tests

---

Code performing important tasks need to be included in at least 1 Junit test method. This is to ensure that written code perform as excepted, both for expected inputs and for unexpected (i.e. throwing correct execeptions).

The adviced procedure is as follows:

1. Write new class/method.

2. Write unit tests for the written class/method

    - Verify that intended input is properly used and returns valid results.
    - Check actions when using invalid input. Correct exceptions, error correction etc. should be tested in this part.

3. If 2. uncover any issues those issues must be addresses before the new code is pushed/merge to develop.

## Unit test coverage

---

Unit test coverage is measured using [jacoco](https://github.com/jacoco/jacoco). Each modules test coverage is measured and aggregated into a common report available in `diary/testing/target/site/jacoco-aggregate/index.html`.

The project is expected to have test coverage above 70% for each module.
For gitlab the aggregate test coverage in the main branch is shown as both a project badge as well as a badge in the root-directory readme.md file.
The UI module will however not be included in the aggregate report, due to javafx tests not working as a headless gitlab pipeline. The aggregate coverage for the overall project is therefore set at 50% minimum.

## Checkstyle

---

This project utilizes the `maven-checkstyle-plugin` to maintain a cohesive coding style between every developer. The selected checkstyle profile chosen is a modified version of Google's coding conventions for java. The custom stylesheet is located in `diary/config/custom_checkstyle.xml`.

Warnings from checkstyle is allowed when merging on gitlab, but any branch containing a checkstyle error will not be allowed to be integrated into the master branch.

## Spotbugs

---

The maven spotbugs plugin, spotbugs-maven-plugin, is used to detect well known bugs and insecurities in java code. Any error stemming from spotbugs will prevent the branch from being integrated into the master branch on gitlab.

This project use a spotbugs exclusion list, which contains any spotbugs error that is allowed within the project. This exclusion list is located in `diary/config/spotbugs_exclude.xml`.

Currently one bug is excluded from bugspot tests. `DLS_DEAD_LOCAL_STORE` is included due to usage of `File.delete()` in the persistance layer. The `File.delete()` method returns a boolean value, however currently the project design just needs the file to delete without a return value, which is why the `DLS_DEAD_LOCAL_STORE` is on the exclusion list.

## How to run test

---

Tests are run in the verify phase of the maven lifecycle. Therefore a any branch can run every test, checkstyle, spotbugs, and jacoco, using the `mvn verify` command.

A daily jacoco coverage report is generated on the master branch on gitlab. While a code coverage percentage can be seen on the master branch badges, a more conclusive report can be downloaded as a artifact from the daily `jacocoReport` job performed under the CI/CD meny.

# Making installer and runtime

The project is exported as a runtime, using jlink, and as a installer, using jpackage. The creation of the installer and runtime is done on each operating system (OS) as these utilities can only make usable installers for the currently running OS.

## Prerequisite packages on system

### Linux/Debian

For the linux environment installer we require two non-default packages to be installed first, binutils and fakeroot. In the running gitpod environment we can add these by running the following commands, as described in the [github page for openjdk](https://github.com/jgneff/openjdk#java-platform):

```
sudo apt-get update
sudo apt install binutils fakeroot
```

### Windows

For windows the WiXToolset must be downloaded and installed before you can build the installer using jpackage. WixToolset can be found on their website [wixtoolset.org](https://wixtoolset.org/).

## Creating installer

After the OS specific prerequisite packages has been installed we can make our installer.

First we need to compile our entire project using `mvn compile`, followed by running jlink and jpackage in the ui module.

The following code will complete both these tasks:

```
mvn clean compile
mvn javafx:jlink jpackage:jpackage -f ui/pom.xml
```

After these commands are run the installer and runtime image will be created within the `ui/target` folder. The installer located in the `ui/target/dist` with the runtime contained in `ui/target/diary`.

## Using runtime and installer

### Windows

The produced (or provided) .exe file can be ran, in which case the application will be saved to the default position at

```
c:\Program Files\diary_Application
```

The runable java application is located within the runtime;

```
C:\Program Files\diary_Application\runtime\bin\diary
```

From a CMD window the app can then be run using the following commands:

```
cd C:\Program Files\diary_Application\runtime\bin\
diary
```

### Linux / Gitpod

The application can then be ran using the generated runtime image, as the required java version and dependancies are already present. If not generated within the same gitpod instance the installer located within `ui/target/diary/dist` can be ran using

```
sudo dpkg -i /workspace/gr2172/diary/ui/target/dist/diary-application_1.0.0-1_amd64.deb
```

for gitpod, local applications need to substitute in their own working directory to provide the path for the .deb file.

The install location using this .deb file is to the default install location, `/opt/diary_application`.

Alternatively the runtime can be directly executed by calling the diary file within the `diary/bin/`, which on gitpod will have the following path:

```
/workspace/gr2172/diary/ui/target/diary/bin/diary
```

### macOS

The application once installed using the generated dmg installer is located within

```
/Applications/diary_application
```
