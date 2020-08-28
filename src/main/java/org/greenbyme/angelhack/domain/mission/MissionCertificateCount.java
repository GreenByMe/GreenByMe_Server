package org.greenbyme.angelhack.domain.mission;

public enum MissionCertificateCount {

    ONE_DAY(1),

    ONE_TIMES_A_WEEK(1),
    TWO_TIMES_A_WEEK(2),
    THREE_TIMES_A_WEEK(3),
    ALL_TIMES_A_WEEK(7),

    ONE_TIMES_A_WEEK_MONTH(4),
    TWO_TIMES_A_WEEK_MONTH(8),
    THREE_TIMES_A_WEEK_MONTH(12),
    ALL_TIMES_A_WEEK_MONTH(28);

    private int count;

    MissionCertificateCount(int count){
        this.count=count;
    }

    public int getCount() {
        return count;
    }
}
