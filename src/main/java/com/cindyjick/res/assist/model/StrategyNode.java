package com.cindyjick.res.assist.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

@Getter
public class StrategyNode {
    private StringProperty nodeCode;
    private StringProperty nodeDesc;
    private DoubleProperty x;
    private DoubleProperty y;

    public StrategyNode(String nodeCode, String nodeDesc, double x, double y) {
        this.nodeCode = new SimpleStringProperty(nodeCode);
        this.nodeDesc = new SimpleStringProperty(nodeDesc);
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
    }

    @Override
    public String toString() {
        return "StrategyNode{" +
                "nodeCode='" + nodeCode + '\'' +
                ", nodeDesc=" + nodeDesc +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
