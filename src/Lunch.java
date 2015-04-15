import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Alan.
 * @version 4/15/2015.
 */
public class Lunch {

    public static void main(String[] args) {
        int N = PhilosopherInterface.DINERS;
        Lock lock = new ReentrantLock();
        Condition[] chopsticks = new Condition[N];
        Diner[] diners = new Diner[N];
        for (int i = 0; i < N; i++)
            chopsticks[i] = lock.newCondition();
        for (int j = 0; j < N; j++)
            diners[j] = new Diner(j, lock, chopsticks);
        //todo start threads
    }
}
