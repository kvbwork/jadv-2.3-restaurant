package kvbdev;

import static kvbdev.OrderStatus.NEW;
import static kvbdev.OrderStatus.READY;

public class Waiter extends RestaurantWorker {
    private final long TRANSFER_TIME = 200;

    public Waiter(String name, Restaurant restaurant) {
        super(name, restaurant);
        registerOrderStatusCommand(NEW, order -> restaurant.updateOrder(acceptOrder(order)));
        registerOrderStatusCommand(READY, order -> deliverOrder(order));
    }

    protected Order acceptOrder(Order order) throws InterruptedException {
        System.out.println(getName() + " принимает " + order + " от " + order.getCustomer());
        order.setStatus(OrderStatus.ACCEPTED);
        order.getCustomer().notifyOrderStatus();
        Thread.sleep(TRANSFER_TIME);
        System.out.println(getName() + " передал повару " + order + " от " + order.getCustomer());
        return order;
    }

    protected Order deliverOrder(Order order) throws InterruptedException {
        System.out.println(getName() + " несет " + order + " для " + order.getCustomer());
        Thread.sleep(TRANSFER_TIME);
        order.setStatus(OrderStatus.DELIVERED);
        order.getCustomer().notifyOrderStatus();
        return order;
    }
}
