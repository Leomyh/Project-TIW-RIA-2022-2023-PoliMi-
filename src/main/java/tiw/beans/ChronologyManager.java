package tiw.beans;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import tiw.dao.ShopDao;
import tiw.utilities.Chronology;

public class ChronologyManager {
    public static final int limit = 5;

    public static String default_category = "foods";

    public static ChronologyManager instance;
    // key: user email, value: Chronology
    public HashMap<String, Chronology> chronologyMap = new HashMap<>();

    public static List<Product> getMyUserChronology(Connection connection, String email) {
	if (instance == null) {

	    instance = new ChronologyManager();

	}
	ShopDao dao = new ShopDao(connection);

	if (isStored(email)) {
	    return instance.chronologyMap.get(email).lastSeen;

	} else {
	    // Store a new user Chronology
	    instance.chronologyMap.put(email, new Chronology());
	    List<Product> lastSeen = instance.chronologyMap.get(email).lastSeen;
	    List<Product> randomProducts = null;
	    try {
		// Get random category
		default_category = dao.getRandomCategory();
		// Get from database the full list of the default_category
		randomProducts = dao.getCategoryProducts(default_category);
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    // Randomly add 5 products to last seen
	    while (randomProducts != null && randomProducts.size() > 0 && lastSeen.size() < limit) {
		int rdm = (int) (Math.random() * randomProducts.size());
		Boolean exists = false;
		// Avoid duplicates
		for (Product p : lastSeen) {
		    if (p.getCode().equals(randomProducts.get(rdm).getCode())) {
			exists = true;
		    }
		}
		if (!exists) {
		    instance.chronologyMap.get(email).addProductToLastSeen(randomProducts.get(rdm));
		}
	    }

	    return lastSeen;
	}

    }

    public static boolean isStored(String email) {
	if (instance != null && !instance.chronologyMap.isEmpty() && instance.chronologyMap.containsKey(email)) {
	    return true;
	}
	return false;
    }

    public static boolean addProductToLastSeen(String email, Product product) {
	if (isStored(email)) {
	    instance.chronologyMap.get(email).addProductToLastSeen(product);
	    return true;
	} else {
	    return false;
	}

    }
}
