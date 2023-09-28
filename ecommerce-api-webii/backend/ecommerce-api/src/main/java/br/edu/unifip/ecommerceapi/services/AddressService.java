package br.edu.unifip.ecommerceapi.services;

import br.edu.unifip.ecommerceapi.dtos.EnderecoDTO;
import br.edu.unifip.ecommerceapi.models.Endereco;
import br.edu.unifip.ecommerceapi.models.User;
import br.edu.unifip.ecommerceapi.repositories.EnderecoRepository;
import br.edu.unifip.ecommerceapi.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {
    private final EnderecoRepository addressRepository;

    private final UserRepository userRepository;

    public AddressService(EnderecoRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public EnderecoDTO getAddressByUserId(UUID userId) {
        Optional<Endereco> address = addressRepository.findByUsername_Id(userId);
        return address.map(this::convertToDTO).orElse(null);
    }

    public EnderecoDTO createAddress(UUID userId, EnderecoDTO enderecoDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Endereco address = convertToEntity(enderecoDTO);
        address.setUsername(user);

        return convertToDTO(addressRepository.save(address));
    }

    public EnderecoDTO updateAddress(UUID userId, EnderecoDTO enderecoDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Endereco existingAddress = addressRepository.findByUsername_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));

        existingAddress.setRua(enderecoDTO.rua());
        existingAddress.setCidade(enderecoDTO.cidade());

        return convertToDTO(addressRepository.save(existingAddress));
    }

    public void deleteAddress(UUID userId) {
        Endereco address = addressRepository.findByUsername_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));

        addressRepository.delete(address);
    }

    private EnderecoDTO convertToDTO(Endereco address) {
        return new EnderecoDTO(address.getUsername().getId(), address.getRua(), address.getCidade());
    }

    private Endereco convertToEntity(EnderecoDTO enderecoDTO) {
        Endereco address = new Endereco();
        address.setRua(enderecoDTO.rua());
        address.setCidade(enderecoDTO.cidade());
        return address;
    }
}
