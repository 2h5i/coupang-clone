package com.taei.coupangclone.address.dto;

import com.taei.coupangclone.address.entity.Address;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ResponseAddress {

    private Long id;
    private String userAddress;
    private String zipcode;

    private ResponseAddress(Address address) {
        this.id = address.getId();
        this.userAddress = address.getUserAddress();
        this.zipcode = address.getZipcode();
    }

    public static ResponseAddress of(Address address) {
        return new ResponseAddress(address);
    }

    public static List<ResponseAddress> of(List<Address> addresses) {
        return addresses.stream().map(ResponseAddress::of).collect(Collectors.toList());
    }
}
