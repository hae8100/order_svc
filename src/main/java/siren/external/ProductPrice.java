package siren.external;

import org.springframework.stereotype.Component;

@Component
public class ProductPrice implements ProductService {
    @Override 
    public Integer checkProduct(Long productId) {
    System.out.println("##### /ProductPrice Fallback  called #####");
    Integer price = 0;
            return price;
    }
}


