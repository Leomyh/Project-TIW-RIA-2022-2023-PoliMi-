package tiw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tiw.beans.Product;
import tiw.beans.SupplierOffer;

public class ShopDao {
    private Connection connection;

    public ShopDao(Connection connection) {
	this.connection = connection;
    }

    /*
     * Search by name || description return List of product
     */

    public List<Product> searchProduct(String search) throws SQLException {
	String query = "SELECT P.code, P.name, P.category, P.description, MIN(O.price),P.photo "
		+ "FROM Shop100.Product AS P join Shop100.Offer AS O ON P.code=O.product_code "
		+ "WHERE P.name LIKE ? OR P.description LIKE ? " + "GROUP BY P.code";

	ResultSet resultSet = null;
	PreparedStatement pStatement = null;

	List<Product> tmpProductList = new ArrayList<>();
	SupplierDao supplierDAO = new SupplierDao(connection);

	try {
	    pStatement = connection.prepareStatement(query);
	    pStatement.setString(1, "%" + search + "%");
	    pStatement.setString(2, "%" + search + "%");

	    resultSet = pStatement.executeQuery();

	    while (resultSet.next()) {
		String code = resultSet.getString("P.code");
		String name = resultSet.getString("P.name");
		String category = resultSet.getString("P.category");
		String description = resultSet.getString("P.description");
		String photo = resultSet.getString("P.photo");
		Float min_price = resultSet.getFloat("MIN(O.price)");

		Product tmpProduct = new Product(code);
		tmpProduct.setName(name);
		tmpProduct.setCategory(category);
		tmpProduct.setMinPrice(min_price);
		tmpProduct.setDescription(description);
		tmpProduct.setPhoto(photo);
		// Find Supplier list for this product
		List<SupplierOffer> tmpList = (List<SupplierOffer>) supplierDAO.getProductSupplierList(code);
		if (tmpList != null && tmpList.size() > 0) {
		    tmpProduct.setOfferList(tmpList);
		}

		tmpProductList.add(tmpProduct);

	    }
	} catch (SQLException e) {
	    throw new SQLException();
	} finally {
	    try {
		if (resultSet != null) {
		    resultSet.close();
		}
	    } catch (Exception e1) {
		throw new SQLException(e1);
	    }
	    try {
		if (pStatement != null) {
		    pStatement.close();
		}
	    } catch (Exception e2) {
		throw new SQLException(e2);
	    }
	}
	return tmpProductList;
    }

    public Product getProductWithPrice(String pcode) throws SQLException {
	String query = "SELECT P.code, P.name, P.category, P.description, P.photo, MIN(O.price) FROM shop100.product as P JOIN shop100.offer as O ON P.code = O.product_code WHERE P.code=? GROUP BY P.code;";
	ResultSet resultSet = null;
	PreparedStatement pStatement = null;
	Product tmpProduct = null;
	try {
	    pStatement = connection.prepareStatement(query);
	    pStatement.setString(1, pcode);

	    resultSet = pStatement.executeQuery();

	    while (resultSet.next()) {
		String code = resultSet.getString("P.code");
		String name = resultSet.getString("P.name");
		String category = resultSet.getString("P.category");
		String description = resultSet.getString("P.description");
		String photo = resultSet.getString("photo");
		Float minPrice = resultSet.getFloat("MIN(O.price)");
		tmpProduct = new Product(code);
		tmpProduct.setName(name);
		tmpProduct.setCategory(category);
		tmpProduct.setDescription(description);
		tmpProduct.setMinPrice(minPrice);
		tmpProduct.setPhoto(photo);

	    }
	} catch (SQLException e) {
	    throw new SQLException();
	} finally {
	    try {
		if (resultSet != null) {
		    resultSet.close();
		}
	    } catch (Exception e1) {
		throw new SQLException(e1);
	    }
	    try {
		if (pStatement != null) {
		    pStatement.close();
		}
	    } catch (Exception e2) {
		throw new SQLException(e2);
	    }
	}
	return tmpProduct;

    }

