package com.projeto.apibiblioteca.records;

import com.projeto.apibiblioteca.enums.UserRole;

public record RegisterDTO(String name, String email, String password, UserRole role, String cpf) {
}
