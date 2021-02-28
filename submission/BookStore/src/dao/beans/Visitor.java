package dao.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Visitor extends DataObject {

	public Cart cart;
	
	

	public Cart getCart() {
		return cart;
	}



	public void setCart(Cart cart) {
		this.cart = cart;
	}



	public static class Builder extends DataObjectBuilder{
		private Cart cart;

		public Builder(){
			this.id=emptyId();
			this.cart=new Cart.Builder().build();
		}
		
		public Builder(Visitor visitor){
			this.id=visitor.getId();
			this.cart=visitor.cart;
		}

		public Builder withSessionId(String sessionId){//SUBJECT TO CHANGE
			this.id=sessionId;
			return this;
		}

		public Builder withCart(Cart cart){
			this.cart=cart;
			return this;
		}

		public Visitor build(){
			Visitor visitor=new Visitor();
			visitor.id=this.id;
			visitor.cart=this.cart;
			return visitor;
		}

	}
}
