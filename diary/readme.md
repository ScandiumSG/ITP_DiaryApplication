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

Currently on bug that is excluded from bugspot tests. `DLS_DEAD_LOCAL_STORE` is included due to usage of `File.delete()` in the persistance layer. The `File.delete()` method returns a boolean value, however currently the project design just needs the file to delete without a return value, which is why the `DLS_DEAD_LOCAL_STORE` is on the exclusion list.

## How to run test

---

Tests are run in the verify phase of the maven lifecycle. Therefore a any branch can run every test, checkstyle, spotbugs, and jacoco, using the `mvn verify` command.

A daily jacoco coverage report is generated on the master branch on gitlab. While a code coverage percentage can be seen on the master branch badges, a more conclusive report can be downloaded as a artifact from the daily `jacocoReport` job performed under the CI/CD meny.
