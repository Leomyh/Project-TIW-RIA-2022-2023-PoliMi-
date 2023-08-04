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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;

import tiw.beans.ChronologyManager;
import tiw.beans.Product;
import tiw.beans.User;
import tiw.dao.ShopDao;
import tiw.utilities.ConnectionManager;

/**
 * Servlet implementation class ClickProduct
 */
@WebServlet("/ClickProduct")
@MultipartConfig
public class ClickProduct extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClickProduct() {
	super();
    }

    public void init() throws UnavailableException {
	connection = ConnectionManager.getConnection(getServletContext());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String this_code = StringEscapeUtils.escapeJava(request.getParameter("product_code"));
	HttpSession session = request.getSession();
	User user = (User) session.getAttribute("user");

	if (this_code != null) {
	    ShopDao dao = new ShopDao(connection);
	    Product x = null;
	    try {
		x = dao.getProduct(this_code);
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    if (x != null) {
		// Update Chronology
		if (!ChronologyManager.addProductToLastSeen(user.getEmail(), x)) {
		    ChronologyManager.getMyUserChronology(connection, user.getEmail());
		    ChronologyManager.addProductToLastSeen(user.getEmail(), x);
		}

	    } else {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
	    }
	} else {
	    response.setStatus(HttpServletResponse.SC_NO_CONTENT);// 204
	}

	response.setStatus(HttpServletResponse.SC_OK);// 200
	System.out.println("Clicked:" + this_code);

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
