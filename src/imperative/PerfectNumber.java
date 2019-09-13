package imperative;

import edu.emory.mathcs.backport.java.util.Collections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * @author Maksims Medveckis 171RDB030 3.kurs 1.grupa
 */
public class PerfectNumber {

    public enum STATE {
        ABUNDANT, DEFICIENT, PERFECT, UNKNOWN;
    }

    public static Set<Integer> divisors(int n) {
        Set<Integer> integerSet = new HashSet<>();

        IntStream.range(1, (int) Math.ceil(Math.sqrt(n)) + 1)
                .filter(i -> n % i == 0)
                .forEach(i -> {
                    integerSet.add(i);
                    integerSet.add(n / i);
                });

        return integerSet;
    }

    @SuppressWarnings("unchecked")
    public static STATE process(int n) {
        Set<Integer> divisorsOfN = divisors(n);

        Integer sumOfDivisorsOfN = divisorsOfN.stream()
                .filter(e -> !e.equals(n))
                .reduce(0, Integer::sum);

        Map<Predicate<Integer>, STATE> ruleMapTmp = new HashMap<Predicate<Integer>, STATE>() {{
            put(i -> i.compareTo(n) < 0, STATE.DEFICIENT);
            put(i -> i.compareTo(n) == 0, STATE.PERFECT);
            put(i -> i.compareTo(n) > 0, STATE.ABUNDANT);
        }};
        Map ruleMap = Collections.unmodifiableMap(ruleMapTmp); // constants, do not modify

        return (STATE) ruleMap.get(ruleMap.keySet()
                .stream()
                .filter(c -> {
                    Predicate<Integer> con = (Predicate<Integer>) c;
                    return con.test(sumOfDivisorsOfN);
                })
                .findFirst()
                .orElse(STATE.UNKNOWN));
    }
}
