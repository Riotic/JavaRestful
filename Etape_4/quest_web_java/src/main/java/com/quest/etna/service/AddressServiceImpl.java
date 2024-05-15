package com.quest.etna.service;

import com.quest.etna.DTO.AddressDTO;
import com.quest.etna.model.Address;
import com.quest.etna.model.User;
import com.quest.etna.repositories.AddressRepository;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public AddressDTO createAddress(User user, AddressDTO addressDTO) {

        Address address = new Address();
        address.setStreet(addressDTO.street());
        address.setPostalCode(addressDTO.postalCode());
        address.setCity(addressDTO.city());
        address.setCountry(addressDTO.country());
        address.setUser(user);
        // address.setUser(addressDTO.id());

        // Set the creationDate to the current date and time
        address.setCreationDate(new Date());

        // Save the Address object to the database
        Address savedAddress = addressRepository.save(address);

        return modelToDto(savedAddress);
    }

    // reconstuire DTO
    private AddressDTO modelToDto(Address address) {
        return new AddressDTO(address.getId(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity(),
                address.getCountry(),
                address.getUser().getId(),
                address.getCreationDate(),
                address.getUpdatedDate());
    }

}
