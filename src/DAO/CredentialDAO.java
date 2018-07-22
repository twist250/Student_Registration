package DAO;

import Domain.Admin;
import Domain.Credentials;
import Domain.Student;
import org.hibernate.Session;

import java.util.List;

public class CredentialDAO {
	public static boolean save(Credentials credential) {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(credential);
			session.getTransaction().commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}finally {
			session.getSessionFactory().close();
		}
	}

	public static boolean checkPassword(Credentials credential) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		org.hibernate.query.Query query=session.createQuery("from Credentials where id=:i and password=:p");
		query.setParameter("i", credential.getId());
		query.setParameter("p", credential.getPassword());
		List<Credentials> credentials = query.list();

		if (credentials.isEmpty()) {
			session.close();
			HibernateUtil.shutdown();
			return false;
		}else
			session.close();
		HibernateUtil.shutdown();
		return true;
	}
}
