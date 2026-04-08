package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import main.najah.code.Recipe;
import main.najah.code.RecipeException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Recipe Tests")
public class RecipeTest {

    @Test
    @DisplayName("setAmtMilk should set valid milk amount")
    void testSetMilkValid() throws Exception {
        Recipe r = new Recipe();
        r.setAmtMilk("10");

        assertAll("valid milk",
                () -> assertEquals(10, r.getAmtMilk()),
                () -> assertTrue(r.getAmtMilk() >= 0)
        );
    }

    @Test
    @DisplayName("setAmtMilk should throw exception for invalid input")
    void testSetMilkInvalid() {
        Recipe r = new Recipe();

        assertThrows(RecipeException.class, () -> r.setAmtMilk("abc"));
    }

    @Test
    @DisplayName("setAmtSugar should set valid sugar amount")
    void testSetSugarValid() throws Exception {
        Recipe r = new Recipe();
        r.setAmtSugar("5");

        assertAll("valid sugar",
                () -> assertEquals(5, r.getAmtSugar()),
                () -> assertTrue(r.getAmtSugar() >= 0)
        );
    }

    @Test
    @DisplayName("setAmtSugar should throw exception for invalid input")
    void testSetSugarInvalid() {
        Recipe r = new Recipe();

        assertThrows(RecipeException.class, () -> r.setAmtSugar("xyz"));
    }

    @Test
    @DisplayName("setAmtChocolate should set valid chocolate amount")
    void testSetChocolateValid() throws Exception {
        Recipe r = new Recipe();
        r.setAmtChocolate("7");

        assertAll("valid chocolate",
                () -> assertEquals(7, r.getAmtChocolate()),
                () -> assertTrue(r.getAmtChocolate() >= 0)
        );
    }

    @Test
    @DisplayName("setAmtChocolate should throw exception for invalid input")
    void testSetChocolateInvalid() {
        Recipe r = new Recipe();

        assertThrows(RecipeException.class, () -> r.setAmtChocolate("!@#"));
    }

    @Test
    @DisplayName("setPrice should set valid price")
    void testSetPriceValid() throws Exception {
        Recipe r = new Recipe();
        r.setPrice("50");

        assertAll("valid price",
                () -> assertEquals(50, r.getPrice()),
                () -> assertTrue(r.getPrice() >= 0)
        );
    }

    @Test
    @DisplayName("setPrice should throw exception for invalid input")
    void testSetPriceInvalid() {
        Recipe r = new Recipe();

        assertThrows(RecipeException.class, () -> r.setPrice("price"));
    }

    @Test
    @DisplayName("setAmtCoffee should set valid coffee amount")
    void testSetCoffeeValid() throws Exception {
        Recipe r = new Recipe();
        r.setAmtCoffee("8");

        assertAll(
                () -> assertEquals(8, r.getAmtCoffee()),
                () -> assertTrue(r.getAmtCoffee() >= 0)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "-1"})
    @DisplayName("setAmtCoffee should throw exception for invalid coffee input")
    void testSetCoffeeInvalid(String input) {
        Recipe r = new Recipe();

        assertThrows(RecipeException.class, () -> r.setAmtCoffee(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "-5"})
    @DisplayName("setAmtMilk should throw exception for negative milk input")
    void testSetMilkNegative(String input) {
        Recipe r = new Recipe();

        assertThrows(RecipeException.class, () -> r.setAmtMilk(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "-2"})
    @DisplayName("setAmtSugar should throw exception for negative sugar input")
    void testSetSugarNegative(String input) {
        Recipe r = new Recipe();

        assertThrows(RecipeException.class, () -> r.setAmtSugar(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "-3"})
    @DisplayName("setAmtChocolate should throw exception for negative chocolate input")
    void testSetChocolateNegative(String input) {
        Recipe r = new Recipe();

        assertThrows(RecipeException.class, () -> r.setAmtChocolate(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "-10"})
    @DisplayName("setPrice should throw exception for negative price input")
    void testSetPriceNegative(String input) {
        Recipe r = new Recipe();

        assertThrows(RecipeException.class, () -> r.setPrice(input));
    }

    @Test
    @DisplayName("setName should update name when value is not null")
    void testSetNameValid() {
        Recipe r = new Recipe();
        r.setName("Mocha");

        assertAll(
                () -> assertEquals("Mocha", r.getName()),
                () -> assertEquals("Mocha", r.toString())
        );
    }

    @Test
    @DisplayName("setName should ignore null value")
    void testSetNameNull() {
        Recipe r = new Recipe();
        r.setName("Latte");
        r.setName(null);

        assertAll(
                () -> assertEquals("Latte", r.getName()),
                () -> assertEquals("Latte", r.toString())
        );
    }

    @Test
    @DisplayName("equals should return true for recipes with same name")
    void testEqualsSameName() {
        Recipe r1 = new Recipe();
        Recipe r2 = new Recipe();

        r1.setName("Espresso");
        r2.setName("Espresso");

        assertAll(
                () -> assertEquals(r1, r2),
                () -> assertEquals(r1.hashCode(), r2.hashCode())
        );
    }

    @Test
    @DisplayName("equals should return false for different names")
    void testEqualsDifferentNames() {
        Recipe r1 = new Recipe();
        Recipe r2 = new Recipe();

        r1.setName("Espresso");
        r2.setName("Latte");

        assertAll(
                () -> assertNotEquals(r1, r2),
                () -> assertNotEquals(r1.hashCode(), r2.hashCode())
        );
    }

    @Test
    @DisplayName("equals should handle null and different object type")
    void testEqualsNullAndDifferentType() {
        Recipe r = new Recipe();
        r.setName("Mocha");

        assertAll(
                () -> assertNotEquals(r, null),
                () -> assertNotEquals(r, "not a recipe"),
                () -> assertEquals(r, r)
        );
    }

    @Test
    @DisplayName("equals should work when both recipe names are default empty strings")
    void testEqualsDefaultRecipes() {
        Recipe r1 = new Recipe();
        Recipe r2 = new Recipe();

        assertAll(
                () -> assertEquals(r1, r2),
                () -> assertEquals(r1.hashCode(), r2.hashCode()),
                () -> assertEquals("", r1.toString())
        );
    }
}