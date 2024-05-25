package com.schmuhis.quantumschmuh;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("dice")
public class DiceView extends VerticalLayout {

    private final DiceService diceService;

    private final Button button;

    private final NativeLabel label;
    private final Button plusButton;

    public DiceView(DiceService diceService) {

        this.diceService = diceService;

        this.getThemeList().add(Lumo.DARK);
        this.setHeight(100, Unit.VH);
        this.setWidth(100, Unit.VW);

        this.label = new NativeLabel();
        this.label.setClassName(LumoUtility.FontSize.XXXLARGE);
        this.label.setVisible(false);

        this.plusButton = new Button(new Icon(VaadinIcon.REFRESH));
        plusButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        plusButton.setVisible(false);
        this.plusButton.addClickListener(event -> refresh());

        this.button = new Button("Roll Die");
        button.addClassNames(LumoUtility.FontSize.XXLARGE, LumoUtility.Padding.XLARGE);
        this.button.addClickListener(event -> clickButton());

        var layout = new HorizontalLayout(this.button, this.label, this.plusButton);
        layout.setAlignItems(Alignment.CENTER);
        layout.setHeight(100, Unit.PERCENTAGE);

        this.setAlignItems(Alignment.CENTER);
        add(layout);
    }

    private void clickButton() {
        this.button.setVisible(false);
        this.label.setVisible(true);
        this.plusButton.setVisible(true);
        this.label.setText(String.valueOf(diceService.dieRollFuture()));
    }

    private void refresh() {
        this.label.setVisible(false);
        this.plusButton.setVisible(false);
        this.button.setVisible(true);
    }
}
