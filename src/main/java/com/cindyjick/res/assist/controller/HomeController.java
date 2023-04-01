package com.cindyjick.res.assist.controller;

import com.cindyjick.res.assist.controller.cell.StrategyOperationCell;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class HomeController {
    private final Properties properties;
    @FXML
    private ComboBox<String> searchType;
    @FXML
    private TextField searchContent;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<String> searchResult;

    @SneakyThrows
    public HomeController() {
        this.properties = new Properties();
        var resource = Optional.ofNullable(getClass().getClassLoader().getResource("home.properties")).orElseThrow(() -> new RuntimeException("load home.properties fail"));
        this.properties.load(new BufferedReader(new FileReader(resource.getFile(), StandardCharsets.UTF_8)));
    }

    @FXML
    public void initialize() {
        bindComboBoxItems();
        bindSearchContent();
        bindSearchButton();
        initSearchResult();
    }

    private void bindComboBoxItems() {
        this.searchType.getItems().addAll(StringUtils.split(StringUtils.defaultIfBlank(this.properties.getProperty("search-type"), "All"), ","));
        this.searchType.getSelectionModel().selectFirst();
    }

    private void bindSearchContent() {
        this.searchContent.setOnMouseClicked(event -> searchResultHideOrShow(true));
    }

    private void bindSearchButton() {
        this.searchButton.setOnMouseClicked(event -> {
            // TODO: search result by key word and show
            searchResult.setItems(FXCollections.observableArrayList(List.of("jiebei_call3", "jiebei_call30", "jiebei_call2")));
            System.out.println("initData.....................");
            searchResultHideOrShow(false);
        });
    }

    @SuppressWarnings("unchecked")
    private void initSearchResult() {
        TableColumn<String, String> strategyCode = new TableColumn<>("StrategyCode");
        TableColumn<String, String> strategyDesc = new TableColumn<>("StrategyDesc");
        TableColumn<String, Number> strategyVersion = new TableColumn<>("StrategyVersion");
        TableColumn<String, String> operation = new TableColumn<>("Operation");
        strategyCode.setCellValueFactory(item -> {
            System.out.println("strategyCode cell value " + item);
            return new SimpleStringProperty(item.getValue());
        });
        strategyDesc.setCellValueFactory(item -> {
            System.out.println("strategyDesc cell value " + item);
            return new SimpleStringProperty("策略描述");
        });
        strategyVersion.setCellValueFactory(item -> {
            System.out.println("strategyVersion cell value " + item);
            return new SimpleIntegerProperty(1);
        });
        operation.setCellFactory(item -> new StrategyOperationCell<>());
        this.searchResult.getColumns().setAll(
                strategyCode,
                strategyDesc,
                strategyVersion,
                operation
        );
        searchResultHideOrShow(true);
    }

    private void searchResultHideOrShow(boolean isHide) {
        if (isHide) {
            this.searchResult.opacityProperty().set(0);
            return;
        }
        this.searchResult.opacityProperty().set(1);
    }
}
