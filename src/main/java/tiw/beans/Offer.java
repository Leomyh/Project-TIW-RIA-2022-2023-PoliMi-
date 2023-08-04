package tiw.beans;

public class Offer {
    private String codeProduct;
    private String codeSeller;
    private float price;

    public Offer(float price) {
	this.price = price;
    }

    public Offer(String codeProduct, float price) {
	this.codeProduct = codeProduct;
	this.price = price;
    }

    public String getCodeProduct() {
	return codeProduct;
    }

    public void setCodeProduct(String codeProduct) {
	this.codeProduct = codeProduct;
    }

    public String getCodeSeller() {
	return codeSeller;
    }

    public void setCodeSeller(String codeSeller) {
	this.codeSeller = codeSeller;
    }

    public float getPrice() {
	return price;
    }

    public void setPrice(float price) {
	this.price = price;
    }

}
