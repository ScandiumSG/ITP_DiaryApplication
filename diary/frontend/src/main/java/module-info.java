module diary.frontend{
    exports diary.frontend;

    requires diary.core;

    opens diary.frontend to diary.ui, diary.backend;
}
