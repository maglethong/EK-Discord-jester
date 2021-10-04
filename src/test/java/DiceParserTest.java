import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.Random;

/**
 * Test class for {@link DiceParser}
 *
 * @author Maglethong spirr
 * @since 04/10/2021
 */
class DiceParserTest {

    private int rngCounter;
    private DiceParser parser;

    @BeforeEach
    void setUp() {
        rngCounter = 0;
        Random rng = Mockito.mock(Random.class); // Fake RNG returning incrementing numbers. Resets for every test
        parser = new DiceParser(rng);

        Mockito.when(rng.nextInt())
               .then(m -> rngCounter++);
    }

    @ParameterizedTest
    @ValueSource(strings = {
                    "1d20 + 41",
                    "3d7 +42 -6",
                    "   1d1    +     41",
                    "-1d4 + 43",
                    "(1d4) * (1d2) + 40",
                    "40 + 2",
                    "(1 + 2*3) + 42 - 7",
                    "(((42)))",
                    "((1 + 2) * 3) + 42 - 9",
                    "(1 + (2*3)) + 42 - 7",
                    "x | x = 42",
                    "xd4 *y | x=2 | y=21",
                    "xdy | x = 1d4 * 21 | y = 1d4",
                    "abc | abc = 42",
                    "xdy + 42 -3| y = 1d4 | x = 1d4",
                    "x + 40 | x = 2 * y | y = 1d4",
                    "2^3 + 42 - 8",
                    "84 / 2",
                    "85 / 2", // Work only with integers
    })
    void parametrizedTestReturning42(String formula) {
        int result = parser.parse(formula).getRight();

        Assertions.assertEquals(42, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
                    "asd",
                    "1d4",
                    "1d-5",
                    "d",
                    "d | d=1",
                    "abc | a = 1 | b = 2 | c = 3",
                    "x",
                    "2x | x = 2",
                    "(((42))",
                    "(42))",
    })
    @NullSource
    @EmptySource
    void parametrizedTestReturningThrowingException(String formula) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parse(formula));
    }
}