package com.khoders.asset.mapper;

import com.khoders.asset.dto.UserAccountDto;
import com.khoders.asset.entities.UserAccount;
import com.khoders.asset.utils.SpringUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserAccount toEntity(UserAccountDto dto) {
        UserAccount user = new UserAccount();
        if(dto.getId() != null){
            user.setId(SpringUtils.stringToUUID(dto.getId()));
        }
        user.setRefNo(user.getRefNo());
        user.setEmailAddress(dto.getEmailAddress());
        user.setPrimaryNumber(dto.getPrimaryNumber());
        user.setSecondNumber(dto.getSecondNumber());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        return user;
    }

    public UserAccountDto toDto(UserAccount user) {
        UserAccountDto dto = new UserAccountDto();
        if(user == null) {
            return null;
        }
        dto.setId(user.getId().toString());
        dto.setRefNo(user.getRefNo());
        dto.setEmailAddress(user.getEmailAddress());
        dto.setPrimaryNumber(user.getPrimaryNumber());
        dto.setSecondNumber(user.getSecondNumber());
        return dto;
    }
}
