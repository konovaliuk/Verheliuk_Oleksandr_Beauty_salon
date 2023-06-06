package org.webproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.webproject.models.User;
import org.webproject.models.Workday;

import java.util.List;

public interface WorkdayRepository extends JpaRepository<Workday, Long> {
}
