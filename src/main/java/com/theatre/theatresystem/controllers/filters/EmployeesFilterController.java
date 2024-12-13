package com.theatre.theatresystem.controllers.filters;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class EmployeesFilterController {
    public AnchorPane employeesFilter;
    public Button closeMenuButton;
    public ScrollPane chipsScrollPane;
    public RadioButton allRadioButton;
    public RadioButton femaleRadioButton;
    public RadioButton maleRadioButton;
    public ScrollPane mainScrollPane;
    public Button clearFiltersButton;

    @FXML
    void initialize() {
        // Обработчик прокрутки для chipsScrollPane (внутренний ScrollPane)
        chipsScrollPane.setOnScroll(event -> {
            // Перенаправляем вертикальную прокрутку на горизонтальную только в том случае, если мышь на chipsScrollPane
            if (chipsScrollPane.contains(event.getX(), event.getY())) {
                event.consume(); // Останавливаем дальнейшее распространение события
                double deltaY = event.getDeltaY(); // Величина прокрутки по вертикали
                chipsScrollPane.setHvalue(chipsScrollPane.getHvalue() - deltaY / chipsScrollPane.getWidth());
            } else {
                // Прокручиваем внешний ScrollPane, если мышь не на chipsScrollPane
                mainScrollPane.setVvalue(mainScrollPane.getVvalue() - event.getDeltaY() / mainScrollPane.getHeight());
            }
        });

        // Обработчик прокрутки для mainScrollPane (внешний ScrollPane)
        mainScrollPane.setOnScroll(event -> {
            // Проверяем, если мышь находится на chipsScrollPane
            if (chipsScrollPane.contains(event.getX(), event.getY())) {
                // Если мышь на chipsScrollPane, прокручиваем только его
                event.consume(); // Останавливаем распространение события
                double deltaY = event.getDeltaY(); // Величина прокрутки по вертикали
                chipsScrollPane.setHvalue(chipsScrollPane.getHvalue() - deltaY / chipsScrollPane.getWidth());
            } else {
                // Если мышь на mainScrollPane, прокручиваем только его
                event.consume(); // Останавливаем распространение события
                mainScrollPane.setVvalue(mainScrollPane.getVvalue() - event.getDeltaY() / mainScrollPane.getHeight());
            }
        });


        ToggleGroup genderGroup = new ToggleGroup();

        allRadioButton.setToggleGroup(genderGroup);
        femaleRadioButton.setToggleGroup(genderGroup);
        maleRadioButton.setToggleGroup(genderGroup);
    }
}
