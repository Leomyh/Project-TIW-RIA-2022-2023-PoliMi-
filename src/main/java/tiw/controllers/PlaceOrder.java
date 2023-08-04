package tiw.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tiw.beans.Order;
import tiw.dao.OrderDao;
import tiw.utilities.ConnectionManager;
import tiw.utilities.OrderPack;

/**
 * Servlet implementation class PlaceOrder
 */
@WebServlet("/PlaceOrder")
public class PlaceOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceOrder() {
	super();
    }

    public void init() throws UnavailableException {
	connection = ConnectionManager.getConnection(getServletContext());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	BufferedReader reader = request.getReader();
	StringBuilder sb = new StringBuilder();
	String line;

	while ((line = reader.readLine()) != null) {
	    sb.append(line);
	}

	String json = sb.toString();

	Gson gson = new Gson();
	OrderPack object = gson.fromJson(json, OrderPack.class);

	OrderDao orderDao = new OrderDao(connection);
	int id = -1;
	try {
	    id = orderDao.getLastOrderID();
	} catch (SQLException e) {
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    e.printStackTrace();
	}
	if (id >= 0) {
	    // Create order
	    Order order = new Order(id);
	    order.setEmailUser(object.email);
	    order.setSupplierCode(object.supplierCode);
	    float tot = Float.valueOf(object.totalPrice) + Float.valueOf(object.shippingFee);
	    order.setTotalValue(tot);
	    order.setUserAddress(object.address);
	    order.setShippingDate(LocalDate.now().toString());
	    HashMap<String, Integer> orderMap = new HashMap<>();
	    for (int i = 0; i < object.productList.size(); i++) {
		orderMap.put(object.productList.get(i), object.quantityList.get(i));
	    }
	    // Set Order Map
	    order.setOrderMap(orderMap);
	    try {
		// Upload Order
		if (orderDao.uploadOrder(order)) {
		    response.setStatus(HttpServletResponse.SC_OK);
		} else {
		    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	    } catch (SQLException e) {
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		e.printStackTrace();
	    }
	} else {
	    response.sendError(HttpServletResponse.SC_NO_CONTENT);
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
