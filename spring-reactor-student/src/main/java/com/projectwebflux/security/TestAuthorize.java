package com.projectwebflux.security;

import org.springframework.stereotype.Component;

@Component
public class TestAuthorize {

    public boolean hasAccess(){
        return true;
    }
}
