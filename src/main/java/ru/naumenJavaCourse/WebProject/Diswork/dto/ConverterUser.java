package ru.naumenJavaCourse.WebProject.Diswork.dto;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.naumenJavaCourse.WebProject.Diswork.models.User;
@Component
public class ConverterUser {

    private final ModelMapper modelMapper;

    public ConverterUser(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO convertToUserDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }
}
