package com.avenue.java.spb.crosses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameResult {

    private boolean isGameEnded;

    private Player winner;
    private boolean isDraw;

}
