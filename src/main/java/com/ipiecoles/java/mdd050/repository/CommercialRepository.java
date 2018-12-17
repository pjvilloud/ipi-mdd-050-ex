package com.ipiecoles.java.mdd050.repository;

import com.ipiecoles.java.mdd050.model.Commercial;
import com.ipiecoles.java.mdd050.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommercialRepository extends JpaRepository<Commercial, Long> {

}
