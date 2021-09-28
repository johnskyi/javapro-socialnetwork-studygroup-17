package ru.skillbox.socialnetwork.data.entity;

public enum Permission {
    PERSON("user:write"),
    USER_ADMINISTRATION("user:administration"),
    USER_MODERATE("user:moderate");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
