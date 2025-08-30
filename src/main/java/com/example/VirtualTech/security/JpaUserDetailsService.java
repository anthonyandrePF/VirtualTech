package com.example.VirtualTech.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
  private final AppUserRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var u = userRepo.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("No existe: " + username));

    var auths = u.getRoles().stream()
        .map(r -> new org.springframework.security.core.authority.SimpleGrantedAuthority(r.getName()))
        .toList();

    return new org.springframework.security.core.userdetails.User(
        u.getUsername(), u.getPassword(), u.isEnabled(),
        true, true, true, auths);
  }
}
