package tiw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tiw.beans.Offer;
import tiw.beans.SpendingRange;
import tiw.beans.Supplier;
import tiw.beans.SupplierOffer;

public class SupplierDao {
    private Connection connection;

    public SupplierDao(Connection connection) {
	this.connection = connection;
    }

    public List<SupplierOffer> getProductSupplierList(String productCode) throws SQLException {
	String query = "SELECT O.product_code,O.price, O.supplier_code, S.name, S.free_shipping, S.rating "
		+ "FROM Shop100.supplier AS S join Shop100.Offer AS O ON O.supplier_code=S.code "
		+ "WHERE O.product_code=? ORDER BY O.price ASC";
	ResultSet resultSet = null;
	PreparedStatement pStatement = null;

	List<SupplierOffer> tmpSupplierList = new ArrayList<>();
	RangeDao rangeDAO = new RangeDao(connection);
	try {

	    pStatement = connection.prepareStatement(query);

	    pStatement.setString(1, productCode);

	    resultSet = pStatement.executeQuery();

	    while (resultSet.next()) {
		String supplierName = resultSet.getString("S.name");
		int rating = resultSet.getInt("S.rating");
		Float freeShip = resultSet.getFloat("S.free_shipping");
		Float price = resultSet.getFloat("O.price");
		String supplier_code = resultSet.getString("O.supplier_code");
		// Spending ranges for each supplier
		List<SpendingRange> ranges = new ArrayList<>();
		ranges = rangeDAO.getRanges(supplier_code);

		Supplier tmpSup = new Supplier(supplierName, rating);
		tmpSup.setCode(supplier_code);
		tmpSup.setRanges(ranges);
		tmpSup.setFreeShipping(freeShip);
		Offer tmpOffer = new Offer(price);
		SupplierOffer tmp = new SupplierOffer(tmpSup, tmpOffer);

		tmpSupplierList.add(tmp);
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
	return tmpSupplierList;

    }

    public Supplier getSupplier(String supplier_code) throws SQLException {
	String query = "SELECT * FROM shop100.supplier WHERE code=?;";
	ResultSet resultSet = null;
	PreparedStatement pStatement = null;
	Supplier supplier = null;
	try {
	    pStatement = connection.prepareStatement(query);

	    pStatement.setString(1, supplier_code);

	    resultSet = pStatement.executeQuery();

	    if (resultSet.next()) {
		String code = resultSet.getString("code");
		String name = resultSet.getString("name");
		int rating = resultSet.getInt("rating");
		float freeShip = resultSet.getFloat("free_shipping");
		supplier = new Supplier(code);
		supplier.setName(name);
		supplier.setRating(rating);
		supplier.setFreeShipping(freeShip);

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
	return supplier;

    }

}
