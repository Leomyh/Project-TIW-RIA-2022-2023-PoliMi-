package tiw.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import tiw.dao.UserDao;
import tiw.utilities.ConnectionManager;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/DoRegistration")
@MultipartConfig
public class DoRegistration extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    public DoRegistration() {
	super();
    }

    public void init() throws UnavailableException {
	connection = ConnectionManager.getConnection(getServletContext());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String email = StringEscapeUtils.escapeJava(request.getParameter("email_reg"));
	String password = StringEscapeUtils.escapeJava(request.getParameter("password_reg"));
	String name = StringEscapeUtils.escapeJava(request.getParameter("name_reg"));
	String surname = StringEscapeUtils.escapeJava(request.getParameter("surname_reg"));
	String address = StringEscapeUtils.escapeJava(request.getParameter("address_reg"));
	String cardNumber = StringEscapeUtils.escapeJava(request.getParameter("card_number_reg"));
	String cardMonth = StringEscapeUtils.escapeJava(request.getParameter("month_reg"));
	String cardYear = StringEscapeUtils.escapeJava(request.getParameter("year_reg"));
	String cardCvv = StringEscapeUtils.escapeJava(request.getParameter("card_cvv_reg"));
	String confirmPassword = StringEscapeUtils.escapeJava(request.getParameter("password_conf_reg"));

	String cardExpiration = cardYear + "-" + cardMonth + "-01";

	String error = "";

	// email too long
	if (email.length() >= 45)
	    error += "Email must be at most 45 characters";
	if (email == null || email.isEmpty() || !isValidEmail(email))
	    error += "Invalid email format";

	// name too long
	if (name.length() >= 45)
	    error += "Name must be at most 45 characters";
	// name incorrect input
	if (!name.matches("[a-zA-Z]+"))
	    error += "Name must contain only letters";

	// surname is too long
	if (surname.length() >= 45)
	    error += "Surname must be at most 45 characters";
	// surname incorrect input
	if (!surname.matches("[a-zA-Z]+"))
	    error += "Surname must contain only letters";

	// password too long
	if (password.length() >= 45)
	    error += "Password must be at most 45 characters";
	// password confirmed
	if (!password.equals(confirmPassword))
	    error += "Passwords must be equal";

	// address too long
	if (address.length() >= 45)
	    error += "Address must be at most 45 characters";

	// card must be 16 number type
	if (!isValidCardNumber(cardNumber)) {
	    error += "Card number must be a 16-digit number";
	}

	// Check if month e/o year of card are selected
	if (cardMonth == null || cardYear == null)
	    error += "Please selected expire month e/o year of the card ";
	// card CVV must be 3 number type
	if (!isValidCardCvv(cardCvv)) {
	    error += "Card cvv must be a 3-digit number";
	}

	if (!error.equals("")) {
	    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);// Code 400
	    response.getWriter().println(error);
	    return;
	}

	UserDao userDao = new UserDao(connection);
	boolean result = false;
	try {
	    result = userDao.registerAccount(email, name, surname, password, address, cardNumber, cardExpiration,
		    cardCvv);

	    if (result == true) {

		response.setStatus(HttpServletResponse.SC_OK);// Code 200
		response.getWriter().println("Registered");

	    } else {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);// Code 400
		response.getWriter().println("Account is registered, please try login or change another one");

	    }
	} catch (SQLException e) {
	    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);// Code 500
	    response.getWriter().println("Internal server error, retry later");
	}

    }

    public boolean isValidEmail(String email) {
	String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	Pattern pattern = Pattern.compile(emailRegex);
	return pattern.matcher(email).matches();
    }

    public boolean isValidCardNumber(String cardNumber) {
	String cardNumberRegex = "\\d{16}";
	return cardNumber.matches(cardNumberRegex);
    }

    public boolean isValidCardCvv(String cvvNumber) {
	String cvvNumberRegex = "\\d{3}";
	return cvvNumber.matches(cvvNumberRegex);
    }

    public void destroy() {
	try {
	    if (connection != null) {
		connection.close();
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

}
