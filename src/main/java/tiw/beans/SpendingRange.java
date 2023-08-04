package tiw.beans;

public class SpendingRange {

    private String codeSupplier;
    private String id;
    private int maxQty;
    private int minQty;
    private float price;

    public SpendingRange(String id) {
	this.id = id;
    }

    public String getCodeSupplier() {
	return codeSupplier;
    }

    public void setCodeSupplierr(String codeSupplier) {
	this.codeSupplier = codeSupplier;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public int getMaxQty() {
	return maxQty;
    }

    public void setMaxQty(int maxQty) {
	this.maxQty = maxQty;
    }

    public int getMinQty() {
	return minQty;
    }

    public void setMinQty(int minQty) {
	this.minQty = minQty;
    }

    public float getPrice() {
	return price;
    }

    public void setPrice(float price) {
	this.price = price;
    }

}
