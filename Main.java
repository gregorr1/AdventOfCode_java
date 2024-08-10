import tools.StopWatch;

public class Main {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        Naloga6.naloga6_2();

        System.out.println("This took " + stopWatch.getElapsedTime() + " ms.");
        stopWatch.stop();
    }
}
