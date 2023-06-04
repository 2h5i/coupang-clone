package com.taei.coupangclone.address.repository;

import com.taei.coupangclone.address.entity.Address;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUserId(Long userId);

    Optional<Address> findByUserIdAndDefaultAddress(Long userId, boolean defaultAddress);
}
