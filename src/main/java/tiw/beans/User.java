package tiw.beans;

public class User {
    private String email;
    private String name;
    private String surname;
    private String password;
    private String address;
    private String cardNumber;
    private String cardExpiration;
    // private DateFormat Data = new SimpleDateFormat("dd-MM-yyyy");
    private String day;
    private String month;
    private String year;
    private String cardCvv;

    public User(String email, String password) {
	this.email = email;
	this.password = password;

    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getSurname() {
	return surname;
    }

    public void setSurname(String surname) {
	this.surname = surname;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getCardNumber() {
	return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
	this.cardNumber = cardNumber;
    }

    public String getCardExpiration() {
	return cardExpiration;
    }

    public String getCardCvv() {
	return cardCvv;
    }

    public void setCardCvv(String cardCvv) {
	this.cardCvv = cardCvv;
    }

    public String getDay() {
	return day;
    }

    public void setDay(String day) {
	this.day = day;
    }

    public String getMonth() {
	return month;
    }

    public void setMonth(String month) {
	this.month = month;
    }

    public String getYear() {
	return year;
    }

    public void setYear(String year) {
	this.year = year;
    }

    public void setCardExpiration(String cardExpiration) {
	this.cardExpiration = cardExpiration;
    }

}