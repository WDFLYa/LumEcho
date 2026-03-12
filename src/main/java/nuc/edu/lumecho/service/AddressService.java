package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.entity.Address;

import java.util.List;

public interface AddressService {
    void add(Address address);
    List<Address> getByUserId();
    void delete(Long id);
}
