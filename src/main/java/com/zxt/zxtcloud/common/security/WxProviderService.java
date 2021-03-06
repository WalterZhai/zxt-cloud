package com.zxt.zxtcloud.common.security;

import com.zxt.zxtcloud.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Service
public class WxProviderService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.zxt.zxtcloud.system.entity.User user = userRepository.findUserByLoginNameAndIsLockedAndIsDelete(username,0, 0);
        if(user == null){
            return null;
        }
        String dbPassword = user.getPassword();
        return new User(username,dbPassword,true,true,true,true,new ArrayList<>());
    }
}
