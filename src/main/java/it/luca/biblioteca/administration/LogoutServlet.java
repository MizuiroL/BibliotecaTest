package it.luca.biblioteca.administration;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();

		request.getRequestDispatcher("navigationpanel.html").include(request, response);

		HttpSession session = request.getSession();
		session.invalidate();

		writer.print("Hai eseguito correttamente il logout");

		writer.close();
	}

}
