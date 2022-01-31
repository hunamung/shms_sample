package com.skt.shms.tools;

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.skt.shms.user.model.User;
import com.skt.shms.user.dao.UserDao;

/**
 * H2 초기계정 세팅
 * @author taes
 *
 */
@Component
public class DataInitializer implements ApplicationRunner {
    @Resource (name="UserDao")
    private UserDao UserDao;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        PasswordEncoder passwordEncoder;
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        
        User newUser = new User();
        newUser.setUsername("taes");
        newUser.setPassword(passwordEncoder.encode("luke"));
        newUser.setUserType(0);
        newUser.setDate(new Date());
        UserDao.save(newUser);
    }
}
