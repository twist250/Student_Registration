package DAO;

import Domain.Admin;
import org.hibernate.Session;

import java.util.List;

public interface AdminDAOInt {
	boolean checkPassword(Admin admin);
}
