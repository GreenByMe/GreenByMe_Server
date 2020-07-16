package org.greenbyme.angelhack.domain.Category;

public enum  DayCategory {

    DAY(1),
    WEEK(7),
    MONTH(30);

    private int day;

    DayCategory(int day){
        this.day=day;
    }

    public int getDay() {
        return day;
    }
}