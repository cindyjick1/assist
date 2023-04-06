package com.cindyjick.res.assist.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class StrategyController {
    public static class Params {
        public static final String CONTEXT = "Context";
        private final Map<String, Class<?>> param;
        private final Map<String, LinkedList<String>> source;

        public Params(List<String> varCodeList) {
            this.param = new ConcurrentHashMap<>();
            this.source = new ConcurrentHashMap<>();
            Optional.ofNullable(varCodeList).ifPresent(e -> e.forEach(f -> {
                param.put(f, String.class);
                source.computeIfAbsent(f, k -> new LinkedList<>()).add(CONTEXT);
            }));
        }
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
        private Map<String, List<StrategyNode>> inputDegree;

        public Strategy(Params params, Map<String, StrategyNode> nodes) {
            this.params = params;
            this.nodes = nodes;
        }
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
        var startNode = new StrategyNode("start", "开始", 50, 50);
        var node1 = new StrategyNode("node1", "节点1", 50, 100);
        var node2 = new StrategyNode("node1", "节点2", 50, 200);
        var end = new StrategyNode("end", "结束", 50, 300);
        this.strategy = new Strategy(new Params(List.of("a", "b", "c")), Map.of(
                startNode.nodeCode, startNode,
                node1.nodeCode, node1,
                node2.nodeCode, node2,
                end.nodeCode, end));
    }
}
