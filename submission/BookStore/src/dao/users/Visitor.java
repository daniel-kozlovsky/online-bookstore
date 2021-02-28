package dao.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.DataSchema;
import dao.carts.Cart;

public class Visitor {
	public String sessionId="SESSION_ID";
	public Cart cart;

	public static class Builder{
		private String sessionId;
		private Cart cart;

		public Builder(){
			this.sessionId="";
			this.cart=new Cart.Builder().build();
		}
		
		public Builder(Visitor visitor){
			this.sessionId=visitor.sessionId;
			this.cart=visitor.cart;
		}

		public Builder withSessionId(String sessionId){
			this.sessionId=sessionId;
			return this;
		}

		public Builder withCart(Cart cart){
			this.cart=cart;
			return this;
		}

		public Visitor build(){
			Visitor visitor=new Visitor();
			visitor.sessionId=this.sessionId;
			visitor.cart=this.cart;
			return visitor;
		}

	}
}
