package kvbdev;

import java.util.*;

public class Restaurant {
    private final int MAX_WAITERS = 3;
    private final ThreadGroup restaurantWorkers = new ThreadGroup("RestaurantWorkers");
    private final Set<Order> orderEvents = new HashSet<>();

    public Restaurant() {
    }

    public void open(){
        System.out.println("Ресторан открывается");

        Chef chef = new Chef("Повар", this);
        new Thread(restaurantWorkers, chef).start();

        for (int i = 0; i < MAX_WAITERS; i++) {
            Waiter waiter = new Waiter("Официант" + i, this);
            new Thread(restaurantWorkers, waiter).start();
        }
    }

    public void notifyWaiters(Customer customer) {
        if (customer.hasOrder()) {
            updateOrder(customer.getOrder());
        } else {
            System.out.println(customer + " позвал официанта, но не готов заказывать");
        }
    }

    public void updateOrder(Order order) {
        synchronized (orderEvents) {
            orderEvents.add(order);
            orderEvents.notify();
        }
    }

    public Order awaitNextOrderEvent(Collection<OrderStatus> processingStatusList) throws InterruptedException {
        synchronized (orderEvents) {
            while (true) {
                Iterator<Order> orderIterator = orderEvents.iterator();
                while(orderIterator.hasNext()){
                    Order order = orderIterator.next();
                    if (processingStatusList.contains(order.getStatus())){
                        orderIterator.remove();
                        return order;
                    }
                }
                orderEvents.wait();
            }

        }
    }

    public void close(){
        System.out.println("Ресторан закрывается");
        restaurantWorkers.interrupt();
    }
}
