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
 * Servlet implementation class ControlPanelServlet
 */
@WebServlet(urlPatterns = "/controlpanel")
public class ControlPanelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		request.getRequestDispatcher("navigationpanel.html").include(request, response);
		
		/*
		 * Ottiene la sessione se questa esiste altrimenti ritorna null
		 */
		HttpSession session = request.getSession(false);
		if (session != null) {
			String username = (String) session.getAttribute("username");
			writer.println("Pannello di controllo bibioteca");
			writer.println("Utente loggato: " + username);
			request.getRequestDispatcher("/controlpanel/visualizzaUtenti").include(request, response);
			request.getRequestDispatcher("/managementnavbar.html").include(request, response);
		} else {
			writer.print("Accesso vietato. Fare il login per continuare");
			request.getRequestDispatcher("login.html").include(request, response);
		}
		writer.close();
	}
}
