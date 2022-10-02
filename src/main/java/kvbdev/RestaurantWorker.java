package kvbdev;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class RestaurantWorker extends Person implements Runnable {
    private final Restaurant restaurant;
    private final Map<OrderStatus, InterruptableConsumer<Order>> orderStatusCommands;

    public RestaurantWorker(String name, Restaurant restaurant) {
        super(name);
        this.restaurant = restaurant;
        this.orderStatusCommands = new HashMap<>();
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    protected void registerOrderStatusCommand(OrderStatus status, InterruptableConsumer<Order> orderConsumer) {
        orderStatusCommands.put(status, orderConsumer);
    }

    protected Collection<OrderStatus> getProcessingStatusList() {
        return orderStatusCommands.keySet();
    }

    protected Optional<InterruptableConsumer<Order>> getOrderStatusCommand(OrderStatus status) {
        return Optional.ofNullable(orderStatusCommands.get(status));
    }

    @Override
    public void run() {
        System.out.println(getName() + " на работе!");

        try {
            while (!Thread.currentThread().isInterrupted()) {
                Order order = restaurant.awaitNextOrderEvent(getProcessingStatusList());
                getOrderStatusCommand(order.getStatus())
                        .orElseThrow()
                        .accept(order);
            }
        } catch (InterruptedException ex) {
        }

        System.out.println(getName() + " завершил смену");
    }

}
