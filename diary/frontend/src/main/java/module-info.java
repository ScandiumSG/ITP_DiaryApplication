module diary.frontend{
    exports diary.frontend;

    requires diary.core;
    requires transitive jakarta.servlet;

    opens diary.frontend to diary.ui, diary.backend;
}
