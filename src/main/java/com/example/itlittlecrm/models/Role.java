package com.example.itlittlecrm.models;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, DEV, DEVLEAD, DEVOWN, PRODUCT, HR;

    @Override
    public String getAuthority() {
        return name();
    }


}
