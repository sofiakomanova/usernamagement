package main.java.ua.nure.kn.komanova.usermanagement.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import main.java.ua.nure.kn.komanova.usermanagement.User;
import main.java.ua.nure.kn.komanova.usermanagement.db.DaoFactory;
import main.java.ua.nure.kn.komanova.usermanagement.db.DatabaseExeption;

public class EditServlet extends HttpServlet {
	private static final String DATE = "date";
	private static final String LAST_NAME = "lastName";
	private static final String FIRST_NAME = "firstName";
	private static final String ID = "id";
	private static final String CANCEL_BUTTON_COMPONENT_NAME = "cancelButton";
	private static final String ADD_BUTTON_COMPONENT_NAME = "addButton";

	private static final long serialVersionUID = -8865093574275121251L;

	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			if (req.getParameter(ADD_BUTTON_COMPONENT_NAME) != null) {
				try {
					try {
						doOk(req, resp);
					} catch (ReflectiveOperationException e) {
						e.printStackTrace();
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else if (req.getParameter(CANCEL_BUTTON_COMPONENT_NAME) != null) {
				doCancel(req, resp);
			} else {
				showPage(req, resp);
			}
		}
	
	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/edit.jsp").forward(req, resp);
	}

	protected void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/browse.jsp").forward(req, resp);
	}

	protected void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, ParseException, ReflectiveOperationException {
		User user = null;
		try {
			user = getUser(req);
		} catch (ValidationException e1) {
			req.setAttribute("error", e1.getMessage());
			try {
				showPage(req, resp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		try {
			processUser(user);
		} catch (DatabaseExeption e) {
			e.printStackTrace();
			new ServletException(e);
		}
		try {
			req.getRequestDispatcher("/browse").forward(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private User getUser(HttpServletRequest req) throws ValidationException, java.text.ParseException {
		User user = new User();
		String idStr = req.getParameter(ID);
		String firstName = req.getParameter(FIRST_NAME);
		String lastName = req.getParameter(LAST_NAME);
		String dateStr = req.getParameter(DATE);
		
		if (firstName == null) {
			throw new ValidationException("First name is empty");
		}
		
		if (lastName == null) {
			throw new ValidationException("Last name is empty");
		}
		
		if (dateStr == null) {
			throw new ValidationException("Date is empty");
		}
		
		if (idStr != null) {
			user.setId(new Long(idStr));
		}
		user.setFirstName(firstName);
		user.setLastName(lastName);
		try {
			user.setDateOfBirth(DateFormat.getDateInstance().parse(dateStr));
		} catch (ParseException e) {
			throw new ValidationException("Date format is incorrect");
		}
		return user;
	}

	protected void processUser(User user) throws DatabaseExeption, ReflectiveOperationException {
		DaoFactory.getInstance().getUserDao().update(user);
	}
}