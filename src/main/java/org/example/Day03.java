package org.example;

import org.example.utils.AbstractDay;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day03 extends AbstractDay {

    public Day03(List<String> lines) {
        super(lines);
    }

    private Pattern mulPattern = Pattern.compile("(mul\\((?<first>\\d{1,3}),(?<second>\\d{1,3})\\))");

    @Override
    public int partOne() {

//        return lines.stream()
//                .map(mulPattern::matcher)
//                .map(matcher -> {
//                    matcher.matches();
//                    return matcher;
//                })
//                .map(Matcher::results)
//                .map(
//                        matchResult -> {
//                            return
//                                    Integer.parseInt(matchResult.group("first")) *
//                                            Integer.parseInt(matchResult.group("second"));
//                        }
//                )
//                .reduce(0, (a, b) -> a + b);
        int sum = 0;
        return lines.stream().map( line -> {
            Matcher matcher = mulPattern.matcher(line);
            matcher.results();
            return matcher.results()
                    .map(matchResult -> Integer.parseInt(matchResult.group("first")) * Integer.parseInt(matchResult.group("second")))
                    .reduce(0, (a, b) -> a + b);
        }
        ).reduce(0, (a, b) -> a + b);



    }

    @Override
    public int partTwo() {
        String megaLine = String.join("\n", lines);
        megaLine = megaLine.replaceAll("\\s", "");
        String megaLineCleansed = megaLine.replaceAll("(?:don't\\(\\)).*?(?:do\\(\\))", "");
        Matcher matcher = mulPattern.matcher(megaLineCleansed);
        matcher.results();
        return matcher.results()
                .map(matchResult -> Integer.parseInt(matchResult.group("first")) * Integer.parseInt(matchResult.group("second")))
                .reduce(0, (a, b) -> a + b);
    }
}
