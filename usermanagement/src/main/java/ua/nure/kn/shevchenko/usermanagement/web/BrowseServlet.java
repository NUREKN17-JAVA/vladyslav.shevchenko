package ua.nure.kn.shevchenko.usermanagement.web;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn.shevchenko.usermanagement.User;
import ua.nure.kn.shevchenko.usermanagement.db.DaoFactory;
import ua.nure.kn.shevchenko.usermanagement.db.DatabaseException;

public class BrowseServlet extends HttpServlet {
	private static final long serialVersionUID = 5024985720240889745L;
	
	private static final String DETAIL_BUTTON_COMPONENT_NAME = "detailsButton";
	private static final String DELETE_BUTTON_COMPONENT_NAME = "deleteButton";
	private static final String EDIT_BUTTON_COMPONENT_NAME = "editButton";
	private static final String ADD_BUTTON_COMPONENT_NAME = "addButton";

	protected void service (HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		if (req.getParameter(ADD_BUTTON_COMPONENT_NAME) != null) {
			add(req, resp);
		}
		else if (req.getParameter(EDIT_BUTTON_COMPONENT_NAME) != null) {
			edit(req, resp);
		}
		else if (req.getParameter(DELETE_BUTTON_COMPONENT_NAME) != null) {
			delete(req, resp);
		}
		else if (req.getParameter(DETAIL_BUTTON_COMPONENT_NAME) != null) {
			details(req, resp);
		}
		else {
			try {
				browse(req, resp);
			} catch (ReflectiveOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idStr = 	req.getParameter("id");
		if (idStr == null || idStr.trim().length() == 0) {
			req.setAttribute("error", "You must select a user");
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			return;
		}
		try {
			User user = DaoFactory.getInstance().getUserDao().find(new Long(idStr));
			req.getSession().setAttribute("user", user);
		} catch (Exception e) {
			req.setAttribute("error", "ERROR:" + e.toString());
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			return;
		}
		req.getRequestDispatcher("/details").forward(req, resp);
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idStr = 	req.getParameter("id");
		if (idStr == null || idStr.trim().length() == 0) {
			req.setAttribute("error", "You must select a user");
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			return;
		}
		try {
			User user = DaoFactory.getInstance().getUserDao().find(new Long(idStr));
			req.getSession().setAttribute("user", user);
		} catch (Exception e) {
			req.setAttribute("error", "ERROR:" + e.toString());
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			return;
		}
		req.getRequestDispatcher("/delete").forward(req, resp);
	}

	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idStr = 	req.getParameter("id");
		if (idStr == null || idStr.trim().length() == 0) {
			req.setAttribute("error", "You must select a user");
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			return;
		}
		try {
			User user = DaoFactory.getInstance().getUserDao().find(new Long(idStr));
			req.getSession().setAttribute("user", user);
		} catch (Exception e) {
			req.setAttribute("error", "ERROR:" + e.toString());
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			return;
		}
		req.getRequestDispatcher("/edit").forward(req, resp);
	}

	private void add(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.getRequestDispatcher("/add").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ReflectiveOperationException {
		Collection<User> users;
		try {
			users = DaoFactory.getInstance().getUserDao().findAll();
			req.getSession().setAttribute("users", users);
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
		} catch (DatabaseException e) {
			throw new ServletException(e);
		}
	}
	
}