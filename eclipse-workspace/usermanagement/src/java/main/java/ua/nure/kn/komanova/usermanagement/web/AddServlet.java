package main.java.ua.nure.kn.komanova.usermanagement.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.ua.nure.kn.komanova.usermanagement.User;
import main.java.ua.nure.kn.komanova.usermanagement.db.DaoFactory;
import main.java.ua.nure.kn.komanova.usermanagement.db.DatabaseExeption;

public class AddServlet extends EditServlet {

	private static final long serialVersionUID = 1579163404364469187L;

	@Override
	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/add.jsp").forward(req, resp);
	}

	@Override
	protected void processUser(User user) throws DatabaseExeption, ReflectiveOperationException {
		DaoFactory.getInstance().getUserDao().create(user);
	}
	
}