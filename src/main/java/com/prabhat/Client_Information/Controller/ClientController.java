package com.prabhat.Client_Information.Controller;

import com.prabhat.Client_Information.Exception.ClientNotFoundException;
import com.prabhat.Client_Information.Model.Client;
import com.prabhat.Client_Information.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping()
    public Iterable<Client> findAll(){
        return clientRepository.findAll();
    }

    @GetMapping("/mobileNumber/{mobileNumber}")
    public Client findByMobileNumber(@PathVariable String mobileNumber){
        return clientRepository.findByMobileNumber(mobileNumber);
    }

    @GetMapping("/emailId/{emailId}")
    public Client findByEmailId(@PathVariable String emailId){
        return clientRepository.findByEmailId(emailId);
    }

    @GetMapping("/{id}")
    public Client GetEachById(@PathVariable Long id) throws ClientNotFoundException {
        return clientRepository.findById(id)
                .orElseThrow(()-> new ClientNotFoundException("The client is not found with id : "+id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Client saveClient(@RequestBody Client client){
        return clientRepository.save(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id,@RequestBody Client client1) throws ClientNotFoundException{
        Client client = clientRepository.findById(id)
                .orElseThrow(()-> new ClientNotFoundException("The client is not found with id : "+id));

        client.setName(client1.getName());
        client.setMobileNumber(client1.getMobileNumber());
        client.setEmailId(client1.getEmailId());
        client.setAddress(client1.getAddress());
        clientRepository.save(client);
        return new ResponseEntity<>("The information of the Client is Updated",HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) throws ClientNotFoundException{
        Client client = clientRepository.findById(id)
                .orElseThrow(()-> new ClientNotFoundException("The client is not found with id : "+id));
        clientRepository.delete(client);
        return new ResponseEntity<>("Client's data is deleted now",HttpStatus.OK);
    }




}

