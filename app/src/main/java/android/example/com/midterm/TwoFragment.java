package android.example.com.midterm;

import android.content.Intent;
import android.example.com.midterm.PhotoDiary.*;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TwoFragment extends Fragment {
	private TextView txtMonth;
	private TextView txtYear;

	private ArrayList<TextView> arDay = new ArrayList<TextView>();

	DBDiary db;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.photo_main, container, false);

		// Init database instance
		db = new DBDiary(getActivity().getApplicationContext());

		// Init text view
		txtMonth = (TextView) v.findViewById(R.id.txtMonth);
		txtYear = (TextView) v.findViewById(R.id.txtYear);

		// Add list of textview to arraylist
		for (int i = 0; i < 35; i++) {
			TextView txtDay = (TextView) v.findViewById(getResources().getIdentifier("txt" + (i + 1), "id", getActivity().getPackageName()));
			arDay.add(txtDay);
		}

		return v;
	}

	@Override
    public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// Get current day
		long time = System.currentTimeMillis();

		// Set text
		txtYear.setText(DateFormat.format("yyyy", time));
		txtMonth.setText(DateFormat.format("MM", time));

		// First day of current month
		long firstdayTime = getFirstDateOfCurrentMonth().getTime();
		boolean addDate = true;
		// Insert current month's date to database if it's not existed
		do {

			if (Integer.parseInt(DateFormat.format("dd", firstdayTime).toString()) < getLastDateOfCurrentMonth()) {

				if (db.getOneRow(DateFormat.format("yyyy/MM/dd", firstdayTime).toString()) == null) {
					db.INSERT(DateFormat.format("yyyy/MM/dd", firstdayTime).toString(), new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date(firstdayTime)), "", "");
				}

				firstdayTime += 1000 * 60 * 60 * 24;

			} else if (Integer.parseInt(DateFormat.format("dd", firstdayTime).toString()) == getLastDateOfCurrentMonth()) {
				if (db.getOneRow(DateFormat.format("yyyy/MM/dd", firstdayTime).toString()) == null) {
					db.INSERT(DateFormat.format("yyyy/MM/dd", firstdayTime).toString(), new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date(firstdayTime)), "", "");
				}
				addDate = false;
			}

		} while (addDate);

		// Query database to get data of current month
		ArrayList<Diary> arDate = db.query(DateFormat.format("yyyy/MM", time).toString());

		int position = 0;
		for (int i = 0; i < arDay.size(); i++) {

			// reset textview
			arDay.get(i).setBackgroundColor(Color.parseColor("#2a3036"));
			arDay.get(i).setText("");
			arDay.get(i).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
				}
			});

			String tag = (String) arDay.get(i).getTag();

			if (position == 0) {
				// Check to set first date in right position
				if (position < arDate.size()) {
					if (tag.equals(arDate.get(position).getWeekdate().toLowerCase(Locale.ENGLISH))) {
						arDay.get(i).setBackgroundColor(Color.parseColor("#181c1f"));
						arDay.get(i).setText(Integer.parseInt(arDate.get(0).getDate().split("/")[2]) + "");

						if (!arDate.get(position).getContent().equals("") || !arDate.get(position).getImage().equals("")) {
							arDay.get(i).setTextColor(Color.parseColor("#FF0000"));
						} else {
							arDay.get(i).setTextColor(Color.parseColor("#FFFFFF"));
						}
						if (Integer.parseInt(arDate.get(position).getDate().split("/")[2]) == Integer.parseInt(DateFormat.format("dd", time).toString())) {
							arDay.get(i).setTextColor(Color.parseColor("#FFFF00"));
						}

						final String date = arDate.get(position).getDate();
						arDay.get(i).setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								Intent intent = new Intent(getActivity(), SetupActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								intent.putExtra("Date", date);
								startActivity(intent);

							}
						});
						position++;
					}

				}

			} else {
				// Set next date in right position
				if (position < arDate.size()) {
					arDay.get(i).setBackgroundColor(Color.parseColor("#181c1f"));
					arDay.get(i).setText(Integer.parseInt(arDate.get(position).getDate().split("/")[2]) + "");

					if (!arDate.get(position).getContent().equals("") || !arDate.get(position).getImage().equals("")) {
						arDay.get(i).setTextColor(Color.parseColor("#FF0000"));
					} else {
						arDay.get(i).setTextColor(Color.parseColor("#FFFFFF"));
					}

					if (Integer.parseInt(arDate.get(position).getDate().split("/")[2]) == Integer.parseInt(DateFormat.format("dd", time).toString())) {
						arDay.get(i).setTextColor(Color.parseColor("#FFFF00"));
					}
					final String date = arDate.get(position).getDate();
					arDay.get(i).setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							Intent intent = new Intent(getActivity(), SetupActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent.putExtra("Date", date);
							startActivity(intent);

						}
					});
					position++;
				}

			}

		}

	}

	// Get first date of current month
	private Date getFirstDateOfCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	// Get last date of current month
	private int getLastDateOfCurrentMonth() {
		return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
	}
}

