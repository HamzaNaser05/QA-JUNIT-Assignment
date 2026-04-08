package main.najah.test;

import main.najah.code.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product Tests")
@Execution(ExecutionMode.CONCURRENT)
public class ProductTest {

	Product p;

	@BeforeEach
	void setUp() {
		p = new Product("Laptop", 1000.0);
	}

	@Test
	@DisplayName("constructor should initialize name and price correctly")
	void testConstructorValid() {
		assertAll("valid constructor",
				() -> assertEquals("Laptop", p.getName()),
				() -> assertEquals(1000.0, p.getPrice()),
				() -> assertEquals(0.0, p.getDiscount())
		);
	}

	@Test
	@DisplayName("constructor should throw exception for negative price")
	void testConstructorInvalid() {
		IllegalArgumentException ex =
				assertThrows(IllegalArgumentException.class, () -> new Product("Phone", -50.0));

		assertAll("invalid constructor",
				() -> assertEquals("Price must be non-negative", ex.getMessage()),
				() -> assertNotNull(ex)
		);
	}

	@ParameterizedTest(name = "discount {0}% gives final price {1}")
	@CsvSource({
			"10, 900.0",
			"25, 750.0",
			"50, 500.0"
	})
	@DisplayName("applyDiscount should update final price correctly")
	void testApplyDiscountValid(double discount, double expectedFinalPrice) {
		p.applyDiscount(discount);

		assertAll("valid discount",
				() -> assertEquals(discount, p.getDiscount()),
				() -> assertEquals(expectedFinalPrice, p.getFinalPrice())
		);
	}

	@ParameterizedTest(name = "invalid discount = {0}")
	@CsvSource({
			"-1",
			"55"
	})
	@DisplayName("applyDiscount should throw exception for invalid discount values")
	void testApplyDiscountInvalid(double discount) {
		IllegalArgumentException ex =
				assertThrows(IllegalArgumentException.class, () -> p.applyDiscount(discount));

		assertAll("invalid discount",
				() -> assertEquals("Invalid discount", ex.getMessage()),
				() -> assertNotNull(ex)
		);
	}

	@Test
	@DisplayName("getFinalPrice should return original price when discount is zero")
	void testGetFinalPriceWithoutDiscount() {
		assertAll("final price without discount",
				() -> assertEquals(1000.0, p.getFinalPrice()),
				() -> assertEquals(0.0, p.getDiscount())
		);
	}

	@Test
	@DisplayName("getFinalPrice should finish within timeout")
	void testTimeout() {
		assertTimeout(Duration.ofMillis(100), () -> {
			p.applyDiscount(20);
			assertEquals(800.0, p.getFinalPrice());
		});
	}
}