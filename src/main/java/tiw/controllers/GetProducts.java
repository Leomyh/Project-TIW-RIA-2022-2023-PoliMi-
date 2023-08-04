package tiw.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.gson.Gson;

import tiw.beans.Product;
import tiw.beans.User;
import tiw.dao.ShopDao;
import tiw.utilities.ConnectionManager;

@WebServlet("/GetProducts")
@MultipartConfig
public class GetProducts extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Connection connection;

    public GetProducts() {
	super();
    }

    public void init() throws UnavailableException {
	connection = ConnectionManager.getConnection(getServletContext());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	String search = StringEscapeUtils.escapeJava(request.getParameter("search"));
	if (search == null) {
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	User user = (User) request.getSession().getAttribute("user");
	if (user == null) {
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Session Error");
	}
	ShopDao sDao = new ShopDao(connection);

	try {
	    List<Product> resultProducts = null;
	    resultProducts = (List<Product>) sDao.searchProduct(search);

	    if (resultProducts != null && !resultProducts.isEmpty()) {
		// RESEARCH Successful

		String jsonResults = new Gson().toJson(resultProducts);

		request.getSession().setAttribute("products", resultProducts);
		request.getSession().setAttribute("keyword", search);

		response.setStatus(HttpServletResponse.SC_OK);// 200
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		response.getWriter().write(jsonResults);

	    } else {
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	    }

	} catch (SQLException e) {
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SQL error");
	}
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
