package com.cindyjick.res.assist.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class StrategyController {
    public abstract static class Params {
        private Map<String, Class<?>> param;
        private Map<String, LinkedList<String>> source;
    }

    public static class StrategyNode {
        private String nodeCode;
        private String nodeDesc;
        private double x;
        private double y;

        public StrategyNode(String nodeCode, String nodeDesc, double x, double y) {
            this.nodeCode = nodeCode;
            this.nodeDesc = nodeDesc;
            this.x = x;
            this.y = y;
        }
    }

    public static class Strategy {
        private Params params;
        private Map<String, StrategyNode> nodes;
        private StrategyNode startNode;
        private Map<String, StrategyNode> endNodes;
        private Map<String, List<OutputDegree>> outputDegree;

    }

    public static class OutputDegree {
        private StrategyNode node;
        private Predicate<Params> predicate;
    }

    private Strategy strategy;

    public StrategyController(Strategy strategy) {
        this.strategy = strategy;
    }

    public StrategyController() {
        // TODO: remove mock data
        this.strategy = new Strategy();
        StrategyNode startNode = new StrategyNode("start", "开始", 50, 50);

    }
}
