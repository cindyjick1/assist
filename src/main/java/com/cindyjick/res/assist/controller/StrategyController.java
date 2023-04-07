package com.cindyjick.res.assist.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class StrategyController {
    @FXML
    private AnchorPane strategyPane;
    private List<Button> nodes;
    private List<Line> lines;
    private Strategy strategy;

    public StrategyController(Strategy strategy) {
        this.strategy = strategy;
    }

    public StrategyController() {
        // TODO: remove mock data
        var startNode = new StrategyNode("start", "开始", 250, 50);
        var node1 = new StrategyNode("node1", "节点1", 250, 100);
        var node2 = new StrategyNode("node2", "节点2", 250, 150);
        var end = new StrategyNode("end", "结束", 250, 200);
        this.strategy = new Strategy(new Params(List.of("a", "b", "c")), Map.of(
                startNode.nodeCode, startNode,
                node1.nodeCode, node1,
                node2.nodeCode, node2,
                end.nodeCode, end));

    }

    @FXML
    public void initialize() {
        System.out.println("pane x:" + this.strategyPane.getLayoutX() + ",pane y:" + this.strategyPane.getLayoutY());
        bindStrategy(this.strategy);
        this.strategyPane.getChildren().setAll(this.nodes);
        this.strategyPane.getChildren().addAll(this.lines);
    }

    static class DragButton extends Button {
        private double anchorX;
        private double anchorY;

        public DragButton(String text) {
            super(text);
        }

        public DragButton(String text, Node graphic) {
            super(text, graphic);
        }

        public DragButton setAnchorXY(double x, double y) {
            this.anchorX = x;
            this.anchorY = y;
            return this;
        }

        public double getDiffX(double x) {
            return x - this.anchorX;
        }

        public double getDiffY(double y) {
            return y - this.anchorY;
        }
    }

    private void bindStrategy(Strategy strategy) {
        clear();
        this.strategy = strategy;
        // 绑定节点
        Optional.ofNullable(strategy.nodes).orElse(new ConcurrentHashMap<>()).forEach((k, v) -> {
            DragButton button = new DragButton(k);
            button.setLayoutX(v.x);
            button.setLayoutY(v.y);
            button.setPrefWidth(60);
            button.setPrefHeight(40);
            button.setOnMousePressed(event -> {
                button.setAnchorXY(event.getSceneX(), event.getSceneY());
            });
            button.setOnMouseDragged(event -> {
                button.setTranslateX(button.getDiffX(event.getSceneX()));
                button.setTranslateY(button.getDiffY(event.getSceneY()));
            });
            button.setOnMouseReleased(event -> {
                button.setLayoutX(button.getLayoutX() + button.getTranslateX());
                button.setLayoutY(button.getLayoutY() + button.getTranslateY());
                button.setTranslateX(0);
                button.setTranslateY(0);
            });
            nodes.add(button);
        });
        // 绑定边
    }

    private void clear() {
        this.nodes = new LinkedList<>();
        this.lines = new ArrayList<>();
        this.strategy = null;
        this.strategyPane.getChildren().clear();
    }


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
}
