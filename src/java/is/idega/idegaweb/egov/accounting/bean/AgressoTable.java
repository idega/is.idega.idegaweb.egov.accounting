package is.idega.idegaweb.egov.accounting.bean;

public enum AgressoTable {

	AFTER_SCHOOL_CARE("AFTER_SCHOOL_CARE") {

		@Override
		public String getDBTableName() {
			return "RRVK_AGRESSO";
		}

	},

	COURSE("COURSE") {

		@Override
		public String getDBTableName() {
			return "RRVK_AGRESSO_COURSE";
		}

	};

	private String type = null;

	private AgressoTable(String type) {
		this.type = type;
	}

	public abstract String getDBTableName();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}