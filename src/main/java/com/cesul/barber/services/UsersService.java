package com.cesul.barber.services;

import com.cesul.barber.dtos.UserCreationDTO;
import com.cesul.barber.entities.User;
import com.cesul.barber.mappers.UserMapper;
import com.cesul.barber.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserCreationDTO create(UserCreationDTO dto) {
        User user = UserMapper.toEntity(dto);
        User savedUser = this.usersRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    public List<UserCreationDTO> findAll() {
        return this.usersRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserCreationDTO> findById(UUID id) {
        return this.usersRepository.findById(id)
                .map(UserMapper::toDTO);
    }

    public Optional<UserCreationDTO> update(UUID id, UserCreationDTO updatedUser) {
        Optional<User> userOptional = this.usersRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (updatedUser.getName() != null) {
                user.setName(updatedUser.getName());
            }
            if (updatedUser.getEmail() != null) {
                user.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getAvatarUrl() != null) {
                user.setAvatarUrl(updatedUser.getAvatarUrl());
            }
            User savedUser = this.usersRepository.save(user);
            return Optional.of(UserMapper.toDTO(savedUser));
        }
        return Optional.empty();
    }

    public Optional<UserCreationDTO> updateAvatar(UUID id, String avatarUrl) {
        Optional<User> userOptional = this.usersRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (avatarUrl != null) {
                user.setAvatarUrl(avatarUrl);
            }
            User savedUser = this.usersRepository.save(user);
            return Optional.of(UserMapper.toDTO(savedUser));
        }
        return Optional.empty();
    }

    public boolean delete(UUID id) {
        if (this.usersRepository.existsById(id)) {
            this.usersRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void clear() {
        this.usersRepository.deleteAll();
    }
}
