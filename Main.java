import Naloga10.Naloga10;
import tools.StopWatch;

public class Main {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        Naloga10.naloga10_2();
        
        stopWatch.stop();
        System.out.println("This took " + stopWatch.getElapsedTime() + " ms.");
    }
}
