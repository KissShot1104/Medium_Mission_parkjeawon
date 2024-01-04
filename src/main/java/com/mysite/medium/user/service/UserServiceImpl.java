package com.mysite.medium.user.service;


import com.mysite.medium.global.exception.AuthException;
import com.mysite.medium.global.exception.EntityNotFoundException;
import com.mysite.medium.global.exception.ErrorCode;
import com.mysite.medium.global.security.UserRole;
import com.mysite.medium.global.security.provider.JwtTokenProvider;
import com.mysite.medium.token.service.TokenService;
import com.mysite.medium.user.dto.SiteUserDto;
import com.mysite.medium.user.dto.SiteUserDtoMapper;
import com.mysite.medium.user.dto.UserCreateDto;
import com.mysite.medium.user.dto.UserLoginResponse;
import com.mysite.medium.user.entity.SiteUser;
import com.mysite.medium.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SiteUserDtoMapper siteUserDtoMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    @Transactional
    public void createUser(final UserCreateDto userCreateDto) {

        Optional<SiteUser> duplicatedLoginId = userRepository.findByUsername(userCreateDto.getUsername());
        if (duplicatedLoginId.isPresent()) {
            throw new AuthException(ErrorCode.DUPLICATED_LOGIN_ID);
        }
        Optional<SiteUser> duplicatedEmail = userRepository.findByEmail(userCreateDto.getEmail());
        if (duplicatedEmail.isPresent()) {
            throw new AuthException(ErrorCode.DUPLICATED_EMAIL);
        }

        userCreateDto.checkEqualsPassword();

        final SiteUser user = SiteUser.builder()
                .username(userCreateDto.getUsername())
                .email(userCreateDto.getEmail())
                .password(passwordEncoder.encode(userCreateDto.getPassword1()))
                .isPaid(userCreateDto.getIsPaid())
                .build();

        userRepository.save(user);
    }

    public SiteUserDto getUser(final String username) {
        final SiteUser siteUser = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException(ErrorCode.ENTITY_NOT_FOUND));

        final SiteUserDto siteUserDto = siteUserDtoMapper.siteUserToSiteUserDto(siteUser);

        return siteUserDto;
    }

    /////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////

    private void checkPassword(SiteUser user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthException(ErrorCode.ENTITY_NOT_FOUND);
        }
    }

    @Transactional
    public UserLoginResponse login(String username, String password) {
        SiteUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException(ErrorCode.FAIL_LOGIN));

        checkPassword(user, password);

        String userId = String.valueOf(user.getId());
        List<String> userAuthorities = getRoles(userId);

        String accessToken = jwtTokenProvider.createAccessToken(userId, userAuthorities);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId);

        tokenService.register(refreshToken, user.getId());

        return UserLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();


    }
    public void logout(String memberId) {
        tokenService.deleteToken(memberId);
    }
    @Transactional
    public List<String> getRoles(String userId) {
        SiteUser member = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        List<String> authorities = new ArrayList<>();
        authorities.add(UserRole.USER.getRole());

        if (member.getIsPaid()) {
            authorities.add(UserRole.PAID.getRole());
        }

        return authorities;
    }

}