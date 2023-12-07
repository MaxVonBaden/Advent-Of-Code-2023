package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainDay1 {
    private static final String ALL_DIGITS_REGEX = "one|two|three|four|five|six|seven|eight|nine|\\d";

    private static final Map<String, Character> NAME_TO_DIGIT = Map.of(
            "one", '1',
            "two", '2',
            "three", '3',
            "four", '4',
            "five", '5',
            "six", '6',
            "seven", '7',
            "eight", '8',
            "nine", '9'
    );

    public static void main(String[] args) {
        System.out.print("Enter file path: ");
        final Scanner commandLineScanner = new Scanner(System.in);
        final File file = new File(commandLineScanner.nextLine());
        commandLineScanner.close();
        final Scanner fileScanner;

        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException exception) {
            System.err.println("Invalid file path.");
            return;
        }

        final long startTime = System.currentTimeMillis();
        int part1Sum = 0;
        int part2Sum = 0;

        String currentLine;

        while (fileScanner.hasNext()) {
            currentLine = fileScanner.nextLine();
            part1Sum += Integer.parseInt(new String(new char[]{
                    firstDigit(currentLine, false),
                    lastDigit(currentLine, false)
            }));
            part2Sum += Integer.parseInt(new String(new char[]{
                    firstDigit(currentLine, true),
                    lastDigit(currentLine, true)
            }));
        }

        fileScanner.close();
        final long endTime = System.currentTimeMillis();
        System.out.println();
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);

        System.out.println(System.lineSeparator() + "Total execution time: " + (endTime - startTime) + "ms");
    }

    private static char firstDigit(String line, boolean includeDigitNames) {
        if (!includeDigitNames) {
            for (char character : line.toCharArray()) {
                if (Character.isDigit(character)) {
                    return character;
                }
            }
        } else {
            Matcher digitMatcher;

            for (int i = 0; i <= line.length(); i++) {
                digitMatcher = Pattern.compile(ALL_DIGITS_REGEX).matcher(line.substring(0, i));
                if (!digitMatcher.find()) {
                    continue;
                }
                return parseDigit(digitMatcher);
            }
        }
        return '0';
    }

    private static char lastDigit(String line, boolean includeDigitNames) {
        if (!includeDigitNames) {
            for (int i = line.length() - 1; i >= 0; i--) {
                if (Character.isDigit(line.charAt(i))) {
                    return line.charAt(i);
                }
            }
        } else {
            Matcher digitMatcher;

            for (int i = line.length() - 1; i >= 0; i--) {
                digitMatcher = Pattern.compile(ALL_DIGITS_REGEX).matcher(line.substring(i));
                if (!digitMatcher.find()) {
                    continue;
                }
                return parseDigit(digitMatcher);
            }
        }
        return '0';
    }

    private static char parseDigit(Matcher digitMatcher) {
        if (digitMatcher.group().length() == 1) {
            return digitMatcher.group().charAt(0);
        }
        return NAME_TO_DIGIT.get(digitMatcher.group());
    }
}
