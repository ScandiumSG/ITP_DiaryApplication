[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2172/gr2172)

[![pipeline status](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2172/gr2172/badges/master/pipeline.svg)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2172/gr2172/-/pipelines)

[![coverage report](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2172/gr2172/badges/master/coverage.svg)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2172/gr2172/-/graphs/master/charts)

# Group2172 README [![Group2172](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2172/gr2172/-/blob/master/readme.md)


Greetings!

<br/>
Welcome to the project of group 2172 Fall 2021.

<br/>
<br/>
This project creates a simple diary-application that allowes the user to write diary entries, save and load them.
<br/>
The root-folder of the project is the toplevel of the repository. Because of this we have combined the two README-files.
<br/>

Here's an illustration of how it would look, for now.
<br/>

![illustration 1](Illustrasjon_1.jpg)

<br/>

# How to build and run applicaiton
This project utilize maven to build and run the code.
Build the project by using `mvn clean install` inside the diary folder, then the UI can be initialized using the `mvn javafx:run -f ui/pom.xml` command.

## Running the application
### **Gitpod**
Gitpod is configured to automatically select the diary directory, application will run using the following:
~~~
mvn clean install
mvn javafx:run -f ui/pom.xml
~~~

### **Local**
For a local project you first need to select the diary directory then use the same commands as for Gitpod. Run the following commands:
~~~
cd diary
mvn clean install
mvn javafx:run -f ui/pom.xml
~~~

# Userhistory

## Diary

As an imagianry and emotional person is collecting my thoughts somewhere necessary. With that would I like to write down my thought in  order to look back at them in the future.

The product offers teh ability to create a new page for the curent date. This page is blank and ready for new content.

Users are able to choose dates from the past in order to read earlier content written by themself.

### Important for the user
    - When writing a new page is a wish: Visible button to create a new page.
    - If the user wishes to read old content: Visible area to set a suggested date.





