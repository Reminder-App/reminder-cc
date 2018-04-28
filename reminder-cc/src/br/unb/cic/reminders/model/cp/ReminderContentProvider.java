package br.unb.cic.reminders.model.cp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import br.unb.cic.framework.persistence.DBException;
import br.unb.cic.reminders.model.Reminder;
import br.unb.cic.reminders.model.db.DBConstants;
import br.unb.cic.reminders.model.db.DefaultDBFactory;
import br.unb.cic.reminders.model.db.ReminderDAO;

public class ReminderContentProvider extends ContentProvider {
	private static final int REMINDERS = 10;
	private static final String SECURITY_EXCEPTION = "You are not allowed to call this method";
	private static final String AUTHORITY = "br.com.positivo.reminders.contentprovider";
	private static final String BASE_PATH = "reminders";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

	public static final String text() {
		return DBConstants.REMINDER_TEXT_COLUMN;
	}

	public static final String date() {
		return DBConstants.REMINDER_DATE_COLUMN;
	}

	public static final String hour() {
		return DBConstants.REMINDER_HOUR_COLUMN;
	}

	private ReminderDAO rdao;
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH, REMINDERS);
	}

	@Override
	public boolean onCreate() {
		rdao = DefaultDBFactory.factory(getContext()).createReminderDAO();
		return false;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		try {
			Reminder reminder = createReminderInsert(values);
			reminder.setText(values.getAsString(text()));
			Long id = rdao.saveReminder(reminder);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH + "/" + id);
		} catch (DBException e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}

	private Reminder createReminderInsert(ContentValues values) throws DBException {
		Reminder reminder = new Reminder();
		reminder.setDate(values.getAsString(date()));
		reminder.setHour(values.getAsString(hour()));
		return reminder;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3, String arg4) {
		return null;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		throw new SecurityException(SECURITY_EXCEPTION);
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		throw new SecurityException(SECURITY_EXCEPTION);
	}
}