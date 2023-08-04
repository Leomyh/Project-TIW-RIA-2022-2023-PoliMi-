package tiw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import tiw.beans.Order;
import tiw.beans.User;

public class OrderDao {
    private Connection connection;

    public OrderDao(Connection connection) {
	this.connection = connection;
    }

    public boolean uploadOrder(Order order) throws SQLException {
	String queryOrder = "INSERT INTO Shop100.Order (suplier_code,user_email,total_value,date_of_shipment) VALUES (?,?,?,?)";
	String queryOrderDetails = "INSERT INTO Shop100.Order_composition (code_order,code_product) VALUES (?,?)";

	try (PreparedStatement orderStatement = connection.prepareStatement(queryOrder,
		Statement.RETURN_GENERATED_KEYS);
		PreparedStatement productStatement = connection.prepareStatement(queryOrderDetails)) {

	    orderStatement.setString(1, order.getSupplierCode());
	    orderStatement.setString(2, order.getEmailUser());
	    orderStatement.setFloat(3, order.getTotalValue());
	    orderStatement.setString(4, order.getShippingDate());

	    int rowsAffected = orderStatement.executeUpdate();

	    if (rowsAffected <= 0) {
		return false;
	    }

	    ResultSet generatedKeys = orderStatement.getGeneratedKeys();
	    int orderId = 0;
	    if (generatedKeys.next()) {
		orderId = generatedKeys.getInt(1);
	    }
	    for (String product : order.getOrderMap().keySet()) {
		;
		productStatement.setInt(1, orderId);
		productStatement.setString(2, product);
		productStatement.executeUpdate();

	    }

	    return true;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}

    }

    public HashMap<Integer, Order> getOrders(User user) throws SQLException {

	String query = "SELECT O.date_of_shipment,O.code,O.suplier_code,P.name,O.total_value,U.address "
		+ "FROM shop100.order_composition as OC,shop100.order as O,shop100.user as U,shop100.product as P "
		+ "WHERE OC.code_order=O.code AND O.user_email=U.email AND P.code=OC.code_product AND O.user_email=? "
		+ "ORDER BY O.date_of_shipment DESC";

	ResultSet resultSet = null;
	PreparedStatement pStatement = null;

	HashMap<Integer, Order> orderList = new HashMap<>();

	try {
	    pStatement = connection.prepareStatement(query);
	    pStatement.setString(1, user.getEmail());

	    resultSet = pStatement.executeQuery();

	    while (resultSet.next()) {
		int orderCode = resultSet.getInt("O.code");
		String supplierCode = resultSet.getString("O.suplier_code");
		String productName = resultSet.getString("P.name");
		float totalValue = resultSet.getFloat("O.total_value");
		String address = resultSet.getString("U.address");

		String dateOfShipment = resultSet.getString("O.date_of_shipment");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate pickDate = LocalDate.parse(dateOfShipment, formatter);

		Order tmpOrder = orderList.get(orderCode);

		if (tmpOrder == null) {
		    tmpOrder = new Order(orderCode);
		    tmpOrder.setSupplierCode(supplierCode);
		    tmpOrder.setShippingDate(pickDate.toString());
		    tmpOrder.setTotalValue(totalValue);
		    tmpOrder.setUserAddress(address);

		    orderList.put(orderCode, tmpOrder);
		}
		tmpOrder.getProductNameList().add(productName);
	    }

	} catch (SQLException e) {
	    throw new SQLException();

	} finally {
	    // Close the result set and prepared statement
	    if (resultSet != null) {
		resultSet.close();
	    }
	    if (pStatement != null) {
		pStatement.close();
	    }
	}

	// return order list
	return orderList;
    }

    public int getLastOrderID() throws SQLException {
	String query = "SELECT max(code) FROM shop100.order;";

	ResultSet resultSet = null;
	PreparedStatement pStatement = null;
	try {
	    pStatement = connection.prepareStatement(query);

	    resultSet = pStatement.executeQuery();

	    if (resultSet.next()) {
		int lastCode = resultSet.getInt("max(code)");
		if (lastCode >= 0) {
		    return lastCode;
		}
	    }

	} catch (SQLException e) {
	    throw new SQLException();

	} finally {
	    // Close the result set and prepared statement
	    if (resultSet != null) {
		resultSet.close();
	    }
	    if (pStatement != null) {
		pStatement.close();
	    }
	}
	return -1;
    }

}
