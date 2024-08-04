package Naloga5;

public class MapElement {
    private long destination;
    private long source;
    private long range;
    
    public MapElement(long destination, long source, long range) {
        this.destination = destination;
        this.source = source;
        this.range = range;
    }

    public long getDestination() {
        return destination;
    }
    public void setDestination(long destination) {
        this.destination = destination;
    }
    public long getSource() {
        return source;
    }
    public void setSource(long source) {
        this.source = source;
    }
    public long getRange() {
        return range;
    }
    public void setRange(long range) {
        this.range = range;
    }

    @Override
    public String toString() {
        return destination + " " + source + " " + range;
    }
}
