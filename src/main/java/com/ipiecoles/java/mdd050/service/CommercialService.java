package com.ipiecoles.java.mdd050.service;

import com.ipiecoles.java.mdd050.exception.EmployeException;
import com.ipiecoles.java.mdd050.model.Commercial;
import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.repository.CommercialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;

@Service
public class CommercialService {

    @Autowired
    private CommercialRepository commercialRepository;

    public Commercial findById(Long id){
        Optional<Commercial> c = commercialRepository.findById(id);
        if(!c.isPresent()){
            throw new EntityNotFoundException("Impossible de trouver le commercial d'identifiant " + id);
        }
        return c.get();
    }

    public Commercial creerEmploye(Commercial e) {
        return commercialRepository.save(e);
    }

    public Commercial updateEmploye(Long id, Commercial employe) throws EmployeException {
        return commercialRepository.save(employe);
    }


}
