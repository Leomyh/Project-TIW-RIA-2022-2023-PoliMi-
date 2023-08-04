package tiw.utilities;

import java.util.ArrayList;
import java.util.List;

import tiw.beans.ChronologyManager;
import tiw.beans.Product;

public class Chronology {
    public List<Product> lastSeen = new ArrayList<>();

    /**
     * Add product to user chronology
     * 
     * @param lastSeen old user chronology
     * @param product  last seen product
     */
    public void addProductToLastSeen(Product product) {
	if (lastSeen != null) {
	    // Remove duplicate if necessary
	    for (int i = 0; i < lastSeen.size(); i++) {
		if (product.getCode().equals(lastSeen.get(i).getCode())) {
		    lastSeen.remove(i);
		}
	    }
	    if (lastSeen.size() >= 0 && lastSeen.size() < ChronologyManager.limit) {
		lastSeen.add(product);
	    } else if (lastSeen.size() >= ChronologyManager.limit) {
		lastSeen.remove(lastSeen.get(0));
		lastSeen.add(product);
	    }
	}
    }
}
