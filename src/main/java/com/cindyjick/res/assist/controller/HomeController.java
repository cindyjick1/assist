package com.cindyjick.res.assist.controller;

import com.cindyjick.res.assist.util.NumberUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import java.util.Objects;
import java.util.Optional;

public class HomeController {
    @FXML
    private NavigationController navigationController;
    @FXML
    private Tab navigationTab;
    @FXML
    private BorderPane navigation;
    @FXML
    private TabPane tabs;

    @FXML
    public void initialize() {
        System.out.println("init home");
        transferInstance();
    }

    private void transferInstance() {
        navigationController.setHomeController(this);
    }

    public void setTabs(TabOperation operation, Tab tab) {
        if (navigationTab.equals(tab)) {
            System.out.println("navigation tab can't be operation");
            return;
        }
        final var tabItems = this.tabs.getTabs();
        final int start = 1;
        final int endIndex = tabItems.size() - 1;
        switch (Objects.requireNonNull(operation)) {
            case ADD -> {
                tabItems.add(Objects.requireNonNull(tab));
                this.tabs.getSelectionModel().select(tab);
                this.tabs.requestFocus();
            }
            case REMOVE -> Optional.of(tab)
                    .map(tabItems::indexOf)
                    .filter(e -> NumberUtil.inRange(e, start, endIndex))
                    .ifPresent(e -> tabItems.remove(e.intValue()));
            case REMOVE_LEFT -> Optional.of(tab)
                    .map(tabItems::indexOf)
                    .filter(e -> NumberUtil.inRange(e, start + 1, endIndex))
                    .ifPresent(e -> tabItems.remove(start, e));
            case REMOVE_RIGHT -> Optional.of(tab)
                    .map(tabItems::indexOf)
                    .filter(e -> NumberUtil.inRange(e, start, endIndex - 1))
                    .ifPresent(e -> tabItems.remove(e + 1, endIndex + 1));
            case REMOVE_OTHER -> Optional.of(tab).map(tabItems::indexOf)
                    .filter(e -> NumberUtil.inRange(e, start, endIndex))
                    .filter(e -> endIndex > 1)
                    .ifPresent(e -> {
                        setTabs(TabOperation.REMOVE_LEFT, tab);
                        setTabs(TabOperation.REMOVE_RIGHT, tab);
                    });
            case CLEAN -> {
                if (endIndex >= start) {
                    tabItems.remove(start, endIndex + 1);
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + Objects.requireNonNull(operation));
        }
    }

    public enum TabOperation {
        ADD("add", "新增"),
        REMOVE("remove", "关闭"),
        REMOVE_LEFT("removeLeft", "关闭左边"),
        REMOVE_RIGHT("removeRight", "关闭右边"),
        REMOVE_OTHER("removeOther", "关闭其它"),
        CLEAN("clean", "关闭所有"),
        ;

        TabOperation(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private final String code;
        private final String desc;

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }
}
