package com.quizclient.controller;

import com.quizclient.api.ResourceHttpClient;
import com.quizclient.model.command.Resource.CreateFileCommand;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

public class AddFile {

    public TextField exampleIdTextField;
    public TextField nameTextField;
    public TextArea codeTextField;

    public void save() {
        CreateFileCommand file = new CreateFileCommand();
        file.setExampleId(exampleIdTextField.getText());
        file.setName(nameTextField.getText());
        file.setContent(codeTextField.getText());

        ResourceHttpClient.createFile(List.of(file));
    }
}
