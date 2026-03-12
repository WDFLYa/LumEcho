package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.mapper.AddressMapper;
import nuc.edu.lumecho.model.entity.Address;
import nuc.edu.lumecho.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;
    @Override
    public void add(Address address) {
        addressMapper.insert(address);
    }

    @Override
    public List<Address> getByUserId() {
        return addressMapper.listByUserId(WdfUserContext.getCurrentUserId());
    }

    @Override
    public void delete(Long id) {
        addressMapper.delete(id);
    }


}
