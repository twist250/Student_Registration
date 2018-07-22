package DAO;

import Domain.Course;
import Domain.Student;
import javafx.scene.control.Alert;
import org.hibernate.Session;

import javax.management.Query;
import javax.persistence.criteria.From;

import java.util.List;

public class StudentDAO {
	public static String register(Student student) {
		try {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(student);
			session.getTransaction().commit();
			return "Well registered";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Student getStudent(int id) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		return (Student) session.get(Student.class, id);
	}

	public static void updateStudent(Student student) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.saveOrUpdate(student);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}

	public static Student getCourses(int id){
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		Student student = session.get(Student.class, id);
		if (student != null) {

		List<Course> courses = student.getCourses();
		session.getTransaction().commit();
		HibernateUtil.shutdown();
		return student;
		}else {
		session.getTransaction().commit();
		HibernateUtil.shutdown();
			return null;
		}
	}
}
