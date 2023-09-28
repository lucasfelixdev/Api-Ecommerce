package br.edu.unifip.ecommerceapi.controllers;

import br.edu.unifip.ecommerceapi.dtos.EnderecoDTO;
import br.edu.unifip.ecommerceapi.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users/{userId}/enderco")
public class EnderecoController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<EnderecoDTO> getAddressByUserId(@PathVariable UUID userId) {
        EnderecoDTO addressDTO = addressService.getAddressByUserId(userId);
        if (addressDTO != null) {
            return ResponseEntity.ok(addressDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> createAddress(@PathVariable UUID userId, @RequestBody EnderecoDTO addressDTO) {
        EnderecoDTO createdAddress = addressService.createAddress(userId, addressDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @PutMapping
    public ResponseEntity<EnderecoDTO> updateAddress(@PathVariable UUID userId, @RequestBody EnderecoDTO addressDTO) {
        EnderecoDTO updatedAddress = addressService.updateAddress(userId, addressDTO);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID userId) {
        addressService.deleteAddress(userId);
        return ResponseEntity.noContent().build();
    }
}
