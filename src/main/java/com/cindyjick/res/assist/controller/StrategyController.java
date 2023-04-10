package com.cindyjick.res.assist.controller;

import com.cindyjick.res.assist.model.OutputDegree;
import com.cindyjick.res.assist.model.Params;
import com.cindyjick.res.assist.model.Strategy;
import com.cindyjick.res.assist.model.StrategyNode;
import com.cindyjick.res.assist.util.StrategyNodeUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.*;

@Slf4j
public class StrategyController {
    @FXML
    private AnchorPane strategyPane;
    private Map<String, StrategyRectangle> nodes;
    private List<Line> lines;
    private Strategy strategy;
    private Canvas canvas;
    private GraphicsContext gc;

    public StrategyController(Strategy strategy) {
        this.strategy = strategy;
    }

    public StrategyController() {
        // TODO: remove mock data
        log.info("create strategy controller, instance:{}", this);
        var startNode = new StrategyNode("start", "开始", 250, 50);
        var node1 = new StrategyNode("node1", "节点1", 250, 100);
        var node2 = new StrategyNode("node2", "节点2", 250, 150);
        var end = new StrategyNode("end", "结束", 250, 200);
        this.strategy = new Strategy(new Params(List.of("a", "b", "c")), Map.of(
                startNode.getNodeCode().get(), startNode,
                node1.getNodeCode().get(), node1,
                node2.getNodeCode().get(), node2,
                end.getNodeCode().get(), end));
    }

    @FXML
    public void initialize() {
        bindCanvas();
        bindStrategy(this.strategy);

    }

    private void bindCanvas() {
        this.canvas = new Canvas(250, 250);
        this.gc = this.canvas.getGraphicsContext2D();
        this.gc.setFill(Color.BLUE);
        this.gc.fillRect(75, 75, 100, 100);
        this.strategyPane.getChildren().add(this.canvas);
    }

    public static class StrategyRectangle extends Rectangle {
        private double anchorX;
        private double anchorY;
        private StringProperty code;
        private StrategyNode strategyNode;

        public StrategyRectangle() {
            super();
        }

        public StrategyRectangle(double width, double height, Paint fill) {
            super(width, height, fill);
            this.code = new SimpleStringProperty();
        }

        public void recordAnchor(double x, double y) {
            this.anchorX = x;
            this.anchorY = y;
        }

        public void bindStrategyNode(@NonNull StrategyNode node) {
            Optional.of(node).ifPresent(e -> {
                this.strategyNode = node;
                this.code.bindBidirectional(node.getNodeCode());
                this.layoutXProperty().bindBidirectional(node.getX());
                this.layoutYProperty().bindBidirectional(node.getY());
            });
        }

        public void unbindStrategyNode(@NonNull StrategyNode node) {
            Optional.of(node).ifPresent(e -> {
                this.strategyNode = null;
                this.code.unbindBidirectional(node.getNodeCode());
                this.layoutXProperty().unbindBidirectional(node.getX());
                this.layoutYProperty().unbindBidirectional(node.getY());
            });
        }
    }

    private void bindStrategy(@NonNull Strategy strategy) {
        clear();
        this.strategy = strategy;
        // 可视化节点
        if (MapUtils.isNotEmpty(strategy.getNodes())) {
            strategy.getNodes().forEach((k, v) -> {
                var node = new StrategyRectangle(30, 30, Color.PINK);
                // 绑定节点
                node.bindStrategyNode(v);
                node.setOnMousePressed(event -> node.recordAnchor(event.getSceneX(), event.getSceneY()));
                node.setOnMouseDragged(event -> {
                    node.setTranslateX(event.getSceneX() - node.anchorX);
                    node.setTranslateY(event.getSceneY() - node.anchorY);
                });
                node.setOnMouseReleased(event -> {
                    node.setLayoutX(node.getLayoutX() + node.getTranslateX());
                    node.setLayoutY(node.getLayoutY() + node.getTranslateY());
                    node.setTranslateX(0);
                    node.setTranslateY(0);
                });
                nodes.put(k, node);
            });
        }
        // 可视化边
        if (MapUtils.isNotEmpty(strategy.getOutputDegree())) {
            strategy.getOutputDegree().forEach((k, v) -> {
                if (CollectionUtils.isEmpty(v)) {
                    return;
                }
                for (OutputDegree outputDegree : v) {
                    lines.add(initLine(nodes.get(k), nodes.get(outputDegree.getNode().getNodeCode().get())));
                }
            });
        }
        this.strategyPane.getChildren().addAll(this.nodes.values());
        this.strategyPane.getChildren().addAll(this.lines);
    }

    private Line initLine(@NonNull StrategyRectangle start, @NonNull StrategyRectangle end) {
        return StrategyNodeUtil.initLine(start, end);
    }

    private void clear() {
        Optional.ofNullable(this.nodes).ifPresent(e -> this.strategyPane.getChildren().removeAll(e.values()));
        Optional.ofNullable(this.lines).ifPresent(e -> this.strategyPane.getChildren().removeAll(e));
        this.strategy = null;
        this.nodes = new HashMap<>();
        this.lines = new ArrayList<>();
    }

}
