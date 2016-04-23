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

    product.setPriceInCents(100);
  }

  @Test
  public void fourPercentPriceReductionDoesNotStartRedPencilPromotion() {
    product.setPriceInCents(96);
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void fivePercentPriceReductionStartsRedPencilPromotion() {
    product.setPriceInCents(95);
    assertTrue(product.isRedPencilPromotion());
  }

  @Test
  public void thirtyPercentPriceReductionStartsRedPencilPromotion() {
    product.setPriceInCents(70);
    assertTrue(product.isRedPencilPromotion());
  }

  @Test
  public void thirtyOnePercentPriceReductionDoesNotStartRedPencilPromotion() {
    product.setPriceInCents(69);
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionExpiresInThirtyDays() {
    product.setPriceInCents(75);

    stubSystemCalendar.incrementDate(29);
    assertTrue(product.isRedPencilPromotion());

    stubSystemCalendar.incrementDate(30);
    assertFalse(product.isRedPencilPromotion());
  }
}
