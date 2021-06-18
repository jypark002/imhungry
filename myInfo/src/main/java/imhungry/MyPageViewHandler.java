package imhungry;

import imhungry.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MyPageViewHandler {


    @Autowired
    private MyPageRepository myPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenRequested_then_CREATE_1 (@Payload Requested requested) {
        try {
            System.out.println("########## whenRequested_then_CREATE_1 : " + requested.toJson());

            if (!requested.validate()) return;

            // view 객체 생성
            MyPage myPage = new MyPage();
            // view 객체에 이벤트의 Value 를 set 함
            myPage.setStatus(requested.getStatus());
            myPage.setRequestId(requested.getRequestId());
            myPage.setMenuType(requested.getMenuType());
            // view 레파지 토리에 save
//            myPageRepository.save(myPage);
        
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenMenuSelected_then_UPDATE_1(@Payload MenuSelected menuSelected) {
        try {
            System.out.println("########## whenMenuSelected_then_UPDATE_1 : " + menuSelected.toJson());
            if (!menuSelected.validate()) return;
                // view 객체 조회
//            List<MyPage> myPageList = myPageRepository.findByRequestId(menuSelected.getRequestId());
//            for(MyPage myPage : myPageList){
            MyPage myPage = new MyPage();
            // view 객체에 이벤트의 eventDirectValue 를 set 함
            myPage.setStatus(menuSelected.getStatus());
            myPage.setMenuType(menuSelected.getMenuType());
            myPage.setRequestId(menuSelected.getRequestId());
            myPage.setMenuId(menuSelected.getMenuId());
            // view 레파지 토리에 save
            myPageRepository.save(myPage);
//            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrdered_then_UPDATE_2(@Payload Ordered ordered) {
        try {
            System.out.println("########## whenOrdered_then_UPDATE_2 : " + ordered.toJson());
            if (!ordered.validate()) return;
                // view 객체 조회
            List<MyPage> myPageList = myPageRepository.findByRequestId(ordered.getRequestId());
            for(MyPage myPage : myPageList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setStatus(ordered.getStatus());
                myPage.setOrderId(ordered.getOrderId());
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenRequestCanceled_then_UPDATE_3(@Payload RequestCanceled requestCanceled) {
        try {
            System.out.println("########## whenRequestCanceled_then_UPDATE_3 : " + requestCanceled.toJson());
            if (!requestCanceled.validate()) return;
                // view 객체 조회
            List<MyPage> myPageList = myPageRepository.findByRequestId(requestCanceled.getRequestId());
            for(MyPage myPage : myPageList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setStatus(requestCanceled.getStatus());
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}