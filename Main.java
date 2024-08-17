import tools.StopWatch;

public class Main {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        Naloga9.naloga9_2();

        System.out.println("This took " + stopWatch.getElapsedTime() + " ms.");
        stopWatch.stop();
    }
}
