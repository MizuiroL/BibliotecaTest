package it.luca.biblioteca.administration.utente;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class EliminaUtenteServlet
 */
@WebServlet("/controlPanel/eliminaUtente")
public class EliminaUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DELETEQUERY = "DELETE FROM utente WHERE id = ?";
	private Connection connection;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			ServletContext context = config.getServletContext();
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(context.getInitParameter("dbUrl"), context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		request.getRequestDispatcher("/navigationpanel.html").include(request, response);
		request.getRequestDispatcher("/managementnavbar.html").include(request, response);

		/*
		 * Ottiene la sessione se questa esiste altrimenti ritorna null
		 */
		HttpSession session = request.getSession(false);
		if (session != null) {
			String username = (String) session.getAttribute("username");
			writer.println("Pannello di controllo bibioteca");
			writer.println("Utente loggato: " + username);

			Integer id = Integer.valueOf(request.getParameter("id"));

			try {
				PreparedStatement statement = connection.prepareStatement(DELETEQUERY);
				statement.setInt(1, id);
				int result = statement.executeUpdate();
				if (result > 0) {
					writer.print("<h1>L'utente è stato eliminato</h1");
				} else {
					writer.print("<h1>Errore nel terminare l'utente</h1>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			writer.print("Accesso vietato. Fare il login per continuare");
			request.getRequestDispatcher("/index.html").include(request, response);
		}
		writer.close();

	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
