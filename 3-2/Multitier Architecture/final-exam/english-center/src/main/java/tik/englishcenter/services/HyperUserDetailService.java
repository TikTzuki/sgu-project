package tik.englishcenter.services;

import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lombok.Data;
import tik.englishcenter.constants.Messages;
import tik.englishcenter.models.User;
import tik.englishcenter.repositories.AuthorityRepository;
import tik.englishcenter.repositories.UserRepository;

@Service
public class HyperUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public void createUser(final User user) {
        validateUserDetail(user);
        userRepository.save(user);
        System.out.println(user.getAuths());
        authorityRepository.saveAll(user.getAuths());
    }

//	public void createUser(final UserDetails user){
//		validateUserDetail(user);
//		userRepository.save(user);
//	}

    public void validateUserDetail(UserDetails user) {
        Assert.hasText(user.getUsername(), "Username may not be empty or null");
        validateAuthorities(user.getAuthorities());
    }

    public void updateUser(final User user) {
        userRepository.save(user);
    }

    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    private void validateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Authorities list must not be null");
        for (GrantedAuthority authority : authorities) {
            Assert.notNull(authority, "Authorities list contains a null entry");
            Assert.hasText(authority.getAuthority(), "getAuthority() method must return a non-empty string");
        }
    }

    public boolean isEnabled(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? user.isEnabled() : false;
    }

    public void changePassword(String oldPassword, String newPassword) throws Exception {
        throw new Exception(Messages.NOT_IMPLEMENT);
    }

//    protected void sendResetPasswordEmail(UserEntity user) {
//        SecureToken secureToken= secureTokenService.createSecureToken();
//        secureToken.setUser(user);
//        secureTokenRepository.save(secureToken);
//        ForgotPasswordEmailContext emailContext = new ForgotPasswordEmailContext();
//        emailContext.init(user);
//        emailContext.setToken(secureToken.getToken());
//        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
//        try {
//            emailService.sendMail(emailContext);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }

}
