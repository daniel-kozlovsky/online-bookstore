package dao.accessors.mutations;

import dao.accessors.DataAccessRequest;
import dao.accessors.queries.Query;

public abstract class Mutation<T extends Mutation> extends DataAccessRequest<T> {

	public abstract String renderMutationString();
	public abstract String update();
	public abstract String insert();
}
