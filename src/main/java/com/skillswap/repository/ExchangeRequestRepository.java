package com.skillswap.repository;

import com.skillswap.model.ExchangeRequest;
import com.skillswap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequest, Long> {
    List<ExchangeRequest> findByReceiverOrderByCreatedAtDesc(User receiver);
    List<ExchangeRequest> findBySenderOrderByCreatedAtDesc(User sender);
    boolean existsBySenderAndSkillAndStatus(User sender, com.skillswap.model.Skill skill, ExchangeRequest.Status status);
}
