
package com.example.bai07;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;

public class Controller {
    @FXML private TextField nameField;
    @FXML private CheckBox statusCheck;

    @FXML
    protected void onSaveButtonClick() {
        String name = nameField.getText();
        boolean isDone = statusCheck.isSelected();
        System.out.println("Đã lưu: " + name + " - Trạng thái: " + isDone);
    }
}
        