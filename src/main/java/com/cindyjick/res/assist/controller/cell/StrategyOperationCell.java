package com.cindyjick.res.assist.controller.cell;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

public abstract class StrategyOperationCell<S, T> extends TableCell<S, T> {
    private final HBox hBox;
    protected final Button detailButton;
    protected final Button editButton;

    public StrategyOperationCell() {
        this.detailButton = new Button("detail");
        this.editButton = new Button("edit");
        initButton();
        this.hBox = new HBox();
        this.hBox.getChildren().setAll(this.detailButton, this.editButton);
    }

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

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (this.isEmpty()) {
            System.out.println("update item is empty");
            this.setText(null);
            this.setGraphic(null);
        } else {
            this.setGraphic(this.hBox);
            bindDataOnUpdateItem(item, empty);
            if (this.isEditing()) {
                System.out.println("update item is editing, item:" + item);
            } else {
                System.out.println("update item is not editing, item:" + item);
            }
        }
    }

    protected abstract void initButton();

    protected abstract void bindDataOnUpdateItem(T item, boolean rowEmpty);
}
