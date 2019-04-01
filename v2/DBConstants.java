//#if reminder && manageReminder
package br.unb.cic.reminders.model.db;

public class DBConstants {
	public static String DROP_TABLE_STATEMENTS[] = { "DROP TABLE IF EXISTS REMINDER"
			//#if staticCategory || manageCategory
			, "DROP TABLE IF EXISTS CATEGORY"
			//#endif
	};
	public static String CREATE_TABLE_STATEMENTS[] = { "CREATE TABLE REMINDER ( "
			//#if staticCategory || manageCategory
			+ "FK_CATEGORY INTEGER NOT NULL REFERENCES CATEGORY ON DELETE CASCADE,"
			//#endif
			//#ifdef fixedDate
			+ "DATE CHAR(10) NULL,"
			+ "HOUR CHAR(5) NULL,"
			//#endif
			//#ifdef done
			+ "DONE INTEGER NOT NULL,"
			//#endif
			+ "PK INTEGER PRIMARY KEY AUTOINCREMENT, " + "TEXT VARCHAR(50) NOT NULL," + "DETAILS VARCHAR(50) NULL,"
			+ ");"
			//#if staticCategory || manageCategory
			, "CREATE TABLE CATEGORY(" + "PK INTEGER PRIMARY KEY AUTOINCREMENT, " + "NAME VARCHAR(50) NOT NULL, "
					+ "LOCKED INT NOT NULL);"
			//#endif
	};
	public static final String SELECT_REMINDERS = "SELECT * FROM REMINDER";
	public static String REMINDER_TABLE = "REMINDER";
	public static String REMINDER_PK_COLUMN = "PK";
	public static String REMINDER_TEXT_COLUMN = "TEXT";
	public static String REMINDER_DETAILS_COLUMN = "DETAILS";
	//#ifdef fixedDate
	public static String REMINDER_DATE_COLUMN = "DATE";
	public static String REMINDER_HOUR_COLUMN = "HOUR";
	//#endif
	//#ifdef done
	public static String REMINDER_DONE_COLUMN = "DONE";
	//#endif

	//#if staticCategory || manageCategory
	public static final String SELECT_CATEGORIES = "SELECT PK, NAME FROM CATEGORY";
	public static final String SELECT_CATEGORY_BY_NAME = "SELECT PK, NAME FROM CATEGORY WHERE NAME = ?";
	public static final String SELECT_CATEGORY_BY_ID = "SELECT PK, NAME FROM CATEGORY WHERE PK = ?";
	public static final String SELECT_REMINDERS_BY_CATEGORY = "SELECT * FROM REMINDER WHERE FK_CATEGORY = ?";
	public static String DELETE_CATEGORIES = "DELETE FROM CATEGORY WHERE PK = ?";
	public static final String PREDEFINED_CATEGORIES[] = { "INSERT INTO CATEGORY VALUES (NULL,'College',1);",
			"INSERT INTO CATEGORY VALUES (NULL,'Job',1);", "INSERT INTO CATEGORY VALUES (NULL,'Personal',1);" };
	public static String CATEGORY_TABLE = "CATEGORY";
	public static String CATEGORY_PK_COLUMN = "PK";
	public static String CATEGORY_NAME_COLUMN = "NAME";
	public static String CATEGORY_LOCKED_COLUMN = "LOCKED";
	public static String REMINDER_FK_CATEGORY_COLUMN = "FK_CATEGORY";
	//#endif
}
//#endif