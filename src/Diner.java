import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author Alan.
 * @version 4/14/2015.
 */
public class Diner implements PhilosopherInterface, Runnable{

    /*Eating lock*/
    private Lock lock;
    /*Diner id*/
    private final int id;
    /*State the diner is in*/
    private State state;
    /*Reference to chopsticks*/
    private Condition[] chopsticks;

    public Diner(int id, Lock lock, Condition[] chopsticks) {
        this.id = id;
        this.lock = lock;
        this.chopsticks = chopsticks;
    }

    public void think() {
        state = State.THINKING;
        //todo pause up to 5 secs
        takeChopsticks();
    }

    public void eat() {
        state = State.EATING;
        //todo pause up to 5 secs
        replaceChopsticks();
        think();
    }

    @Override
    public void takeChopsticks() {
        state = State.HUNGRY;
        lock.lock();
        try {
            //todo check for states to prevent deadlock.
            chopsticks[id].await();
            chopsticks[(id+1)%PhilosopherInterface.DINERS].await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        eat();
    }

    @Override
    public void replaceChopsticks() {
        chopsticks[id].signal();
        chopsticks[(id+1)%PhilosopherInterface.DINERS].signal();
        think();
    }

    @Override
    public void run() {

    }
}
