package com.example.seradmin.calendario;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seradmin.R;

import org.joda.time.DateTime;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class MonthFragment extends Fragment{

    private ImageView right, left;
    private TextView mes, dayTV;
    private final int DAYS_CNT = 42;
    private final String DATE_PATTERN = "ddMMYYYY";
    private final String YEAR_PATTERN = "YYYY";
    private final String today = new DateTime().toString(DATE_PATTERN);
    private DateTime targetDate = new DateTime();
    private Resources res;
    private String packageName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        mes = view.findViewById(R.id.mes);
        right = view.findViewById(R.id.right);
        left = view.findViewById(R.id.left);

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNextMonth(view);
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrevMonth(view);
            }
        });

        packageName = getActivity().getPackageName();

        res = view.getResources();
        for (int i = 0; i < DAYS_CNT; i++) {
            dayTV = view.findViewById(res.getIdentifier("day_" + i, "id", packageName));
        }
        updateCalendar(targetDate, view);

        return view;
    }

    public void updateCalendar(DateTime targetDate, View view) {
        this.targetDate = targetDate;
        getMonthName();
        getDays(targetDate, view);
    }

    public void getPrevMonth(View view) {
        updateCalendar(targetDate.minusMonths(1), view);
    }

    public void getNextMonth(View view) {
        updateCalendar(targetDate.plusMonths(1), view);
    }

    private void getDays(DateTime targetDate, View view) {
        final List<Day> days = new ArrayList<>(DAYS_CNT);

        final int currMonthDays = targetDate.dayOfMonth().getMaximumValue();
        final int firstDayIndex = targetDate.withDayOfMonth(1).getDayOfWeek() - 1;
        final int prevMonthDays = targetDate.minusMonths(1).dayOfMonth().getMaximumValue();

        boolean isThisMonth = false;
        boolean isToday;
        int value = prevMonthDays - firstDayIndex + 1;

        for (int i = 0; i < DAYS_CNT; i++) {
            if (i < firstDayIndex) {
                isThisMonth = false;
            } else if (i == firstDayIndex) {
                value = 1;
                isThisMonth = true;
            } else if (value == currMonthDays + 1) {
                value = 1;
                isThisMonth = false;
            }

            isToday = isThisMonth && isToday(targetDate, value);

            final Day day = new Day(value, isThisMonth, isToday);
            days.add(day);
            value++;
        }

        updateCalendar(getMonthName(), days, view);
    }

    public void updateCalendar(String month, List<Day> days, View view) {
        updateMonth(month);
        updateDays(days, view);
    }

    private void updateMonth(String month) {
        mes.setText(month);
        mes.setTextColor(Color.WHITE);
    }

    private void updateDays(List<Day> days, View view) {
        final int len = days.size();

        for (int i = 0; i < len; i++) {
            final Day day = days.get(i);
             dayTV = view.findViewById(res.getIdentifier("day_" + i, "id", packageName));
            int curTextColor = Color.GRAY;

            if (day.getIsThisMonth()) {
                curTextColor = Color.WHITE;
            }

            if (day.getIsToday()) {
                dayTV.setTextAppearance(R.style.hoy);
            }

            dayTV.setText(String.valueOf(day.getValue()));
            dayTV.setTextColor(curTextColor);

        }
    }

    private boolean isToday(DateTime targetDate, int curDayInMonth) {
        return targetDate.withDayOfMonth(curDayInMonth).toString(DATE_PATTERN).equals(today);
    }

    private String getMonthName() {
        final String[] meses =getResources().getStringArray(R.array.months);
        String mes = (meses[targetDate.getMonthOfYear() - 1]);
        final String targetYear = targetDate.toString(YEAR_PATTERN);
        if (!targetYear.equals(new DateTime().toString(YEAR_PATTERN))) {
            mes += " " + targetYear;
        }
        return mes;
    }

    public DateTime getTargetDate() {
        return targetDate;
    }
}