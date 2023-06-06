package org.webproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "workdays")
@AllArgsConstructor
@NoArgsConstructor
public class Workday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long idWorkday;

    @ManyToOne
    @JoinColumn(name = "id_master")
    private User Master;

    @Column(name = "work_start")
    private LocalDateTime workStart;

    @Column(name = "work_finish")
    private LocalDateTime workFinish;
}