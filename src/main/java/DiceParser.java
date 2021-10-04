import net.dv8tion.jda.internal.utils.tuple.ImmutablePair;
import net.dv8tion.jda.internal.utils.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A Class for parsing a string into a mathematical formula for throwing dice
 *
 * @author Maglethong spirr
 * @since 04/10/2021
 */
public class DiceParser {

    private final Random rng;

    public DiceParser(Random rng) {
        this.rng = rng;
    }

    /*
     * Notes:
     *
     * XdY:
     *  X is INT
     *  Y is INT > 0
     *  1d1 is allowed
     *  1d1 = constant 1
     *
     * Allowed Operators (order of precedence):
     *  ()
     *  d
     *  ^
     *  /
     *  *
     *  -
     *  +
     *
     * Use variables with pipe '|' to determine value. Value may be a roll. Eg:
     *  (xd20 + x) * y  | x = 1d4 | y = 2
     *  Multiple pipes allowed
     *  Resolved from left to right
     *  variables may be floats. but myst respect other constraints
     *  d is not allowed as a variable
     *  multi-char variables allowed
     */

    public Pair<String, Integer> parse(final String formula) {

        String left = formula.replaceAll("\\s*", "");

        // Roll dice
        final Pattern dicePattern = Pattern.compile("([0-9]*)[dD]([0-9]*)");
        final Matcher matcher = dicePattern.matcher(left);
        while (matcher.find()){
            final int amount = Integer.parseInt(matcher.group(1));
            final int sides = Integer.parseInt(matcher.group(2));

            final List<Integer> results = IntStream.range(0, amount)
                                                   .boxed()
                                                   .map(d -> roll(sides))
                                                   .collect(Collectors.toList());

            final String innerLeft = "(" + results.stream()
                            .map(d -> "" + d)
                            .collect(Collectors.joining(" + ")) + ")";
//            final Integer innerRight = results.stream()
//                                              .reduce(Integer::sum)
//                                              .orElse(0);
            left = left.replace(matcher.group(0), innerLeft);
        }
        
        // Add terms
        final Integer right = Arrays.stream(left.split("[+]"))
                        .map(s -> s.replaceAll("[(]", "").replaceAll("[)]", "")) // for now, ignoring parentheses
                        .map(Integer::parseInt)
                        .reduce(Integer::sum)
                        .orElseThrow(() -> new IllegalArgumentException("No enough Terms"));

        return new ImmutablePair<>(left, right);
    }

    private int roll(final int sides) {
        return (rng.nextInt() % sides +1);
    }
}
