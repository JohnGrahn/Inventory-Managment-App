module grahn.c482 {
    requires javafx.controls;
    requires javafx.fxml;


    opens grahn.c482 to javafx.fxml;
    exports grahn.c482;
    opens grahn.c482.Controller to javafx.fxml;
    exports grahn.c482.Controller;

    opens grahn.c482.Model to javafx.base;
}