package tiw.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.gson.Gson;

import tiw.beans.User;
import tiw.dao.UserDao;
import tiw.utilities.ConnectionManager;

@WebServlet("/CheckLogin")
@MultipartConfig
public class CheckLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    public CheckLogin() {
	super();
    }

    public void init() throws UnavailableException {
	connection = ConnectionManager.getConnection(getServletContext());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String email = StringEscapeUtils.escapeJava(request.getParameter("email"));
	String password = StringEscapeUtils.escapeJava(request.getParameter("password"));
	if (email == null || password == null) {
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);// 401
	    response.getWriter().println("Email e/o password are undefined;");
	}

	UserDao userDao = new UserDao(connection);
	User user;

	try {
	    user = userDao.checkCredentials(email, password);
	    if (user != null) {
		// successful login
		String jsonUser = new Gson().toJson(user);
		request.getSession().setAttribute("user", user);
		response.setStatus(HttpServletResponse.SC_OK);// 200
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonUser);
	    } else {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);// 401
		response.getWriter().println("Email e/o password are incorrect;");
	    }

	} catch (SQLException e) {
	    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);// 500
	    response.getWriter().println("Internal server error, retry later");
	}

    }

    /**
     * close the connection
     */
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