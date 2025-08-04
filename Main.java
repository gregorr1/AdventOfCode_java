import tools.StopWatch;

public class Main {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        Naloga15.naloga15_2();
        
        stopWatch.stop();
        System.out.println("This took " + stopWatch.getElapsedTime() + " ms.");
    }
}
