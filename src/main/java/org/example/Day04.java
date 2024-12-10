package org.example;

import java.awt.Point;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.utils.AbstractDay;

import java.util.List;

public class Day04 extends AbstractDay {


    public Day04(List<String> lines) {
        super(lines);
    }


    //grid is square

    @Override
    public long partOne() {
        var height = lines.size();
        var width = lines.getFirst().length();
//        Grid grid = new Grid(width, height);
//        for (int i = 0; i < height; i++) {
//            grid.setRow(i, lines.get(i));
//        }

        //four passes
        List<String> horizontal = lines;
        List<String> vertical = new ArrayList<>();
        List<String> diagonalUp = new ArrayList<>();
        List<String> diagonalDown = new ArrayList<>();

        for(int column = 0; column < width; column++) {
            StringBuilder sb = new StringBuilder();
            for(int row = 0; row < height; row++) {
                sb.append(lines.get(row).charAt(column));
            }
            vertical.add(sb.toString());
        }

        // diagonal up
        for(int i = 0; i < height + width; i++) {
            int startRow = Math.min(i, height-1);
            int startColumn = Math.max(i-height, 0);
            int finishRow = startColumn;
            int finishColumn = startRow;
            int row = startRow;
            int column = startColumn;
            StringBuilder sb = new StringBuilder();
            while(row >= finishRow && column <= finishColumn) {
                    sb.append(lines.get(row).charAt(column));
                    row--;
                    column++;
            }
            if (i != height - 1) diagonalUp.add(sb.toString());
        }

        //diagonal down
        for(int i = 0; i < height + width; i++) {
            int startRow = Math.max(0, height - 1 - i);
            int startColumn = Math.max(i - height, 0);
            int finishRow = height - 1 - startColumn;
            int finishColumn = height - startRow;
            int row = startRow;
            int column = startColumn;
            StringBuilder sb = new StringBuilder();
            while(row <= finishRow && column <= finishColumn) {
                sb.append(lines.get(row).charAt(column));
                row++;
                column++;
            }
            if (i != height - 1) diagonalDown.add(sb.toString());
        }

        int totalCountOfXmas = 0;

        totalCountOfXmas += horizontal.stream().map(s -> {
            StringBuilder sb = new StringBuilder(s);
            return sb.reverse().toString();
        }).map(line ->  (line.length() - line.replace("XMAS", "").length()) / 4)
            .reduce(0, Integer::sum);

        totalCountOfXmas += vertical.stream().map(s -> {
                StringBuilder sb = new StringBuilder(s);
                return sb.reverse().toString();
            }).map(line ->  (line.length() - line.replace("XMAS", "").length()) / 4)
            .reduce(0, Integer::sum);

        totalCountOfXmas += diagonalUp.stream().map(s -> {
                StringBuilder sb = new StringBuilder(s);
                return sb.reverse().toString();
            }).map(line ->  (line.length() - line.replace("XMAS", "").length()) / 4)
            .reduce(0, Integer::sum);

        totalCountOfXmas += diagonalDown.stream().map(s -> {
                StringBuilder sb = new StringBuilder(s);
                return sb.reverse().toString();
            }).map(line ->  (line.length() - line.replace("XMAS", "").length()) / 4)
            .reduce(0, Integer::sum);

        totalCountOfXmas += horizontal.stream()
            .map(line ->  (line.length() - line.replace("XMAS", "").length()) / 4)
            .reduce(0, Integer::sum);

        totalCountOfXmas += vertical.stream()
            .map(line ->  (line.length() - line.replace("XMAS", "").length()) / 4)
            .reduce(0, Integer::sum);

        totalCountOfXmas += diagonalUp.stream()
            .map(line ->  (line.length() - line.replace("XMAS", "").length()) / 4)
            .reduce(0, Integer::sum);

        totalCountOfXmas += diagonalDown.stream()
            .map(line ->  (line.length() - line.replace("XMAS", "").length()) / 4)
            .reduce(0, Integer::sum);

        return totalCountOfXmas;
    }

    @Override
    public long partTwo() {
        var height = lines.size();
        var width = lines.getFirst().length();

        char[][] wordSearch = new char[height][width];
        for(int i = 0; i < lines.size(); i++) {
            wordSearch[i] = lines.get(i).toCharArray();
        }

        Pattern pattern = Pattern.compile("MAS|SAM");
        int sum = 0;
        for(int row = 1; row < height - 1; row++) {
            for(int column = 1; column < width - 1; column++) {
                if(wordSearch[row][column] != 'A') continue;
                char upLeft = wordSearch[row-1][column-1];
                char downRight = wordSearch[row+1][column+1];
                String diagonalDown = upLeft + "A" + downRight;
                Matcher diagonalDownMatcher = pattern.matcher(diagonalDown);

                char downLeft = wordSearch[row-1][column+1];
                char upRight = wordSearch[row+1][column-1];
                String diagonalUp = downLeft + "A" + upRight;
                Matcher diagonalUpMatcher = pattern.matcher(diagonalUp);

                if (diagonalDownMatcher.matches() && diagonalUpMatcher.matches()) {
                    sum += 1;
                }
            }
        }
        return sum;
    }
}
