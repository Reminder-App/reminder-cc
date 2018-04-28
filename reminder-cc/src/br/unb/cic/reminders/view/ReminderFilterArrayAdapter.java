package br.unb.cic.reminders.view;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.unb.cic.reminders.controller.ReminderFilter;
import br.unb.cic.reminders2.R;

public class ReminderFilterArrayAdapter extends ArrayAdapter<ReminderFilter> {
	public ReminderFilterArrayAdapter(Context context, List<ReminderFilter> objects) {
		super(context, R.layout.reminder_row, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout filterRow;
		if (convertView == null) {
			filterRow = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi;
			vi = (LayoutInflater) getContext().getSystemService(inflater);
			vi.inflate(R.layout.reminder_row, filterRow, true);
		} else {
			filterRow = (LinearLayout) convertView;
		}
		TextView tvReminder = (TextView) filterRow.findViewById(R.id.txtReminder);
		tvReminder.setText(getItem(position).getName());
		return filterRow;
	}
}