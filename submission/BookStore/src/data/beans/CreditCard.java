package data.beans;

public class CreditCard implements Bean{
//	CREDIT_CARD,CREDIT_CARD_NUMBER,CREDIT_CARD_EXPIRY,CREDIT_CARD_CVV2)	
	private String creditCardType;

	private String creditCardNumber;
	private String creditCardExpiry;
	private String creditCardCVV2;
	private CreditCard() {
		
	}
	
	public String getCreditCardType() {
		return creditCardType;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public String getCreditCardExpiry() {
		return creditCardExpiry;
	}

	public String getCreditCardCVV2() {
		return creditCardCVV2;
	}

	public boolean isEmpty() {
		return  creditCardType==null || creditCardType.isEmpty() ||creditCardExpiry==null || creditCardExpiry.isEmpty()||creditCardNumber==null || creditCardNumber.isEmpty()||creditCardCVV2==null || creditCardCVV2.isEmpty();
	}
	public static class Builder {
		private String creditCardType;
		private String creditCardNumber;
		private String creditCardExpiry;
		private String creditCardCVV2;
		public Builder() {
			this.creditCardCVV2="";
			this.creditCardNumber="";
			this.creditCardExpiry="";
			this.creditCardType="";
		}
		
		public Builder withCreditCardType(String creditCardType) {
			this.creditCardType=creditCardType;
			return this;
		}
		public Builder withCreditCardNumber(String creditCardNumber ) {
			this.creditCardNumber=creditCardNumber;
			return this;
		}
		public Builder withCreditCardExpiry(String creditCardExpiry) {
			this.creditCardExpiry=creditCardExpiry;
			return this;
		}
		public Builder withCreditCardCVV2(String creditCardCVV2) {
			this.creditCardCVV2=creditCardCVV2;
			return this;
		}
		
		public Builder(CreditCard creditCard) {
			this.creditCardCVV2=creditCard.creditCardCVV2;
			this.creditCardNumber=creditCard.creditCardNumber;
			this.creditCardExpiry=creditCard.creditCardExpiry;
			this.creditCardType=creditCard.creditCardType;
		}
	}
	@Override
	public String toJson() {
		return  "{"+Bean.jsonMapVarChar("creditCardType",this.creditCardType)+","+
				Bean.jsonMapVarChar("creditCardNumber",this.creditCardNumber)+","+
				Bean.jsonMapVarChar("creditCardExpiry",this.creditCardExpiry)+","+
				Bean.jsonMapVarChar("creditCardCVV2",this.creditCardCVV2)+","+
				"}"
				;
	}
}
