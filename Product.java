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
    } else if (newPrice.getPriceInCents() < currentPrice.getPriceInCents()) {
      reducePrice(newPrice);
    } else if (newPrice.getPriceInCents() > currentPrice.getPriceInCents()) {
      increasePrice(newPrice);
    }
  }

  private void reducePrice(Price newPrice) {
    changePrice(newPrice);

    if (priceIsReducedEnoughToEndPromotion()) {
      promotion = null;
    } else if (priceIsStable() && priceIsReducedEnoughToStartPromotion()) {
      promotion = new Promotion();
      prePromotionPrice = previousPrice;
    }
  }

  private void changePrice(Price newPrice) {
    previousPrice = currentPrice;
    currentPrice = newPrice;
  }

  private boolean priceIsReducedEnoughToEndPromotion() {
    if (isPromotion()) {
      return newPriceIsLessThan70PercentOf(prePromotionPrice);
    } else {
      return newPriceIsLessThan70PercentOf(previousPrice);
    }
  }

  public boolean isPromotion() {
    return promotion != null && !promotion.hasExpired();
  }

  private boolean newPriceIsLessThan70PercentOf(Price price) {
    int priceInCents = price.getPriceInCents();
    return currentPrice.getPriceInCents() < (int)Math.round(priceInCents * 0.7);
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
    int currentPriceInCents = currentPrice.getPriceInCents();
    int previousPriceInCents = previousPrice.getPriceInCents();

    return currentPriceInCents <= (int)Math.round(previousPriceInCents * 0.95);
  }

  private void increasePrice(Price newPrice) {
    changePrice(newPrice);

    if (isPromotion()) {
      promotion = null;
    }
  }
}
