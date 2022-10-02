package kvbdev;

public enum OrderStatus {
    NEW("Новый"),
    ACCEPTED("Принят"),
    PROCESSING("Приготовление"),
    READY("Готов"),
    DELIVERED("Выдан");

    private final String statusString;

    OrderStatus(String statusString) {
        this.statusString = statusString;
    }

    @Override
    public String toString() {
        return statusString;
    }
}
