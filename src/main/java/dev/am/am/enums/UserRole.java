package dev.am.am.enums;

public enum UserRole {
    USER("user"),
    GOD("god");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
