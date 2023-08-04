package tiw.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import tiw.beans.Order;
import tiw.beans.User;
import tiw.dao.OrderDao;
import tiw.utilities.ConnectionManager;

@WebServlet("/ViewOrder")
@MultipartConfig

public class ViewOrder extends HttpServlet implements Serializable {

    private static final long serialVersionUID = 1L;
    private Connection connection;

    /**
     * @see HttpServlet#HttpServlet()
     * 
     */
    public ViewOrder() {
	super();
    }

    public void init() throws UnavailableException {
	connection = ConnectionManager.getConnection(getServletContext());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	HttpSession s = request.getSession();

	User user = (User) s.getAttribute("user");
	if (user == null) {
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);// 401
	    return;
	}

	OrderDao dao = new OrderDao(connection);
	HashMap<Integer, Order> userOrderMap = null;
	try {
	    // Get User Orders
	    userOrderMap = dao.getOrders(user);
	} catch (SQLException e) {
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	// Convert the order map in list
	List<Order> orders = new ArrayList<>(userOrderMap.values());
	orders.sort(Comparator.comparing(Order::getShippingDate).reversed());
	String jsonOrder = new Gson().toJson(orders);

	response.setStatus(HttpServletResponse.SC_OK);// 200
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");

	response.getWriter().write(jsonOrder);

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
