package com.skillswap.repository;

import com.skillswap.model.Skill;
import com.skillswap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByTypeAndOwner_IdNotOrderByCreatedAtDesc(Skill.SkillType type, Long ownerId);
    List<Skill> findByTypeAndOwner_IdNotAndTitleContainingIgnoreCaseOrderByCreatedAtDesc(
            Skill.SkillType type, Long ownerId, String title);
    List<Skill> findByOwnerOrderByCreatedAtDesc(User owner);
}
