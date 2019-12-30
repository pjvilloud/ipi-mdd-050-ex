package com.ipiecoles.java.mdd050.repository;

import com.ipiecoles.java.mdd050.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Utilisateur findByUserName(String userName);
}
