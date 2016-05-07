import java.time.LocalDate;

class Product {
  private DateProvider dateProvider;

  private Price currentPrice;
  private Price previousPrice;
  private Price prePromotionPrice;

  private Promotion promotion;

  public Product(DateProvider dateProvider) {
    this.dateProvider = dateProvider;
  }

  public void setPriceInCents(int priceInCents) {
    LocalDate today = dateProvider.getDate();
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
      promotion = new Promotion(dateProvider);
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

  public void decreasePriceByPercent(double percent) {
    LocalDate today = dateProvider.getDate();
    int newPriceInCents = currentPrice.getPriceInCents();
    newPriceInCents -= Math.round(percent / 100 * newPriceInCents);
    Price newPrice = new Price(newPriceInCents, today);
    decreasePrice(newPrice);
  }

  public void increasePriceByPercent(double percent) {
    LocalDate today = dateProvider.getDate();
    int newPriceInCents = currentPrice.getPriceInCents();
    newPriceInCents += Math.round(percent / 100 * newPriceInCents);
    Price newPrice = new Price(newPriceInCents, today);
    increasePrice(newPrice);
  }

  public LocalDate getPromotionExpirationDate() {
    return promotion.getExpirationDate();
  }
}
