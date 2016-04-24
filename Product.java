import java.time.LocalDate;
import java.util.TreeMap;

class Product {
  private SystemCalendar systemCalendar;
  private RedPencilPromotion redPencilPromotion;
  private TreeMap<LocalDate, Integer> pricingHistory;

  public Product(SystemCalendar systemCalendar) {
    this.systemCalendar = systemCalendar;
    pricingHistory = new TreeMap<LocalDate, Integer>();
  }

  public void setPriceInCents(int newPriceInCents) {
    int currentPriceInCents;

    if (!pricingHistory.isEmpty()) {
      currentPriceInCents = pricingHistory.get(pricingHistory.lastKey());

      if (newPriceInCents == currentPriceInCents) {
        return;
      }

      determinePromotionStatus(currentPriceInCents, newPriceInCents);
    }

    LocalDate today = systemCalendar.getDate();
    newPriceInCents = new Integer(newPriceInCents);

    pricingHistory.put(today, newPriceInCents);
  }

  private void determinePromotionStatus(
    int currentPriceInCents,
    int newPriceInCents
  ) {
    if (newPriceInCents > currentPriceInCents) {
      redPencilPromotion = null;
    } else if (redPencilPromotion != null && !redPencilPromotion.isExpired()) {
      return;
    } else if (!priceReductionQualifies(currentPriceInCents, newPriceInCents)) {
      return;
    } else if (!priceIsStable()) {
      return;
    } else {
      redPencilPromotion = new RedPencilPromotion(systemCalendar);
    }
  }

  private boolean priceReductionQualifies(
    int currentPriceInCents,
    int newPriceInCents
  ) {
    return (
      newPriceInCents <= currentPriceInCents * 0.95 &&
      newPriceInCents >= currentPriceInCents * 0.70
    );
  }

  private boolean priceIsStable() {
    LocalDate dateOfLastPriceChange = pricingHistory.lastKey();
    LocalDate today = systemCalendar.getDate();

    return dateOfLastPriceChange.plusDays(29).isBefore(today);
  }

  public boolean isRedPencilPromotion() {
    return redPencilPromotion != null && !redPencilPromotion.isExpired();
  }
}
