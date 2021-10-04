module diary.ui {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;

    requires diary.core;

    opens diary.ui to javafx.graphics, javafx.fxml;
}
