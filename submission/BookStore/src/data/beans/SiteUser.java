package data.beans;

public abstract class SiteUser extends User{
	protected Cart cart;
	public abstract String getUserType();
	
	public Cart getCart() {
		return cart;
	}



	public void setCart(Cart cart) {
		this.cart = cart;
	}
}
