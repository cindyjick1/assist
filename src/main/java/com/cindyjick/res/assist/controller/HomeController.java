package com.cindyjick.res.assist.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

    @SneakyThrows
    public HomeController() {
        this.properties = new Properties();
        URL resource = Optional.ofNullable(getClass().getClassLoader().getResource("home.properties")).orElseThrow(() -> new RuntimeException("load home.properties fail"));
        this.properties.load(new BufferedReader(new FileReader(resource.getFile(), StandardCharsets.UTF_8)));
    }

    @FXML
    public void initialize() {
        bindComboBoxItems();
    }

    private void bindComboBoxItems() {
        searchType.getItems().addAll(StringUtils.split(StringUtils.defaultIfBlank(this.properties.getProperty("search-type"), "All"), ","));
        searchType.getSelectionModel().selectFirst();
    }
}
