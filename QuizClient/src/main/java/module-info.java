module com.quizclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires jdk.jshell;


    opens com.quizclient to javafx.fxml;
    exports com.quizclient;
    opens com.quizclient.model.query;
    exports com.quizclient.model.query;
    opens com.quizclient.model.command;
    exports com.quizclient.model.command;
    opens com.quizclient.controller;
    exports com.quizclient.controller;
    opens com.quizclient.api;
    exports com.quizclient.api;
    opens com.quizclient.enums;
    exports com.quizclient.enums;
    exports com.quizclient.utils;
    opens com.quizclient.utils;
    exports com.quizclient.ui;
    opens com.quizclient.ui;
}