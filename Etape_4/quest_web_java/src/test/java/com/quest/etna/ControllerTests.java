package com.quest.etna;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quest.etna.DTO.AddressDTO;
import com.quest.etna.DTO.UserDTO;
import com.quest.etna.model.Address;
import com.quest.etna.model.UserRole;
import com.quest.etna.repositories.AddressRepository;
import com.quest.etna.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private AddressRepository addressRepository;

        @Autowired
        private UserRepository userRepository;

        private static String authToken;

        private static String authAdminToken;


        @Test
        @Order(1)
        public void testAuthenticate() throws Exception {
                // Test /register endpoint
                MvcResult registerResult = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                                .content(objectMapper.writeValueAsString(
                                                new UserDTO("testuser", "password", UserRole.ROLE_USER)))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                .andReturn();

                // Log register response
                System.out.println("Register response: " + registerResult.getResponse().getContentAsString());

                // Test duplicate registration doit avoir 409
                mockMvc.perform(MockMvcRequestBuilders.post("/register")
                                .content(objectMapper.writeValueAsString(
                                                new UserDTO("testuser", "password", UserRole.ROLE_USER)))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isConflict())

                                .andDo(MockMvcResultHandlers.print())
                                .andReturn();

                // Test /authenticate endpoint doit retuner 200
                mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                                .content(objectMapper.writeValueAsString(
                                                new UserDTO("testuser", "password", UserRole.ROLE_USER)))

                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty())// Expects a non-empty
                                .andDo(MockMvcResultHandlers.print())
                                .andReturn();

                // Test /authenticate endpoint
                MvcResult authenticateResult = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                                .content(objectMapper.writeValueAsString(
                                                new UserDTO("testuser", "password", UserRole.ROLE_USER)))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty())
                                .andReturn();

                System.out.println("Authenticate response: " + authenticateResult.getResponse().getContentAsString());

                // Extract token from authenticate response
                authToken = objectMapper.readTree(authenticateResult.getResponse().getContentAsString()).get("token")
                                .asText();

                // Test /me endpoint with token
                MvcResult meResult = mockMvc.perform(MockMvcRequestBuilders.get("/me")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                System.out.println("/me response: " + meResult.getResponse().getContentAsString());

        }

        @Test
        @Order(2)
        public void testAuthenticateUserRole() throws Exception {
                // Test /register endpoint
                mockMvc.perform(MockMvcRequestBuilders.post("/register")
                                .content(objectMapper.writeValueAsString(
                                                new UserDTO("adm", "adm", UserRole.ROLE_ADMIN)))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                .andReturn();

                // Test /authenticate endpoint
                MvcResult authenticateResult = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                                .content(objectMapper.writeValueAsString(
                                                new UserDTO("adm", "adm", UserRole.ROLE_ADMIN)))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty())
                                .andReturn();

                System.out.println("Authenticate response: " + authenticateResult.getResponse().getContentAsString());

                // Extract token from authenticate response and store it in authAdminToken
                authAdminToken = objectMapper.readTree(authenticateResult.getResponse().getContentAsString())
                                .get("token")
                                .asText();
        }


        @Test
        @Order(3)
        public void testUser() throws Exception {

                MvcResult registerResult = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                                .content(objectMapper.writeValueAsString(
                                                new UserDTO("userr", "userr", UserRole.ROLE_USER)))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                .andReturn();

                MvcResult authenticateResult = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                                .content(objectMapper.writeValueAsString(
                                                new UserDTO("userr", "userr", UserRole.ROLE_USER)))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty())
                                .andReturn();

                String userAuthToken = objectMapper.readTree(authenticateResult.getResponse().getContentAsString())
                                .get("token")
                                .asText();

                // Test sans token Bearer, doit retourner un statut 401
                mockMvc.perform(get("/user"))
                                .andExpect(status().isUnauthorized());

                // Extract token from authenticate response

                mockMvc.perform(MockMvcRequestBuilders.get("/user")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAuthToken))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                // ramener auth token

                // Test avec ROLE_USER, la suppression doit retourner un statut 403
                mockMvc.perform(delete("/user/3")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isForbidden());

                // // Test avec ROLE_ADMIN, la suppression doit retourner un statut 200
                mockMvc.perform(delete("/user/3")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authAdminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("role", "ROLE_ADMIN"))
                                .andExpect(status().isOk());
        }

        
        @Test
        @Order(4)
        public void testAddress() throws Exception {

                // Test sans token Bearer, la route /address/ retourne bien un statut 401
                mockMvc.perform(get("/address"))
                                .andExpect(status().isUnauthorized());

                // Test avec un token Bearer valide, la route /address/ retourne bien un statut
                // 200
                mockMvc.perform(get("/address")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken))
                                .andExpect(status().isOk());

                // Création d'une adresse pour le test
                AddressDTO addressDTO = new AddressDTO(null, "Rue Test", "88000", "VlleTest", "Testland", null,
                                null, null);
                mockMvc.perform(post("/address")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(addressDTO)))
                                .andExpect(status().isCreated());

                // ANOTHER USER

                mockMvc.perform(MockMvcRequestBuilders.post("/register")
                                .content(objectMapper.writeValueAsString(
                                                new UserDTO("user2", "user2", UserRole.ROLE_USER)))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                .andReturn();

                // Test /authenticate endpoint
                MvcResult authenticateResult = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                                .content(objectMapper.writeValueAsString(
                                                new UserDTO("user2", "user2", UserRole.ROLE_USER)))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty())
                                .andReturn();

                System.out.println("Authenticate response: " + authenticateResult.getResponse().getContentAsString());

                // Extract token from authenticate response and store it in authAdminToken
                String authOtherUserToken = objectMapper.readTree(authenticateResult.getResponse().getContentAsString())
                                .get("token")
                                .asText();

                // Récupération de l'adresse créée pour les tests suivants
                List<Address> addresses = addressRepository.findAll();
                Address createdAddress = addresses.get(addresses.size() - 1);

                // doit returner 403 au lieu de 200
                // doit creer un autre user pour faire un deletge de l'autr user
                mockMvc.perform(delete("/address/{id}", createdAddress.getId())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authOtherUserToken))
                                .andExpect(status().isForbidden());

                // Test ROLE_ADMIN, la suppression d'une adresse qui n'est pas la sienne : 200
                // doit bien un statut 200 au lieu de 404
                mockMvc.perform(delete("/address/{id}", createdAddress.getId())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authAdminToken))
                                .andExpect(status().isOk());
        }

}
