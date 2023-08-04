package tiw.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order {
    private int code;
    private String emailUser;
    private String shippingDate;

    private int totQuantity;
    private float totalValue;
    private String supplierCode;
    private String supplierName;
    private String userAddress;
    // Key : product code,Value: quantity
    private HashMap<String, Integer> orderMap = new HashMap<>();
    // TODO: Maybe List of Product and not only names
    private List<String> productNameList = new ArrayList<>();

    public List<Product> productList = new ArrayList<>();

    /*
     * public Order(int code) { this.code = code; }
     */
    public Order(int code) {
	this.code = code;
	this.productNameList = new ArrayList<>();
    }

    public String getSupplierCode() {
	return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
	this.supplierCode = supplierCode;
    }

    public HashMap<String, Integer> getOrderMap() {
	return orderMap;
    }

    public void setOrderMap(HashMap<String, Integer> orderMap) {
	this.orderMap = orderMap;
    }

    public String getUserAddress() {
	return userAddress;
    }

    public void setUserAddress(String userAddress) {
	this.userAddress = userAddress;
    }

    public String getSupplierName() {
	return supplierName;
    }

    public void setSupplierName(String supplierName) {
	this.supplierName = supplierName;
    }

    public void setShippingDate(String shippingDate) {
	this.shippingDate = shippingDate;
    }

    public int getCode() {
	return code;
    }

    public void setCode(int code) {
	this.code = code;
    }

    public String getEmailUser() {
	return emailUser;
    }

    public void setEmailUser(String emailUser) {
	this.emailUser = emailUser;
    }

    public String getShippingDate() {
	return shippingDate;
    }

    public int getQuantity() {
	return totQuantity;
    }

    public void setQuantity(int quantity) {
	this.totQuantity = quantity;
    }

    public float getTotalValue() {
	return totalValue;
    }

    public void setTotalValue(float totalValue) {
	this.totalValue = totalValue;
    }

    public List<String> getProductNameList() {
	return productNameList;
    }

    public void setProductNameList(List<String> productNameList) {
	this.productNameList = productNameList;
    }

}