package com.quest.etna.DTO;

import java.util.Date;

//record = immutable une fois utilisé, les objects peuvent pas etre modifié

public record AddressDTO(Integer id, String street, String postalCode, String city, String country, Integer user_id,  Date creationDate, Date updatedDate) {
}
