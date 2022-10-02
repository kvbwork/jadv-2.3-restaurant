package kvbdev;


public class Customer extends Person implements Runnable {
    private final long ORDER_DELAY = 1000;
    private final long EATING_TIME = 1500;

    private final Restaurant restaurant;
    private Order order;

    public Customer(String name, Restaurant restaurant) {
        super(name);
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        System.out.println(getName() + " в ресторане");
        try {
            System.out.println(getName() + " выбирает что заказать");
            Thread.sleep(ORDER_DELAY);
            setOrder(new Order(this));

            System.out.println(getName() + " решил что заказать и ждет официанта");
            restaurant.notifyWaiters(this);
            awaitOrder();

            System.out.println(getName() + " ждет готовности " + getOrder());
            awaitOrder();

            System.out.println(getName() + " приступил к еде");
            Thread.sleep(EATING_TIME);

        } catch (InterruptedException ex) {
        }
        System.out.println(getName() + " вышел из ресторана");
    }

    public synchronized void awaitOrder() throws InterruptedException {
        wait();
    }

    public synchronized void notifyOrderStatus() {
        notify();
        System.out.println(getName() + " уведомлен, что его " + getOrder() + " имеет статус " + getOrder().getStatus());
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean hasOrder() {
        return order != null;
    }
}
