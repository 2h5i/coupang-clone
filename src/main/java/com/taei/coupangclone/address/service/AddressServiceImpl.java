package com.taei.coupangclone.address.service;

import com.taei.coupangclone.address.dto.CreateAddress;
import com.taei.coupangclone.address.dto.UpdateAddress;
import com.taei.coupangclone.address.entity.Address;
import com.taei.coupangclone.address.repository.AddressRepository;
import com.taei.coupangclone.user.entity.User;
import com.taei.coupangclone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public void createAddress(CreateAddress createAddressDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new IllegalArgumentException("해당하는 회원이 없습니다.")
        );

        Address address = Address.builder()
            .userAddress(createAddressDto.getUserAddress())
            .zipcode(createAddressDto.getZipcode())
            .user(user)
            .build();

        addressRepository.save(address);
    }

    @Override
    @Transactional
    public void updateAddress(Long addressId, UpdateAddress updateAddressDto) {
        Address address = addressRepository.findById(addressId).orElseThrow(
            () -> new IllegalArgumentException("해당하는 주소지가 없습니다.")
        );

        address.updateAddressInfo(updateAddressDto.getUserAddress(), updateAddressDto.getZipcode());

        addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(
            () -> new IllegalArgumentException("해당하는 주소지가 없습니다.")
        );

        addressRepository.delete(address);
    }
}
