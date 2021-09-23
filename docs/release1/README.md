I denne releasen har vi opprettet et skjelett for maven/javaFX prosjektet, og deretter fylt på FXML, skrevet controllet-klassen, skrevet entry-klasse, skrevet klasser for lesing og skriving til FXML-filer, i tillegg til noen enkle tester. 

DiaryApp starter applikasjonen bassert på Diary.FXML, som videre referer til DiaryController. DiaryController gjør om input til Entry-objekt som så ved hjelp av EntryToJSON skrives i JSON-filer. Brukeren kan også laste tidligere innlegg ved hjelp av EntryFromJSON. 
