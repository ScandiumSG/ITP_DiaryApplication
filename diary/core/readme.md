# Persistance - core.json

[Diary readme](../readme.md)

## Source code for persistance layer

The implentation of persistance is done using [GSON](https://github.com/google/gson). GSON is used to read and write java objects to JSON files.

The reason GSON was selected for this project is that it provides a very simple to use, open-source, framework which easily converts to and from JSON files. GSON also have extensive support for java generics and can convert pre-existing objects to/from JSON as it does not require java annotations in the object class.

A downside is that GSON is [not currently under development](https://old.reddit.com/r/androiddev/comments/684flw/why_use_moshi_over_gson/dgx3gpm/?context=3), but in maintenance mode. New features to java is therefore unlikely to be supported by GSON in the future, but critical bugs and minor fixes do currently get implemented.

### _Why is the project not migrated to Moshi, the successor of GSON?_

GSON fullfill all requirements set for this project. The nature of the Diary project does not require better separation of possible exceptions thrown nor does it benefit from Kotlin support. If GSON becomes insufficient for future development the project will migrate over to Moshi, but the developers see no reason to invest development time into this convertion at this time.

## File storage

The file storage is based on documents, which means that no file is stored automatically if the save entry button is not pressed. The saved json files are stored in the core/src/main/resources folder of the project, or in user.home/diary/core/src/main/resources if the user is using the provided installable version.
This is done to ensure that the saved JSON entries are stored in a safe location that is predictable to access for the project code.
The reason we use core/src/main/resources by default is to keep all facets of the project within one folder, making it easy to keep track of all associated files and remove project files when project is done. We could not make files within the installed diary directory, which is why the installable version uses a different directory to store files.