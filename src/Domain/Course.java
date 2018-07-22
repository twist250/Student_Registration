package Domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "courses")
public class Course {
	@Id
	@Column(name = "course_code")
	private String code;
	@Column
	private String name;
	@Column
	private int credits;
	@ManyToMany(cascade = CascadeType.ALL,mappedBy = "courses")
	private List<Student> students;

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}


	public Course() {
	}

	public Course(String code, String nname, int credits) {
		this.code = code;
		this.name = nname;
		this.credits = credits;
	}

	public String getCode() {
		return  code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}
	@Transient
	private CheckBox checkBox;

	public CheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(CheckBox checkBox) {
		this.checkBox = checkBox;
	}
}
