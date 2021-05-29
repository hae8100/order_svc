package siren;

import siren.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired OrderRepository orderRepository;
    @Autowired ProductRepository productRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverProductRegistered_UpdatedProductStatus(@Payload ProductRegistered productRegistered){

        if(!productRegistered.validate()) return;

        System.out.println("\n\n##### listener UpdatedProductStatus : " + productRegistered.toJson() + "\n\n");

        Product product = new Product();
        product.setId(productRegistered.getId());
        product.setStatus(productRegistered.getStatus());
        productRepository.save(product);  

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverUpdatedProductStatus_UpdatedProductStatus(@Payload UpdatedProductStatus updatedProductStatus){

        if(!updatedProductStatus.validate()) return;

        System.out.println("\n\n##### listener UpdatedProductStatus : " + updatedProductStatus.toJson() + "\n\n");

        Product product = new Product();
        product.setId(updatedProductStatus.getId());
        product.setStatus(updatedProductStatus.getStatus());
        productRepository.save(product);  
            
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
