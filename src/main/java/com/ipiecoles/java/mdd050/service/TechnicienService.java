package com.ipiecoles.java.mdd050.service;

import com.ipiecoles.java.mdd050.model.Entreprise;
import com.ipiecoles.java.mdd050.model.Manager;
import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.repository.ManagerRepository;
import com.ipiecoles.java.mdd050.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Pattern;
import java.util.Optional;

@Service
@Validated
public class TechnicienService {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private TechnicienRepository technicienRepository;

    public Technicien addManager(Long idTechnicien,
                                 @Pattern(regexp = Entreprise.REGEX_MATRICULE_MANAGER,
                                         message = "doit être M, T ou C suivi de 5 chiffres") String matricule) {
        Optional<Technicien> t = technicienRepository.findById(idTechnicien);
        if(!t.isPresent()){
            throw new EntityNotFoundException("Impossible de trouver le technicien d'identifiant " + idTechnicien);
        }
        Manager m = managerRepository.findByMatricule(matricule);
        if(m == null){
            throw new EntityNotFoundException("Impossible de trouver le manager de matricule " + matricule);
        }
        Technicien technicien = t.get();
        if(technicien.getManager() != null){
            throw new IllegalArgumentException("Le technicien a déjà un manager : " + technicien.getManager().getPrenom() + " " + technicien.getManager().getNom()
                    + " (matricule " + technicien.getManager().getMatricule() + ")");
        }

        technicien.setManager(m);

        return technicienRepository.save(technicien);
    }
}
