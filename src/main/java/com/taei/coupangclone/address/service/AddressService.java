package com.taei.coupangclone.address.service;

import com.taei.coupangclone.address.dto.CreateAddress;
import com.taei.coupangclone.address.dto.UpdateAddress;

public interface AddressService {

    void createAddress(CreateAddress createAddressDto, Long userId);

    void updateAddress(Long addressId, UpdateAddress updateAddressDto);

    void deleteAddress(Long addressId);
}
