//#if reminder && manageReminder
package br.unb.cic.reminders.model.db;

import java.util.List;
import br.unb.cic.framework.persistence.DBException;
import br.unb.cic.reminders.model.Reminder;

public interface ReminderDAO {
	//#ifdef create
	public Long saveReminder(Reminder r) throws DBException;
	//#endif

	//#ifdef view
	public List<Reminder> listReminders() throws DBException;
	//#endif

	//#ifdef edit
	public void updateReminder(Reminder reminder) throws DBException;
	//#endif

	//#ifdef delete
	public void deleteReminder(Reminder reminder) throws DBException;
	//#endif

	public void persistReminder(Reminder reminder) throws DBException;
}
//#endif
