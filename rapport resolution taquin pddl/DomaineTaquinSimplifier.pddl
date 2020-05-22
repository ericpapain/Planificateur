(define (domain taquin)
    (:requirements :strips :typing :negative-preconditions )

    (:types coordonne_case pions)

    (:predicates
                 (positioner_a ?pion - pions ?coordonne_case - coordonne_case)
                 (est_voisin_de ?case1 - coordonne_case ?case2 - coordonne_case)
                 (case_vide ?case - coordonne_case)

                 (est_voisin_haut_de ?case1 - coordonne_case ?case2 - coordonne_case)
                 (est_voisin_bas_de ?case1 - coordonne_case ?case2 - coordonne_case)
                 (est_voisin_gauche_de ?case1 - coordonne_case ?case2 - coordonne_case)
                 (est_voisin_droite_de ?case1 - coordonne_case ?case2 - coordonne_case)
    )

    (:action deplacer_vers
            :parameters (?pion - pions ?bas - coordonne_case ?haut - coordonne_case)
            :precondition (and
                               (est_voisin_de ?bas ?haut)
                               (positioner_a ?pion ?bas)
                               (case_vide ?haut)
                          )

            :effect (and
                         (positioner_a ?pion ?haut)
                         (case_vide ?bas)
                         (not (positioner_a ?pion ?bas))
                         (not (case_vide ?haut))
                    )
    )
)
