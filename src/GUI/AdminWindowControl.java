package GUI;

import DAO.CourseDAO;
import DAO.StudentDAO;
import Domain.Course;
import Domain.Student;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class AdminWindowControl {

	@FXML
	private AnchorPane modifyPane;

	@FXML
	private JFXTextField enteredIdText;

	@FXML
	private TableView<?> tableSearchView;

	@FXML
	private TableColumn<Student,CheckBox> checkSearchColumn;

	@FXML
	private TableColumn<Course,String> idSearchColumn;

	@FXML
	private TableColumn<Course,String> codeSearchColumn;

	@FXML
	private TableColumn<Course,String> nameSearchColumn;

	@FXML
	private TableColumn<Course,String> creditsSearchColumn;

	@FXML
	private AnchorPane addPane;

	@FXML
	private TextField courseCodeText;

	@FXML
	private TextField courseNameText;

	@FXML
	private TextField courseCreditsText;

	@FXML
	private JFXButton addCourseButton;

	@FXML
	private AnchorPane viewPane;

	@FXML
	private TableColumn<Course, CheckBox> courseCheckViewColumn;

	@FXML
	private TableColumn<Course, String> courseCodeViewColumn;

	@FXML
	private TableColumn<Course, String> courseNameViewColumn;

	@FXML
	private TableColumn<Course, Integer> creditsViewColumn;

	private Stage stage;
	@FXML
	private TableView<Course> viewCoursesTable;
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	void addButtonPressed(ActionEvent event) {
		modifyPane.setVisible(false);
		viewPane.setVisible(false);
		addPane.setVisible(true);
	}

	@FXML
	void addCoursePressed(ActionEvent event) {
		Course course=new Course();
		course.setCode(courseCodeText.getText());
		course.setName(courseNameText.getText());
		course.setCredits(Integer.parseInt(courseCreditsText.getText()));
		String msg = CourseDAO.addCourse(course);
		Alert alert=new Alert(Alert.AlertType.INFORMATION);
		alert.setContentText(msg);
		alert.show();
	}

	@FXML
	void cancelPressed(ActionEvent event) {

	}

	@FXML
	void deletePressed(ActionEvent event) {
		List<Course> courseList= new ArrayList<>();
		List<Integer> indexes = new ArrayList<>();
		Vector<ObservableList<Course>> courses=new Vector<>();
		for (Course cours : viewCoursesTable.getItems()) {
			if (cours.getCheckBox().isSelected()) {
				courses.add(viewCoursesTable.getItems());
				indexes.add(viewCoursesTable.getItems().indexOf(cours));
			}
		}
		int i = 0;
		for (ObservableList<Course> cours : courses) {
			CourseDAO.deleteCourse(cours.get(indexes.get(i)));
			i++;
		}
		setCoursesTable();
	}

	@FXML
	void logOutPressed(ActionEvent event) {
		new Main().getPrimaryStage().show();
		this.stage.close();
	}
	StringProperty wrapper(Object o) {
		StringProperty stringProperty=new SimpleStringProperty();
		stringProperty.setValue(String.valueOf(o));
		return stringProperty;
	}
	@FXML
	void enteredIdTextPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			Student student = StudentDAO.getCourses(Integer.parseInt(enteredIdText.getText()));
			if (student != null) {
				if (!student.getCourses().isEmpty()) {

			ObservableList list = FXCollections.observableArrayList();
			ObservableList list2 = FXCollections.observableArrayList();
//			Object[][] data=new Object[courses.size()][5];

			for (Course course : student.getCourses()) {
				course.setCheckBox(new CheckBox());
				course.setStudents(new ArrayList<>());
				course.getStudents().add(student);
				list.add(course);
			}



			checkSearchColumn.setCellValueFactory(new PropertyValueFactory("checkBox"));
			idSearchColumn.setCellValueFactory(param ->wrapper(param.getValue().getStudents().get(0).getId()));
			codeSearchColumn.setCellValueFactory(param -> wrapper(param.getValue().getCode() ));
			nameSearchColumn.setCellValueFactory(param->wrapper(param.getValue().getName() ));
			creditsSearchColumn.setCellValueFactory(param->wrapper(param.getValue().getCredits()));



			//Using a wrapper to add some other infos
			creditsSearchColumn.setCellValueFactory(param->wrapper(param.getValue().getCredits()+" RWF"));



			tableSearchView.setItems(list);
				}else {
					Alert alert=new Alert(Alert.AlertType.INFORMATION);
					alert.setContentText("This ID: "+enteredIdText.getText()+" not associated with any course!!!");
					alert.show();
				}
		}else {
				Alert alert=new Alert(Alert.AlertType.ERROR);
				alert.setContentText("This ID: "+enteredIdText.getText()+" not in our Database");
				alert.show();
			}
			}
	}

	@FXML
	void modificationButtonPressed(ActionEvent event) {
		modifyPane.setVisible(true);
		viewPane.setVisible(false);
		addPane.setVisible(false);
	}

	@FXML
	void modifyPressed(ActionEvent event) {

	}

	@FXML
	void viewButtonPressed(ActionEvent event) {
		setCoursesTable();
		modifyPane.setVisible(false);
		viewPane.setVisible(true);
		addPane.setVisible(false);


	}
	void setCoursesTable(){
		viewCoursesTable.getItems().clear();
		ObservableList<Course> list=FXCollections.observableArrayList();
		List<Course> courses = CourseDAO.getAllCourses();
		for (Course cours : courses) {
			CheckBox box= new CheckBox();
			cours.setCheckBox(box);

			list.add(cours);
		}

		courseCheckViewColumn.setCellValueFactory(new PropertyValueFactory<Course, CheckBox>("checkBox"));
		courseCodeViewColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("code"));
		courseNameViewColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
		creditsViewColumn.setCellValueFactory(new PropertyValueFactory<Course ,Integer>("credits"));
		viewCoursesTable.setItems(list);
	}

}

