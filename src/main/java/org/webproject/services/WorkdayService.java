package org.webproject.services;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.webproject.models.Workday;
import org.webproject.repository.WorkdayRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Service
@Transactional
public class WorkdayService {

    private final WorkdayRepository workdayRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public WorkdayService(WorkdayRepository workdayRepository) {
        this.workdayRepository = workdayRepository;
    }

    public Workday getWorkday(long id){
        return workdayRepository.findById(id).orElse(null);
    }
    public List<Workday> getAllWorkdays(){
        return workdayRepository.findAll();
    }
    public Page<Workday> getAllWorkdays(int page, int pageSize){
        Pageable pageable = PageRequest.of(page-1, pageSize);
        return workdayRepository.findAll(pageable);
    }

    public Workday createWorkday(Workday workday){
        return workdayRepository.save(workday);
    }
    public void updateWorkday(Workday workday){
        workdayRepository.save(workday);
    }
    public void deleteWorkday(long id){
        workdayRepository.deleteById(id);
    }
}
