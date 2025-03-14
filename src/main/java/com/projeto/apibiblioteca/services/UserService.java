package com.projeto.apibiblioteca.services;

import com.projeto.apibiblioteca.entities.User;
import com.projeto.apibiblioteca.exceptions.NotFoundException;
import com.projeto.apibiblioteca.mappers.UserMapper;
import com.projeto.apibiblioteca.records.UserRecord;
import com.projeto.apibiblioteca.records.UserRequest;
import com.projeto.apibiblioteca.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void registerUser(UserRequest userRequest){
        User user = UserMapper.INSTANCE.toUser(userRequest);
        userRepository.save(user);
    }

    public void deleteUser(UUID userId){
        if (userRepository.existsById(userId)){
            userRepository.deleteById(userId);
        }else {
            throw new NotFoundException("Usuário não encontrado");
        }
    }

    public void updateUser(UserRecord userRecord, UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        updateData(user, userRecord);
        userRepository.save(user);
    }


    public UserRecord getUserDetails(UUID userId){
        if (userRepository.existsById(userId)){
            User user = userRepository.getReferenceById(userId);
            return UserMapper.INSTANCE.toUserRecord(user);
        }else {
            throw new NotFoundException("Usuário não encontrado");
        }
    }

    public UserRecord searchUser(String email, String name) {
        User user;
        if (email != null) {
            user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        } else {
            user = userRepository.findByName(name).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        }
        return UserMapper.INSTANCE.toUserRecord(user);
    }


    private void updateData(User user, UserRecord userRecord){
        user.setName(userRecord.name());
        user.setSurname(userRecord.surname());
        user.setEmail(userRecord.email());
    }

}
