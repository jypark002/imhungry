package imhungry;

import imhungry.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyHandler{
    @Autowired DicisionRepository dicisionRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRequestCanceled_MenuCancel(@Payload RequestCanceled requestCanceled){

        if(!requestCanceled.validate()) return;

        System.out.println("\n\n##### listener MenuCancel : " + requestCanceled.toJson() + "\n\n");

        // Sample Logic //
        Dicision dicision = dicisionRepository.findByRequestId(requestCanceled.getRequestId());
//        List<Dicision> list = dicisionRepository.findByRequestId(requestCanceled.getRequestId());
//        for (Dicision dicision : list) {
//            dicision.setStatus("CANCELED");
            dicision.setStatus(requestCanceled.getStatus());
            dicisionRepository.save(dicision);
//        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}

}
