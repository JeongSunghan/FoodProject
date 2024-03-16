package com.example.food.service;

import com.example.food.entity.Address;
import java.util.List;

public interface AddressService {
	
    List<Address> findAll();
    
    void insert(Address address);
    
    void update(Address address);
    
    Address findById(Long bid);
}