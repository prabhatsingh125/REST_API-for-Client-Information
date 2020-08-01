package com.prabhat.Client_Information.Controller;

import com.prabhat.Client_Information.Exception.ClientNotFoundException;
import com.prabhat.Client_Information.Exception.InvalidMobileNumberException;
import com.prabhat.Client_Information.Model.Client;
import com.prabhat.Client_Information.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public Client findByMobileNumber(@PathVariable String mobileNumber) throws InvalidMobileNumberException {
        if(isValid(mobileNumber)) {
            return clientRepository.findByMobileNumber(mobileNumber);
        }
        throw new InvalidMobileNumberException("This Mobile Number is invalid : "+mobileNumber);

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

    @PutMapping("/mobileNumber/{mobileNumber}")
    ResponseEntity<?> updateClientByMobile(@PathVariable String mobileNumber, @RequestBody Client client1){
        Client client = clientRepository.findByMobileNumber(mobileNumber);
        client.setName(client1.getName());
        client.setMobileNumber(client1.getMobileNumber());
        client.setEmailId(client1.getEmailId());
        client.setAddress(client1.getAddress());
        clientRepository.save(client);
        return new ResponseEntity<>("Client's information is updated",HttpStatus.OK);

    }

    
    @DeleteMapping("/mobileNumber/{mobileNumber}")
    public ResponseEntity<?> deleteByMobileNumber(@PathVariable String mobileNumber)
    {
        Client client=clientRepository.findByMobileNumber(mobileNumber);
        clientRepository.delete(client);
        return new ResponseEntity<>("Client is deleted by mobilenumber : "+mobileNumber,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) throws ClientNotFoundException{
        Client client = clientRepository.findById(id)
                .orElseThrow(()-> new ClientNotFoundException("The client is not found with id : "+id));
        clientRepository.delete(client);
        return new ResponseEntity<>("Client's data is deleted now",HttpStatus.OK);
    }

    public boolean isValid(String mobileNumber)
    {
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(mobileNumber);
        return (m.find() && m.group().equals(mobileNumber));
    }




}

