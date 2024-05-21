package rs.raf.student.mapper;

import rs.raf.student.dto.user.UserCreateDto;
import rs.raf.student.dto.user.UserGetDto;
import rs.raf.student.dto.user.UserUpdateDto;
import rs.raf.student.model.User;
import rs.raf.student.utils.Utils;

public class UserMapper {

    public UserGetDto mapDto(User user) {
        UserGetDto userGetDto = new UserGetDto();

        userGetDto.setId(user.getId());
        userGetDto.setFirstName(user.getFirstName());
        userGetDto.setLastName(user.getLastName());
        userGetDto.setUsername(user.getUsername());

        return userGetDto;
    }

    public User map(User user, UserCreateDto createDto) {
        user.setFirstName(createDto.getFirstName());
        user.setLastName(createDto.getLastName());
        user.setUsername(createDto.getUsername());
        user.setPassword(Utils.encodePassword(createDto.getPassword()));

        return user;
    }

    public User map(User user, UserUpdateDto updateDto) {
        user.setFirstName(updateDto.getFirstName());
        user.setLastName(updateDto.getLastName());
        user.setUsername(updateDto.getUsername());
        user.setPassword(Utils.encodePassword(updateDto.getPassword()));

        return user;
    }

}
