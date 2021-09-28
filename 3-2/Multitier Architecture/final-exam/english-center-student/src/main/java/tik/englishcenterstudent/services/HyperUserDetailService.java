package tik.englishcenterstudent.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tik.englishcenterstudent.constants.Constants;
import tik.englishcenterstudent.constants.Messages;
import tik.englishcenterstudent.dto.SignUpRequest;
import tik.englishcenterstudent.models.Auth;
import tik.englishcenterstudent.models.User;
import tik.englishcenterstudent.repositories.AuthorityRepository;
import tik.englishcenterstudent.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        authorityRepository.saveAll(user.getAuths());
    }

    public String createUser(SignUpRequest request) {
        Optional<User> userOpt = userRepository.findByUsernameOrPhoneOrIdentity(request.username, request.phone, request.identity);
        System.out.println(request);
        if (!request.password.equals(request.rePassword))
            return "Password not match";
        if (request.password.length() < 8)
            return "Password to short";
        if (userOpt.isPresent())
            return "Username or Phone or Identity already used!";
        User user = new User(
                request.username,
                request.password,
                new ArrayList<>()
        );
        user.identity = request.identity;
        user.name = request.name;
        user.phone = request.phone;
        user.setAuths(Stream.of(new Auth(user, Constants.ROLE_USER)).collect(Collectors.toList()));
        try {
            createUser(user);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return null;
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
