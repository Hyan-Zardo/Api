package com.projeto.apibiblioteca.services;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.entities.User;
import com.projeto.apibiblioteca.exceptions.NotFoundException;
import com.projeto.apibiblioteca.mappers.BookMapper;
import com.projeto.apibiblioteca.mappers.UserMapper;
import com.projeto.apibiblioteca.records.UserRecord;
import com.projeto.apibiblioteca.records.UserRequest;
import com.projeto.apibiblioteca.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public void updateUser(UserRequest userRequest, UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        updateData(user, userRequest);
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

    public List<UserRecord> searchUser(String name) {
        List<User> users = userRepository.findByNameContaining(name);

        if (users.isEmpty()){
            throw new NotFoundException("Livro não encontrado");
        }
        return users.stream().map(UserMapper.INSTANCE::toUserRecord).collect(Collectors.toList());


    }


    public void updateData(User user, UserRequest userRequest){
        user.setName(userRequest.name());
        user.setSurname(userRequest.surname());
        user.setEmail(userRequest.email());
        user.setCpf(userRequest.cpf());
        user.setPassword(userRequest.password());
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com id: " + id));
    }

}
