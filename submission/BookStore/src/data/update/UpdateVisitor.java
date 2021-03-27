package data.update;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import data.schema.UserTypes;

public class UpdateVisitor extends DataUpdate{
	public void executeInsertNewVisitor(HttpServletRequest request) {
		if(request==null) return;
		String id=request.getSession().getId();
		String epoch =Long.toString(Instant.now().getEpochSecond());
		String userTablesUpdate = "INSERT INTO SITE_USER (ID,USER_TYPE) VALUES ('"+id+"','"+UserTypes.VISITOR+"')";
		sendUpdateToDatabase(userTablesUpdate);
		String update ="INSERT INTO VISITOR (ID,USER_TYPE,CREATED_AT_EPOCH) VALUES ('"+
				"('"+id+"','"+UserTypes.VISITOR+"',"+epoch+")";
		sendUpdateToDatabase(update);
	}
}
