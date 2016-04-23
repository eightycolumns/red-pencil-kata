import java.util.Calendar;
import java.util.TreeMap;

class Product {
  private SystemCalendar systemCalendar;
  private RedPencilPromotion redPencilPromotion;
  private TreeMap<Calendar, Integer> pricingHistory;

  public Product(SystemCalendar systemCalendar) {
    this.systemCalendar = systemCalendar;
    pricingHistory = new TreeMap<Calendar, Integer>();
  }

  public void setPriceInCents(int priceInCents) {
    if (priceQualifiesForRedPencilPromotion(priceInCents)) {
      redPencilPromotion = new RedPencilPromotion(systemCalendar);
    }

    Calendar today = systemCalendar.getDate();
    priceInCents = new Integer(priceInCents);

    pricingHistory.put(today, priceInCents);
  }

  private boolean priceQualifiesForRedPencilPromotion(int priceInCents) {
    if (pricingHistory.isEmpty()) {
      return false;
    }

    int previousPriceInCents = pricingHistory.get(pricingHistory.lastKey());
    
    return (
      priceInCents <= previousPriceInCents * 0.95 &&
      priceInCents >= previousPriceInCents * 0.70
    );
  }

  public boolean isRedPencilPromotion() {
    return redPencilPromotion != null && !redPencilPromotion.isExpired();
  }
}
