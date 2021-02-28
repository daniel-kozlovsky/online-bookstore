package dao.beans;

public abstract class IdObject <T> {
	protected T id;
	
	protected T getId() {
		return id;
	}
}
