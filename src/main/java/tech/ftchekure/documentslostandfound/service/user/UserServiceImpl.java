package tech.ftchekure.documentslostandfound.service.user;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplate;
import io.github.hobbstech.springdatajpahelper.specification.CustomSpecificationTemplateImplBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tech.ftchekure.documentslostandfound.daos.user.GroupRepository;
import tech.ftchekure.documentslostandfound.daos.user.StationRepository;
import tech.ftchekure.documentslostandfound.daos.user.UserRepository;
import tech.ftchekure.documentslostandfound.entities.user.Group;
import tech.ftchekure.documentslostandfound.entities.user.Station;
import tech.ftchekure.documentslostandfound.entities.user.User;
import tech.ftchekure.documentslostandfound.service.dtos.ChangePasswordDto;
import tech.ftchekure.documentslostandfound.service.dtos.UserDto;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final StationRepository stationRepository;

    public UserServiceImpl(UserRepository userRepository, GroupRepository groupRepository, StationRepository stationRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.stationRepository = stationRepository;
    }

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Page<User> findAll(ListRequestTemplate listRequestTemplate) {
        val pageRequest = listRequestTemplate.getPageRequest();
        val spec = new CustomSpecificationTemplateImplBuilder<User>()
                .buildSpecification(listRequestTemplate.getSearch());
        if (isNull(listRequestTemplate.getSearch()))
            return userRepository.findAll(pageRequest);
        else
            return userRepository.findAll(spec, pageRequest);

    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(UserDto userDto) {
        val user = new User();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAddress(userDto.getAddress());
        user.setActive(userDto.getActive());
        user.setGroup(getGroup(userDto.getGroupId()));
        user.setStation(getStation(userDto.getStationId()));
        return userRepository.save(user);
    }

    private Group getGroup(Long groupId) {
        return groupRepository.findById(groupId).get();
    }

    private Station getStation(Long stationId) {
        return stationRepository.findById(stationId).get();
    }

    @Override
    public User update(Long id, UserDto userDto) {
        val user = findById(id);
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAddress(userDto.getAddress());
        user.setActive(userDto.getActive());
        user.setGroup(getGroup(userDto.getGroupId()));
        user.setStation(getStation(userDto.getStationId()));
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User changePassword(String username, ChangePasswordDto changePasswordDto) {
        Assert.isTrue(changePasswordDto.getConfirmPassword().equals(changePasswordDto.getNewPassword()),
                "New password and Confirm Password do not match");

        requireNonNull(changePasswordDto.getOldPassword(), "Old password should be provided");

        val user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User was not found"));

        Assert.isTrue(passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword()),
                "Invalid old password provided");

        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));

        return userRepository.save(user);
    }

    @Override
    public User changeActivationStatus(Long id, boolean active) {
        val user = userRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("User not found"));
        user.setActive(active);
        return userRepository.save(user);
    }

    @Override
    public Collection<User> findByGroupName(String groupName) {

        return userRepository.findAllByGroup_Name(groupName);
    }
}
