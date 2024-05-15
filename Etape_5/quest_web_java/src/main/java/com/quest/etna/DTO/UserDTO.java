package com.quest.etna.DTO;

import com.quest.etna.model.UserRole;

public record UserDTO(String username, String password, UserRole role) {

    // public UserRole getRole() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'getRole'");
    // }

}
    

