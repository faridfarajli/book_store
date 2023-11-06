package az.bookstore.model;

import lombok.Getter;

public enum Permissions {

    ADMIN_CREATE("admin:create"),
    ADMIN_READERS("admin:readers"),
    ADMIN_DELETE("admin:delete"),
    USER_READ("user:read"),
    USER_CURRENTLY_READING("user:currently-reading")
    ;

    @Getter
    private final String permissions;

    Permissions(String permissions) {
        this.permissions = permissions;
    }


}
