package dao.users;

public class Address {
	String number;
	String street;
	String postalCode;
	String province;
	String country;
	public static class Builder{
		private String number;
		private String street;
		private String postalCode;
		private String province;
		private String country;

		public Builder(){
		}

		public Builder withNumber(String number){
			this.number=number;
			return this;
		}

		public Builder withStreet(String street){
			this.street=street;
			return this;
		}

		public Builder withPostalCode(String postalCode){
			this.postalCode=postalCode;
			return this;
		}

		public Builder withProvince(String province){
			this.province=province;
			return this;
		}

		public Builder withCountry(String country){
			this.country=country;
			return this;
		}

		public Address build(){
			Address address=new Address();
			address.number=this.number;
			address.street=this.street;
			address.postalCode=this.postalCode;
			address.province=this.province;
			address.country=this.country;
			return address;
		}

	}
}
