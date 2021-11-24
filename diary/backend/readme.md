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