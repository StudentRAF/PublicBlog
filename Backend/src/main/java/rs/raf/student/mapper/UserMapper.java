package rs.raf.student.mapper;

import rs.raf.student.dto.user.UserGetDto;
import rs.raf.student.model.User;

public class UserMapper {

    public UserGetDto toUserDto(User user) {
        UserGetDto userGetDto = new UserGetDto();

        userGetDto.setId(user.getId());
        userGetDto.setFirstName(user.getFirstName());
        userGetDto.setLastName(user.getLastName());
        userGetDto.setUsername(user.getUsername());

        return userGetDto;
    }

}
