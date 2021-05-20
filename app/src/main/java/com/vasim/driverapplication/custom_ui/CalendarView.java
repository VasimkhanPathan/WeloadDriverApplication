package com.vasim.driverapplication.custom_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.vasim.driverapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;


public class CalendarView extends LinearLayout
{
	// for logging
	private static final String LOGTAG = "Calendar View";

	// how many days to show, defaults to six weeks, 42 days
	private static final int DAYS_COUNT = 42;

	// default date format
	private static final String DATE_FORMAT = "MMM yyyy";

	// date format
	private String dateFormat;

	// current displayed month
	private Calendar currentDate = Calendar.getInstance();

	//event handling
	private EventHandler eventHandler = null;

	// internal components
	private LinearLayout header;
	private ImageView btnPrev;
	private ImageView btnNext;
	private TextView txtDate;
	private TextView oldCell;
	private GridView grid;


//	// seasons' rainbow
//	int[] rainbow = new int[] {
//			R.color.summer,
//			R.color.fall,
//			R.color.winter,
//			R.color.spring
//	};

	// month-season association (northern hemisphere, sorry australia :)
	int[] monthSeason = new int[] {2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};

	public CalendarView(Context context)
	{
		super(context);
	}

	public CalendarView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initControl(context, attrs);
	}

	public CalendarView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		initControl(context, attrs);
	}

	/**
	 * Load control xml layout
	 */
	private void initControl(Context context, AttributeSet attrs)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.control_calendar, this);

		loadDateFormat(attrs);
		assignUiElements();
		assignClickHandlers();

		updateCalendar();
	}

	private void loadDateFormat(AttributeSet attrs)
	{
		TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

		try
		{
			// try to load provided date format, and fallback to default otherwise
			dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
			if (dateFormat == null)
				dateFormat = DATE_FORMAT;
		}
		finally
		{
			ta.recycle();
		}
	}
	private void assignUiElements()
	{
		// layout is inflated, assign local variables to components
		header = (LinearLayout)findViewById(R.id.calendar_header);
		btnPrev = (ImageView)findViewById(R.id.calendar_prev_button);
		btnNext = (ImageView)findViewById(R.id.calendar_next_button);
		txtDate = (TextView)findViewById(R.id.calendar_date_display);
		grid = (GridView)findViewById(R.id.calendar_grid);

	}

	private void assignClickHandlers()
	{
		// add one month and refresh UI
		btnNext.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				currentDate.add(Calendar.MONTH, 1);
				updateCalendar();
			}
		});

		// subtract one month and refresh UI
		btnPrev.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				currentDate.add(Calendar.MONTH, -1);
				updateCalendar();
			}
		});

		// long-pressing a day
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> view, View cell, int position, long id)
			{
				// handle long-press
				if (eventHandler == null)
					return ;

				if(oldCell != null)
					//oldCell.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
					oldCell.setBackgroundResource(R.drawable.cricle_white);

				//((TextView)cell).setTextColor(ContextCompat.getColor(getContext(),R.color.red));
			((TextView)cell).setBackgroundResource(R.drawable.cricle);

				oldCell = (TextView) cell;

				eventHandler.onDayLongPress((Date)view.getItemAtPosition(position));
				Toast.makeText(getContext(), "click"+view.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

			}
		});
	}

	/**
	 * Display dates correctly in grid
	 */
	public void updateCalendar()
	{
		updateCalendar(null);
	}

	/**
	 * Display dates correctly in grid
	 */
	public void updateCalendar(HashSet<Date> events)
	{
		ArrayList<Date> cells = new ArrayList<>();
		Calendar calendar = (Calendar)currentDate.clone();

		// determine the cell for current month's beginning
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Calendar calendar1 = (Calendar) calendar.clone();

		int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		// move calendar backwards to the beginning of the week
		calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

		// fill cells
		while (cells.size() < DAYS_COUNT)
		{
			cells.add(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}

		// update grid
		grid.setAdapter(new CalendarAdapter(getContext(), eventHandler,cells, events,calendar1.get(Calendar.MONTH),calendar1.get(Calendar.YEAR)));

		// update title
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		txtDate.setText(sdf.format(currentDate.getTime()));

		// set header color according to current season
		int month = currentDate.get(Calendar.MONTH);
//		int season = monthSeason[month];
//		int color = rainbow[season];

		//header.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.splash_color));
	}


	private class CalendarAdapter extends ArrayAdapter<Date>
	{
		// days with events
		private HashSet<Date> eventDays;

		// for view inflation
		private LayoutInflater inflater;
		private int month ,year;
		private EventHandler eventHandler;

		public CalendarAdapter(Context context,EventHandler eventHandler, ArrayList<Date> days, HashSet<Date> eventDays,int month,int year)
		{
			super(context, R.layout.control_calendar_day, days);
			this.eventHandler = eventHandler;
			this.eventDays = eventDays;
			this.month = month;
			this.year = year;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View view, ViewGroup parent)
		{
			// day in question
			Date date = getItem(position);
			int day = date.getDate();
			int month = date.getMonth();
			int year = date.getYear();

			// today
			Date today = new Date();

			// inflate item if it does not exist yet
			if (view == null)
				view = inflater.inflate(R.layout.control_calendar_day, parent, false);

			// if this day has an event, specify event image
			view.setBackgroundResource(0);
			if (eventDays != null)
			{
				for (Date eventDate : eventDays)
				{
					if (eventDate.getDate() == day &&
							eventDate.getMonth() == month &&
							eventDate.getYear() == year)
					{
						// mark this day for event
						view.setBackgroundResource(R.drawable.reminder);
						break;
					}
				}
			}


			// clear styling
			((TextView)view).setTypeface(null, Typeface.NORMAL);
			((TextView)view).setTextColor(Color.BLACK);

			if (month != this.month && year != this.year)
			{
				// if this day is outside current month, grey it out
				((TextView)view).setTextColor(getResources().getColor(R.color.greyed_out));
			} else if (day == today.getDate())
			{
				// if it is today, set it to blue/bold
				((TextView)view).setTypeface(null, Typeface.BOLD);
				((TextView)view).setTextColor(getResources().getColor(R.color.today));
			}else {
					((TextView)view).setTextColor(Color.BLACK);
				}

			// set text
			((TextView)view).setText(String.valueOf(date.getDate()));

			return view;
		}
	}

	/**
	 * Assign event handler to be passed needed events
	 */
	public void setEventHandler(EventHandler eventHandler)
	{
		this.eventHandler = eventHandler;
	}

	/**
	 * This interface defines what events to be reported to
	 * the outside world
	 */
	public interface EventHandler
	{
		void onDayLongPress(Date date);
	}
}
