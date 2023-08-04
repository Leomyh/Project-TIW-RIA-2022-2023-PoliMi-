package tiw.beans;

import java.util.List;

public class Supplier {

    private String code;
    private String name;
    private int rating;
    private List<SpendingRange> ranges;
    private PreOrder preOrder_ref;

    private Float freeShipping;

    public Supplier(String name, int rating) {
	this.name = name;
	this.rating = rating;
    }

    public Supplier(String code) {
	this.code = code;
    }

    public List<SpendingRange> getRanges() {
	return ranges;
    }

    public void setRanges(List<SpendingRange> ranges) {
	this.ranges = ranges;
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

    public int getRating() {
	return rating;
    }

    public void setRating(int rating) {
	this.rating = rating;
    }

    public Float getFreeShipping() {
	return freeShipping;
    }

    public void setFreeShipping(Float freeShipping) {
	this.freeShipping = freeShipping;
    }

    public PreOrder getPreOrder_ref() {
	return preOrder_ref;
    }

    public void setPreOrder_ref(PreOrder preOrder_ref) {
	this.preOrder_ref = preOrder_ref;
    }

}
