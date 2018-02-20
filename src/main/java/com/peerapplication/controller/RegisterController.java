package com.peerapplication.controller;

import com.peerapplication.model.User;
import com.peerapplication.util.Main;
import com.peerapplication.validator.UserValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private ImageView userImage;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnRegister;

    @FXML
    private Label statusLabel;

    private FileChooser fileChooser;

    private Image image;

    private UserValidator userValidator;

    @FXML
    void register(MouseEvent event) {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        User user = new User(Main.getSystemUserID(), name, email, image);
        String validity = userValidator.validate(user);
        if (validity.equals("Success")){

        }else{
            statusLabel.setText(validity);
        }
    }

    @FXML
    void selectImage(MouseEvent event) {
        Stage stage = (Stage)userImage.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            image = new Image(file.toURI().toString());
            userImage.setImage(image);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        fileChooser.setTitle("Select Profile Picture");
    }
}
