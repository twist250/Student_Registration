package DAO;

import javafx.scene.control.Alert;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.JDBCConnectionException;


public class HibernateUtil {

	private static SessionFactory sessionFactory;

	private static void setSessionFactory() {
		try {
			Configuration configuration = new Configuration().configure();
			sessionFactory = configuration.buildSessionFactory();

		} catch (HibernateException e) {
//			e.printStackTrace();
			Alert alert =new Alert(Alert.AlertType.ERROR, "Connection to DataBase Error");
			alert.show();
		}

	}

	public static Session getSession() {
		setSessionFactory();
		return sessionFactory.openSession();
	}
	public static void shutdown(){
		sessionFactory.close();
	}
}
