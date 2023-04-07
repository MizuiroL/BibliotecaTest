package it.luca.biblioteca.management.utente;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class VisualizzaUtentiServlet
 */
@WebServlet(urlPatterns = "/controlpanel/visualizzaUtenti")
public class VisualizzaUtentiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM utente;");
				writer.print("<table>");
				writer.print("<tr>");
				writer.print("<th>");
				writer.println("Id");
				writer.print("</th>");
				writer.print("<th>");
				writer.println("Nome");
				writer.print("</th>");
				writer.print("<th>");
				writer.println("Cognome");
				writer.print("</th>");
				writer.print("<th>");
				writer.println("Data di nascita");
				writer.print("</th>");
				writer.print("</tr>");
				while (resultSet.next()) {
					writer.println("<tr>");
					writer.println("<td>");
					writer.print(resultSet.getInt(1));
					writer.println("</td>");
					writer.println("<td>");
					writer.print(resultSet.getString(2));
					writer.println("</td>");
					writer.println("<td>");
					writer.print(resultSet.getString(3));
					writer.println("</td>");
					writer.println("<td>");
					writer.print(resultSet.getDate(4));
					writer.println("</td>");
					writer.println("</tr>");
				}
				writer.print("</table>");

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			writer.print("Accesso vietato. Fare il login per continuare");
			request.getRequestDispatcher("login.html").include(request, response);
		}
		writer.close();

	}

	@Override
	public void destroy() {
		try {
			System.out.println("destroy()");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
