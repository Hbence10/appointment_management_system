package com.Hbence.appointmentManagementAPI.configurations.security;

import com.Hbence.appointmentManagementAPI.entity.User;
import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSetter implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User users = userRepository.login(username);
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(users.getRole().getName()));

        return new org.springframework.security.core.userdetails.User(users.getUsername(), users.getPassword(), authorities);
    }
}
