package com.example.bai10;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.sql.SQLException;

public class Controller {
    @FXML private TextField nameField;
    @FXML private CheckBox statusCheck;
    @FXML private Label errorLabel;
    @FXML private ProgressIndicator progressIndicator;
    @FXML
    public void initialize() {
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                nameField.setStyle("-fx-border-color: #ff4d4d; -fx-border-width: 2px;");
                errorLabel.setText("Họ tên không được để trống!");
            } else if (newValue.length() < 2) {
                nameField.setStyle("-fx-border-color: #ff9800;");
                errorLabel.setText("Tên quá ngắn...");
            } else {
                nameField.setStyle("-fx-border-color: #5eba22; -fx-border-width: 2px;");
                errorLabel.setText("");
            }
        });
    }

    @FXML
    void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            statusCheck.requestFocus();
        }
    }

    @FXML
    void onSaveButtonClick(ActionEvent event) {
        String name = nameField.getText().trim();
        boolean isAgreed = statusCheck.isSelected();

        if (name.isEmpty()) {
            errorLabel.setText("Vui lòng nhập tên trước khi lưu!");
            return;
        }

        Task<Void> saveTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // Giả lập độ trễ 1.5 giây để thấy vòng xoay tải
                Thread.sleep(1500);
                DatabaseHelper.saveUser(name, isAgreed);
                return null;
            }
        };

        saveTask.setOnRunning(e -> progressIndicator.setVisible(true));

        saveTask.setOnSucceeded(e -> {
            progressIndicator.setVisible(false);
            Platform.runLater(() -> {
                showNotification(Alert.AlertType.INFORMATION, "Thành công", "Đã lưu " + name + " vào DB (Async)!");
            });
        });

        saveTask.setOnFailed(e -> {
            progressIndicator.setVisible(false);
            Throwable ex = saveTask.getException();
            Platform.runLater(() -> {
                showNotification(Alert.AlertType.ERROR, "Lỗi DB", "Lỗi: " + ex.getMessage());
            });
        });

        new Thread(saveTask).start();
    }

    @FXML
    void onLoadButtonClick(ActionEvent event) {
        Task<User> loadTask = new Task<>() {
            @Override
            protected User call() throws Exception {
                Thread.sleep(1000); // Giả lập độ trễ
                return DatabaseHelper.getLastUser();
            }
        };

        loadTask.setOnRunning(e -> progressIndicator.setVisible(true));

        loadTask.setOnSucceeded(e -> {
            progressIndicator.setVisible(false);
            User user = loadTask.getValue();
            Platform.runLater(() -> {
                if (user != null) {
                    nameField.setText(user.getFullName());
                    statusCheck.setSelected(user.isAgreedStatus());
                    showNotification(Alert.AlertType.INFORMATION, "Tải xong", "Dữ liệu đã được load.");
                } else {
                    showNotification(Alert.AlertType.WARNING, "Trống", "Không tìm thấy bản ghi.");
                }
            });
        });

        new Thread(loadTask).start();
    }

    private void showNotification(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}