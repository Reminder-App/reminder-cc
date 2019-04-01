//#if reminder && gui
package br.unb.cic.reminders.view;

import br.unb.cic.reminders.controller.ReminderFilter;

public interface FiltersListChangeListener {
	public void onSelectedFilterChanged(ReminderFilter filter);
}
//#endif
