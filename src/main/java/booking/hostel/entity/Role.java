package booking.hostel.entity;

public enum Role {
    ROLE_ADMIN("Администратор"),
    ROLE_OWNER("Владелец"),
    ROLE_CLIENT("Клиент");

    private final String name;
    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
