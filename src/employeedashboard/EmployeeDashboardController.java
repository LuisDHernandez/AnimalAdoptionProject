package employeedashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javax.xml.crypto.Data;
import main.Database;
import models.questions.Question;
import models.user.AuthorizationLevel;

public class EmployeeDashboardController {


  @FXML
  private Button viewEventsBtn;

  @FXML
  private Button viewAnimalBtn;

  @FXML
  private Tab anwQuestionsList;

  @FXML
  private ListView<Question> ans;

  @FXML
  private TextField answerQuestionTxtBox;

  @FXML
  private Button answerBtn;

  @FXML
  private Label userStatusLbl;

  @FXML
  private Button recordHoursBtn;

  @FXML
  private Text answerAuthorTxt;

  @FXML
  private ListView<Question> unanwQuestionList;

  @FXML
  private TextArea logList;

  @FXML
  private Label dashLoginLbl;

  @FXML
  private Text answerTxt;

  @FXML
  private void answerQuestion(ActionEvent event) {
    Database database = Database.get();

    // sets the answer to the Question
    unanwQuestionList.getSelectionModel().getSelectedItem()
        .setAnswer(answerQuestionTxtBox.getText());

    // sets the answer boolean to true
    unanwQuestionList.getSelectionModel().getSelectedItem().setAnswered(true);

    // adds the data to the database
    database.answerQuestion(Database.getCurrentUser(),
        unanwQuestionList.getSelectionModel().getSelectedItem());
  }

  private void setUpAnsweredList() {

    Database database = Database.get();

    ObservableList<Question> answeredQustions = FXCollections
        .observableArrayList(database.getAnsweredQuestions());

    ans.setItems(answeredQustions);
  }

  private void setUpUnansweredList() {

    Database database = Database.get();

    ObservableList<Question> unansweredQuestions = FXCollections
        .observableArrayList(database.getUnansweredQuestions());

    unanwQuestionList.setItems(unansweredQuestions);
  }


  public void initialize() {
    dashLoginLbl.setText(
        "Welcome " + Database.getCurrentUser().getLastName() + ", " + Database.getCurrentUser()
            .getFirstName() + "!");
    if (Database.getCurrentUser().getPrivileges() == AuthorizationLevel.ADMINISTRATION) {
      userStatusLbl.setText("Employee");
    } else {
      userStatusLbl.setText("Volunteer");
    }
    setUpUnansweredList();
    setUpAnsweredList();
    answerAuthorTxt
        .setText(String.valueOf(ans.getSelectionModel().getSelectedItem().getEmployeeId()));
    answerTxt.setText(ans.getSelectionModel().getSelectedItem().getAnswer());
  }
}