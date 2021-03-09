import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.Before;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PolynomialTest {
    private String input1;
    private String input2;
    private String expected;

    public PolynomialTest(String input1, String input2, String output) throws InvalidInputException {
        this.input1 = input1;
        this.input2 = input2;
        this.expected = output;
    }

    @Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {"x^2-2x+3", "x-1", "x^2-x+2"},
                {"x+3", "-x", "3"}
        });
    }

    @Test
    public void testAddPolynomials() throws InvalidInputException {
        assertThat(Controller.getPolynomialFromTexField(input1).add(Controller.getPolynomialFromTexField(input2)).toString(), equals(expected));
    }
}