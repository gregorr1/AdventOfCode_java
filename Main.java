import Naloga4.Naloga4;
import tools.StopWatch;

public class Main {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        Naloga4.naloga4_2();

        System.out.println("This took " + stopWatch.getElapsedTime() + " ms.");
        stopWatch.stop();
    }
}
