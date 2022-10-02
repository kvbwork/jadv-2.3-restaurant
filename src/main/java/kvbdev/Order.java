package kvbdev;

public class Order {
    private final Customer customer;
    private OrderStatus status = OrderStatus.NEW;

    public Order(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Заказ(" + Integer.toHexString(hashCode()) + ")";
    }

    public Customer getCustomer() {
        return customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

}
