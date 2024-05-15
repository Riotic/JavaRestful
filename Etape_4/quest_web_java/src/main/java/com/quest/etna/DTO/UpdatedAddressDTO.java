package com.quest.etna.DTO;

import java.util.Date;

public record UpdatedAddressDTO(Integer id, String street, String postalCode, String city, String country, Integer user_id, Date updatedDate ) {


}
