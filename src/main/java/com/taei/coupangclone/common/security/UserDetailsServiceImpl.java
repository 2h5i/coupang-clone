package com.taei.coupangclone.common.security;

import com.taei.coupangclone.user.entity.User;
import com.taei.coupangclone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements DetailsServiceImpl {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new CommonDetailsImpl(user.getId(), user.getEmail(), user.getUserRole());
    }

    public UserDetailsServiceType getServiceType() {
        return UserDetailsServiceType.USER;
    }
}
