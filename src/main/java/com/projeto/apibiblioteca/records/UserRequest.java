package com.projeto.apibiblioteca.records;

import com.projeto.apibiblioteca.enums.UserRole;

import java.util.UUID;

public record UserRequest(UUID id,
                          String name,
                          String surname,
                          String email,
                          String password,
                          String cpf,
                          UserRole role){
}
