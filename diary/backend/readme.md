# REST-API

[Diary readme](../readme.md)

## How to run backend server
To run the backend server, simply navigate into the diary directory using `cd diary`, and then run `mvn jetty:run -f backend/pom.xml` to start the server. In gitpod this is done automatically after `maven clean install` completes, in the second terminal tab.

## The client server message format
For both GET and POST requests the server uses the url `*/api/diary/filename`, where filename is the name of the file, without the .json ending. For POST requests, the body of the requests is simply the content of the diary named in the url, represented as a json string, though the server does not require it to be valid json. For GET requests, the body of the server reponse is the diary represented as a json string. 

We also added an optional querystring of `getFileNames` to be used on GET requests, which instead returns a json array of the files beginnig with the given fileName, which is essential to retrieving diaries. The complete url would then be `*/api/diary/filename?getFileNames`

## Testing of the API
In this project we have implemented an integrationtest to test the API. In our case, we felt this was enough, as the API merely stores and retrieves static data, and is not stateful. The integration test is of course run as part of mvn clean install. Because the server needs to run while the integration test runs, it is placed in the backend module. 

## Storing local files
In our application we never delete local files, and the reason for this is twofold. First, we use the files stored to autocomplete filling in the username, so the user doesn't have to do so manually for every startup. Secondly, as we pull from the server every time the user logs in, the local diaries will never be in danger of being outdated, and since we write to the server every time we save, newer files will never get overwritten by older ones. This allows the flexibility of logging in to the app on any computer, while not forgeting the user every logout.


## Current backend location
Currently the backend stores the diaries in the project folder (diary/), as this is the default working directory. We could make them be stored elsewhere, for example in backend/../resources, but this would be subject to change for any actual deployment, and therefore we have let it stay as the project folder.


## Security
As this is just a school project, we knew we didn't have time to implement comprehensive security measures. Despite this, we have managed to implement some fundamental security, which allows several different users to save their diaries to the same location, without being able to view eachothers diaries. Every diary filename is the user's username, pluss their user pin, pluss the diary's name. The client and server are both blissfully unaware that they are transporting usernames and pins, they only see filenames. 

## How it actually works
When the user logs in, the client sends several get requests to the server. The first one uses the `getFileNames` querystring to request a list of the diaries that start with the given filename. Since all filenames start with the userid, this gets a list of all the users diaries on the server. After that, a normal get request is sent for every file, and each reponse is saved locally using EntryToJSON. Saving is far simpler, the client is told which diary was updated, and it sends that diary to the server. 