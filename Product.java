import java.time.LocalDate;

class Product {
  private SystemCalendar systemCalendar;

  private Price currentPrice;
  private Price previousPrice;
  private Price prePromotionPrice;

  private Promotion promotion;

  public Product(SystemCalendar systemCalendar) {
    this.systemCalendar = systemCalendar;
  }

  public void setPriceInCents(int priceInCents) {
    LocalDate today = systemCalendar.getDate();
    Price newPrice = new Price(priceInCents, today);

    if (currentPrice == null) {
      currentPrice = newPrice;
    } else if (newPrice.isLessThanPrice(currentPrice)) {
      decreasePrice(newPrice);
    } else if (newPrice.isGreaterThanPrice(currentPrice)) {
      increasePrice(newPrice);
    }
  }

  private void decreasePrice(Price newPrice) {
    changePrice(newPrice);

    if (priceIsReducedEnoughToEndPromotion()) {
      promotion = null;
    } else if (priceIsStable() && priceIsReducedEnoughToStartPromotion()) {
      promotion = new Promotion(systemCalendar);
      prePromotionPrice = previousPrice;
    }
  }

  private void changePrice(Price newPrice) {
    previousPrice = currentPrice;
    currentPrice = newPrice;
  }

  private boolean priceIsReducedEnoughToEndPromotion() {
    if (isPromotion()) {
      return currentPrice.isLessThanPercentOfPrice(70, prePromotionPrice);
    } else {
      return currentPrice.isLessThanPercentOfPrice(70, previousPrice);
    }
  }

  public boolean isPromotion() {
    return promotion != null && !promotion.hasExpired();
  }

  private boolean priceIsStable() {
    if (promotion == null) {
      return priceIsStableBeforePromotion();
    } else {
      return priceIsStableAfterPromotion();
    }
  }

  private boolean priceIsStableBeforePromotion() {
    LocalDate datePreviousPriceWasSet = previousPrice.getDatePriceWasSet();
    LocalDate dateCurrentPriceWasSet = currentPrice.getDatePriceWasSet();

    return (
      previousPrice != null &&
      datePreviousPriceWasSet.plusDays(29).isBefore(dateCurrentPriceWasSet)
    );
  }

  private boolean priceIsStableAfterPromotion() {
    LocalDate datePreviousPriceWasSet = previousPrice.getDatePriceWasSet();
    LocalDate dateCurrentPriceWasSet = currentPrice.getDatePriceWasSet();

    return (
      promotion.expiredThirtyDaysAgo() &&
      datePreviousPriceWasSet.plusDays(29).isBefore(dateCurrentPriceWasSet)
    );
  }

  private boolean priceIsReducedEnoughToStartPromotion() {
    return !currentPrice.isGreaterThanPercentOfPrice(95, previousPrice);
  }

  private void increasePrice(Price newPrice) {
    changePrice(newPrice);

    if (isPromotion()) {
      promotion = null;
    }
  }

  public LocalDate getPromotionExpirationDate() {
    return promotion.getExpirationDate();
  }

  public void increasePriceByPercent(double percent) {
    LocalDate today = systemCalendar.getDate();
    int newPriceInCents = currentPrice.getPriceInCents();
    newPriceInCents += Math.round(percent / 100 * newPriceInCents);
    Price newPrice = new Price(newPriceInCents, today);
    increasePrice(newPrice);
  }

  public void decreasePriceByPercent(double percent) {
    LocalDate today = systemCalendar.getDate();
    int newPriceInCents = currentPrice.getPriceInCents();
    newPriceInCents -= Math.round(percent / 100 * newPriceInCents);
    Price newPrice = new Price(newPriceInCents, today);
    decreasePrice(newPrice);
  }
}
