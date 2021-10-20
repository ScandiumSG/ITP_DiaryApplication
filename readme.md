[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2172/gr2172)

[![pipeline status](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2172/gr2172/badges/master/pipeline.svg)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2172/gr2172/-/pipelines)

[![coverage report](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2172/gr2172/badges/master/coverage.svg)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2172/gr2172/-/graphs/master/charts)

# Group2172 README [![Group2172](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2172/gr2172/-/blob/master/readme.md)


Greetings!

<br/>
Welcome to the project of group 2172 Fall 2021.

<br/>
<br/>
This project creates a simple diary-application that allows the user to write diary entries, save and load them. 
<br/>
The root-folder of the project is the top level of the repository. Because of this we have combined the two README-files. 
<br/>

Here's an illustration of how it would look, for now.
<br/>

![illustration 1](docs/Illustrasjon_1.jpg)

<br/>
Here's an illustration of the current architecture:
<br/>

![Architecture.png](docs/release2/Architecture.png)

# How to build and run application
This project utilize maven to build and run the code. 
Build the project by using `mvn clean install` inside the diary folder, then the UI can be initialized using the `mvn javafx:run -f ui/pom.xml` command.

## Running the application
### **Gitpod**
Gitpod is configured to automatically select the diary directory and run `mvn clean install`, application will run using the following:
~~~
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

As an imaginative and emotional person I find it necessary to collect my thoughts somewhere. That's why I need some place where I can write down my thoughts and look back at them in the future.

That's why I would like a diary capable of storing my thoughts over extended periods of time, with the option to view and edit them later.

### Important for the user
    - The submit button saves your current entry to the selected date. (Today by default)
    - Use the datepicker to edit or read the entry for a different date.

