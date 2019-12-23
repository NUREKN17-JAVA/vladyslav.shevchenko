package ua.nure.kn.shevchenko.usermanagement.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn.shevchenko.usermanagement.User;
import ua.nure.kn.shevchenko.usermanagement.db.DaoFactory;
import ua.nure.kn.shevchenko.usermanagement.db.DatabaseException;

public class DeleteServlet extends EditServlet {

	private static final long serialVersionUID = -7146827351041282267L;

	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/delete.jsp").forward(req, resp);
	}

	protected void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doCancel(req, resp);
	}

	protected void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, ReflectiveOperationException {
		try {
            DaoFactory.getInstance().getUserDao().delete((User) req.getSession().getAttribute("user"));
        } catch (DatabaseException e) {
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