package kvbdev;

import static kvbdev.OrderStatus.ACCEPTED;

public class Chef extends RestaurantWorker {
    private final long COOKING_TIME = 1500;

    public Chef(String name, Restaurant restaurant) {
        super(name, restaurant);
        registerOrderStatusCommand(ACCEPTED, order -> restaurant.updateOrder(cooking(order)));
    }

    protected Order cooking(Order order) throws InterruptedException {
        System.out.println(getName() + " начал готовить " + order + " для " + order.getCustomer());
        Thread.sleep(COOKING_TIME);
        order.setStatus(OrderStatus.READY);
        System.out.println(getName() + " завершил приготовление " + order + " для " + order.getCustomer());
        return order;
    }
}
