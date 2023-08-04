package tiw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tiw.beans.SpendingRange;

public class RangeDao {

    public Connection connection;

    public RangeDao(Connection connection) {
	this.connection = connection;
    }

    public List<SpendingRange> getRanges(String supplierCode) throws SQLException {
	String query = "SELECT * FROM shop100.spending_range WHERE supplier_code=?";
	ResultSet resultSet = null;
	PreparedStatement pStatement = null;

	List<SpendingRange> tmpRangeList = new ArrayList<>();
	try {

	    pStatement = connection.prepareStatement(query);

	    pStatement.setString(1, supplierCode);

	    resultSet = pStatement.executeQuery();
	    // System.exit(0);
	    while (resultSet.next()) {
		String id = resultSet.getString("id");
		String supplier_code = resultSet.getString("supplier_code");
		int min_qty = resultSet.getInt("min_qty");
		int max_qty = resultSet.getInt("max_qty");
		Float price = resultSet.getFloat("price");

		SpendingRange tmpRange = new SpendingRange(id);
		tmpRange.setCodeSupplierr(supplier_code);
		tmpRange.setMinQty(min_qty);
		tmpRange.setMaxQty(max_qty);
		tmpRange.setPrice(price);

		tmpRangeList.add(tmpRange);
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
	return tmpRangeList;

    }

}
