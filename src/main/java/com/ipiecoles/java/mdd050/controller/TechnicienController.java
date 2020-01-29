package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.service.ManagerService;
import com.ipiecoles.java.mdd050.service.TechnicienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/techniciens")
public class TechnicienController {
    @Autowired
    private TechnicienService technicienService;

    @RequestMapping(method = RequestMethod.GET, value = "/{idTechnicien}/manager/{matricule}/add")
    public Technicien addTechniciens(@PathVariable Long idTechnicien, @PathVariable String matricule) {
        return this.technicienService.addManager(idTechnicien, matricule);
    }
}



















