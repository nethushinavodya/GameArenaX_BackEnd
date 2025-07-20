package org.example.gamearenax_backend.service.impl;

import jakarta.transaction.Transactional;
import org.example.gamearenax_backend.dto.UserDTO;
import org.example.gamearenax_backend.entity.User;
import org.example.gamearenax_backend.repository.UserRepo;
import org.example.gamearenax_backend.service.UserService;
import org.example.gamearenax_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    public int saveUser(UserDTO userDTO) {
        if (userRepo.existsByEmail(userDTO.getEmail())){
            return VarList.Not_Acceptable;
        }else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepo.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }

    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        return modelMapper.map(user,UserDTO.class);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        return modelMapper.map(users,new TypeToken<List<User>>(){}.getType());
    }

    @Override
    public int updateUser(UserDTO userDTO) {
       try {
           System.out.println(userDTO.getUsername() + " " + userDTO.getCountry() + " " + userDTO.getRole());
           userRepo.updateUser(userDTO.getEmail(), userDTO.getRole(), userDTO.getCountry(),userDTO.getUsername());
           return VarList.Created;
       }catch (Exception e){
           throw new RuntimeException(e.getMessage());
       }
    }

    @Override
    public User SearchByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public boolean ifEmailExists(String email) {
        return userRepo.existsByEmail(email);
    }
}
