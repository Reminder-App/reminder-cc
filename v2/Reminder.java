//#if reminder && manageReminder
package br.unb.cic.reminders.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import util.Patterns;
import br.unb.cic.framework.persistence.DBTypes;
import br.unb.cic.framework.persistence.annotations.Column;
import br.unb.cic.framework.persistence.annotations.Entity;
//#if staticCategory || manageCategory
import br.unb.cic.framework.persistence.annotations.ForeignKey;
//#endif
import br.unb.cic.reminders.view.InvalidHourException;

@Entity(table = "REMINDER")
public class Reminder {
	@Column(column = "PK", primaryKey = true, type = DBTypes.LONG)
	private Long id;
	@Column(column = "TEXT", type = DBTypes.TEXT)
	private String text;
	@Column(column = "DETAILS", type = DBTypes.TEXT)
	private String details;
	//#ifdef fixedDate
	@Column(column = "DATE", type = DBTypes.TEXT)
	private String date;
	@Column(column = "HOUR", type = DBTypes.TEXT)
	private String hour;
	//#endif
	//#ifdef done
	@Column(column = "DONE", type = DBTypes.INT)
	private boolean done;
	//#endif

	//#if staticCategory || manageCategory
	@Column(column = "FK_CATEGORY", type = DBTypes.LONG)
	@ForeignKey(mappedBy = "id")
	private Category category;
	//#endif

	public Reminder() {
	}

	public Reminder(Long id, String text) {
		this.id = id;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text == null || text.trim().equals("")) {
			throw new InvalidTextException(text);
		}
		this.text = text;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		if (details == null || details.trim().equals("")) {
			this.details = null;
		} else {
			this.details = details;
		}
	}

	//#ifdef fixedDate
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		if (!(date == null || date.equals("")) && !checkFormat(date, Patterns.DATE_PATTERN)) {
			throw new InvalidDateException(date);
		}
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		if (!(hour == null || hour.equals("")) && !checkFormat(hour, Patterns.HOUR_PATTERN)) {
			throw new InvalidHourException(hour);
		}
		this.hour = hour;
	}

	private boolean checkFormat(String date, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(date);
		return m.matches();
	}
	//#endif

	//#if staticCategory || manageCategory
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	//#endif

	public boolean isValid() {
		return (text != null
		//#ifdef fixedDate
				&& date != null && hour != null
		//#endif
		//#if staticCategory || manageCategory
				&& category != null
		//#endif
		);
	}

	//#ifdef done
	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public int getDone() {
		return done ? 1 : 0;
	}

	public void setDone(int done) {
		this.done = (done == 0 ? false : true);
	}
	//#endif
}
//#endif