package tiw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tiw.beans.User;

public class UserDao {
    private Connection connection;

    public UserDao(Connection connection) {
	this.connection = connection;
    }

    /**
     * query to check login credentials
     * 
     * @param email
     * @param password
     * @return
     * @throws SQLException
     */
    public User checkCredentials(String email, String password) throws SQLException {
	String query = "SELECT * FROM Shop100.User WHERE email=? AND password=?";
	ResultSet resultSet = null;
	PreparedStatement pStatement = null;

	try {

	    pStatement = connection.prepareStatement(query);
	    pStatement.setString(1, email);
	    pStatement.setString(2, password);

	    resultSet = pStatement.executeQuery();

	    if (resultSet.next()) {
		User tmpUser = new User(resultSet.getString("email"), resultSet.getString("password"));
		tmpUser.setName(resultSet.getString("name"));
		tmpUser.setAddress(resultSet.getString("address"));
		System.out.println(tmpUser.getAddress());

		return tmpUser;
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
	return null;
    }

    public boolean registerAccount(String email, String name, String surname, String password, String address,
	    String cardNumber, String cardExpiration, String cardCvv) throws SQLException {

	if (existsAccount(email))
	    return false;

	String query = "INSERT INTO Shop100.User (email,name,surname,password,address,card_number,card_expiration,card_cvv) VALUES (?,?,?,?,?,?,?,?)";
	PreparedStatement pStatement = null;

	try {
	    pStatement = connection.prepareStatement(query);
	    pStatement.setString(1, email);
	    pStatement.setString(2, name);
	    pStatement.setString(3, surname);
	    pStatement.setString(4, password);
	    pStatement.setString(5, address);
	    pStatement.setString(6, cardNumber);
	    pStatement.setString(7, cardExpiration);
	    pStatement.setString(8, cardCvv);
	    pStatement.executeUpdate();

	    return true;

	} catch (SQLException e) {
	    throw new SQLException();
	} finally {
	    try {
		if (pStatement != null) {
		    pStatement.close();
		}
	    } catch (Exception e1) {
		throw new SQLException(e1);
	    }
	}

    }

    /**
     * method to check if the email is exists in DB
     * 
     * @param email
     * @return true if exists, false otherwise
     * @throws SQLException
     */
    public boolean existsAccount(String email) throws SQLException {
	String query = "SELECT email FROM Shop100.User WHERE email=?";
	ResultSet resultSet = null;
	PreparedStatement pStatement = null;

	try {

	    pStatement = connection.prepareStatement(query);
	    pStatement.setString(1, email);

	    resultSet = pStatement.executeQuery();

	    if (resultSet.next()) {
		return true;
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
	return false;
    }

}