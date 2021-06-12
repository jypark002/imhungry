package jyrestaurant;

import jyrestaurant.config.kafka.KafkaProcessor;
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

            if (!requested.validate()) return;

            // view 객체 생성
            MyPage myPage = new MyPage();
            // view 객체에 이벤트의 Value 를 set 함
            myPage.setStatus(requested.getStatus());
            myPage.setRequestId(requested.getId());
            myPage.setMenuType(requested.getMemuType());
            // view 레파지 토리에 save
            myPageRepository.save(myPage);
        
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenMenuSelected_then_UPDATE_1(@Payload MenuSelected menuSelected) {
        try {
            if (!menuSelected.validate()) return;
                // view 객체 조회
            List<MyPage> myPageList = myPageRepository.findByRequestId(menuSelected.getRequestId());
            for(MyPage myPage : myPageList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setStatus(menuSelected.getStatus());
                myPage.setMenuId(menuSelected.getId());
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrdered_then_UPDATE_2(@Payload Ordered ordered) {
        try {
            if (!ordered.validate()) return;
                // view 객체 조회
            List<MyPage> myPageList = myPageRepository.findByRequestId(ordered.getRequestId());
            for(MyPage myPage : myPageList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myPage.setStatus(ordered.getStatus());
                myPage.setOrderId(ordered.getId());
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
            if (!requestCanceled.validate()) return;
                // view 객체 조회
            List<MyPage> myPageList = myPageRepository.findByRequestId(requestCanceled.getId());
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