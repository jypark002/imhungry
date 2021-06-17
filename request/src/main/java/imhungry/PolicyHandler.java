package imhungry;

import imhungry.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @Autowired RequestRepository requestRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrdered_UpdateStatus(@Payload Ordered ordered){

        if(!ordered.validate()) return;

        System.out.println("\n\n##### listener wheneverOrdered_UpdateStatus : " + ordered.toJson() + "\n\n");

        // Sample Logic //
        Optional<Request> optionalRequest = requestRepository.findById(ordered.getRequestId());
        Request request = optionalRequest.get();
        request.setStatus(ordered.getStatus());
        request.setRequestId(ordered.getRequestId());
        request.setOrderId(ordered.getId());
        request.setMenuId(ordered.getMenuId());
        requestRepository.save(request);
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}
}
