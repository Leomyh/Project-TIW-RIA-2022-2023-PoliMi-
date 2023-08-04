/**
 * 
 */
class Product {
	constructor(code, quantity, price, name, description, category) {
		this.code = code;
		this.quantity = quantity;
		this.price = price;
		this.name = name;
		this.description = description;
		this.category = category;
	}
}

class Range {
	constructor(max, min, price) {
		this.max = max;
		this.min = min;
		this.price = price;
	}
}

/**
 * 
 */
class Cart {
	constructor(email, supplierCode, freeShipping, supplierName) {
		this.email = email;
		this.supplierCode = supplierCode;
		this.supplierName = supplierName;
		this.products = [];
		this.ranges = [];
		this.freeShipping = freeShipping;
	}

	checkMaxium(code, quantity) {
		let product = this.products.find((product) => product.code === code);
		if (product) {
			const newQuantity = product.quantity + parseInt(quantity);
			if (newQuantity > 999) {
				alert("The maximum number of items in the cart is 999.");
				return false;
			}
			else
				return true;
		}
		else {
			if (this.getTotalQuantity() + parseInt(quantity) > 999) {
				alert("The maximum number of items in the cart is 999.");
				return false;
			}

			else
				return true;
		}

	}

	// add product
	addProduct(code, quantity, price, name, description, category) {
		let product = this.products.find((product) => product.code === code);
		if (product) {

			product.quantity += parseInt(quantity);
		} else {

			product = new Product(
				code,
				parseInt(quantity),
				price,
				name,
				description,
				category
			);
			this.products.push(product);
		}
		this.saveCart();
	}

	//add shipping Range
	addRanges(max, min, price) {
		let range = new Range(max, min, price);
		this.ranges.push(range);
		console.log(this.ranges);
	}

	// clear cart (util for place order)
	clearCart() {
		this.products = [];
		this.saveCart();
	}

	// calculate price
	calculateTotalPrice() {
		let total = 0;
		for (let product of this.products) {
			total += parseFloat(product.price) * product.quantity;
		}
		return total;
	}

	//calculate total shipping fee
	calculateShippingFee() {
		
		if (this.freeShipping>0 && this.freeShipping <= this.calculateTotalPrice()) {
		
			return 0;
		} else {
		
			for (let range of this.ranges) {

				if (this.getTotalQuantity() >= range.min && this.getTotalQuantity() <= range.max) {
					console.log("Range Price:" + range.price);
					return range.price;
				}

			}
		}

	}

	//calculate total quantity of product
	getTotalQuantity() {
		let totalQuantity = 0;
		for (let product of this.products) {
			totalQuantity += parseInt(product.quantity, 10);
		}
		return totalQuantity;
	}

	// save in localStorage
	saveCart() {

		localStorage.setItem(`cart_${this.email}_${this.supplierCode}_${this.freeShipping}`, JSON.stringify({
			products: this.products,
			ranges: this.ranges,
			supplierName: this.supplierName
		}));

	}

	// get from localStorage
	loadCart() {
		if (this.supplier == null) {

		}
		let loadedData = localStorage.getItem(`cart_${this.email}_${this.supplierCode}_${this.freeShipping}`);
		if (loadedData) {
			let data = JSON.parse(loadedData);
			this.products = data.products;
			this.ranges = data.ranges;
			this.supplierName = data.supplierName;

		} else {
			this.products = [];
			this.ranges = [];

		}
	}

	//Remove cart from LocalStorage
	removeFromStorage() {
		localStorage.removeItem(`cart_${this.email}_${this.supplierCode}_${this.freeShipping}`);
	}

}


class CartManager {
	constructor(email) {
		this.email = email;
		this.carts = [];
	}

	createCart(supplierCode, freeShipping, supplierName) {
		// check if exists
		let existingCart = this.carts.find(cart => cart.supplierCode === supplierCode);
		if (existingCart) {

			return existingCart;
		} else {
			// create new cart
			let newCart = new Cart(this.email, supplierCode, freeShipping, supplierName);
			newCart.loadCart();
			this.carts.push(newCart);
			return newCart;
		}
	}

	// get cart for supplier
	getCart(supplierCode) {
		let cart = this.carts.find(cart => cart.supplierCode === supplierCode);
		if (!cart) {
			return null;
		}
		return cart;
	}

	// clear cart by supplier
	clearCart(supplierCode) {
		let cart = this.getCart(supplierCode);
		cart.clearCart();
	}

	// load cart by supplier
	loadCart(supplierCode, freeShipping) {  // add freeShipping parameter
		let loadedData = localStorage.getItem(`cart_${this.email}_${supplierCode}_${freeShipping}`);
		if (loadedData) {
			let cart = new Cart(this.email, supplierCode, freeShipping, "");  // use freeShipping parameter
			let data = JSON.parse(loadedData);
			cart.products = data.products;
			cart.ranges = data.ranges;
			cart.supplierName = data.supplierName;
			this.carts.push(cart);

			return cart;
		}
		console.log(`No data found for cart_${this.email}_${supplierCode}_${freeShipping}`);
		return null;
	}



	// update carts from localStorage
	updateCarts() {
		this.carts = [];
		var keys = Object.keys(localStorage);
		for (var i = 0; i < keys.length; i++) {
			var key = keys[i];
			if (key.startsWith(`cart_${this.email}_`)) {
				var arraySplit = key.split("_");
				let sup = arraySplit[2];
				let freeShipping = arraySplit[3];  // Extract freeShipping value from the key
				this.loadCart(sup, freeShipping);  // Pass freeShipping value to loadCart method

			}
		}
	}

	getAllCarts() {
		this.updateCarts();
		return this.carts;
	}

	deleteCart(supplierCode) {
		var cartToDelete = this.getCart(supplierCode);
		var index = this.carts.indexOf(cartToDelete);
		// Remove item from localStorage
		cartToDelete.removeFromStorage();
		this.carts.splice(index, 1);

	}
}



