package com.levine.base.data.bean;

import java.util.Date;

public class CalendarBean {
    Date date;
    String number;
    boolean isDayOfMonth=true;
    boolean isToday=false;
    boolean attendanceAM=false;
    String attendanceAMTime;
    String attendanceAMAddress;

    boolean attendancePM=false;
    String attendancePMTime;
    String attendancePMAddress;

    public CalendarBean(Date date, String number, boolean isToday, boolean attendanceAM, String attendanceAMTime, String attendanceAMAddress, boolean attendancePM, String attendancePMTime, String attendancePMAddress,boolean isDayOfMonth) {
        this.date = date;
        this.number = number;
        this.isToday = isToday;
        this.attendanceAM = attendanceAM;
        this.attendanceAMTime = attendanceAMTime;
        this.attendanceAMAddress = attendanceAMAddress;
        this.attendancePM = attendancePM;
        this.attendancePMTime = attendancePMTime;
        this.attendancePMAddress = attendancePMAddress;
        this.isDayOfMonth=isDayOfMonth;
    }
    public CalendarBean(){}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public boolean isAttendanceAM() {
        return attendanceAM;
    }

    public void setAttendanceAM(boolean attendanceAM) {
        this.attendanceAM = attendanceAM;
    }

    public String getAttendanceAMTime() {
        return attendanceAMTime;
    }

    public void setAttendanceAMTime(String attendanceAMTime) {
        this.attendanceAMTime = attendanceAMTime;
    }

    public String getAttendanceAMAddress() {
        return attendanceAMAddress;
    }

    public void setAttendanceAMAddress(String attendanceAMAddress) {
        this.attendanceAMAddress = attendanceAMAddress;
    }

    public boolean isAttendancePM() {
        return attendancePM;
    }

    public void setAttendancePM(boolean attendancePM) {
        this.attendancePM = attendancePM;
    }

    public String getAttendancePMTime() {
        return attendancePMTime;
    }

    public void setAttendancePMTime(String attendancePMTime) {
        this.attendancePMTime = attendancePMTime;
    }

    public String getAttendancePMAddress() {
        return attendancePMAddress;
    }

    public void setAttendancePMAddress(String attendancePMAddress) {
        this.attendancePMAddress = attendancePMAddress;
    }

    /*
    是否是当前月的某一天
     */
    public boolean isDayOfMonth() {
        return isDayOfMonth;
    }

    public void setDayOfMonth(boolean dayOfMonth) {
        isDayOfMonth = dayOfMonth;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CalendarBean{");
        sb.append("date='").append(date).append('\'');
        sb.append(", number='").append(number).append('\'');
        sb.append(", isToday=").append(isToday);
        sb.append(", attendanceAM=").append(attendanceAM);
        sb.append(", attendanceAMTime='").append(attendanceAMTime).append('\'');
        sb.append(", attendanceAMAddress='").append(attendanceAMAddress).append('\'');
        sb.append(", attendancePM=").append(attendancePM);
        sb.append(", attendancePMTime='").append(attendancePMTime).append('\'');
        sb.append(", attendancePMAddress='").append(attendancePMAddress).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
