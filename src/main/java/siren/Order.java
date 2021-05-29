package siren;

import javax.persistence.*;

import org.bouncycastle.asn1.ocsp.SingleResponse;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    // @GeneratedValue(strategy=GenerationType.AUTO)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Integer price;

    @PostPersist
    public void onPostPersist() throws Exception {

        Integer price = OrderApplication.applicationContext.getBean(siren.external.ProductService.class)
                .checkProduct(this.getProductId());

        Optional<Product> productOptional  = OrderApplication.applicationContext.getBean(siren.ProductRepository.class).findById(this.getProductId());
        Product product = productOptional.get();
        String status = product.getStatus();

                if ( price > 0 && !(status.equals("SoldOut")) && status != null ) {
        
                        Ordered ordered = new Ordered();
                        this.setPrice(price);
                        BeanUtils.copyProperties(this, ordered);
                        ordered.publishAfterCommit();

                } else
                    throw new Exception("Product Sold Out - Exception Raised");

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }




}
