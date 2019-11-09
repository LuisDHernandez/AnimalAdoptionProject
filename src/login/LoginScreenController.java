package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Database;
import models.user.AuthorizationLevel;
import models.user.User;

public class LoginScreenController {

  @FXML
  private AnchorPane backPane;

  @FXML
  private TextField usernameText;

  @FXML
  private PasswordField passwordText;

  @FXML
  private Button loginButton;

  @FXML
  private Button registerHereButton;

  @FXML
  private Label errorLabel;

  @FXML
  private Label logginLabel;

  public void initialize() {

  }

  /**
   * @param event pressing the login button
   */
  @FXML
  void pressedLogin(ActionEvent event) throws Exception {
    Database database = Database.get();
    User user = database.loginUser(usernameText.getText(), passwordText.getText());
    if (user == null) {
      errorLabel.setVisible(true);
    } else {
      errorLabel.setVisible(false);
      if (user.getPrivileges() == AuthorizationLevel.VOLUNTEER || user.getPrivileges() == AuthorizationLevel.ADMINISTRATION) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader
            .load(getClass().getResource("../employeedashboard/EmployeeDashboard.fxml"));
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
      }
      else {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader
            .load(getClass().getResource("../customerdashboard/CustomerDashboard.fxml"));
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
      }
    }
  }

  @FXML
  void goToRegisterScreen(ActionEvent event) throws Exception{
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../registeruser/RegisterUser.fxml"));
    primaryStage.setTitle("Registration Page");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
