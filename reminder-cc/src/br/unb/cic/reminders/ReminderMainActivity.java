package br.unb.cic.reminders;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import br.unb.cic.reminders.view.AddReminderActivity;
import br.unb.cic.reminders.view.ReminderListFragment;
import br.unb.cic.reminders2.R;

public class ReminderMainActivity extends Activity {
	private static String TAG = "Reminder";
	private FragmentTransaction ft;
	private ReminderListFragment listReminderFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reminders_main_activity);
		createUI();
	}

	private void createUI() {
		ft = getFragmentManager().beginTransaction();
		listReminderFragment = new ReminderListFragment();
		ft.add(R.id.listReminders, listReminderFragment);
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_bar_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_addReminder:
			Intent reminderIntent = new Intent(getApplicationContext(), AddReminderActivity.class);
			startActivity(reminderIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}