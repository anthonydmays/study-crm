package com.codedifferently.studycrm.auth.web.security;

import static org.mockito.Mockito.*;

import com.codedifferently.studycrm.auth.domain.User;
import com.codedifferently.studycrm.auth.domain.UserRepository;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

class UserRepositoryOAuth2UserHandlerTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserRepositoryOAuth2UserHandler userHandler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void accept_NewUser_SaveUserToRepository() {
    // Arrange
    DefaultOAuth2User user =
        createDefaultOAuth2User("john.doe", "john.doe@example.com", "John", "Doe");
    when(userRepository.findByUsername("john.doe")).thenReturn(null);

    // Act
    userHandler.accept(user);

    var expected =
        User.builder()
            .username("john.doe")
            .email("john.doe@example.com")
            .password("{noop}john.doe")
            .firstName("John")
            .lastName("Doe")
            .build();
    Objects.requireNonNull(expected);

    // Assert
    verify(userRepository, times(1)).save(expected);
  }

  @Test
  void accept_ExistingUser_DoNotSaveUserToRepository() {
    // Arrange
    DefaultOAuth2User user =
        createDefaultOAuth2User("john.doe", "john.doe@example.com", "John", "Doe");
    when(userRepository.findByUsername("john.doe")).thenReturn(new User());

    // Act
    userHandler.accept(user);

    // Assert
    verify(userRepository, never()).save(any(User.class));
  }

  private DefaultOAuth2User createDefaultOAuth2User(
      String username, String email, String givenName, String familyName) {
    return new DefaultOAuth2User(
        Arrays.asList(),
        Map.of(
            "email", email, "given_name", givenName, "family_name", familyName, "name", username),
        "name");
  }
}
