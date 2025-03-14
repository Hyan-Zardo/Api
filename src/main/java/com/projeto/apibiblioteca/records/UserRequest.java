package com.projeto.apibiblioteca.records;

public record UserRequest(String name,
                          String surname,
                          String email,
                          String password,
                          boolean isAdmin){
}
