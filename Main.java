import java.util.ArrayList;
import java.util.List;

import Naloga7.Naloga7;
import Naloga8.Naloga8;
import tools.StopWatch;

public class Main {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        Naloga7.naloga7_2();

        System.out.println("This took " + stopWatch.getElapsedTime() + " ms.");
        stopWatch.stop();
    }
}
