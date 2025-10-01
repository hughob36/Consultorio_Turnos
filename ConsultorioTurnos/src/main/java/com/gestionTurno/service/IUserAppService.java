package com.gestionTurno.service;


import com.gestionTurno.dto.UserResponseDTO;
import com.gestionTurno.model.UserApp;

import java.util.List;
import java.util.Optional;

public interface IUserAppService {

    public List<UserResponseDTO> finAll();
    public Optional<UserResponseDTO> findById(Long id);

    public UserApp findByIdUserApp(Long id);
    public UserResponseDTO save(UserApp userApp);
    public boolean deleteById(Long id);
    public UserResponseDTO update(Long id, UserApp userApp);

    public String encriptPassword(String password);
}
