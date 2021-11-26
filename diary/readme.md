# Links

[Root readme](../readme.md)  
[Backend readme](backend/readme.md)  
[Core readme](core/readme.md)  
[Frontend readme](frontend/readme.md)  

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
### Installers
Generated installers are provided through [folk.ntnu.no](https://folk.ntnu.no/stiankg/IT1901/Installers/).
* [Windows](https://folk.ntnu.no/stiankg/IT1901/Installers/Windows/)
* [Linux](https://folk.ntnu.no/stiankg/IT1901/Installers/Linux/)
* [macOS](https://folk.ntnu.no/stiankg/IT1901/Installers/macOS/)

### Windows

The produced (or provided) .exe file can be ran, in which case the application will be saved to the default position at

```
c:\Program Files\diaryApplication
```

The runable java application is located within the runtime;

```
C:\Program Files\diaryApplication\runtime\bin\diary
```

From a CMD window the app can then be run using the following commands:

```
cd C:\Program Files\diaryapplication\runtime\bin\
diary
```

### Linux / Gitpod

The installer on gitpod would be located within `ui/target/diary/dist`, and can be ran using the following command for gitpod:
```
sudo dpkg -i /workspace/gr2172/diary/ui/target/dist/diary-application_1.0.0-1_amd64.deb
```
Local applications need to substitute in their own working directory to provide the path for the .deb file, or double-click the downloaded .deb file if their configuration allows this action.

The install location using this .deb file is to the default install location, `/opt/diaryapplication`. The application can therefore be run using the following commands;
```
cd /opt/diaryapplication/bin/
./diaryApplication
```

Alternatively, if you have just compiled the installer and runtime, the runtime can be directly executed by calling the diary file within the `diary/bin/`, which on gitpod will have the following path:

```
/workspace/gr2172/diary/ui/target/diary/bin/diary
```

### macOS

The application once installed using the generated dmg installer is located within

```
/applications/diaryApplication
```
