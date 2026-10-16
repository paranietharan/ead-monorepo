package com.teamz.customer.service;

import com.teamz.customer.DTO.CustomerDTO;
import com.teamz.customer.DTO.UpdateCustomerDetailsDTO;
import com.teamz.customer.entity.Address;
import com.teamz.customer.entity.User;
import com.teamz.customer.exceptions.CustomerNotFoundException;
import com.teamz.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO addAddressForACustomer(Long id, Address address) {
        User user = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));
        user.setAddress(address);
        User updatedUser = customerRepository.save(user);

        return CustomerDTO.builder()
                .name(updatedUser.getName())
                .userName(updatedUser.getUsername())
                .email(updatedUser.getEmail())
                .address(updatedUser.getAddress())
                .phoneNo(updatedUser.getPhoneNo())
                .build();
    }

    public CustomerDTO getCustomerByID(Long id) {
        User user=customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));

        return CustomerDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .address(user.getAddress() != null ? user.getAddress() : null)
                .phoneNo(user.getPhoneNo())
                .build();
    }

    public void updateCustomerDetails(Long id, UpdateCustomerDetailsDTO updateCustomerDetailsDTO) {
        User user=customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));

        if (updateCustomerDetailsDTO.getName() != null){
            user.setName(updateCustomerDetailsDTO.getName());
        }
        if (updateCustomerDetailsDTO.getPhoneNo()!=null){
            user.setPhoneNo(updateCustomerDetailsDTO.getPhoneNo());
        }

        User updatedUser=customerRepository.save(user);
    }


    public void updateCustomerAddress(Long id, Address address) {
        User user = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));

        Address existingAddress = user.getAddress();

        if (address.getHouseNo() != null) {
            existingAddress.setHouseNo(address.getHouseNo());
        }
        if (address.getStreet() != null) {
            existingAddress.setStreet(address.getStreet());
        }
        if (address.getCity() != null) {
            existingAddress.setCity(address.getCity());
        }
        if (address.getDistrict() != null) {
            existingAddress.setDistrict(address.getDistrict());
        }
        if (address.getProvince() != null) {
            existingAddress.setProvince(address.getProvince());
        }
        if (address.getPostalCode() != null) {
            existingAddress.setPostalCode(address.getPostalCode());
        }

        user.setAddress(existingAddress);

        customerRepository.save(user);
    }

    public void DeleteCustomerId(Long id) {
        customerRepository.deleteById(id);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customers= new ArrayList<>();
        List<User> users= customerRepository.findAll();
        for (User user:users){
            CustomerDTO customer= CustomerDTO.builder()
                    .address(user.getAddress())
                    .userName(user.getName())
                    .phoneNo(user.getPhoneNo())
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
            customers.add(customer);
        }
        return customers;
    }
}
