package main.najah.test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@Suite
@SelectClasses({
        CalculatorTest.class,
        ProductTest.class,
        UserServiceSimpleTest.class,
        RecipeBookTest.class,
        RecipeTest.class
})
public class AllTestsSuite {
}