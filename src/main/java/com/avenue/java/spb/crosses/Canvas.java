package com.avenue.java.spb.crosses;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Data
@Slf4j
public class Canvas {

    private int size;
    private Figure[][] figures;

    private int filledCells;

    public Canvas(int size) {
        this.size = size;
        this.figures = new Figure[size][size];
        this.filledCells = 0;
        for (Figure[] row : figures) {
            Arrays.fill(row, Figure.EMPTY);
        }
    }

    public void draw() {
        StringBuilder line = new StringBuilder();
        line.append("C | ");
        for (int i = 1; i <= size; i++) {
            line.append(i);
        }
        log.info(line.toString());
        log.info("-".repeat(size + 4));
        for (int i = 0; i < size; i++) {
            line = new StringBuilder();
            line.append(i + 1).append(" | ");
            for (int j = 0; j < size; j++) {
                line.append(figures[i][j].getView());
            }
            log.info(line.toString());
        }
    }

    /**
     * Sets figure to provided coordinates if it is applicable
     * @param x horizontal coordinate
     * @param y vertical coordinate
     * @param figure a figure to set
     * @return true if figure is set or false if it is impossible
     */
    public boolean setFigure(int x, int y, Figure figure) {
        if (figures[y - 1][x - 1] != Figure.EMPTY) {
            return false;
        }
        if (x > size || y > size) {
            return false;
        }
        figures[y - 1][x - 1] = figure;
        filledCells++;
        return true;
    }

    public GameResult checkResult(Player player) {
        for (int i = 0; i < size; i++) {
            int lineSum = 0;
            int columnSum = 0;
            int firstDiagonalSum = 0;
            int secondDiagonalSum = 0;
            for (int j = 0; j < size; j++) {
                /*
                    Value is 1 for crosses, -1 for zeros.
                    Summing values we check for all crosses or zeros in line/column
                 */
                lineSum += figures[i][j].getValue();
                columnSum += figures[j][i].getValue();
            }
            firstDiagonalSum += figures[i][i].getValue();
            secondDiagonalSum += figures[i][size - i - 1].getValue();
            if (isAnythingEqualsToSize(lineSum, columnSum, firstDiagonalSum, secondDiagonalSum)) {
                return new GameResult(true, player, false);
            }
        }
        if (filledCells == size * size) {
            return new GameResult(true, null, true);
        }
        return new GameResult(false, null, false);
    }

    private boolean equalsToSize(int number) {
        return number == size || number == -size;
    }

    private boolean isAnythingEqualsToSize(int lineSum, int columnSum,
                                           int firstDiagonalSum, int secondDiagonalSum) {
        return equalsToSize(lineSum) || equalsToSize(columnSum)
                || equalsToSize(firstDiagonalSum) || equalsToSize(secondDiagonalSum);
    }

}
