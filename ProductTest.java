import org.junit.Test;
import static org.junit.Assert.*;

public class ProductTest {
  @Test
  public void fourPercentPriceReductionDoesNotStartRedPencilPromotion() {
    Product product = new Product(100);
    product.setPriceInCents(96);
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void fivePercentPriceReductionStartsRedPencilPromotion() {
    Product product = new Product(100);
    product.setPriceInCents(95);
    assertTrue(product.isRedPencilPromotion());
  }
}
