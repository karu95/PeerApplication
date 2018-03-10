package com.peerapplication.controller;

import com.peerapplication.model.User;
import com.peerapplication.util.SystemUser;
import com.peerapplication.validator.UserValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
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

    private BufferedImage bufferedImage;

    private UserValidator userValidator;

    private File file;

    @FXML
    void register(MouseEvent event) throws IOException {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        User user = new User(SystemUser.getSystemUserID(), name, email);
        String validity = userValidator.validate(user);
        if (validity.equals("Success")) {
            if (file != null) {
                user.setUserImage(ImageIO.read(file));
                user.setImageURL(String.valueOf(user.getUserID()));
            }
            user.setRegisterTime(new Date(System.currentTimeMillis()).getTime());
            user.saveUser();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/homepage.fxml"));
            Parent root = loader.load();
            HomeController controller = loader.getController();
            controller.init(user);
            Stage stage = (Stage) btnRegister.getScene().getWindow();
            Scene scene = new Scene(root, 1035, 859);
            stage.setTitle("Home");
            stage.setScene(scene);
        } else {
            statusLabel.setText(validity);
        }
    }

    @FXML
    void selectImage(MouseEvent event) throws IOException {
        Stage stage = (Stage) userImage.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            userImage.setImage(image);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userValidator = new UserValidator();
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        fileChooser.setTitle("Select Profile Picture");
    }
}
