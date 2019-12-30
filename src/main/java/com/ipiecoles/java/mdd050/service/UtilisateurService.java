package com.ipiecoles.java.mdd050.service;

import com.ipiecoles.java.mdd050.model.Utilisateur;
import com.ipiecoles.java.mdd050.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UtilisateurService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByUserName(userName);
        if(utilisateur == null){
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }
        GrantedAuthority authority = new SimpleGrantedAuthority(utilisateur.getRole());
        UserDetails userDetails = (UserDetails)new User(utilisateur.getUserName(),
                utilisateur.getPassword(), Arrays.asList(authority));
        return userDetails;
    }
}
