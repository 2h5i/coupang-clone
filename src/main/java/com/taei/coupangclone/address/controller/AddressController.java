package com.taei.coupangclone.address.controller;

import com.taei.coupangclone.address.dto.CreateAddress;
import com.taei.coupangclone.address.dto.UpdateAddress;
import com.taei.coupangclone.address.service.AddressService;
import com.taei.coupangclone.common.security.CommonDetailsImpl;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void createAddress(@RequestBody @Valid CreateAddress createAddressDto,
        @AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        addressService.createAddress(createAddressDto, commonDetails.getId());
    }

    @PutMapping("/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void updateAddress(@PathVariable Long addressId,
        @RequestBody @Valid UpdateAddress updateAddressDto,
        @AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        addressService.updateAddress(addressId, commonDetails.getId(), updateAddressDto);
    }

    @DeleteMapping("/{addressId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteAddress(@PathVariable Long addressId) {

        addressService.deleteAddress(addressId);
    }

}
