package com.example.bai09;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;

public class Controller {
    @FXML private TextField nameField;
    @FXML private CheckBox statusCheck;
    @FXML private Label errorLabel;

    @FXML
    void onSaveButtonClick(ActionEvent event) {
        String name = nameField.getText().trim();
        boolean isAgreed = statusCheck.isSelected();

        if (name.isEmpty()) {
            errorLabel.setText("Vui lòng nhập tên!");
            return;
        }

        try {
            DatabaseHelper.saveUser(name, isAgreed);
            showNotification(Alert.AlertType.INFORMATION, "Thành công", "Đã lưu vào Database!");
            errorLabel.setText("");
        } catch (SQLException e) {
            showNotification(Alert.AlertType.ERROR, "Lỗi DB", "Không thể lưu dữ liệu: " + e.getMessage());
        }
    }

    @FXML
    void onLoadButtonClick(ActionEvent event) {
        try {
            User lastUser = DatabaseHelper.getLastUser();
            if (lastUser != null) {
                nameField.setText(lastUser.getFullName());
                statusCheck.setSelected(lastUser.isStatus());
                showNotification(Alert.AlertType.INFORMATION, "Tải xong", "Đã load dữ liệu cũ.");
            } else {
                showNotification(Alert.AlertType.WARNING, "Trống", "Chưa có dữ liệu trong DB.");
            }
        } catch (SQLException e) {
            showNotification(Alert.AlertType.ERROR, "Lỗi Load", e.getMessage());
        }
    }

    private void showNotification(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}