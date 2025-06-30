package com.example.petdrop.model;

public class DateObj {
    String startDate;
    String endDate;
    int recurrances;

    public DateObj(String startDate, String endDate, int recurrances) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.recurrances = recurrances;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getRecurrances() {
        return recurrances;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setRecurrances(int recurrances) {
        this.recurrances = recurrances;
    }
}
