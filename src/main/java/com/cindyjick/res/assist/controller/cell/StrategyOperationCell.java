package com.cindyjick.res.assist.controller.cell;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class StrategyOperationCell<S,T> extends TableCell<S, T> {
    private Button detailButton;
    private Button editButton;

    @Override
    public void startEdit() {
        super.startEdit();
        System.out.println("start edit");
    }

    @Override
    public void commitEdit(T newValue) {
        super.commitEdit(newValue);
        System.out.println("commit edit");
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        System.out.println("cancel edit");
    }
}
