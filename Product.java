class Product {
  private int priceInCents;
  private boolean isRedPencilPromotion;

  public Product(int initialPriceInCents) {
    priceInCents = initialPriceInCents;
    isRedPencilPromotion = false;
  }

  public void setPriceInCents(int newPriceInCents) {
    priceInCents = newPriceInCents;
  }

  public boolean isRedPencilPromotion() {
    return isRedPencilPromotion;
  }
}
