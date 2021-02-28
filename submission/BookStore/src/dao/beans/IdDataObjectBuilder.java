package dao.beans;

public abstract class IdDataObjectBuilder<T> {
	protected T id;
	protected abstract T emptyId();
	

}