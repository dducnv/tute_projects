package com.example.tute_backend.config.oauth2;

import com.example.tute_backend.config.oauth2.user.OAuth2UserInfo;
import com.example.tute_backend.config.oauth2.user.OAuth2UserInfoFactory;
import com.example.tute_backend.entity.Role;
import com.example.tute_backend.entity.User;
import com.example.tute_backend.entity.my_enum.AuthProvider;
import com.example.tute_backend.entity.my_enum.UserStatus;
import com.example.tute_backend.exception.OAuth2AuthenticationProcessingException;
import com.example.tute_backend.repository.RoleRepository;
import com.example.tute_backend.repository.UserRepository;
import com.example.tute_backend.service.impl.UserServiceImpl;
import com.example.tute_backend.utils.GeneratingPassword;
import com.example.tute_backend.utils.SlugGenerating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    public static final Pattern STUDENT_FPT_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Za-z0-9._%+-]+@fpt.edu.vn$", Pattern.CASE_INSENSITIVE);

    public static final Pattern STAFF_FPT_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Za-z0-9._%+-]+@fe.edu.vn$", Pattern.CASE_INSENSITIVE);

    private static final String[] IS_ADMIN = {"dduc7th.dec@gmail.com", "ducnvth2008042@fpt.edu.vn","quangnhth2008059@fpt.edu.vn"};

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User)
    {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User account;
        if (userOptional.isPresent()) {
            account = userOptional.get();
            if (!account.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        account.getProvider() + " account. Please use your " + account.getProvider() +
                        " account to login.");
            }
            account = updateExistingUser(account, oAuth2UserInfo);
        } else {
            account = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(account, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setAvatar(oAuth2UserInfo.getImageUrl());
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        String password = String.valueOf(GeneratingPassword.generatePassword(12));
        user.setUsername(password);
        user.setPassword(passwordEncoder.encode(oAuth2UserInfo.getEmail()+"tute_2023"));
        user.setStatus(UserStatus.ACTIVE);
        user = userRepository.save(user);
        return userService.setRoleForUser(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setAvatar(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

}
