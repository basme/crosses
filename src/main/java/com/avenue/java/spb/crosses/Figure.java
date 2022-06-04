package com.avenue.java.spb.crosses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Figure {

    CROSS('x', 1),
    ZERO('o', -1),
    EMPTY('_', 0);

    @Getter
    private final char view;

    @Getter
    private final int value;

}
