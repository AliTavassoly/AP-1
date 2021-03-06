package hearthstone.util.timer;

public class HSDelayTimerTask extends Thread {
    private HSDelayTask task;
    private long delay;

    public HSDelayTimerTask(long delay, HSDelayTask task) {
        this.delay = delay;
        this.task = task;
    }

    @Override
    public void run() throws NullPointerException {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        task.delayAction();
    }

    public void start(){
        super.start();
    }
}
