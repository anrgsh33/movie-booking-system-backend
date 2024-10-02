package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="refund")
public class RefundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int refundId;

    @Column(nullable = false)
    private String refundStatus;

    @OneToOne
    @JoinColumn(name = "viewer_id",nullable = false)
    private ViewerEntity viewer;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime refundRequestAt;

}
