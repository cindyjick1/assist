module com.cindyjick.res.demo {
    requires lombok;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.lang3;
    requires java.logging;
    requires org.slf4j;
    requires org.apache.commons.collections4;

    opens com.cindyjick.res.assist to javafx.fxml;
    opens com.cindyjick.res.assist.controller to javafx.fxml;
    exports com.cindyjick.res.assist;
}