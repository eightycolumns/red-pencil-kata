class Product {
  private int priceInCents;
  private boolean isRedPencilPromotion;

  public Product(int initialPriceInCents) {
    priceInCents = initialPriceInCents;
    isRedPencilPromotion = false;
  }

  public void setPriceInCents(int newPriceInCents) {
    if (
      newPriceInCents <= priceInCents * 0.95 &&
      newPriceInCents >= priceInCents * 0.70
    ) {
      isRedPencilPromotion = true;
    }

    priceInCents = newPriceInCents;
  }

  public boolean isRedPencilPromotion() {
    return isRedPencilPromotion;
  }
}
