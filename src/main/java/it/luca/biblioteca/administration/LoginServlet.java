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
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		request.getRequestDispatcher("navigationpanel.html").include(request, response);

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		System.out.println(username + "\n" + password);

		if (password.equals("admin")) {
			System.out.println("Utente loggato: " + username);
			writer.println("Utente loggato: " + username);
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
		} else {
			System.out.println("Password sbagliata " + password);
			request.getRequestDispatcher("index.html").include(request, response);
		}
		writer.close();
	}

}
