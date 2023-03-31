module com.cindyjick.res.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.apache.commons.lang3;

    opens com.cindyjick.res.assist to javafx.fxml;
    opens com.cindyjick.res.assist.controller to javafx.fxml;
    exports com.cindyjick.res.assist;
}