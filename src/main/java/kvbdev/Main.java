package kvbdev;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final int MAX_CUSTOMERS = 5;
        final int NEXT_CUSTOMER_INTERVAL = 1250;

        Restaurant restaurant = new Restaurant();
        restaurant.open();

        ThreadGroup customersGroup = new ThreadGroup("Customers");

        for (int i = 0; i < MAX_CUSTOMERS; i++) {
            Thread.sleep(NEXT_CUSTOMER_INTERVAL);
            new Thread(customersGroup, new Customer("Посетитель" + i, restaurant)).start();
        }

        awaitShutdown(customersGroup);

        restaurant.close();
    }

    protected static void awaitShutdown(ThreadGroup threadGroup) {
        try {
            while (threadGroup.activeCount() > 0) {
                Thread[] threads = new Thread[1];
                threadGroup.enumerate(threads, false);
                threads[0].join();
            }
        } catch (InterruptedException ex) {
        }
    }
}
