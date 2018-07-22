package Domain;

import javax.persistence.*;

@Entity
@Table
public class Credentials {
	@Id
	private int id;

	private String password;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private Student student;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}
