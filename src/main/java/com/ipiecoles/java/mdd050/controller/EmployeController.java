package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.exception.ConflictException;
import com.ipiecoles.java.mdd050.exception.EmployeException;
import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.service.EmployeService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/employes")
public class EmployeController {

    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";


    @Autowired
    private EmployeService employeService;

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Long count(){
        return employeService.countAllEmploye();
    }

    @RequestMapping(value = "/{id}", produces = APPLICATION_JSON_CHARSET_UTF_8, method = RequestMethod.GET)
    public Employe findById(@PathVariable(value = "id") Long id){
        Employe employe = employeService.findById(id);
        if(employe == null){
            throw new EntityNotFoundException("L'employé d'identifiant : " + id + " n'a pas été trouvé.");
        }
        return employe;
    }

    @RequestMapping(value = "", produces = APPLICATION_JSON_CHARSET_UTF_8, method = RequestMethod.GET, params = "matricule")
    public Employe findByMatricule(@RequestParam("matricule") String matricule){
        Employe employe =  employeService.findMyMatricule(matricule);
        if(employe == null){
            throw new EntityNotFoundException("L'employé de matricule : " + matricule + " n'a pas été trouvé.");
        }
        return employe;
    }

    @RequestMapping(value = "", produces = APPLICATION_JSON_CHARSET_UTF_8, method = RequestMethod.GET)
    public Page<Employe> findAll(@RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam("sortDirection") String sortDirection, @RequestParam("sortProperty") String sortProperty){
        return employeService.findAllEmployes(page, size, sortProperty, sortDirection);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_CHARSET_UTF_8, produces = APPLICATION_JSON_CHARSET_UTF_8, value = "")
    public Employe createEmploye(@RequestBody Employe employe) throws ConflictException {
        try {
            return this.employeService.creerEmploye(employe);
        }
        catch (DataIntegrityViolationException e){
            if(e.getMessage().contains("matricule_unique")){
                throw new ConflictException("L'employé de matricule " + employe.getMatricule() + " existe déjà !");
            }
            throw new IllegalArgumentException("Une erreur technique est survenue");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Employe updateEmploye(@PathVariable("id") Long id, @RequestBody Employe employe){
        return this.employeService.updateEmploye(id,employe);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteEmploye(@PathVariable("id") Long id) throws EmployeException {
            this.employeService.deleteEmploye(id);
    }

    /*@ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        return "ERREUR !!!";
    }*/

}
