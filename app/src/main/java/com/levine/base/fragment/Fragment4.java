package com.levine.base.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.levine.base.DateUtils;
import com.levine.base.R;
import com.levine.base.data.bean.CalendarBean;
import com.levine.utils.app.fragment.FragmentFactory;
import com.levine.utils.app.fragment.TargetFragmentTag;
import com.levine.utils.app.view.GridRecyclerView;
import com.levine.utils.app.view.adapter.BaseRecyclerViewAdapter;
import com.levine.utils.app.view.adapter.BaseViewHolder;
import com.levine.utils.base.LevineAnnotationUtils;
import com.levine.utils.base.LevineBindView;
import com.levine.utils.base.LevineOnClick;
import com.levine.utils.base.LogUtils;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import static com.levine.base.DateUtils.getDaysOfMonth;

@TargetFragmentTag(FragmentTag.FRAGMENT4)
public class Fragment4 extends Fragment implements View.OnClickListener, BaseRecyclerViewAdapter.OnBaseItemClickListener {
    @LevineBindView(R.id.mWeekDaysGRV)
    private GridRecyclerView mWeekDaysGRV;
    @LevineBindView(R.id.mMonthDaysGRV)
    private GridRecyclerView mMonthDaysGRV;

    @LevineOnClick
    @LevineBindView(R.id.mAttendanceDateChangeLastIV)
    private ImageView mAttendanceDateChangeLastIV;

    @LevineOnClick
    @LevineBindView(R.id.mAttendanceDateChangeNextIV)
    private ImageView mAttendanceDateChangeNextIV;

    @LevineOnClick
    @LevineBindView(R.id.mAttendanceDateChangeDisplayTV)
    private TextView mAttendanceDateChangeDisplayTV;

    @LevineBindView(R.id.mAttendanceDateChangeLL)
    private LinearLayout mAttendanceDateChangeLL;


    private List<String> defaultWeekDays;
    private List<CalendarBean> defaultMonthDays;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String currentTime = "";

