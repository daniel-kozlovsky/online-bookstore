package dao.accessors.queries;

public class QueryString {
	public String prefix;
	public String suffix;
	
	public QueryString(String prefix, String suffix) {
		super();
		this.prefix = prefix;
		this.suffix = suffix;
	}

	public String getPrefix() {
		return prefix;
	}


	public String getSuffix() {
		return suffix;
	}

	public String renderQueryString(String parameter) {
		return prefix+parameter+suffix;
	}

}
