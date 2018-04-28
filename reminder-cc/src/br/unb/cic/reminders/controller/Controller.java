package br.unb.cic.reminders.controller;

import java.util.List;
import android.content.Context;
import br.unb.cic.framework.persistence.DBException;
import br.unb.cic.reminders.model.Reminder;
import br.unb.cic.reminders.model.db.DBFactory;

public class Controller {
	private Context context;
	private static Controller instance;

	private Controller(Context c) {
		this.context = c;
	}

	public static final Controller instance(Context c) {
		if (instance == null) {
			instance = new Controller(c);
		}
		return instance;
	}

	public List<Reminder> listReminders() throws Exception {
		try {
			return DBFactory.factory(context).createReminderDAO().listReminders();
		} catch (DBException e) {
			throw e;
		}
	}

	public void addReminder(Reminder reminder) throws DBException {
		try {
			DBFactory.factory(context).createReminderDAO().saveReminder(reminder);
		} catch (DBException e) {
			throw e;
		}
	}

	public void updateReminder(Reminder reminder) throws DBException {
		try {
			DBFactory.factory(context).createReminderDAO().updateReminder(reminder);
		} catch (DBException e) {
			throw e;
		}
	}

	public void deleteReminder(Reminder reminder) throws DBException {
		try {
			DBFactory.factory(context).createReminderDAO().deleteReminder(reminder);
		} catch (DBException e) {
			throw e;
		}
	}

	public void persistReminder(Reminder reminder) throws DBException {
		try {
			DBFactory.factory(context).createReminderDAO().persistReminder(reminder);
		} catch (DBException e) {
			throw e;
		}
	}

	private void notImplementedYet() {
		throw new RuntimeException("not implemented yet");
	}
}