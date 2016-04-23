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
  public void fourPercentPriceReductionDoesNotStartRedPencilPromotion() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(96);
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void fivePercentPriceReductionStartsRedPencilPromotion() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(95);
    assertTrue(product.isRedPencilPromotion());
  }

  @Test
  public void thirtyPercentPriceReductionStartsRedPencilPromotion() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(70);
    assertTrue(product.isRedPencilPromotion());
  }

  @Test
  public void thirtyOnePercentPriceReductionDoesNotStartRedPencilPromotion() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(69);
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionCanOnlyStartAfterThirtyDaysOfStablePricing() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(29);
    product.setPriceInCents(75);
    assertFalse(product.isRedPencilPromotion());

    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(75);
    assertTrue(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionExpiresInThirtyDays() {
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(75);

    stubSystemCalendar.incrementDate(29);
    assertTrue(product.isRedPencilPromotion());

    stubSystemCalendar.incrementDate(30);
    assertFalse(product.isRedPencilPromotion());
  }
}
