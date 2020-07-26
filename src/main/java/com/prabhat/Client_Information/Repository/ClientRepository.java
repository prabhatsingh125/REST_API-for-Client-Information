package com.prabhat.Client_Information.Repository;

import com.prabhat.Client_Information.Model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client,Long> {
     Client findByMobileNumber(String mobileNumber);
     Client findByEmailId(String emailId);

}
