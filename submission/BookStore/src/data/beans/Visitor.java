package data.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.beans.Cart.Builder;
import data.beans.IdObject.IdObjectBuilder;
import data.schema.UserTypes;
public class Visitor extends User {
	public Cart cart;
	private  long createdAtEpoch;
	private  long lastAccessedAtEpoch;
	public static final String userType=UserTypes.VISITOR;
	
	
	
	public String getUserType() {
		return this.userType;
	}
	
	public Cart getCart() {
		return cart;
	}
	
	public long getCreatedAtEpoch() {
		return createdAtEpoch;
	}
	
	
	public long getLastAccessedAtEpoch() {
		return lastAccessedAtEpoch;
	}





	public void setCart(Cart cart) {
		this.cart = cart;
	}

	private Visitor() {
		
	}


	public static class Builder extends IdObjectBuilder<Builder>{
		private Cart cart;
		private  long createdAtEpoch;
		private  long lastAccessedAtEpoch;

		public Builder(){
			super();
			this.cart=null;
			this.id=new Id("");
			createdAtEpoch=0;
			lastAccessedAtEpoch=0;
		}
		
		public Builder(Visitor visitor){
			super();
			this.id=visitor.getId();
			this.cart=visitor.cart;
			this.createdAtEpoch=visitor.createdAtEpoch;
			this.lastAccessedAtEpoch=visitor.lastAccessedAtEpoch;
		}

		public Builder withSessionId(String sessionId){//SUBJECT TO CHANGE
			this.id=new Id(sessionId);
			if(this.id.isEmpty())System.err.println("warning: generated visitor object without sessionId!");
			return this;
		}

		public Builder withCart(Cart cart){
			this.cart=cart;
			return this;
		}
		
		public Builder withCreatedAtEpoch(long createdAtEpoch) {
			this.createdAtEpoch=createdAtEpoch;
			return this;
		}
		
		public Builder withLastAccessedAtEpoch(long lastAccessedAtEpoch) {
			this.lastAccessedAtEpoch=lastAccessedAtEpoch;
			return this;
		}
		
		public Builder withCreatedAtEpoch(String createdAtEpoch) {
			this.createdAtEpoch=Long.parseLong(createdAtEpoch);
			return this;
		}
		
		public Builder withLastAccessedAtEpoch(String lastAccessedAtEpoch) {
			this.lastAccessedAtEpoch=Long.parseLong(lastAccessedAtEpoch);
			return this;
		}

		public Visitor build(){
			Visitor visitor=new Visitor();
			visitor.id=this.id;
			visitor.cart=this.cart;
			visitor.createdAtEpoch=this.createdAtEpoch;
			visitor.lastAccessedAtEpoch=this.lastAccessedAtEpoch;
			return visitor;
		}

	}



	@Override
	public String toJson() {
		// TODO Auto-generated method stub		
		return "{"+Bean.jsonMapVarChar("id",this.id.toString())+","+
		Bean.jsonMapNumber("createdAtEpoch",Long.toString(createdAtEpoch))+","+
		Bean.jsonMapNumber("lastAccessedAtEpoch",Long.toString(lastAccessedAtEpoch))+
		Bean.jsonMapNumber("cart",this.cart.toJson())+","+
		"}";
	}
}
