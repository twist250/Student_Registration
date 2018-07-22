package DAO;

import Domain.Admin;
import Domain.Credentials;
import org.hibernate.Session;

import javax.management.Query;
import java.util.List;

public class AdminDAO  {


	public static boolean checkPassword(Admin admin) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		org.hibernate.query.Query query=session.createQuery("from Admin where userName=:u and password=:p");
		query.setParameter("u", admin.getUserName());
		query.setParameter("p", admin.getPassword());
		List<Admin> credentials = query.list();

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
