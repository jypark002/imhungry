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
    @Autowired RequestRepository requestRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrdered_UpdateStatus(@Payload Ordered ordered){

        if(!ordered.validate()) return;

        System.out.println("\n\n##### listener UpdateStatus : " + ordered.toJson() + "\n\n");

        // Sample Logic //
        Request request = new Request();
        requestRepository.save(request);
            
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
