import Naloga17.Naloga17;
import tools.StopWatch;

public class Main {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        Naloga17.naloga17_1();
        Naloga17.naloga17_2();
        
        stopWatch.stop();
        System.out.println("This took " + stopWatch.getElapsedTime() + " ms.");
    }
}
