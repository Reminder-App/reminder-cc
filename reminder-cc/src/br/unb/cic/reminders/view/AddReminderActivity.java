package br.unb.cic.reminders.view;

import br.unb.cic.framework.persistence.DBException;
import br.unb.cic.reminders.controller.Controller;
import br.unb.cic.reminders.model.Reminder;
//#ifdef staticCategory 
import br.unb.cic.reminders.model.Category;
import java.util.List;
//#endif 

public class AddReminderActivity extends ReminderActivity {
	@Override
	protected void initializeValues() {
	}

	@Override
	protected void persist(Reminder reminder) {
		//#ifdef staticCategory
		try {
			Category category = findCategory(reminder.getCategory());
			reminder.setCategory(category);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//#endif

		try {
			Controller.instance(getApplicationContext()).addReminder(reminder);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}

	//#ifdef staticCategory
	private Category findCategory(Category category) throws Exception {
		List<Category> categories = Controller.instance(getApplicationContext()).listCategories();
		for (Category c : categories) {
			if (c.getName().equals(category.getName()))
				return c;
		}
		return null;
	}
	//#endif
}