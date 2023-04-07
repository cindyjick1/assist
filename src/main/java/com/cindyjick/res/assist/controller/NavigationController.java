package com.cindyjick.res.assist.controller;

import com.cindyjick.res.assist.controller.cell.StrategyOperationCell;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class NavigationController {
    private final Properties properties;
    @FXML
    private ComboBox<String> searchType;
    @FXML
    private TextField searchContent;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<String> searchResult;
    @FXML
    private TabPane tabs;
    private HomeController homeController;

    @SneakyThrows
    public NavigationController() {
        var resource = Optional.ofNullable(getClass().getClassLoader().getResource("navigation.properties")).orElseThrow(() -> new RuntimeException("load navigation.properties fail"));
        this.properties = new Properties();
        this.properties.load(new BufferedReader(new FileReader(resource.getFile(), StandardCharsets.UTF_8)));
    }

    @FXML
    public void initialize() {
        bindSearchType();
        bindSearchContent();
        bindSearchButton();
        bindSearchResult();
    }

    public void setHomeController(HomeController homeController) {
        if (null != this.homeController) {
            return;
        }
        this.homeController = homeController;
    }

    private void bindSearchType() {
        this.searchType.getItems().addAll(StringUtils.split(StringUtils.defaultIfBlank(this.properties.getProperty("search-type"), "All"), ","));
        this.searchType.getSelectionModel().selectFirst();
    }

    private void bindSearchContent() {
        this.searchContent.setOnMouseClicked(event -> searchResultHideOrShow(true));
    }

    private void bindSearchButton() {
        this.searchButton.setOnMouseClicked(event -> {
            // TODO: search result by key word and show
            searchResult.setItems(FXCollections.observableArrayList(List.of("strategyCode1", "strategyCode2", "strategyCode3")));
            searchResultHideOrShow(false);
        });
    }

    @SuppressWarnings("unchecked")
    private void bindSearchResult() {
        searchResultHideOrShow(true);
        var strategyCode = new TableColumn<String, String>("StrategyCode");
        var strategyDesc = new TableColumn<String, String>("StrategyDesc");
        var strategyVersion = new TableColumn<String, Number>("StrategyVersion");
        var strategyOperation = new TableColumn<String, String>("StrategyOperation");
        strategyCode.setCellValueFactory(item -> {
            System.out.println("strategyCode cell value " + item);
            return new SimpleStringProperty(item.getValue());
        });
        strategyDesc.setCellValueFactory(item -> {
            System.out.println("strategyDesc cell value " + item);
            return new SimpleStringProperty("fake strategy desc");
        });
        strategyVersion.setCellValueFactory(item -> {
            System.out.println("strategyVersion cell value " + item);
            return new SimpleIntegerProperty(1);
        });
        strategyOperation.setCellValueFactory(item -> {
            System.out.println("strategyOperation cell value " + item);
            return new SimpleStringProperty(item.getValue());
        });
        strategyOperation.setCellFactory(item -> new StrategyOperationCell<>() {
            @Override
            protected void initButton() {
                this.detailButton.setText("detail");
                this.editButton.setText("edit");
            }

            @Override
            protected void bindDataOnUpdateItem(String item, boolean rowEmpty) {
                this.detailButton.setOnMouseClicked(event -> {
                    System.out.println("click detail button, item:" + item);
                    // TODO: jump to strategy detail tab
                    Tab tab = loadStrategyTab(item);
                    homeController.setTabs(HomeController.TabOperation.ADD, tab);
                });
                this.editButton.setOnMouseClicked(event -> System.out.println("click edit button, item:" + item));
            }
        });
        strategyOperation.setEditable(true);
        this.searchResult.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.searchResult.setEditable(true);
        this.searchResult.getColumns().setAll(
                strategyCode,
                strategyDesc,
                strategyVersion,
                strategyOperation
        );
    }

    private void searchResultHideOrShow(boolean isHide) {
        if (isHide) {
            this.searchResult.opacityProperty().set(0);
            return;
        }
        this.searchResult.opacityProperty().set(1);
    }

    @SneakyThrows
    private Tab loadStrategyTab(String strategyCode) {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(this.getClass().getResource("strategy.fxml")));
        var tab = new Tab(strategyCode, fxmlLoader.load());
        var contextMenu = new ContextMenu();
        contextMenu.getItems().setAll(Arrays.stream(HomeController.TabOperation.values()).filter(e -> !HomeController.TabOperation.ADD.equals(e)).map(e -> {
            var menu = new MenuItem(e.getDesc());
            menu.setOnAction(event -> homeController.setTabs(e, tab));
            return menu;
        }).toArray(MenuItem[]::new));
        tab.setContextMenu(contextMenu);
        return tab;
    }
}
