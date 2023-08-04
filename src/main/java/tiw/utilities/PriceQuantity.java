package tiw.utilities;

import tiw.beans.Product;

public class PriceQuantity {
    public int quantity;
    public float price;
    public Product product; // Product Descriptor

    public PriceQuantity(float price, int quantity) {
	this.quantity = quantity;
	this.price = price;
    }

}
