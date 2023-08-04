package tiw.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import tiw.beans.ChronologyManager;
import tiw.beans.Product;
import tiw.beans.User;
import tiw.utilities.ConnectionManager;

/**
 * Servlet implementation class GetLastSeen
 */
@WebServlet("/GetLastSeen")
public class GetLastSeen extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLastSeen() {
	super();
    }

    public void init() throws UnavailableException {
	connection = ConnectionManager.getConnection(getServletContext());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	HttpSession s = request.getSession();

	User user = (User) s.getAttribute("user");

	if (user == null) {
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User Null Error");
	}

	// Get User Chronology and Cart
	List<Product> last_seen = null;
	last_seen = ChronologyManager.getMyUserChronology(connection, user.getEmail());
	if (last_seen != null && last_seen.size() > 0 && last_seen.size() <= 5) {
	    // Chronology is inserted in session
	    String jsonLastSeen = new Gson().toJson(last_seen);
	    response.setStatus(HttpServletResponse.SC_OK);// 200
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(jsonLastSeen);

	} else {
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Undefined Chronology Error");
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
