module com.quizclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires jdk.jshell;
    requires java.desktop;
    requires gson.javatime.serialisers;

    opens com.quizclient to javafx.fxml;
    exports com.quizclient;
    opens com.quizclient.dialog;
    exports com.quizclient.dialog;
    opens com.quizclient.model.auth;
    exports com.quizclient.model.auth;
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
    exports com.quizclient.helpers;
    opens com.quizclient.helpers;
    exports com.quizclient.model.query.Quiz;
    opens com.quizclient.model.query.Quiz;
    exports com.quizclient.model.command.Quiz;
    opens com.quizclient.model.command.Quiz;
    opens com.quizclient.model.query.Resource;
    exports com.quizclient.model.query.Resource;
    opens com.quizclient.model.command.Resource;
    exports com.quizclient.model.command.Resource;
}