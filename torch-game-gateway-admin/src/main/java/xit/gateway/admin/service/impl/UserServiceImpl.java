package xit.gateway.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xit.gateway.admin.domain.User;
import xit.gateway.admin.repository.UserRepository;
import xit.gateway.admin.service.UserService;
import xit.gateway.exception.requester.BadRequestException;
import xit.gateway.exception.user.UserNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findOne(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        List<User> userList = this.userRepository.findAll();
        return userList;
    }

    @Override
    public void create(User resources) {
        if (userRepository.findByUsername(resources.getUsername()) != null) {
            throw new BadRequestException("用户已存在");
        }

        resources.setPwd(BCrypt.hashpw(resources.getPwd(), BCrypt.gensalt()));
        userRepository.save(resources);
    }

    @Override
    public void update(User resources) {
        User user = userRepository.findByUsername(resources.getUsername());
        if (userRepository.findByUsername(resources.getUsername()) == null) {
            throw new BadRequestException("用户不存在");
        }

        user.setUsername(resources.getUsername());
        user.setServices(resources.getServices());
        if (resources.getPwd() != null) {
            user.setPwd(BCrypt.hashpw(resources.getPwd(), BCrypt.gensalt()));
        }

        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        userRepository.deleteAllByIdIn(ids);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userInfo = userRepository.findByUsername(username);
        if (userInfo == null) {
            throw new UserNotFoundException();
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .authorities(userInfo.getServices().stream().map(xit.gateway.admin.domain.Service::getName)
                        .toArray(String[]::new)).build();

    }
}

