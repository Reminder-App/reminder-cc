package br.unb.cic.reminders.controller;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import br.unb.cic.reminders.model.Reminder;

public abstract class ReminderFilter {
	private List<Reminder> reminders;
	private Context context;

	public ReminderFilter(Context context) {
		this.context = context;
	}

	public List<Reminder> getReminderList() {
		updateReminders();
		return reminders;
	}

	public int getNumReminders() {
		updateReminders();
		return reminders.size();
	}

	private void updateReminders() {
		reminders = new ArrayList<Reminder>();
		List<Reminder> allReminders = null;
		try {
			allReminders = Controller.instance(context).listReminders();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Reminder r : allReminders) {
			if (selectReminder(r)) {
				reminders.add(r);
			}
		}
	}

	abstract protected boolean selectReminder(Reminder r);

	abstract public String getName();
}