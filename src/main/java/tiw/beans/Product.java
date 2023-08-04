package tiw.beans;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private boolean isSelected = false;
    private String code;
    private String name;

    private String category;
    private String photo;
    private String description;
    private List<SupplierOffer> offerList = new ArrayList<>();

    private Float minPrice;

    public Product() {

    }

    public Product(String code) {
	this.code = code;
    }

    public Product(String code, String name) {
	this.code = code;
	this.name = name;

    }

    public Product(String code, String name, String photo) {
	this.code = code;
	this.name = name;
	this.photo = photo;

    }

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getCategory() {
	return category;
    }

    public void setCategory(String category) {
	this.category = category;
    }

    public String getPhoto() {
	return photo;
    }

    public void setPhoto(String photo) {
	this.photo = photo;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public boolean isSelected() {
	return isSelected;
    }

    public void setSelected(boolean isSelected) {
	this.isSelected = isSelected;
    }

    public List<SupplierOffer> getOfferList() {
	return offerList;
    }

    public void setOfferList(List<SupplierOffer> offerList) {
	this.offerList = offerList;
    }

    public Float getMinPrice() {
	return minPrice;
    }

    public void setMinPrice(Float minPrice) {
	this.minPrice = minPrice;
    }

}