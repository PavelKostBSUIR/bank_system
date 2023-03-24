package com.vojtechruzicka.javafxweaverexample.service;

import com.vojtechruzicka.javafxweaverexample.entity.domain.Client;
import com.vojtechruzicka.javafxweaverexample.entity.dto.UpdateClientDto;
import com.vojtechruzicka.javafxweaverexample.mapper.ClientMapper;
import com.vojtechruzicka.javafxweaverexample.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Long add(Client client) {
        return clientRepository.save(client).getId();
    }

    public Client get(Long id) {
        return clientRepository.findById(id).orElseThrow(NoSuchFieldError::new);
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    public void update(Long id, UpdateClientDto clientDto) {
        // Client client = clientRepository.getOne(id);
        Client client = new Client();
        clientMapper.UpdateClientFromDto(clientDto, client);
        client.setId(id);
        clientRepository.save(client);
    }
}
