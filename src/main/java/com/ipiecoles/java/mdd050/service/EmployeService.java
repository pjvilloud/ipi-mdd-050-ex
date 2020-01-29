package com.ipiecoles.java.mdd050.service;

import com.ipiecoles.java.mdd050.exception.ConflictException;
import com.ipiecoles.java.mdd050.exception.EmployeException;
import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.model.Entreprise;
import com.ipiecoles.java.mdd050.model.Manager;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import com.ipiecoles.java.mdd050.repository.ManagerRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

@Service
@Validated
public class EmployeService {

    public static final int PAGE_SIZE_MIN = 10;
    public static final int PAGE_SIZE_MAX = 100;
    public static final int PAGE_MIN = 0;
    private static final String PAGE_VALID_MESSAGE = "La taille de la page doit être comprise entre 10 et 100";

    @Autowired
    private EmployeRepository employeRepository;

    public Employe findById(Long id){
        Optional<Employe> employe = employeRepository.findById(id);
        if(!employe.isPresent()){
            throw new EntityNotFoundException("L'employé d'identifiant " + id + " n'a pas été trouvé.");
        }
        return employe.get();
    }

    public Long countAllEmploye() {
        return employeRepository.count();
    }

    public void deleteEmploye(Long id) throws EmployeException {
        //Vérification
        Employe employe = this.findById(id);
        if(employe instanceof Manager && !((Manager)employe).getEquipe().isEmpty()){
            throw new EmployeException("Pour supprimer un manager, il faut que son équipe soit vide.");
        }
        employeRepository.deleteById(id);
    }

    public <T extends Employe> T creerEmploye(@Valid T e) throws ConflictException {
        if(employeRepository.existsById(e.getId())) {
            throw new ConflictException("L'employé d'identifiant " + e.getId() + " existe déjà !");
        }
        return employeRepository.save(e);
    }

    public <T extends Employe> T updateEmploye(Long id, @Valid T employe) {
        if(!employeRepository.existsById(id)) {
            throw new EntityNotFoundException("L'employé d'identifiant " + id + " n'existe pas !");
        }
        if(!id.equals(employe.getId())) {
            throw new IllegalArgumentException("Requête invalide");
        }
        return employeRepository.save(employe);
    }

    public Page<Employe> findAllEmployes(
            @Min(message = "Le numéro de page ne peut être inférieur à 0", value = PAGE_MIN)
                    Integer page,
            @Min(value = PAGE_SIZE_MIN, message = PAGE_VALID_MESSAGE)
            @Max(value = PAGE_SIZE_MAX, message = PAGE_VALID_MESSAGE)
                    Integer size,
            String sortProperty,
            Sort.Direction sortDirection
    ) {
        //Vérification de sortProperty
        if(Arrays.stream(Employe.class.getDeclaredFields()).
                map(Field::getName).
                filter(s -> s.equals(sortProperty)).count() != 1){
            throw new IllegalArgumentException("La propriété " + sortProperty + " n'existe pas !");
        };

        Pageable pageable = PageRequest.of(page,size,sortDirection, sortProperty);
        Page<Employe> employes = employeRepository.findAll(pageable);
        if(page >= employes.getTotalPages()){
            throw new IllegalArgumentException("Le numéro de page ne peut être supérieur à " + employes.getTotalPages());
        } else if(employes.getTotalElements() == 0){
            throw new EntityNotFoundException("Il n'y a aucun employé dans la base de données");
        }
        return employes;
    }

    public Employe findByMatricule(
            @Pattern(regexp = Entreprise.REGEX_MATRICULE, message = "doit commencer par M, C ou T suivi de 5 chiffres") String matricule
    ) {
        Employe employe =  this.employeRepository.findByMatricule(matricule);
        if(employe == null){
            throw new EntityNotFoundException("L'employé de matricule '" + matricule + "' n'a pas été trouvé.");
        }
        return employe;
    }

}
