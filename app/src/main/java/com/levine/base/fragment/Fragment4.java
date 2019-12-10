package com.levine.base.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import static com.levine.base.DateUtils.getDaysOfMonth;

@TargetFragmentTag(FragmentTag.FRAGMENT4)
public class Fragment4 extends Fragment implements View.OnClickListener{
    @LevineBindView(R.id.mWeekDaysGRV)
    private GridRecyclerView mWeekDaysGRV;
    @LevineBindView(R.id.mMonthDaysGRV)
    private GridRecyclerView mMonthDaysGRV;

    private List<String> defaultWeekDays;
    private List<CalendarBean> defaultMonthDays;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String currentTime = "";

    FragmentFactory mFactory;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment4,container,false);
        LevineAnnotationUtils.bind(this,view);
        mFactory=FragmentFactory.getInstance();
        init();
        return view;
    }

    private void init() {
        defaultWeekDays=getDefaultWeekDays();

        BaseRecyclerViewAdapter weekDaysAdapter=new BaseRecyclerViewAdapter<String>(defaultWeekDays,getActivity(),R.layout.week_day_item_view) {
            @Override
            public void convert(BaseViewHolder holder, String itemData) {
                holder.setText(R.id.mWeekDayItemTV,itemData);
            }
        };
        mWeekDaysGRV.setAdapter(weekDaysAdapter);


        //获取默认选中的日期的年月日星期的值，并赋值

        Date date=new Date();
        date.setMonth(10);

        currentTime=sdf.format(date);
        int year= Integer.valueOf(currentTime.split("-")[0]);
        int month= Integer.valueOf(currentTime.split("-")[1]);
        int day= Integer.valueOf(currentTime.split("-")[2]);
        defaultMonthDays=getDefaultMonthDays(year,month,day);

        BaseRecyclerViewAdapter monthDaysAdapter=new BaseRecyclerViewAdapter<CalendarBean>(defaultMonthDays,getActivity(),R.layout.month_day_item_view) {
            @Override
            public void convert(BaseViewHolder holder, CalendarBean itemData) {
                      holder.setText(R.id.mMonthDayItemTV,itemData.getNumber());
                      if(!itemData.isDayOfMonth()){
                          holder.setTextColor(R.id.mMonthDayItemTV, Color.GRAY);
                      }else {
                          holder.setTextColor(R.id.mMonthDayItemTV,Color.BLACK);
                      }
            }
        };
        mMonthDaysGRV.setAdapter(monthDaysAdapter);

    }



    private List<CalendarBean> getDefaultMonthDays(int year,int month,int day) {
        List<CalendarBean> list=new ArrayList<>();
        int count= getDaysOfMonth(year,month);


        //添加上个月的部分数据
        list.addAll(getNeededDaysOfLastMonth(year,month));

        for (int i=1;i<=count;i++){
            CalendarBean calendarBean=new CalendarBean();
            calendarBean.setDayOfMonth(true);//是当前月的数据
            calendarBean.setNumber(String.valueOf(i));
            calendarBean.setToday(DateUtils.isToday(year,month,day));
            calendarBean.setDate(DateUtils.getDateByCalendar(year,month,day));
            calendarBean.setAttendancePM(false);
            calendarBean.setAttendanceAM(false);

            list.add(calendarBean);
        }
        //添加下个月的部分数据
        list.addAll(getNeededDaysOfNextMonth(year,month));
        return list;

    }

    private List<CalendarBean> getNeededDaysOfLastMonth( int year, int month) {
        int d=DateUtils.getWeekDayOfFirstDayInMonth(year, month);
        int dw=d-1;
        List<CalendarBean> list=new ArrayList<>();
        month=month-1;//上一个月
        if(month==0){
            year=year-1;
            month=12;
        }
        int daysOfLastMonth=getDaysOfMonth(year,month);
        for(int i=1;i<=dw;i++){
            int day=daysOfLastMonth-dw+i;
            CalendarBean calendarBean=new CalendarBean();
            calendarBean.setDate(DateUtils.getDateByCalendar(year,month,day));
            calendarBean.setDayOfMonth(false);
            calendarBean.setToday(false);
            calendarBean.setNumber(String.valueOf(day));
            calendarBean.setAttendanceAM(false);
            calendarBean.setAttendancePM(false);
            list.add(calendarBean);
        }

        return list;
    }

    private List<CalendarBean> getNeededDaysOfNextMonth( int year, int month) {
        //获取某年某月的最后一天是星期几
        int d=DateUtils.getWeekDayOfLastDayInMonth(year, month);
        int dw=7-d;
        List<CalendarBean> list=new ArrayList<>();
        month=month+1;//下一个月
        if(month==13){
            year=year+1;
            month=1;
        }
        int index=0;
        for(int i=1;i<=dw;i++){
            int day=index+i;
            CalendarBean calendarBean=new CalendarBean();

            calendarBean.setDate(DateUtils.getDateByCalendar(year,month,day));
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
        List<String> list=new ArrayList<>();
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
        switch (v.getId()){

        }
    }
}
