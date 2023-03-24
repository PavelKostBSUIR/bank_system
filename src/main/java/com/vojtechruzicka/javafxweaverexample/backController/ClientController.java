package com.vojtechruzicka.javafxweaverexample.backController;

import com.vojtechruzicka.javafxweaverexample.entity.domain.Client;
import com.vojtechruzicka.javafxweaverexample.entity.dto.UpdateClientDto;
import com.vojtechruzicka.javafxweaverexample.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    public Client get(Long id) {
        return clientService.get(id);
    }

    public List<Client> getAll() {
        return clientService.getAll();
    }

    public Long post(Client client) {
        return clientService.add(client);
    }

    public void put(Long id, UpdateClientDto updateClientDto) {
        clientService.update(id, updateClientDto);
    }

    public void delete(Long id) {
        clientService.delete(id);
    }
}
