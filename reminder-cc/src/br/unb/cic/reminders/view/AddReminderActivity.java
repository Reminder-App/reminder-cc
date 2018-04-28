package br.unb.cic.reminders.view;

import br.unb.cic.framework.persistence.DBException;
import br.unb.cic.reminders.controller.Controller;
import br.unb.cic.reminders.model.Reminder;

public class AddReminderActivity extends ReminderActivity {
	@Override
	protected void initializeValues() {
	}

	@Override
	protected void persist(Reminder reminder) {
		try {
			Controller.instance(getApplicationContext()).addReminder(reminder);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
}