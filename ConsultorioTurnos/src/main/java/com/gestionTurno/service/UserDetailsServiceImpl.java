package com.gestionTurno.service;


import com.gestionTurno.dto.AuthLoginRequestDTO;
import com.gestionTurno.dto.AuthResponseDTO;
import com.gestionTurno.model.UserApp;
import com.gestionTurno.repository.IUserAppRepository;
import com.gestionTurno.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserAppRepository userAppRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserApp userApp = userAppRepository.findEntityUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '"+ username + "' was not found."));


        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userApp.getRoleList()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));

       userApp.getRoleList().stream()
               .flatMap(role -> role.getPermissionList().stream())
               .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermissionName())));


        return new User(
                userApp.getUserName(),
                userApp.getPassword(),
                userApp.isEnable(),
                userApp.isAccountNotExpired(),
                userApp.isCredentialNotExpired(),
                userApp.isAccountNotLocked(),
                authorityList);
    }

    public AuthResponseDTO loginUser(@Valid AuthLoginRequestDTO authLoginRequestDTO) {

        String username =  authLoginRequestDTO.userName();
        String password = authLoginRequestDTO.password();

        Authentication authentication = this.authentication(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtil.createToken(authentication);

        AuthResponseDTO authResponseDTO = new AuthResponseDTO(username,"Login successfull",accessToken,true);

        return authResponseDTO;
    }

    public Authentication authentication(String username, String password) {

        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails == null) {
            throw  new BadCredentialsException("Invalid username or password.");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password.");
        }
        return new UsernamePasswordAuthenticationToken(username,
                                                        userDetails.getPassword(),
                                                        userDetails.getAuthorities());
    }

}
