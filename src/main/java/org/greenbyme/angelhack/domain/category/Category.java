package org.greenbyme.angelhack.domain.category;

public enum Category {

    ALL(0),
    NONE(1),
    ENERGY(2),
    DISPOSABLE(3),
    TRAFFIC(4),
    WATERWORKS(5),
    CAMPAIGN(6);

    private int idx;

    Category(int idx) {
        this.idx = idx;
    }

    public int getIdx() {
        return idx;
    }
}
