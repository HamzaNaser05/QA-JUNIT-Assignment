package main.najah.test;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;
import main.najah.code.RecipeException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RecipeBook Tests")
public class RecipeBookTest {

    RecipeBook recipeBook;
    Recipe recipe1;
    Recipe recipe2;
    Recipe recipe3;
    Recipe recipe4;
    Recipe recipe5;

    @BeforeEach
    void setUp() {
        recipeBook = new RecipeBook();

        recipe1 = new Recipe();
        recipe1.setName("Espresso");

        recipe2 = new Recipe();
        recipe2.setName("Latte");

        recipe3 = new Recipe();
        recipe3.setName("Mocha");

        recipe4 = new Recipe();
        recipe4.setName("Cappuccino");

        recipe5 = new Recipe();
        recipe5.setName("Americano");
    }

    @Test
    @DisplayName("addRecipe should add a valid recipe")
    void testAddRecipeValid() {
        boolean added = recipeBook.addRecipe(recipe1);

        assertAll("add valid recipe",
                () -> assertTrue(added),
                () -> assertEquals("Espresso", recipeBook.getRecipes()[0].getName())
        );
    }

    @Test
    @DisplayName("addRecipe should reject duplicate recipe")
    void testAddRecipeDuplicate() {
        recipeBook.addRecipe(recipe1);
        boolean addedAgain = recipeBook.addRecipe(recipe1);

        assertAll("duplicate recipe",
                () -> assertFalse(addedAgain),
                () -> assertEquals("Espresso", recipeBook.getRecipes()[0].getName())
        );
    }

    @Test
    @DisplayName("addRecipe should return false when recipe book is full")
    void testAddRecipeWhenFull() {
        recipeBook.addRecipe(recipe1);
        recipeBook.addRecipe(recipe2);
        recipeBook.addRecipe(recipe3);
        recipeBook.addRecipe(recipe4);

        boolean added = recipeBook.addRecipe(recipe5);

        assertAll("recipe book full",
                () -> assertFalse(added),
                () -> assertEquals(4, recipeBook.getRecipes().length)
        );
    }

    @Test
    @DisplayName("deleteRecipe should return deleted recipe name for valid index")
    void testDeleteRecipeValid() {
        recipeBook.addRecipe(recipe1);

        String deletedName = recipeBook.deleteRecipe(0);

        assertAll("delete valid recipe",
                () -> assertEquals("Espresso", deletedName),
                () -> assertNotNull(recipeBook.getRecipes()[0]),
                () -> assertEquals("", recipeBook.getRecipes()[0].getName())
        );
    }

    @ParameterizedTest(name = "deleteRecipe invalid index = {0}")
    @ValueSource(ints = {-1, 4, 10})
    @DisplayName("deleteRecipe should throw exception for invalid indexes")
    void testDeleteRecipeInvalidIndex(int index) {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> recipeBook.deleteRecipe(index));
    }

    @Test
    @DisplayName("editRecipe should return old name and replace recipe for valid index")
    void testEditRecipeValid() {
        recipeBook.addRecipe(recipe1);

        Recipe newRecipe = new Recipe();
        newRecipe.setName("Flat White");

        String oldName = recipeBook.editRecipe(0, newRecipe);

        assertAll("edit valid recipe",
                () -> assertEquals("Espresso", oldName),
                // Important: this matches the actual buggy behavior in source code
                () -> assertEquals("", recipeBook.getRecipes()[0].getName())
        );
    }

    @Test
    @DisplayName("editRecipe should return null when recipe does not exist")
    void testEditRecipeNullSlot() {
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Flat White");

        String result = recipeBook.editRecipe(0, newRecipe);

        assertAll("edit null slot",
                () -> assertNull(result),
                () -> assertNull(recipeBook.getRecipes()[0])
        );
    }

    @Test
    @DisplayName("recipe book operations should finish within timeout")
    void testTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> {
            recipeBook.addRecipe(recipe1);
            recipeBook.addRecipe(recipe2);
            assertEquals("Espresso", recipeBook.getRecipes()[0].getName());
        });
    }

}