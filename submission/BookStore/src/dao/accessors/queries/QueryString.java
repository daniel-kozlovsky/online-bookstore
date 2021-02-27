package dao.accessors.queries;

public class QueryString {
	public String prefix;
	public String suffix;
	public String type;
	
	public QueryString(String prefix, String suffix,String type) {
		super();
		this.prefix = prefix;
		this.suffix = suffix;
		this.type=type;
	}

	public String getPrefix() {
		return prefix;
	}


	public String getSuffix() {
		return suffix;
	}


	public String getType() {
		return type;
	}
	
	public String renderQueryString(String parameter) {
		return prefix+parameter+suffix;
	}

}
