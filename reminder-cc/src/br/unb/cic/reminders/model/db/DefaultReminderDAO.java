package br.unb.cic.reminders.model.db;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import br.unb.cic.framework.persistence.DBException;
import br.unb.cic.framework.persistence.DBInvalidEntityException;
import br.unb.cic.framework.persistence.GenericDAO;
import br.unb.cic.reminders.model.Reminder;
//#if staticCategory || manageCategory
import br.unb.cic.reminders.model.Category;
import android.util.Log;
//#endif 
//#ifdef priority 
import br.unb.cic.reminders.model.Priority;
//#endif 

public class DefaultReminderDAO extends GenericDAO<Reminder> implements ReminderDAO {
	public DefaultReminderDAO(Context c) {
		super(c);
	}

	public Long saveReminder(Reminder r) throws DBException {
		try {
			return persist(r);
		} catch (DBInvalidEntityException e) {
			throw new DBException();
		}
	}

	public List<Reminder> listReminders() throws DBException {
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(DBConstants.SELECT_REMINDERS, null);
			return remindersFromCursor(cursor);
		} catch (Exception e) {
			//#if staticCategory || manageCategory
			Log.e(DefaultCategoryDAO.class.getCanonicalName(), e.getLocalizedMessage());
			//#endif
			throw new DBException();
		} finally {
			db.close();
			dbHelper.close();
		}
	}

	public void updateReminder(Reminder reminder) throws DBException {
		try {
			persist(reminder);
		} catch (DBInvalidEntityException e) {
			throw new DBException();
		} finally {
			db.close();
			dbHelper.close();
		}
	}

	public void deleteReminder(Reminder reminder) throws DBException {
		try {
			db = dbHelper.getWritableDatabase();
			db.delete(DBConstants.REMINDER_TABLE, DBConstants.REMINDER_PK_COLUMN + "=" + reminder.getId(), null);
		} catch (SQLiteException e) {
			//#if staticCategory || manageCategory
			Log.e(DefaultCategoryDAO.class.getCanonicalName(), e.getLocalizedMessage());
			//#endif
			throw new DBException();
		} finally {
			db.close();
			dbHelper.close();
		}
	}

	public void persistReminder(Reminder reminder) throws DBException {
		try {
			persist(reminder);
		} catch (DBInvalidEntityException e) {
			throw new DBException();
		} finally {
			db.close();
			dbHelper.close();
		}
	}

	private Reminder cursorToReminder(Cursor cursor) throws DBException {
		Long pk = cursor.getLong(cursor.getColumnIndex(DBConstants.REMINDER_PK_COLUMN));
		String text = cursor.getString(cursor.getColumnIndex(DBConstants.REMINDER_TEXT_COLUMN));
		String details = cursor.getString(cursor.getColumnIndex(DBConstants.REMINDER_DETAILS_COLUMN));
		int done = cursor.getInt(cursor.getColumnIndex(DBConstants.REMINDER_DONE_COLUMN));
		Reminder reminder = createReminderCursor(cursor);
		reminder.setText(text);
		reminder.setDetails(details);
		reminder.setId(pk);
		reminder.setDone(done);

		//#if staticCategory || manageCategory
		Long categoryId = cursor.getLong(cursor.getColumnIndex(DBConstants.REMINDER_FK_CATEGORY_COLUMN));
		Category category = DBFactory.factory(context).createCategoryDAO().findCategoryById(categoryId);
		reminder.setCategory(category);
		//#endif

		//#ifdef priority
		int priority = cursor.getInt(cursor.getColumnIndex(DBConstants.REMINDER_PRIORITY_COLUMN));
		reminder.setPriority(Priority.fromCode(priority));
		//#endif

		return reminder;
	}

	private Reminder createReminderCursor(Cursor cursor) throws DBException {
		Reminder reminder = new Reminder();
		//#ifdef fixedDate
		String date = cursor.getString(cursor.getColumnIndex(DBConstants.REMINDER_DATE_COLUMN));
		reminder.setDate(date);
		//#endif
		//#if fixedDate || dateRepeat
		String hour = cursor.getString(cursor.getColumnIndex(DBConstants.REMINDER_HOUR_COLUMN));
		reminder.setHour(hour);
		//#endif
		//#ifdef dateRange
		String dateStart = cursor.getString(cursor.getColumnIndex(DBConstants.REMINDER_INITIAL_DATE_COLUMN));
		String hourStart = cursor.getString(cursor.getColumnIndex(DBConstants.REMINDER_INITIAL_HOUR_COLUMN));
		String dateFinal = cursor.getString(cursor.getColumnIndex(DBConstants.REMINDER_FINAL_DATE_COLUMN));
		String hourFinal = cursor.getString(cursor.getColumnIndex(DBConstants.REMINDER_FINAL_HOUR_COLUMN));
		reminder.setDateStart(dateStart);
		reminder.setHourStart(hourStart);
		reminder.setDateFinal(dateFinal);
		reminder.setHourFinal(hourFinal);
		//#endif
		//#ifdef dateRepeat
		int monday = cursor.getInt(cursor.getColumnIndex(DBConstants.REMINDER_MONDAY_COLUMN));
		int tuesday = cursor.getInt(cursor.getColumnIndex(DBConstants.REMINDER_TUESDAY_COLUMN));
		int wednesday = cursor.getInt(cursor.getColumnIndex(DBConstants.REMINDER_WEDNESDAY_COLUMN));
		int thursday = cursor.getInt(cursor.getColumnIndex(DBConstants.REMINDER_THURSDAY_COLUMN));
		int friday = cursor.getInt(cursor.getColumnIndex(DBConstants.REMINDER_FRIDAY_COLUMN));
		int saturday = cursor.getInt(cursor.getColumnIndex(DBConstants.REMINDER_SATURDAY_COLUMN));
		int sunday = cursor.getInt(cursor.getColumnIndex(DBConstants.REMINDER_SUNDAY_COLUMN));
		reminder.setMonday(monday);
		reminder.setTuesday(tuesday);
		reminder.setWednesday(wednesday);
		reminder.setThursday(thursday);
		reminder.setFriday(friday);
		reminder.setSaturday(saturday);
		reminder.setSunday(sunday);
		//#endif
		return reminder;
	}

	private List<Reminder> remindersFromCursor(Cursor cursor) throws DBException {
		List<Reminder> reminders = new ArrayList<Reminder>();
		if (cursor.moveToFirst()) {
			do {
				Reminder reminder = cursorToReminder(cursor);
				reminders.add(reminder);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return reminders;
	}

	//#if staticCategory || manageCategory
	public List<Reminder> listRemindersByCategory(Category category) throws DBException {
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(DBConstants.SELECT_REMINDERS_BY_CATEGORY,
					new String[] { category.getId().toString() });

			return remindersFromCursor(cursor);
		} catch (Exception e) {
			Log.e(DefaultCategoryDAO.class.getCanonicalName(), e.getLocalizedMessage());
			throw new DBException();
		} finally {
			db.close();
			dbHelper.close();
		}
	}
	//#endif
}