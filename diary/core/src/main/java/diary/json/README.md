# Source code for persistance layer

The implentation of persistance is done using [GSON](https://github.com/google/gson). GSON is used to read and write objects to JSON files.

The reason GSON was selected for this project is that it provides a very simple to use, open-source, framework which easily converts to and from JSON files. GSON also have extensive support for java generics and can convert pre-existing objects to/from JSON as it does not require java annotations in the object class.

A downside is that GSON is not currently [under development](https://old.reddit.com/r/androiddev/comments/684flw/why_use_moshi_over_gson/dgx3gpm/?context=3), but in maintenance mode. New features to java is therefore unlikely to be supported by GSON in the future, but critical bugs and minor fixes do currently get implemented.

## _Why is the project not migrated to Moshi, the successor of GSON?_

GSON fullfill all requirements set for this project. The nature of the Diary project does not require better separation of possible exceptions thrown nor does it benefit from Kotlin support.

If GSON becomes insufficient in future development the project will migrate over to Moshi, but the developers see no reason to invest development time into this convertion at this time.
