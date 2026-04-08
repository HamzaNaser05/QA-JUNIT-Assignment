package main.najah.test;

import main.najah.code.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserService Tests")
public class UserServiceSimpleTest {

	UserService userService;

	@BeforeEach
	void setUp() {
		userService = new UserService();
	}

	@ParameterizedTest(name = "email \"{0}\" should be {1}")
	@CsvSource({
			"admin@example.com, true",
			"hamza@mail.ps, true",
			"invalidemail, false",
			"hamza@, false",
			"test.com, false"
	})
	@DisplayName("isValidEmail should validate different email formats")
	void testIsValidEmailParameterized(String email, boolean expected) {
		boolean result = userService.isValidEmail(email);

		assertAll("email validation",
				() -> assertEquals(expected, result),
				() -> assertNotNull(result)
		);
	}

	@Test
	@DisplayName("isValidEmail should return false for null")
	void testIsValidEmailNull() {
		assertAll("null email",
				() -> assertFalse(userService.isValidEmail(null)),
				() -> assertNotEquals(true, userService.isValidEmail(null))
		);
	}

	@Test
	@DisplayName("authenticate should return true for correct credentials")
	void testAuthenticateValid() {
		assertAll("valid authentication",
				() -> assertTrue(userService.authenticate("admin", "1234")),
				() -> assertFalse(userService.authenticate("admin", "wrong"))
		);
	}

	@Test
	@DisplayName("authenticate should return false for invalid credentials")
	void testAuthenticateInvalid() {
		assertAll("invalid authentication",
				() -> assertFalse(userService.authenticate("user", "1234")),
				() -> assertFalse(userService.authenticate("admin", "0000"))
		);
	}

	@Test
	@DisplayName("authentication check should finish within timeout")
	void testTimeout() {
		assertTimeout(Duration.ofMillis(100), () -> {
			assertTrue(userService.authenticate("admin", "1234"));
		});
	}
}