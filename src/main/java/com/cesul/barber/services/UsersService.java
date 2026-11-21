package com.cesul.barber.services;

import com.cesul.barber.dtos.UpdateUserAvatarDTO;
import com.cesul.barber.dtos.UserCreationDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersService {

    private List<UserCreationDTO> users = new ArrayList<>();

    public UserCreationDTO create(UserCreationDTO user) {
        user.setId(UUID.randomUUID());
        this.users.add(user);

        return user;
    }

    public List<UserCreationDTO> findAll() {
        return this.users;
    }

    public Optional<UserCreationDTO> findById(UUID id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    public Optional<UserCreationDTO> update(UUID id, UserCreationDTO updatedUser) {
        Optional<UserCreationDTO> userOptional = findById(id);

        if (userOptional.isPresent()) {
            UserCreationDTO user = userOptional.get();
            if (updatedUser.getName() != null) {
                user.setName(updatedUser.getName());
            }
            if (updatedUser.getEmail() != null) {
                user.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getAvatarUrl() != null) {
                user.setAvatarUrl(updatedUser.getAvatarUrl());
            }
        }
        return userOptional;
    }

    public Optional<UserCreationDTO> update(UUID id, Map<String, String> request) {
        Optional<UserCreationDTO> userOptional = findById(id);

        if (userOptional.isPresent()) {
            UserCreationDTO user = userOptional.get();
            if (request.containsKey("name")) {
                user.setName(request.get("name"));
            }
            if (request.containsKey("email")) {
                user.setEmail(request.get("email"));
            }
            if (request.containsKey("avatarUrl")) {
                user.setAvatarUrl(request.get("avatarUrl"));
            }
        }
        return userOptional;
    }

    public Optional<UserCreationDTO> updateAvatar(UUID id, UpdateUserAvatarDTO avatarDTO) {
        if (avatarDTO == null) {
            return Optional.empty();
        }
        return updateAvatar(id, avatarDTO.getAvatarUrl());
    }

    public Optional<UserCreationDTO> updateAvatar(UUID id, String avatarUrl) {
        Optional<UserCreationDTO> userOptional = findById(id);

        if (userOptional.isPresent()) {
            UserCreationDTO user = userOptional.get();
            if (avatarUrl != null) {
                user.setAvatarUrl(avatarUrl);
            }
        }
        return userOptional;
    }

    public boolean delete(UUID id) {
        return users.removeIf(u -> u.getId().equals(id));
    }

    public boolean clear() {
        this.users.clear();
        return true;
    }
}
