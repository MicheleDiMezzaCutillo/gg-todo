package it.mikedmc.ggtodo.dto.builder;

import it.mikedmc.ggtodo.dto.UserDto;
import it.mikedmc.ggtodo.model.User;

public class UserDtoBuilder {

    public static User fromDtoToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public static UserDto fromEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

}
