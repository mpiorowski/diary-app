package pbs.api.rest.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pbs.api.annotations.CurrentUser;
import pbs.api.rest.auth.dto.LoginRequestDto;
import pbs.api.rest.auth.dto.LoginResponseDto;
import pbs.api.rest.users.dto.UserSummaryDto;
import pbs.api.security.JwtAuthenticationTokenProvider;
import pbs.api.security.SystemUser;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtAuthenticationTokenProvider tokenProvider;

  public AuthController(
      AuthenticationManager authenticationManager, JwtAuthenticationTokenProvider tokenProvider) {
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
  }

  @PostMapping("/log")
  public ResponseEntity<LoginResponseDto> logInByUserName(
      @RequestBody @Valid LoginRequestDto loginRequestDto) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUserName(), loginRequestDto.getUserPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String authToken = tokenProvider.generateToken(authentication);

    return ResponseEntity.ok(new LoginResponseDto(authToken));
  }

  @GetMapping("/user")
  public ResponseEntity<UserSummaryDto> getUser(@CurrentUser SystemUser userDetails) {

    UserSummaryDto userSummaryDto =
        new UserSummaryDto(
            userDetails.getUsername(),
            userDetails.getUserEmail(),
            userDetails.getAuthorities());

    return ResponseEntity.ok(userSummaryDto);
  }
}
