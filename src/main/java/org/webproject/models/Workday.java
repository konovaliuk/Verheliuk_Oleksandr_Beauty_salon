package org.webproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class Workday {

    private long idWorkday;

    private User master;

    private LocalDateTime workStart;

    private LocalDateTime workFinish;
}
