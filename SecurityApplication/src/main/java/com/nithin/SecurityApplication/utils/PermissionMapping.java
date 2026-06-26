package com.nithin.SecurityApplication.utils;

import com.nithin.SecurityApplication.enums.Permission;
import com.nithin.SecurityApplication.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.nithin.SecurityApplication.enums.Permission.*;
import static com.nithin.SecurityApplication.enums.Role.*;

@Component
public class PermissionMapping {

    private static final Map<Role, Set<Permission>> map = Map.of(
                USER,Set.of(USER_VIEW,POST_VIEW),
                CREATOR,Set.of(POST_CREATE,USER_UPDATE,POST_UPDATE),
                ADMIN,Set.of(POST_CREATE,USER_UPDATE,POST_UPDATE,USER_CREATE,USER_DELETE,POST_DELETE)
            );

    public static Set<SimpleGrantedAuthority> getAuthorities(Role role){
        return map.get(role)
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
