package eliceproject.bookstore.address;

import eliceproject.bookstore.user.User;
import eliceproject.bookstore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Address create(Address address, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        address.setUser(user);
        return addressRepository.save(address);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Address> findByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Address findById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found with id : " + addressId));
    }

    @Transactional(readOnly = true)
    @Override
    public Address findByUserIdAndIsDefaultTrue(Long userId) {
        return addressRepository.findByUserIdAndIsDefaultTrue(userId);
    }

    @Transactional
    @Override
    public Address update(Long addressId, AddressDTO addressDTO) {
        log.info(addressDTO.toString());
        Address findAddress = findById(addressId);
        findAddress.changeAddress(addressDTO);
        return findAddress;
    }

    @Transactional
    @Override
    public void setDefault(Long userId, Long addressId) {
        try {
            List<Address> findAddressList = findByUserId(userId);
            for (Address findAddress : findAddressList) {
                findAddress.unsetDefault();
            }
            Address defaultAddress = findById(addressId);
            defaultAddress.setDefault();
        } catch (Exception e) {
            log.error("<setDefault> 트랜잭션 롤백 발생 : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Long addressId) {
        addressRepository.deleteById(addressId);
    }

}
