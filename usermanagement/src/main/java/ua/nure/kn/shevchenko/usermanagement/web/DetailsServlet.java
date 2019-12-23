package ua.nure.kn.shevchenko.usermanagement.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetailsServlet extends EditServlet {
	private static final long serialVersionUID = -3546913063579725213L;

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameter("backButton") != null) {
            req.getRequestDispatcher("/browse").forward(req, resp);
        } else {
            showPage(req, resp);
        }
	}

	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/details.jsp").forward(req, resp);
	}
	
}