package ru.skillbox.socialnetwork.data.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.PERSON)),
    MODERATOR(Set.of(Permission.USER_MODERATE, Permission.PERSON)),
    ADMIN(Set.of(Permission.USER_MODERATE, Permission.USER_ADMINISTRATION, Permission.PERSON));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions){
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthority(){
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
