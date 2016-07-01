package com.deuxlapins.amsterdam.vo;

/**
 * Represents the Temperature Units
 * Created by chetan on 29/06/16.
 */
public enum TempertatureUnits {
    FAHRENHEIT("F", "Fahrenheit"), CELSIUS("C", "Celsius");

    private String displaySymbol;
    private String displayName;

    TempertatureUnits(String symbol, String displayName) {
        this.displaySymbol = symbol;
        this.displayName = displayName;
    }

    /**
     * Get the abbreviated symbol for this unit
     * @return symbol
     */
    public String getDisplaySymbol() {
        return displaySymbol;
    }

    /**
     * Get the full name for this unit
     * @return unit name in presentable format
     */
    public String getDisplayName() {
        return displayName;
    }
}
