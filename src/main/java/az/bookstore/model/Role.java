package az.bookstore.model;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static az.bookstore.model.Permissions.*;

public enum Role {


    USER(Set.of(USER_READ,USER_CURRENTLY_READING)),
    ADMIN(Set.of(ADMIN_CREATE,ADMIN_READERS,ADMIN_DELETE));
     @Getter
    private final Set<Permissions> permissions;
    Role(Set<Permissions> permissions) {
        this.permissions = permissions;
    }
    public List<SimpleGrantedAuthority>getAuthorities(){
        var authorities = getPermissions()
                .stream().map(permissions -> new SimpleGrantedAuthority(permissions.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" +this.name()));
        return authorities;
    }

}