    FragmentFactory mFactory;
    BaseRecyclerViewAdapter monthDaysAdapter;
    int currentWeekday = 1;
    int currentSelectedPos = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4, container, false);
        LevineAnnotationUtils.bind(this, view);
        mFactory = FragmentFactory.getInstance();
        init();
        return view;
    }

    private void init() {

        Date date = new Date();
        currentWeekday = DateUtils.getWeekDayByDay(date);
        LogUtils.e("currentWeekday:" + currentWeekday);
        defaultWeekDays = getDefaultWeekDays();

        BaseRecyclerViewAdapter weekDaysAdapter = new BaseRecyclerViewAdapter<String>(defaultWeekDays, getActivity(), R.layout.week_day_item_view) {
            @Override
            public void convert(BaseViewHolder holder, String itemData) {
                holder.setText(R.id.mWeekDayItemTV, itemData);
                if (defaultWeekDays.get(currentWeekday - 1).equals(itemData)) {
                    holder.setTextColor(R.id.mWeekDayItemTV, Color.BLACK);
                } else {
                    holder.setTextColor(R.id.mWeekDayItemTV, Color.GRAY);
                }
            }
        };
        mWeekDaysGRV.setAdapter(weekDaysAdapter);

        //获取默认选中的日期的年月日星期的值，并赋值

        currentTime = sdf.format(date);
        int year = Integer.valueOf(currentTime.split("-")[0]);
        int month = Integer.valueOf(currentTime.split("-")[1]);
        int day = Integer.valueOf(currentTime.split("-")[2]);
        defaultMonthDays = getCurrentMonthDaysData(year, month);
        updateDisplayText(year,month);
        monthDaysAdapter = new BaseRecyclerViewAdapter<CalendarBean>(defaultMonthDays, getActivity(), R.layout.month_day_item_view) {
            @Override
            public void convert(BaseViewHolder holder, CalendarBean itemData) {
                holder.setText(R.id.mMonthDayItemTV, itemData.getNumber());
                if (!itemData.isDayOfMonth()) {
                    holder.setTextColor(R.id.mMonthDayItemTV, Color.LTGRAY);
                } else {
                    holder.setTextColor(R.id.mMonthDayItemTV, Color.BLACK);
                }
                if (!itemData.isAttendanceAM() && !itemData.isAttendancePM()) {
                    //若其中上下班都没打卡，则不显示显示打卡状态栏
                    holder.setViewVisibility(R.id.mAttendanceStatusLL, View.INVISIBLE);
                } else {
                    //若其中上下班至少有一个已经打卡了，则显示打卡状态栏
                    holder.setViewVisibility(R.id.mAttendanceStatusLL, View.VISIBLE);
                    if (itemData.isAttendanceAM()) {
                        holder.setImageFromRes(R.id.mAttendanceStatusAMIV, R.drawable.green_circle_view);
                    } else {
                        holder.setImageFromRes(R.id.mAttendanceStatusAMIV, R.drawable.gray_rectangle_view);
                    }
                    if (itemData.isAttendancePM()) {
                        holder.setImageFromRes(R.id.mAttendanceStatusPMIV, R.drawable.orange_circle_view);
                    } else {
                        holder.setImageFromRes(R.id.mAttendanceStatusPMIV, R.drawable.gray_rectangle_view);
                    }
                }
                TextView textView = holder.getViewAtId(R.id.mMonthDayItemTV);

                if (itemData.isToday()) {
                    //如果日期是今天
                    textView.setBackgroundResource(R.drawable.border_circle_view);
                }
                if (itemData.isSelected()) {
                    //被选中
                    textView.setBackgroundResource(R.drawable.solid_circle_view);
                    textView.setTextColor(Color.WHITE);
                } else {
                    if (!itemData.isToday()) {
                        textView.setBackground(null);
                    } else {

                        //如果日期是今天
                        textView.setBackgroundResource(R.drawable.border_circle_view);
                    }
                    if (itemData.isDayOfMonth()) {
                        textView.setTextColor(Color.BLACK);
                    } else {
                        textView.setTextColor(Color.LTGRAY);
                    }
                }


            }
        };
        mMonthDaysGRV.setAdapter(monthDaysAdapter);
        monthDaysAdapter.setOnItemClickListener(this);

    }


    private List<CalendarBean> getCurrentMonthDaysData(int year, int month) {
        List<CalendarBean> list = new ArrayList<>();
        int count = getDaysOfMonth(year, month);
        LogUtils.e("current date:"+year+","+month);
        //添加上个月的部分数据
        list.addAll(getNeededDaysOfLastMonth(year, month));
        for (int i = 1; i <= count; i++) {
            CalendarBean calendarBean = new CalendarBean();
            calendarBean.setDayOfMonth(true);//是当前月的数据
            calendarBean.setNumber(String.valueOf(i));
            calendarBean.setToday(DateUtils.isToday(year, month, i));
            calendarBean.setDate(DateUtils.getDateByCalendar(year, month, i));
            calendarBean.setAttendancePM(false);
            calendarBean.setAttendanceAM(false);
            if (calendarBean.isToday()) {
                //默认选中今天的日期
                calendarBean.setSelected(true);
                currentSelectedPos = list.size();
            }
            list.add(calendarBean);
        }
        //添加下个月的部分数据
        list.addAll(getNeededDaysOfNextMonth(year, month));
        list.get(0).setAttendanceAM(true);
        list.get(1).setAttendanceAM(true);
        list.get(1).setAttendancePM(true);
        return list;

    }

    /**
     * 获取上个月剩余几天的数据
     *
     * @param year  当前的年份
     * @param month 当前的月份
     * @return
     */
    private List<CalendarBean> getNeededDaysOfLastMonth(int year, int month) {
        int d = DateUtils.getWeekDayOfFirstDayInMonth(year, month);
        int dw = d - 1;
        List<CalendarBean> list = new ArrayList<>();
        month = month - 1;//上一个月
        if (month == 0) {
            year = year - 1;
            month = 12;
        }
        int daysOfLastMonth = getDaysOfMonth(year, month);
        for (int i = 1; i <= dw; i++) {
            int day = daysOfLastMonth - dw + i;
            CalendarBean calendarBean = new CalendarBean();
            calendarBean.setDate(DateUtils.getDateByCalendar(year, month, i));
            calendarBean.setDayOfMonth(false);
            calendarBean.setToday(false);
            calendarBean.setNumber(String.valueOf(day));
            calendarBean.setAttendanceAM(false);
            calendarBean.setAttendancePM(false);
            list.add(calendarBean);
        }

        return list;
    }

    /**
     * 获取下个月最初几天的数据
     *
     * @param year  当前的年份
     * @param month 当前的月份
     * @return
     */
    private List<CalendarBean> getNeededDaysOfNextMonth(int year, int month) {
        //获取某年某月的最后一天是星期几
        int d = DateUtils.getWeekDayOfLastDayInMonth(year, month);
        int dw = 7 - d;
        List<CalendarBean> list = new ArrayList<>();
        month = month + 1;//下一个月9
        if (month == 13) {
            year = year + 1;
            month = 1;
        }
        int index = 0;
        for (int i = 1; i <= dw; i++) {
            int day = index + i;
            CalendarBean calendarBean = new CalendarBean();

            calendarBean.setDate(DateUtils.getDateByCalendar(year, month, i));
            calendarBean.setDayOfMonth(false);
            calendarBean.setToday(false);
            calendarBean.setNumber(String.valueOf(day));
            calendarBean.setAttendanceAM(false);
            calendarBean.setAttendancePM(false);
            list.add(calendarBean);
        }

        return list;
    }

    private List<String> getDefaultWeekDays() {
        List<String> list = new ArrayList<>();
        list.add("一");
        list.add("二");
        list.add("三");
        list.add("四");
        list.add("五");
        list.add("六");
        list.add("日");
        return list;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mAttendanceDateChangeLastIV:
                fillByLastMonthData();
                break;
            case R.id.mAttendanceDateChangeNextIV:
                fillByNextMonthData();
                break;

        }


    }

    private void fillByNextMonthData(){
        int year = Integer.valueOf(currentTime.split("-")[0]);
        int month = Integer.valueOf(currentTime.split("-")[1]);
        int day = Integer.valueOf(currentTime.split("-")[2]);
        List<CalendarBean> currentMonthDaysData = null;
        month = month + 1;
        if (month == 13) {
            year++;
            month = 1;
        }
        currentMonthDaysData = getCurrentMonthDaysData(year, month);
        defaultMonthDays.clear();
        defaultMonthDays.addAll(currentMonthDaysData);
        monthDaysAdapter.notifyDataSetChanged();
        //更改当前的date
        updateDisplayText(year, month);
        currentTime=sdf.format(DateUtils.getDateByCalendar(year,month,day));
        LogUtils.e("next date:"+currentTime);
    }
    private void fillByLastMonthData() {
        int year = Integer.valueOf(currentTime.split("-")[0]);
        int month = Integer.valueOf(currentTime.split("-")[1]);
        int day = Integer.valueOf(currentTime.split("-")[2]);
        List<CalendarBean> currentMonthDaysData = null;
        month = month - 1;
        if (month == 0) {
            year--;
            month = 12;
        }
        currentMonthDaysData = getCurrentMonthDaysData(year, month);
        defaultMonthDays.clear();
        defaultMonthDays.addAll(currentMonthDaysData);
        monthDaysAdapter.notifyDataSetChanged();
        //更改当前的date
        updateDisplayText(year, month);
        currentTime=sdf.format(DateUtils.getDateByCalendar(year,month,day));
    }

    public void updateDisplayText(int year, int month) {
        mAttendanceDateChangeDisplayTV.setText(year+"-"+month);
    }

    @Override
    public void onItemClick(int position) {
        LogUtils.e("当前点击的是：" + defaultMonthDays.get(position).getNumber() + ",istoady:" + defaultMonthDays.get(currentSelectedPos).isToday());
        if (currentSelectedPos == position) {
            return;
        }
        defaultMonthDays.get(currentSelectedPos).setSelected(false);
        monthDaysAdapter.notifyItemChanged(currentSelectedPos);
        defaultMonthDays.get(position).setSelected(true);
        monthDaysAdapter.notifyItemChanged(position);
        currentSelectedPos = position;
    }
}
