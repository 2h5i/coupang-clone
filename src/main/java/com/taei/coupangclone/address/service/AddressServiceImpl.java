package com.taei.coupangclone.address.service;

import com.taei.coupangclone.address.dto.CreateAddress;
import com.taei.coupangclone.address.dto.UpdateAddress;
import com.taei.coupangclone.address.entity.Address;
import com.taei.coupangclone.address.repository.AddressRepository;
import com.taei.coupangclone.user.entity.User;
import com.taei.coupangclone.user.repository.UserRepository;
import java.util.Optional;
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

        if (createAddressDto.isDefaultAddress()) {
            Optional<Address> existAddress = addressRepository.findByUserIdAndDefaultAddress(
                userId, true);
            existAddress.ifPresent(address -> {
                address.updateDefaultAddress(false);
                addressRepository.save(address);
            });
        }

        Address address = Address.builder()
            .userAddress(createAddressDto.getUserAddress())
            .zipcode(createAddressDto.getZipcode())
            .user(user)
            .defaultAddress(createAddressDto.isDefaultAddress())
            .build();

        addressRepository.save(address);
    }

    @Override
    @Transactional
    public void updateAddress(Long addressId, Long userId, UpdateAddress updateAddressDto) {
        Address address = addressRepository.findById(addressId).orElseThrow(
            () -> new IllegalArgumentException("해당하는 주소지가 없습니다.")
        );

        if (updateAddressDto.isDefaultAddress()) {
            Optional<Address> existAddress = addressRepository.findByUserIdAndDefaultAddress(
                userId, true);
            existAddress.ifPresent(addr -> {
                addr.updateDefaultAddress(false);
                addressRepository.save(addr);
            });
        }

        address.updateAddressInfo(updateAddressDto.getUserAddress(), updateAddressDto.getZipcode(),
            updateAddressDto.isDefaultAddress());

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
