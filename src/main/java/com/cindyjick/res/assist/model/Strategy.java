package com.cindyjick.res.assist.model;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Strategy {
    private Params params;
    private Map<String, StrategyNode> nodes;
    private StrategyNode startNode;
    private Map<String, StrategyNode> endNodes;
    private Map<String, List<OutputDegree>> outputDegree;
    private Map<String, List<StrategyNode>> inputDegree;

    public Strategy(Params params, Map<String, StrategyNode> nodes) {
        this.params = params;
        this.nodes = nodes;
        this.outputDegree = new ConcurrentHashMap<>();
        this.inputDegree = new ConcurrentHashMap<>();
    }
}
