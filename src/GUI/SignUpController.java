package GUI;

import DAO.CredentialDAO;
import DAO.StudentDAO;
import Domain.Credentials;
import Domain.Student;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

public class SignUpController {

	@FXML
	private TextField idTextField;

	@FXML
	private TextField firstNameTextField;

	@FXML
	private TextField lastNameTextField;

	@FXML
	private TextField departmentTextField;

	@FXML
	private TextField passwordTextField;

	@FXML
	private JFXButton singupButton;

	@FXML
	private JFXButton loginButton;

	@FXML
	void loginPressed(ActionEvent event) {

	}

	@FXML
	void signUpPressed(ActionEvent event) {
		Student student = new Student();
		Credentials credentials = new Credentials();
		credentials.setId(Integer.parseInt(idTextField.getText()));
		credentials.setPassword(passwordTextField.getText());

		student.setId(Integer.parseInt(idTextField.getText()));
		student.setFirstName(firstNameTextField.getText());
		student.setLastName(lastNameTextField.getText());
		student.setDepartment(departmentTextField.getText());
		credentials.setStudent(student);
		StudentDAO.register(student);
		CredentialDAO.save(credentials);
		JFXAlert alert=new JFXAlert();
		//alert.setAnimation(JFXAlertAnimation.TOP_ANIMATION);
		alert.initModality(Modality.NONE);
		alert.setContentText("Well ");


		alert.show();
	}

}
