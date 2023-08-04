package tiw.beans;

import java.util.HashMap;
import java.util.Map.Entry;

import tiw.utilities.PriceQuantity;

public class PreOrder {
    // key: product_code, value: price and quantity
    public HashMap<String, PriceQuantity> preOrderMap = new HashMap<>();
    public float totalCost;
    public int totalQuantity;
    public float shippingCost;
    public Supplier supplier;

    public PreOrder(Supplier sup) {
	this.totalCost = 0;
	this.totalQuantity = 0;
	this.shippingCost = 0;
	this.supplier = sup;
    }

    public void updatePreOrder(String product_code, int quantity, float price) {
	if (preOrderMap.containsKey(product_code)) {
	    int oldqty = preOrderMap.get(product_code).quantity;
	    preOrderMap.get(product_code).quantity = quantity + oldqty;

	} else {
	    // add new Product type
	    preOrderMap.put(product_code, new PriceQuantity(price, quantity));

	}
	updateTotalCost();
	updateTotalQuantity();
	updateShippingCost();

    }

    public void updateShippingCost() {

	float threshold = supplier.getFreeShipping();
	if (supplier.getFreeShipping() > 0 && totalCost >= threshold) {
	    shippingCost = 0;
	    return;
	} else {
	    // Calculation based on Spending Range of the Supplier
	    for (SpendingRange range : supplier.getRanges()) {
		int min = range.getMinQty();
		int max = range.getMaxQty();
		if (totalQuantity >= min && totalQuantity <= max) {
		    // Found range
		    shippingCost = range.getPrice();
		    return;
		}

	    }
	}
    }

    public void updateTotalQuantity() {
	int updateTotal = 0;
	for (Entry<String, PriceQuantity> entry : preOrderMap.entrySet()) {
	    updateTotal += entry.getValue().quantity;
	}
	totalQuantity = updateTotal;
    }

    public void updateTotalCost() {
	float updateTotal = 0;
	for (Entry<String, PriceQuantity> entry : preOrderMap.entrySet()) {
	    updateTotal += entry.getValue().price * entry.getValue().quantity;
	}
	totalCost = updateTotal;
    }

    public void printPreOrder() {
	System.out.println("PreOrderMap:");
	for (java.util.Map.Entry<String, PriceQuantity> entry : preOrderMap.entrySet()) {
	    System.out.println("Product: " + entry.getKey() + "| PriceQty: " + entry.getValue().price + " : "
		    + entry.getValue().quantity);

	}
	System.out.println("TotalCost: " + totalCost);
	System.out.println("TotalQty: " + totalQuantity);
	System.out.println("TotalShip: " + shippingCost);

    }

    public void executeUpdateSequence() {
	updateTotalCost();
	updateTotalQuantity();
	updateShippingCost();

    }
}
