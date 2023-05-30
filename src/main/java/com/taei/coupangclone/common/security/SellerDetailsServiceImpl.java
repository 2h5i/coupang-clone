package com.taei.coupangclone.common.security;

import com.taei.coupangclone.seller.entity.Seller;
import com.taei.coupangclone.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerDetailsServiceImpl implements DetailsServiceImpl {

    private final SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Seller seller = sellerRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("판매자를 찾을 수 없습니다."));

        return new CommonDetailsImpl(seller.getId(), seller.getEmail(), seller.getUserRole());
    }

    public UserDetailsServiceType getServiceType() {
        return UserDetailsServiceType.SELLER;
    }
}
