package com.example.BACKAppLiv.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import com.example.BACKAppLiv.dto.UserLoginDto;
import com.example.BACKAppLiv.dto.UserRegistrationDto;
import com.example.BACKAppLiv.model.User;
import com.example.BACKAppLiv.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    private final WebClient webClient;

    @Autowired
    public UserService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public ResponseEntity<String> registerUser(UserRegistrationDto registrationDto) {
        // Check if the user already exists
        Optional<User> existingUser = userRepository.findByEmail(registrationDto.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("User already registered");
        }

        String email = registrationDto.getEmail();
        String password = registrationDto.getPassword();




        // Encode the password before saving
        registrationDto.setPassword(passwordEncoder.encode(password));

        // Save user to the database
        User user = new User();

        user.setUsername(registrationDto.getUsername());
        user.setPassword(registrationDto.getPassword());
        user.setEmail(registrationDto.getEmail());

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<Map<String, String>> loginUser(UserLoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        Optional<User> optionalUser = userRepository.findByEmail(loginDto.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();




            // Generate JWT token with additional claims
            Instant instant = Instant.now();
            String scope = authentication.getAuthorities().stream()
                    .map(auth -> auth.getAuthority())
                    .collect(Collectors.joining(" "));

            JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                    .subject(authentication.getName())
                    .issuedAt(instant)
                    .expiresAt(instant.plus(50, ChronoUnit.MINUTES))
                    .issuer("shopbuilder")
                    .claim("scope", scope)
                    .claim("userId", user.getIdUser())
                    .claim("email", user.getEmail())

                    .build();

            String jwt = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

            Map<String, String> response = new HashMap<>();

            response.put("jwt", jwt);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Invalid credentials"));
    }

    private String getWordPressToken(String username, String password, String domaineName) {
        String url = domaineName + "/wp-json/jwt-auth/v1/token?username=" + username + "&password=" + password;
        Mono<WordPressTokenResponse> responseMono = webClient.post()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(WordPressTokenResponse.class);

        WordPressTokenResponse response = responseMono.block();
        return response != null ? response.getToken() : null;
    }

    @Setter
    @Getter
    private static class WordPressTokenResponse {
        private String token;
    }
}
