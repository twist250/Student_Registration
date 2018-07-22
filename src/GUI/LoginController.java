package GUI;

import DAO.AdminDAO;
import DAO.AdminDAOInt;
import DAO.CredentialDAO;
import DAO.StudentDAO;
import Domain.Admin;
import Domain.Credentials;
import Domain.Student;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginController {
	@FXML
	private JFXButton SignUpButton;

	@FXML
	private VBox adminVBox;

	@FXML
	private VBox userVBox;
	@FXML
	private JFXTextField userNameTextField;

	@FXML
	private JFXPasswordField passwordTextField1;

	@FXML
	private JFXButton loginButton;

	@FXML
	private JFXButton SignUpTextField;

	@FXML
	private JFXTextField idTextField;

	@FXML
	private JFXPasswordField passwordTextField;

	@FXML
	private JFXCheckBox adminBox;

	private Main main;
	private Stage stage;
	private Student student;
	//Connect LoginController to Main
	@FXML
	void adminPressed(ActionEvent event) {
		clear();
		adminVBox.setVisible(true);
		if (adminBox.isSelected()) {
			adminVBox.toFront();
			userVBox.toBack();
		}else {
			userVBox.toFront();
			adminVBox.toBack();
		}
	}

	public Alert getAlertInstance(){
		Alert alert=new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Error");
		alert.initStyle(StageStyle.DECORATED);
		alert.initModality(Modality.APPLICATION_MODAL);
		return alert;
	}

	private void clear() {
		idTextField.clear();
		userNameTextField.clear();
		passwordTextField.clear();
		passwordTextField1.clear();
	}
	@FXML
	void idKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			passwordTextField.requestFocus();
		}
	}
	@FXML
	void enterPressedOnUser(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			loginInitialization();
		}
	}
	@FXML
	void enterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			loginInitialization();
		}
	}
	@FXML
	void loginPressed(ActionEvent event) {
		loginInitialization();
	}

	private void loginInitialization() {
		if (!adminBox.isSelected()){
		try {
			Credentials credentials=new Credentials();
			credentials.setId(Integer.parseInt(idTextField.getText()));
			credentials.setPassword(passwordTextField.getText());
			boolean check=CredentialDAO.checkPassword(credentials);
			if (check == false) {
				Alert alert=getAlertInstance();
				alert.setContentText("Id or Password Incorrect");
				alert.show();
			}else if (check == true) {
				Main.getPrimaryStage().close();
				this.student = StudentDAO.getStudent(Integer.parseInt(idTextField.getText()));
				homeWindow();

			}

		} catch (Exception e) {

			Alert alert=getAlertInstance();
			alert.setContentText(e.getMessage());
			alert.show();
		}
		}else
		{
			Admin admin=new Admin();
			admin.setUserName(userNameTextField.getText());
			admin.setPassword(passwordTextField1.getText());
			clear();
			if (AdminDAO.checkPassword(admin) == true) {
				Main.getPrimaryStage().close();
				adminWindow();
			}else {
				Alert alert=getAlertInstance();
				alert.setContentText("Id or Password Incorrect");
				alert.show();
			}
		}
		clear();
	}

	private void adminWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminWindow.fxml"));
			AnchorPane pane = loader.load();
			AdminWindowControl controller = loader.getController();

			Stage stage=new Stage();
			stage.setScene(new Scene(pane));
			stage.setOnCloseRequest(o->System.exit(1));
			controller.setStage(stage);
			controller.setCoursesTable();
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void homeWindow() {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeWindow.fxml"));
			AnchorPane pane = loader.load();
			HomeWindowControl controller = loader.getController();
			controller.setStudent(student);
			controller.tableValues();
			Stage stage=new Stage();
			stage.setScene(new Scene(pane));
			controller.setHomeStage(stage);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void signUPWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
			AnchorPane pane = loader.load();
			SignUpController controller = loader.getController();

			Stage stage=new Stage();
			stage.setScene(new Scene(pane));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void signupPressed(ActionEvent event) {
		signUPWindow();
	}

	@FXML
	private AnchorPane adminPane;
	@FXML
	private VBox textVBox;
	@FXML
	void initialize() {
		adminVBox.setVisible(false);
	}

}
