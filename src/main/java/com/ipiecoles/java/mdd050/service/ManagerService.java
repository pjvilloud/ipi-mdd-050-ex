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
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private TechnicienRepository technicienRepository;

    public void deleteTechniciens(Long idManager, Long idTechnicien) {
        Optional<Manager> m = managerRepository.findById(idManager);
        if(!m.isPresent()){
            throw new EntityNotFoundException("Le manager d'identifiant " + idManager + " n'a pas été trouvé !");
        }
        Optional<Technicien> t = technicienRepository.findById(idTechnicien);
        if(!t.isPresent()){
            throw new EntityNotFoundException("Le technicien d'identifiant " + idTechnicien + " n'a pas été trouvé !");
        }
        Manager manager = m.get();
        Technicien technicien = t.get();
        if(!manager.equals(technicien.getManager())){
            throw new IllegalArgumentException("Le technicien " + technicien.getId() + " n'a pas pour manager le manager " + manager.getId());
        }

        technicien.setManager(null);
        technicienRepository.save(technicien);
    }

    public Technicien addTechniciens(Long idManager, @Pattern(
            regexp = Entreprise.REGEX_MATRICULE_TECHNICIEN,
            message = "doit être M, T ou C suivi de 5 chiffres") String matricule) {
        Optional<Manager> m = managerRepository.findById(idManager);
        if(!m.isPresent()){
            throw new EntityNotFoundException("Impossible de trouver le manager d'identifiant " + idManager);
        }
        Technicien t = technicienRepository.findByMatricule(matricule);
        if(t == null){
            throw new EntityNotFoundException("Impossible de trouver le technicien de matricule " + matricule);
        }

        if(t.getManager() != null){
            throw new IllegalArgumentException("Le technicien de matricule " + matricule + " a déjà un manager : " + t.getManager().getPrenom() + " " + t.getManager().getNom()
                    + " (matricule " + t.getManager().getMatricule() + ")");
        }
        Manager manager = m.get();

        t.setManager(manager);
        return technicienRepository.save(t);
    }
}
