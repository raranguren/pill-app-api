package com.pillapp.api.dto.responses.data;

import com.pillapp.api.domain.entities.User;

public class UserProfileDTO {

    public String userName;

    public UserProfileDTO(User user) {
        this.userName = user.userName;
    }

}
