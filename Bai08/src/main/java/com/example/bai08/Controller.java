package com.example.bai08;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField nameField;

    @FXML
    private CheckBox statusCheck;

    @FXML
    private Label errorLabel; // Label để hiện thông báo lỗi ngay trên form

    @FXML
    void onSaveButtonClick(ActionEvent event) {
        // reset lại trạng thái ban đầu mỗi khi bấm nút
        nameField.setStyle("");
        errorLabel.setText("");

        String name = nameField.getText().trim();
        boolean isAgreed = statusCheck.isSelected();


        if (name.isEmpty()) {
            handleError("Vui lòng không để trống họ tên!");
            return;
        }

        if (name.length() < 2) {
            handleError("Họ tên quá ngắn, vui lòng nhập đầy đủ!");
            return;
        }

        if (!isAgreed) {
            handleError("Bạn phải đồng ý với điều khoản dịch vụ!");
            return;
        }

        showNotification(Alert.AlertType.INFORMATION, "Thành công", "Dữ liệu của " + name + " đã được ghi nhận vào hệ thống.");
        System.out.println("Dữ liệu hợp lệ: " + name);
    }

    private void handleError(String message) {
        errorLabel.setText(message);
        nameField.setStyle("-fx-border-color: #ff4d4d; -fx-border-width: 2px;");
    }

    private void showNotification(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}