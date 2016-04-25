import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductTest {
  private StubSystemCalendar stubSystemCalendar;
  private Product product;

  @Before
  public void init() {
    stubSystemCalendar = new StubSystemCalendar();
    product = new Product(stubSystemCalendar);
  }

  @Test
  public void priceReductionOf4PercentDoesNotStartRedPencilPromotion() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(96);
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void priceReductionOf5PercentDoesStartRedPencilPromotion() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(95);
    assertTrue(product.isRedPencilPromotion());
  }

  @Test
  public void priceReductionOf30PercentDoesStartRedPencilPromotion() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(70);
    assertTrue(product.isRedPencilPromotion());
  }

  @Test
  public void priceReductionOf31PercentDoesNotStartRedPencilPromotion() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(69);
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionCannotStartOn29thDayOfStablePricing() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(29);
    product.setPriceInCents(75);
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionCanStartOn30thDayOfStablePricing() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(75);
    assertTrue(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionCannotStartOn29thDayAfterPreviousPromotion() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(90);
    stubSystemCalendar.incrementDate(29);
    stubSystemCalendar.incrementDate(29);
    product.setPriceInCents(81);
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionCanStartOn30thDayAfterPreviousPromotion() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(90);
    stubSystemCalendar.incrementDate(29);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(81);
    assertTrue(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionLastsFor30Days() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(75);
    stubSystemCalendar.incrementDate(29);
    assertTrue(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionExpiresOn31stDay() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(75);
    stubSystemCalendar.incrementDate(30);
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void furtherPriceReductionDoesNotExtendRedPencilPromotion() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(90);
    stubSystemCalendar.incrementDate(15);
    product.setPriceInCents(81);
    stubSystemCalendar.incrementDate(15);
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionEndsEarlyIfPriceIncreases() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(75);
    product.setPriceInCents(76);
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionEndsIfPriceDropsBelow30PercentStartingPoint() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(90);
    product.setPriceInCents(80);
    product.setPriceInCents(70);
    product.setPriceInCents(60);
    assertFalse(product.isRedPencilPromotion());
  }
}
