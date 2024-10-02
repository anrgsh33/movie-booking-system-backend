package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="movie_viewers")
public class ViewerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="movie_date",nullable = false)
    private LocalDateTime movieDate;

    @CreationTimestamp
    @Column(name = "booked_at", nullable = false, updatable = false)
    private LocalDateTime bookedAt;

    @ManyToOne
    @JoinColumn(name = "movie_id",nullable = false)
    private MovieEntity movie;

    @Builder.Default
    @Column(nullable = false)
    private boolean ticketCancelled=false;

    public boolean getTicketCancelled(){
        return this.ticketCancelled;
    }


}
