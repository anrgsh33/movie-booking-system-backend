package com.example.demo.service.serviceImpl;

import com.example.demo.entity.RefundEntity;
import com.example.demo.entity.ViewerEntity;
import com.example.demo.exception.CustomServiceException;
import com.example.demo.repository.RefundRepository;
import com.example.demo.repository.ViewerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@EnableScheduling
public class CronJobServiceImpl {

    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private ViewerRepository viewerRepository;

    //@Scheduled(cron = "0 * * * * ?")
    public void updateRefundStatus(){
        List<RefundEntity> refundList=refundRepository.findAll();
        log.info("updateRefundStatus called");
        for(RefundEntity refund : refundList){
            ViewerEntity viewer = viewerRepository.findById(refund.getViewer().getId())
                    .orElseThrow(() -> new CustomServiceException("Viewer not found"));

        log.info(refund.getRefundStatus());

           if(!Objects.equals(refund.getRefundStatus(), "rejected")) {
               LocalDateTime movieDate = viewer.getMovieDate();
               LocalDateTime refundRequestAt = refund.getRefundRequestAt();


               long differneceOfDays = ChronoUnit.DAYS.between(refundRequestAt.toLocalDate(), movieDate.toLocalDate());

               log.info("Days Difference"+differneceOfDays);

               if (differneceOfDays <= 1) {
                   refund.setRefundStatus("rejected");
                   log.info("Crone Job Service : Refund Rejected");
               }
               else{
                   refund.setRefundStatus("approved");
                   log.info("Crone Job Service : Refund Approved");
               }
               refundRepository.save(refund);


           }

        }
    }


}
