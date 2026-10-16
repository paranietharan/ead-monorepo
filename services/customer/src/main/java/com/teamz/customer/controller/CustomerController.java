package com.teamz.customer.controller;

import com.teamz.customer.DTO.CustomerDTO;
import com.teamz.customer.DTO.UpdateCustomerDetailsDTO;
import com.teamz.customer.entity.Address;
import com.teamz.customer.exceptions.CustomerNotFoundException;
import com.teamz.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") Long id) {
        try {
            CustomerDTO customerDTO = customerService.getCustomerByID(id);
            return ResponseEntity.ok(customerDTO); // Return the customer data if found
        } catch (CustomerNotFoundException e) {
            // Return a proper error response with status and message
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage()); // Include the exception message in the response
        }
    }


    @PostMapping("/{id}/address")
    public ResponseEntity<?> addAddressForACustomer(@PathVariable("id") Long id, @RequestBody Address address) {
        try{
            return ResponseEntity.ok(customerService.addAddressForACustomer(id, address));
        }catch (CustomerNotFoundException e) {
            // Return a proper error response with status and message
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage()); // Include the exception message in the response
        }

    }


    @PatchMapping("/customerDetails/{id}")
    public ResponseEntity<?> updateCustomerDetails(@PathVariable("id") Long id, @RequestBody UpdateCustomerDetailsDTO updateCustomerDetailsDTO){
        try{
            customerService.updateCustomerDetails(id,updateCustomerDetailsDTO);
            return ResponseEntity.ok("Customer details Updated Successfully");
        }catch (CustomerNotFoundException e) {
            // Return a proper error response with status and message
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage()); // Include the exception message in the response
        }
    }

    @PatchMapping("/customerAddress/{id}")
    public ResponseEntity<?> updateCustomerAddress(@PathVariable("id") Long id, @RequestBody Address address){
        try{
            customerService.updateCustomerAddress(id,address);
            return ResponseEntity.ok("Customer Address Updated Successfully");
        }catch (CustomerNotFoundException e) {
            // Return a proper error response with status and message
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage()); // Include the exception message in the response
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteCustomerId(@PathVariable("id") Long id){
        try{
            customerService.DeleteCustomerId(id);
            return ResponseEntity.ok("Customer Deleted Successfully");
        }catch (CustomerNotFoundException e) {
            // Return a proper error response with status and message
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage()); // Include the exception message in the response
        }

    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<?> getAllCustomers(){
        try{
            return ResponseEntity.ok(customerService.getAllCustomers());
        }catch (CustomerNotFoundException e) {
            // Return a proper error response with status and message
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage()); // Include the exception message in the response
        }


    }
}
