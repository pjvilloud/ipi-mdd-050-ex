package com.ipiecoles.java.mdd050.model;

import org.joda.time.LocalDate;

public final class Entreprise {
	public static final Double SALAIRE_BASE = 1480.27;
	public static final Integer NB_CONGES_BASE = 25;
	public static final Integer NB_RTT_BASE = 12;
	public static final Double INDICE_MANAGER = 1.3;
	public static final Double PRIME_MANAGER_PAR_TECHNICIEN = 250d;
	public static final Double PRIME_ANCIENNETE = 100d;
	public static final String REGEX_MATRICULE = "^[MTC][0-9]{5}$";
	public static final String REGEX_MATRICULE_TECHNICIEN = "^T[0-9]{5}$";
	public static final String REGEX_MATRICULE_MANAGER = "^M[0-9]{5}$";
	public static final String REGEX_MATRICULE_COMMERCIAL = "^C[0-9]{5}$";


	public static Double primeAnnuelleBase() {
		return LocalDate.now().getYear() * 0.5;
	}

}
