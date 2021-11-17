module diary.backend{
    exports diary.backend;

    requires diary.core;
    requires transitive jakarta.servlet;

    opens diary.backend to jakarta.servlet;
}
