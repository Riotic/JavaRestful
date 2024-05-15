package com.quest.etna.service;

import com.quest.etna.DTO.AddressDTO;
import com.quest.etna.model.User;

public interface AddressService {
    AddressDTO createAddress(User connectedUser, AddressDTO addressDTO);
}
