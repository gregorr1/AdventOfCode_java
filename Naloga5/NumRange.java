package Naloga5;

public class NumRange {
    private long rangeStart;
    private long rangeEnd;

    public NumRange(long rangeStart, long rangeEnd) {
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public long getRangeStart() {
        return rangeStart;
    }
    public void setRangeStart(long rangeStart) {
        this.rangeStart = rangeStart;
    }
    public long getRangeEnd() {
        return rangeEnd;
    }
    public void setRangeEnd(long rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    @Override
    public String toString() {
        return rangeStart + " " + rangeEnd;
    }
}
