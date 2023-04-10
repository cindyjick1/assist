package com.cindyjick.res.assist.model;

import lombok.Getter;

import java.util.function.Predicate;

@Getter
public class OutputDegree {
    private StrategyNode node;
    private Predicate<Params> predicate;
}
