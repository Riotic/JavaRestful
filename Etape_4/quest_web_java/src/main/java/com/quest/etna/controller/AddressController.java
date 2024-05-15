package com.quest.etna.controller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quest.etna.DTO.AddressDTO;
import com.quest.etna.DTO.ErrorResponseDTO;
import com.quest.etna.DTO.UpdatedAddressDTO;
import com.quest.etna.config.exception.NoRightsException;
import com.quest.etna.config.exception.NotFoundException;
import com.quest.etna.model.Address;
import com.quest.etna.model.User;
import com.quest.etna.model.UserRole;
import com.quest.etna.repositories.AddressRepository;
import com.quest.etna.repositories.UserRepository;
import com.quest.etna.service.AddressService;

@RestController
@RequestMapping("/address") // préfixe URL pour les requêtes
public class AddressController {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressService addressService;

    // Injecte les dépendances automatiquement
    public AddressController(AddressRepository addressRepository, UserRepository userRepository,
            AddressService addressService) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.addressService = addressService;
    }

    @GetMapping // Récupère toutes les adresses
    public ResponseEntity<?> getAllAddresses(Principal principal) {
        Optional<User> connectedUserOptional = userRepository.findByUsername(principal.getName());
        if (connectedUserOptional.isEmpty()) {
            throw new NotFoundException("Utilisateur inconnu.");
        }
        User connectedUser = connectedUserOptional.get();

        List<Address> addresses;
        if (connectedUser.getRole().equals(UserRole.ROLE_ADMIN)) {
            addresses = addressRepository.findAll();
        } else {
            addresses = addressRepository.findByUser(connectedUser);
        }

        List<AddressDTO> addressDTOs = addresses.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(Principal principal, @PathVariable Long id) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (addressOptional.isEmpty()) {
            throw new NotFoundException("Addresse inconnue.");
        }
        Address address = addressOptional.get();

        Optional<User> connectedUserOptional = userRepository.findByUsername(principal.getName());
        if (connectedUserOptional.isEmpty()) {
            throw new NotFoundException("Utilisateur inconnu.");
        }

        User connectedUser = connectedUserOptional.get();
        if (!connectedUser.getRole().equals(UserRole.ROLE_ADMIN) && !address.getUser().equals(connectedUser)) {
            throw new NoRightsException("Vous n'avez pas les droits requis.");
        }

        AddressDTO addressDTO = modelToDto(address);
        return ResponseEntity.ok(addressDTO);
    }

    @PostMapping // POST qui crée une nouvelle adresse
    public ResponseEntity<?> createAddress(Principal principal, @RequestBody AddressDTO addressDTO) {
        Optional<User> connectedUserOptional = userRepository.findByUsername(principal.getName());
        if (connectedUserOptional.isEmpty()) {
            throw new NotFoundException("Utilisateur inconnu.");
        }
        User connectedUser = connectedUserOptional.get();
        // Call the addressService to create the address
        AddressDTO createdAddress = addressService.createAddress(connectedUser, addressDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(Principal principal, @PathVariable Long id,
            @RequestBody AddressDTO updatedAddressDTO) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isEmpty()) {
            throw new NotFoundException("Addresse inconnue.");
        }
        Address address = optionalAddress.get();

        Optional<User> connectedUserOptional = userRepository.findByUsername(principal.getName());
        if (connectedUserOptional.isEmpty()) {
            throw new NotFoundException("Utilisateur inconnu.");
        }

        User connectedUser = connectedUserOptional.get();
        if (connectedUser.getRole().equals(UserRole.ROLE_ADMIN) || address.getUser().equals(connectedUser)) {
            address.setStreet(updatedAddressDTO.street() != null ? updatedAddressDTO.street() : address.getStreet());
            address.setPostalCode(updatedAddressDTO.postalCode() != null ? updatedAddressDTO.postalCode() : address.getPostalCode());
            address.setCity(updatedAddressDTO.city() != null ? updatedAddressDTO.city() : address.getCity());
            address.setCountry(updatedAddressDTO.country() != null ? updatedAddressDTO.country() : address.getCountry());
            address.setUpdatedDate(new Date());

            Address savedAddress = addressRepository.save(address);
            AddressDTO updatedDto = modelToDto(savedAddress);
            return ResponseEntity.ok(updatedDto);
        } else {
            throw new NoRightsException("Vous n'avez pas les droits requis.");
        }
    }

    @DeleteMapping("/{id}") // supprime une adresse par ID
    public ResponseEntity<?> deleteAddress(Principal principal, @PathVariable Long id) {
        Optional<Address> optionalAddress = addressRepository.findById(id); // Recherche l'adresse dans la base de
        // données par ID
        if(optionalAddress.isEmpty()){
            throw new NotFoundException("Addresse inconnue.");
        }

        Optional<User> connectedUserOptional = userRepository.findByUsername(principal.getName());
        User connectedUser = connectedUserOptional.get();
        Address address = optionalAddress.get();

        if (connectedUser.getRole().equals(UserRole.ROLE_ADMIN) || address.getUser().equals(connectedUser)) {
            addressRepository.deleteById(id); // Supprime l'adresse par ID
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok().body(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    // Méthode pour convertir un objet Address en AddressDTO
    private AddressDTO modelToDto(Address address) {
        return new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity(),
                address.getCountry(),
                address.getUser().getId(),
                address.getCreationDate(),
                address.getUpdatedDate());
    }

}
