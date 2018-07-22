package DAO;

import Domain.Course;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CourseDAO {
	public static List<Course> getAllCourses() {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		List<Course> courses=session.createQuery("from Course ").list();
		session.getTransaction().commit();
		session.close();
		HibernateUtil.shutdown();
		return courses;
	}

	public static String addCourse(Course course) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.save(course);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
		return "Done";
	}

	public static void deleteCourse(Course course) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		Query  query=session.createQuery("delete from Course where code=:c");
		query.setParameter("c", course.getCode());
		query.executeUpdate();
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}
}
