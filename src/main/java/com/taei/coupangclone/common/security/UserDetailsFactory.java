package com.taei.coupangclone.common.security;

import java.util.HashMap;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsFactory {

    private HashMap<UserDetailsServiceType, DetailsServiceImpl> serviceMap = new HashMap<>();

    public UserDetailsFactory(List<DetailsServiceImpl> detailsServiceList) {
        for (DetailsServiceImpl userDetailsService : detailsServiceList) {
            serviceMap.put(userDetailsService.getServiceType(), userDetailsService);
        }
    }

    public UserDetails getUserDetails(String email, UserDetailsServiceType userDetailsServiceType) {
        DetailsServiceImpl userDetailsService = serviceMap.get(userDetailsServiceType);

        return userDetailsService.loadUserByUsername(email);
    }

}