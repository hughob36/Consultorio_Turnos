package com.gestionTurno.service;

import com.gestionTurno.dto.UserResponseDTO;
import com.gestionTurno.mapper.UserAppMapper;
import com.gestionTurno.model.UserApp;
import com.gestionTurno.repository.IUserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAppService implements IUserAppService{

    @Autowired
    private IUserAppRepository userAppRepository;

    @Autowired
    private UserAppMapper userAppMapper;

    @Override
    public List<UserResponseDTO> finAll() {
        return userAppMapper.userResponseDTOList(userAppRepository.findAll());
    }

    @Override
    public Optional<UserResponseDTO> findById(Long id) {
        return userAppMapper.toUserOptional(userAppRepository.findById(id));
    }

    @Override
    public UserApp findByIdUserApp(Long id) {
        return null;
    }

    @Override
    public UserResponseDTO save(UserApp userApp) {
        return userAppMapper.toEntity(userAppRepository.save(userApp));
    }

    @Override
    public boolean deleteById(Long id) {

        if(userAppRepository.existsById(id)) {
            userAppRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public UserResponseDTO update(Long id, UserApp userApp) {

        UserApp userFound = userAppRepository.findById(id).orElse(null);

        if(userFound != null) {
            userFound.setName(userApp.getName());
            userFound.setLastname(userApp.getLastname());
            userFound.setEmail(userApp.getEmail());
            userFound.setUserName(userApp.getUserName());
            userFound.setPassword(userApp.getPassword());
            userFound.setEnable(userApp.isEnable());
            userFound.setAccountNotExpired(userApp.isAccountNotExpired());
            userFound.setAccountNotLocked(userApp.isAccountNotLocked());
            userFound.setCredentialNotExpired(userApp.isCredentialNotExpired());
            userFound.setRoleList(userApp.getRoleList());

           return userAppMapper.toEntity(userAppRepository.save(userFound));
        }
        return userAppMapper.toEntity(userFound);
    }

    @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

}
