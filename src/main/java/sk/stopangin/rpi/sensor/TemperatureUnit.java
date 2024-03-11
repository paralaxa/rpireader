package sk.stopangin.rpi.sensor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TemperatureUnit implements Unit {
  F("Fahrenheit", UnitCategory.TEMPERATURE),
  C("Celsius", UnitCategory.TEMPERATURE);

  private final String symbol;
  private final UnitCategory category;


  @Override
  public String getSymbol() {
    return symbol;
  }

  @Override
  public UnitCategory getCategory() {
    return category;
  }
}
