module diary.core {
    requires com.google.gson;


    exports diary.core;
    exports diary.json;

    opens diary.core to com.google.gson;
    opens diary.json to diary.api;
}
