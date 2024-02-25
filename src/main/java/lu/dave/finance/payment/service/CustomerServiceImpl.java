package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dao.CustomerRepository;
import lu.dave.finance.payment.entity.CustomerEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl  implements CustomerService {

    private  final CustomerRepository customerRepository;

    public List<CustomerEntity> findAll(){
        return customerRepository.findAll();
    }

    @Override
    public CustomerEntity findById(Long id) {
        return  customerRepository.findById(id).orElseThrow();
    }
}
