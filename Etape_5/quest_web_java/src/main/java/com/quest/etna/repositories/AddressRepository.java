package com.quest.etna.repositories;

import com.quest.etna.model.Address;
import com.quest.etna.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    
    List<Address> findByUser(User user);

    List<Address> findByCity(String city);

    List<Address> findByCountry(String country);

    List<Address> findByPostalCode(String postalCode);

    List<Address> findByStreet(String street);

    List<Address> findByUpdatedDateAfter(Date date);

    List<Address> findByCreationDateBefore(Date date);

    Optional<Address> findById(Integer id);

    void deleteById(Integer id);
}
