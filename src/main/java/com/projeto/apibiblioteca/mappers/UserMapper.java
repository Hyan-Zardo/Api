package com.projeto.apibiblioteca.mappers;

import com.projeto.apibiblioteca.entities.User;
import com.projeto.apibiblioteca.records.UserRecord;
import com.projeto.apibiblioteca.records.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserRecord toUserRecord(User user);
    User toUser(UserRecord userRecord);

    @Mapping(target = "isAdmin", source = "admin")
    UserRequest toUserRequest(User user);

    @Mapping(target = "admin", source = "isAdmin")
    User toUser(UserRequest userRequest);
}