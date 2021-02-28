package dao.beans;

public abstract class DataObjectBuilder extends IdDataObjectBuilder<String> {
	protected String emptyId() {
		return "";
	}

}
