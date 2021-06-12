package jyrestaurant;

import jyrestaurant.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired OrderRepository orderRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverMenuSelected_Order(@Payload MenuSelected menuSelected){

        if(!menuSelected.validate()) return;

        System.out.println("\n\n##### listener Order : " + menuSelected.toJson() + "\n\n");

        // Sample Logic //
        Order order = new Order();
        orderRepository.save(order);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverMenuSelected_Order(@Payload MenuSelected menuSelected){

        if(!menuSelected.validate()) return;

        System.out.println("\n\n##### listener Order : " + menuSelected.toJson() + "\n\n");

        // Sample Logic //
        Order order = new Order();
        orderRepository.save(order);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRequestCanceled_OrderCancel(@Payload RequestCanceled requestCanceled){

        if(!requestCanceled.validate()) return;

        System.out.println("\n\n##### listener OrderCancel : " + requestCanceled.toJson() + "\n\n");

        // Sample Logic //
        Order order = new Order();
        orderRepository.save(order);
            
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
