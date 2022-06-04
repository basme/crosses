package com.avenue.java.spb.crosses;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.Scanner;

@Slf4j
public class Game {

    public static void main(String[] args) {
        log.info("Game is started");
        Scanner scanner = new Scanner(System.in);

        log.info("Enter player 1 name: ");
        String nameOne = scanner.nextLine();
        log.info("Enter player 2 name: ");
        String nameTwo = scanner.nextLine();

        Player playerOne = new Player(nameOne, Figure.CROSS);
        Player playerTwo = new Player(nameTwo, Figure.ZERO);

        log.info("Enter field size (max 9): ");
        var rawSize = scanner.nextLine();
        int size = Integer.parseInt(rawSize);
        if (size > 9) {
            log.error("Max size is 9, {} is too much", size);
            return;
        }

        Canvas canvas = new Canvas(size);

        Random random = new Random();
        var cursor = random.nextInt(2);

        while (true) {
            // Players act consequently. Using remainder of to for their ordering
            Player currentPlayer = cursor % 2 == 0 ? playerOne : playerTwo;
            canvas.draw();
            log.info("Player {}, enter coordinates for {}", currentPlayer.getName(), currentPlayer.getFigure().getView());
            String coordinates = scanner.nextLine();
            String[] parts = coordinates.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            var wasLegalMove = canvas.setFigure(x, y, currentPlayer.getFigure());
            if (!wasLegalMove) {
                log.info("This move is illegal, try again");
                continue;
            }
            var gameResult = canvas.checkResult(currentPlayer);
            if (gameResult.isGameEnded()) {
                canvas.draw();
                if (gameResult.isDraw()) {
                    log.info("It's a draw!");
                } else {
                    log.info("Player {} has won!", gameResult.getWinner().getName());
                }
                break;
            }
            cursor++;
        }

        log.info("Game is finished");

    }

}
