package br.unb.cic.reminders.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.unb.cic.reminders.controller.Controller;
import br.unb.cic.reminders.model.InvalidDateException;
import br.unb.cic.reminders.model.InvalidFormatException;
import br.unb.cic.reminders.model.InvalidTextException;
import br.unb.cic.reminders.model.Reminder;
import br.unb.cic.reminders2.R;

public class ReminderAddActivity extends Activity {
	private EditText edtReminder, edtDetails, edtDate, edtHour;
	private Button btnSave, btnCancel;
	private boolean editingReminder;
	private Long previewReminderId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reminder_add);
		Reminder existingReminder = getReminderFromIntent();
		if (existingReminder == null) {
			editingReminder = true;
			Reminder editReminder = getExistingReminder();
			initialize(editReminder);
		} else {
			editingReminder = false;
			initialize(existingReminder);
		}
		configureActionListener();
	}

	private void configureActionListener() {
		addListenerToBtnSave();
		addListenerToBtnCancel();
	}

	private void addListenerToBtnSave() {
		btnSave.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				try {
					Reminder reminder = createReminder();
					if (editingReminder) {
						reminder.setId(previewReminderId);
						Controller.instance(getApplicationContext()).updateReminder(reminder);
					} else {
						Controller.instance(getApplicationContext()).addReminder(reminder);
					}
					finish();
				} catch (Exception e) {
					Log.e("ReminderAddActivity", e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}

	private void addListenerToBtnCancel() {
		btnCancel.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

	private Reminder createReminder() {
		Reminder reminder = new Reminder();
		try {
			reminder = createReminderAux();
			reminder.setText(edtReminder.getText().toString());
			reminder.setDetails(edtDetails.getText().toString());
		} catch (InvalidTextException e) {
			Toast.makeText(getApplicationContext(), "Invalid text.", Toast.LENGTH_SHORT).show();
		} catch (InvalidDateException e) {
			Toast.makeText(getApplicationContext(), "Invalid date.", Toast.LENGTH_SHORT).show();
		} catch (InvalidHourException e) {
			Toast.makeText(getApplicationContext(), "Invalid time.", Toast.LENGTH_SHORT).show();
		}
		return reminder;
	}

	private Reminder createReminderAux() {
		Reminder reminder = new Reminder();
		reminder.setDate(edtDate.getText().toString());
		reminder.setHour(edtHour.getText().toString());
		return reminder;
	}

	private Reminder getExistingReminder() {
		Reminder reminder = null;
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();
		if (Intent.ACTION_SEND.equals(action) && "text/plain".equals(type)) {
			previewReminderId = intent.getLongExtra("id", 0);
			String text = intent.getStringExtra("text");
			reminder = createReminderExisting(intent);
			reminder.setText(text);
			reminder.setId(previewReminderId);
		}
		return reminder;
	}

	private Reminder createReminderExisting(Intent intent) {
		Reminder reminder = new Reminder();
		String date = intent.getStringExtra("date");
		String hour = intent.getStringExtra("hour");
		reminder.setDate(date);
		reminder.setHour(hour);
		return reminder;
	}

	private Reminder getReminderFromIntent() {
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();
		if ("br.com.positivo.reminders.ADD_REMINDER".equals(action) && "text/plain".equals(type)) {
			try {
				String text = intent.getStringExtra("text");
				String details = intent.getStringExtra("details");
				Reminder reminder = createReminderIntent(intent);
				reminder.setText(text);
				reminder.setDetails(details);
				return reminder;
			} catch (InvalidFormatException e) {
			}
		}
		return null;
	}

	private Reminder createReminderIntent(Intent intent) {
		Reminder reminder = new Reminder();
		String date = intent.getStringExtra("date");
		String hour = intent.getStringExtra("hour");
		reminder.setDate(date);
		reminder.setHour(hour);
		return reminder;
	}

	private void initialize(Reminder reminder) {
		try {
			edtReminder = (EditText) findViewById(R.id.edtReminder);
			edtDetails = (EditText) findViewById(R.id.edtDetails);
			if (reminder != null) {
				updateFieldsFromReminder(reminder);
			}
			btnSave = (Button) findViewById(R.id.btnSave);
			btnCancel = (Button) findViewById(R.id.btnCancel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateFieldsFromReminder(Reminder reminder) throws Exception {
		edtReminder.setText(reminder.getText());
		edtDetails.setText(reminder.getDetails());
		edtDate.setText(reminder.getDate());
		edtHour.setText(reminder.getHour());
	}
}