    public Product getProduct(String product_code) throws SQLException {
	String query = "SELECT * FROM shop100.product WHERE code = ?;";

	ResultSet resultSet = null;
	PreparedStatement pStatement = null;

	Product tmpProduct = null;

	try {
	    pStatement = connection.prepareStatement(query);
	    pStatement.setString(1, product_code);

	    resultSet = pStatement.executeQuery();

	    if (resultSet.next()) {
		String code = resultSet.getString("code");
		String name = resultSet.getString("name");
		String category = resultSet.getString("category");
		String description = resultSet.getString("description");
		String photo = resultSet.getString("photo");
		tmpProduct = new Product(code, name);
		tmpProduct.setCategory(category);
		tmpProduct.setDescription(description);
		tmpProduct.setPhoto(photo);

	    }

	} catch (SQLException e) {
	    throw new SQLException();
	} finally {
	    try {
		if (resultSet != null) {
		    resultSet.close();
		}
	    } catch (Exception e1) {
		throw new SQLException(e1);
	    }
	    try {
		if (pStatement != null) {
		    pStatement.close();
		}
	    } catch (Exception e2) {
		throw new SQLException(e2);
	    }

	}
	return tmpProduct;

    }

    public List<Product> getCategoryProducts(String def_category) throws SQLException {
	String query = "SELECT P.code, P.name, P.category, P.description, P.photo, MIN(O.price) FROM shop100.product as P JOIN shop100.offer as O ON P.code = O.product_code WHERE P.category=? GROUP BY P.code;";
	ResultSet resultSet = null;
	PreparedStatement pStatement = null;
	SupplierDao supplierDAO = new SupplierDao(connection);
	List<Product> tmpProductList = new ArrayList<>();
	try {
	    pStatement = connection.prepareStatement(query);
	    pStatement.setString(1, def_category);

	    resultSet = pStatement.executeQuery();

	    while (resultSet.next()) {
		String code = resultSet.getString("P.code");
		String name = resultSet.getString("P.name");
		String category = resultSet.getString("P.category");
		String description = resultSet.getString("P.description");
		Float minPrice = resultSet.getFloat("MIN(O.price)");
		String photo = resultSet.getString("P.photo");
		Product tmpProduct = new Product(code);
		tmpProduct.setName(name);
		tmpProduct.setCategory(category);
		tmpProduct.setDescription(description);
		tmpProduct.setMinPrice(minPrice);
		tmpProduct.setPhoto(photo);
		// Find Supplier list for this product
		List<SupplierOffer> tmpList = (List<SupplierOffer>) supplierDAO.getProductSupplierList(code);
		if (tmpList != null && tmpList.size() > 0) {
		    tmpProduct.setOfferList(tmpList);
		}

		tmpProductList.add(tmpProduct);

	    }
	} catch (SQLException e) {
	    throw new SQLException();
	} finally {
	    try {
		if (resultSet != null) {
		    resultSet.close();
		}
	    } catch (Exception e1) {
		throw new SQLException(e1);
	    }
	    try {
		if (pStatement != null) {
		    pStatement.close();
		}
	    } catch (Exception e2) {
		throw new SQLException(e2);
	    }
	}
	return tmpProductList;

    }

    public String getRandomCategory() throws SQLException {
	String query = "SELECT category FROM shop100.product GROUP BY category";
	ResultSet resultSet = null;
	PreparedStatement pStatement = null;
	List<String> categoryList = new ArrayList<>();
	pStatement = connection.prepareStatement(query);
	resultSet = pStatement.executeQuery();

	while (resultSet.next()) {
	    String category = resultSet.getString("category");
	    categoryList.add(category);

	}

	if (categoryList.size() > 0) {
	    return categoryList.get((int) (Math.random() * (categoryList.size())));

	} else {
	    return "foods";
	}

    }

}
