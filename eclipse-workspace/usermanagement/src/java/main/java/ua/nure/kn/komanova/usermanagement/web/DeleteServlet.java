package main.java.ua.nure.kn.komanova.usermanagement.web;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.ua.nure.kn.komanova.usermanagement.User;
import main.java.ua.nure.kn.komanova.usermanagement.db.DaoFactory;
import main.java.ua.nure.kn.komanova.usermanagement.db.DatabaseExeption;

public class DeleteServlet extends EditServlet {

	private static final long serialVersionUID = -8176527331041282767L;

	@Override
	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/delete.jsp").forward(req, resp);
	}

	@Override
	protected void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doCancel(req, resp);
	}

	@Override
	protected void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, ReflectiveOperationException {
		try {
            DaoFactory.getInstance().getUserDao().delete((User) req.getSession().getAttribute("user"));
        } catch (DatabaseExeption e) {
            req.setAttribute("error", "Error in the database: " + e.getMessage());
            try {
				req.getRequestDispatcher("/delete.jsp").forward(req, resp);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
        try {
			req.getRequestDispatcher("/browse").forward(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
