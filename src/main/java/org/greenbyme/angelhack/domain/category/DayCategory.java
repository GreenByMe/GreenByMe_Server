package org.greenbyme.angelhack.domain.category;

public enum  DayCategory {

    ALL(0),
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
