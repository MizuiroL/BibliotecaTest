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
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class CreaUtenteServlet
 */
@WebServlet(urlPatterns = "/controlpanel/creaUtente")
public class CreaUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String INSERTQUERY = "INSERT INTO utente(nome, cognome, data_nascita) VALUES(?,?,?)";
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

			String nome = request.getParameter("nome");
			String cognome = request.getParameter("cognome");
			/*
			 * Il form chiederà la data di nascita come string ma la converte in Date
			 */
			String dataNascitaStringa = request.getParameter("dataNascita");
			Date dataNascita = null;
			if (dataNascitaStringa != null) {
				dataNascita = Date.valueOf(dataNascitaStringa);
			}

			try {
				PreparedStatement statement = connection.prepareStatement(INSERTQUERY);
				statement.setString(1, nome);
				statement.setString(2, cognome);
				statement.setDate(3, dataNascita);
				int result = statement.executeUpdate();
				if (result > 0) {
					writer.print("<h1>Utente creato con successo</h1>");
				} else {
					writer.print("<h1>Errore nella creazione utente</h1>");
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
