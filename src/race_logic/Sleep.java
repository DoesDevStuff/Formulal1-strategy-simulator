package race_logic;

public class Sleep {
	public static void sleepInterval(long milliseconds) {
        try {
            synchronized (Thread.currentThread()) {
                Thread.currentThread().wait(milliseconds);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
}
