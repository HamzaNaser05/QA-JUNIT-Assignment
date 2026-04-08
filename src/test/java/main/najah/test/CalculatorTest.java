package main.najah.test;

import main.najah.code.Calculator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;



import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
@DisplayName("Calculator Tests")
@TestMethodOrder(OrderAnnotation.class)
public class CalculatorTest {

	static Calculator calc;

	@BeforeAll
	static void beforeAll() {
		calc = new Calculator();
		System.out.println("CalculatorTest: setup complete before all tests");
	}

	@AfterAll
	static void afterAll() {
		System.out.println("CalculatorTest: cleanup complete after all tests");
	}

	@BeforeEach
	void setUp() {
		System.out.println("CalculatorTest: before each test");
	}

	@AfterEach
	void tearDown() {
		System.out.println("CalculatorTest: after each test");
	}

	@Test
	@Order(1)
	@DisplayName("add should return correct sum for normal integers")
	void testAddValid() {
		int result = calc.add(1, 2, 3, 4);

		assertAll("add valid input",
				() -> assertEquals(10, result),
				() -> assertNotEquals(9, result)
		);
	}

	@Test
	@Order(2)
	@DisplayName("add should return 0 when no numbers are provided")
	void testAddEmptyInput() {
		int result = calc.add();

		assertAll("add empty input",
				() -> assertEquals(0, result),
				() -> assertTrue(result >= 0)
		);
	}

	@ParameterizedTest(name = "factorial({0}) = {1}")
	@CsvSource({
			"0, 1",
			"1, 1",
			"3, 6",
			"5, 120"
	})
	@Order(3)
	@DisplayName("factorial should return correct values for valid inputs")
	void testFactorialParameterized(int input, int expected) {
		int result = calc.factorial(input);

		assertAll("factorial valid input",
				() -> assertEquals(expected, result),
				() -> assertTrue(result >= 1)
		);
	}

	@Test
	@Order(4)
	@DisplayName("factorial should throw exception for negative input")
	void testFactorialInvalid() {
		IllegalArgumentException ex =
				assertThrows(IllegalArgumentException.class, () -> calc.factorial(-1));

		assertAll("factorial invalid input",
				() -> assertEquals("Negative input", ex.getMessage()),
				() -> assertNotNull(ex)
		);
	}

	@Test
	@Order(5)
	@DisplayName("divide should return integer quotient for valid input")
	void testDivideValid() {
		int result = calc.divide(20, 5);

		assertAll("divide valid input",
				() -> assertEquals(4, result),
				() -> assertTrue(result > 0)
		);
	}

	@Test
	@Order(6)
	@DisplayName("divide should throw ArithmeticException when divisor is zero")
	void testDivideByZero() {
		ArithmeticException ex =
				assertThrows(ArithmeticException.class, () -> calc.divide(10, 0));

		assertAll("divide invalid input",
				() -> assertEquals("Cannot divide by zero", ex.getMessage()),
				() -> assertNotNull(ex)
		);
	}

	@Test
	@Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
	void testWithAnnotationTimeout() {
		calc.factorial(10);
	}

	@Test
	@Disabled("Intentional failing test. Fix by changing expected value from 5 to 6.")
	@Order(8)
	@DisplayName("intentional failing test for demonstration")
	void intentionalFailingTest() {
		assertEquals(5, calc.add(2, 4));
	}
}