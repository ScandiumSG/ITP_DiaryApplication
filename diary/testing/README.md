# Testing goals

## Unit tests

---

Code performing important tasks need to be included in at least 1 Junit test method. This is to ensure that written code perform as excepted, both for expected inputs and for unexpected (i.e. throwing correct execeptions).

The adviced procedure is as follows:

1. Write new class/method.

2. Write unit tests for the written class/method

    - Verify that intended input is properly used and returns valid results.
    - Check actions when using invalid input. Correct exceptions, error correction etc. should be tested in this part.

3. If 2. uncover any issues those issues must be addresses before the new code is pushed/merge to main.

## Unit test coverage

---

Unit test coverage is measured using [jacoco](https://github.com/jacoco/jacoco). Each modules test coverage is measured and aggregated into a common report available in `diary/testing/target/site/jacoco-aggregate/index.html`.

The project is expected to have aggregate test coverage above 70%. For gitlab the test coverage in the main branch is shown as both a project badge as well as a badge in the root-directory readme.md file.

## Checkstyle

---

This project utilizes the `maven-checkstyle-plugin` to maintain a cohesive coding style between every developer. The selected checkstyle profile chosen is a modified version of Google's coding conventions for java. The custom stylesheet is located in `diary/config/custom_checkstyle.xml`.

Warnings from checkstyle is allowed when merging on gitlab, but any branch containing a checkstyle error will not be allowed to be integrated into the master branch.

## Spotbugs

---

The maven spotbugs plugin, spotbugs-maven-plugin, is used to detect well known bugs and insecurities in java code. Any error stemming from spotbugs will prevent the branch from being integrated into the master branch on gitlab.

This project use a spotbugs exclusion list, which contains any spotbugs error that is allowed within the project. This exclusion list is located in `diary/config/spotbugs_exclude.xml`.

The only bug excluded is `DLS_DEAD_LOCAL_STORE`, this is included due to usage of `File.delete()` in the persistance layer. The `File.delete()` method returns a boolean value, however currently the project design just needs the file to delete without a return value, which is why the `DLS_DEAD_LOCAL_STORE` is on the exclusion list.

## How to run test

---

Tests are run in the verify phase of the maven lifecycle. Therefore a any branch can run every test, checkstyle, spotbugs, and jacoco, using the `mvn verify` command.

A daily jacoco coverage report is generated on the master branch on gitlab. While a code coverage percentage can be seen on the master branch badges, a more conclusive report can be downloaded as a artifact from the daily `jacocoReport` job performed under the CI/CD meny.
