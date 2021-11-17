module diary.ui {
    requires com.google.gson;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;

    requires diary.core;
    requires diary.frontend;

    opens diary.ui to javafx.graphics, javafx.fxml;
}
