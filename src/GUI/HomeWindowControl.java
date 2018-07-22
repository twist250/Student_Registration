package GUI;

import DAO.CourseDAO;
import DAO.StudentDAO;
import Domain.Course;
import Domain.Student;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeWindowControl {


	private void initStudent() {

	}

	private Main main;
	private Stage homeStage;
	private Student student;
	ObservableList<Course> list = FXCollections.observableArrayList();
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;


	@FXML
	private TableView<Course> tableView;

	@FXML
	private TableView<Course> tableView1;

	@FXML
	private TableColumn<Course, String> courseCodeColumn1;

	@FXML
	private TableColumn<Course, String> courseNameColumn1;

	@FXML
	private TableColumn<Course, Integer> creditsColumn1;

	@FXML
	private TableColumn<Course, CheckBox> checkColumn;

	@FXML
	private TableColumn<Course, String> courseCodeColumn;

	@FXML
	private TableColumn<Course, String> courseNameColumn;

	@FXML
	private TableColumn<Course, Integer> creditsColumn;

	@FXML
	private Label idTextField;

	@FXML
	private Label firstNameTextField;

	@FXML
	private Label lastNameTextField;

	@FXML
	private Label departmentTextField;

	@FXML
	private JFXButton logoutButton;

	@FXML
	private JFXButton applyButton;



	@FXML
	void applyPressed(ActionEvent event) {
		List<Course>courseList=new ArrayList<>();
		List<Integer> indexes=new ArrayList<>();
		Vector<ObservableList<Course>> courses= new Vector<>();
		for ( Course course: tableView.getItems()) {
			if (course.getCheckBox().isSelected()) {

				courses.add(tableView.getItems());
				indexes.add(tableView.getItems().indexOf(course));

			}
		}
		int i = 0;
		for (ObservableList<Course> cours : courses) {
			courseList.add(cours.get(indexes.get(i)));
			i++;
		}

		this.student.setCourses(courseList);
		StudentDAO.updateStudent(student);

		String file_name = student.getId() + ".pdf";
		Document document =new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream(file_name));
			document.open();
			Paragraph paragraph =new Paragraph("ID: "+student.getId()+"\nFIRSTNAME: "
					+student.getFirstName()+"\nLASTNAME: "+student.getLastName()+"\nDEPARTMENT: "+student.getDepartment()+"\n\n");

			PdfPTable table= new PdfPTable(3);
			PdfPCell cell = new PdfPCell(new Phrase("COURSE CODE"));
					table.addCell(cell);
			table.addCell(new PdfPCell(new Phrase("COURSE NAME")));
			table.addCell(new PdfPCell(new Phrase("COURSE CREDITS")));
			table.setHeaderRows(1);

			int total=0;
			for (Course course : courseList) {
				total+=course.getCredits();
//				table.addCell(course.getCode());
				table.addCell(course.getName());
				table.addCell(course.getCredits()+" ");
			}
			document.add(paragraph);
			document.add(table);
			document.add(new Paragraph("\n\t\t\t\tTotal Credits: " + total));
//			document.addTitle(student.getFirstName()+" "+student.getLastName()+" Registration Sheet");
			document.close();
			Desktop.getDesktop().open(new File(file_name));
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void logOutPressed(ActionEvent event) {
		new Main().getPrimaryStage().show();
		this.homeStage.close();
	}

	public void tableValues() {
		List<Course> courses = CourseDAO.getAllCourses();
		for (Course c : courses) {
			CheckBox checkBox=new CheckBox();
			c.setCheckBox(checkBox);
			list.add(c);

		}
		tableView.setItems(list);
		courseCodeColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("code"));
		courseNameColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
		creditsColumn.setCellValueFactory(new PropertyValueFactory<Course, Integer>("credits"));
		checkColumn.setCellValueFactory(new PropertyValueFactory<Course, CheckBox>("checkBox"));

	}


	public void setStudent(Student student) {
		this.student = student;
		idTextField.setText(student.getId()+"");
		firstNameTextField.setText(student.getFirstName());
		lastNameTextField.setText(student.getLastName());
		departmentTextField.setText(student.getDepartment());
	}

	public void setHomeStage(Stage homeStage) {
		this.homeStage = homeStage;
		this.homeStage.setOnCloseRequest(o->System.exit(1));
	}



	/*@FXML
	void initialize() {
		initStudent();
		*//*List<Course> courses = CourseDAO.getAllCourses();
		for (Course c : courses) {
			CheckBox checkBox=new CheckBox();
			c.setCheckBox(checkBox);
			list.add(c);
		}*//*

	}*/


}
