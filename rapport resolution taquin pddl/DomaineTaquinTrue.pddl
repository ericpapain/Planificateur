(define (domain taquin)
    (:requirements :strips :typing :negative-preconditions )
    (:types coordonne_case pions)

    (:predicates
                 (case_vide ?case - coordonne_case)
                 (positioner_a ?pion - pions ?coordonne_case - coordonne_case)
                 (est_voisin_de ?case1 - coordonne_case ?case2 - coordonne_case)
                 (est_voisin_haut_de ?case1 - coordonne_case ?case2 - coordonne_case)
                 (est_voisin_bas_de ?case1 - coordonne_case ?case2 - coordonne_case)
                 (est_voisin_gauche_de ?case1 - coordonne_case ?case2 - coordonne_case)
                 (est_voisin_droite_de ?case1 - coordonne_case ?case2 - coordonne_case)
    )

    (:action deplacer_vers_le_haut
            :parameters (?pion - pions ?bas - coordonne_case ?haut - coordonne_case)

            :precondition (and
                               (est_voisin_de ?bas ?haut)
                               (positioner_a ?pion ?bas)
                               (case_vide ?haut)
                               (est_voisin_haut_de ?haut ?bas)
                               (est_voisin_bas_de ?bas ?haut)
                               (not(est_voisin_droite_de ?haut ?bas))
                               (not(est_voisin_gauche_de ?bas ?haut))
                          )

            :effect (and
                         (positioner_a ?pion ?haut)
                         (case_vide ?bas)
                         (not (positioner_a ?pion ?bas))
                         (not (case_vide ?haut))
                         (est_voisin_haut_de ?bas ?haut)
                         (est_voisin_bas_de ?haut ?bas)
                    )
    )

    (:action deplacer_vers_le_bas
            :parameters (?pion - pions ?haut - coordonne_case ?bas - coordonne_case)

            :precondition (and
                               (est_voisin_de ?haut ?bas)
                               (positioner_a ?pion ?haut)
                               (case_vide ?bas)
                               (est_voisin_haut_de ?haut ?bas)
                               (est_voisin_bas_de ?bas ?haut)
                               (not(est_voisin_droite_de ?bas ?haut))
                               (not(est_voisin_gauche_de ?haut ?bas))
                          )

            :effect (and
                         (positioner_a ?pion ?bas)
                         (case_vide ?haut)
                         (not (positioner_a ?pion ?haut))
                         (not (case_vide ?bas))
                         (est_voisin_haut_de ?bas ?haut)
                         (est_voisin_bas_de ?haut ?bas)
                    )
    )

    (:action deplacer_vers_la_gauche
            :parameters (?pion - pions ?droite - coordonne_case ?gauche - coordonne_case)

            :precondition (and
                               (est_voisin_de ?droite ?gauche)
                               (positioner_a ?pion ?droite)
                               (case_vide ?gauche)
                               (not(est_voisin_haut_de ?gauche ?droite))
                               (not(est_voisin_bas_de ?droite ?gauche))
                               (est_voisin_droite_de ?droite ?gauche)
                               (est_voisin_gauche_de ?gauche ?droite)
                          )

            :effect (and
                         (positioner_a ?pion ?gauche)
                         (case_vide ?droite)
                         (not (positioner_a ?pion ?droite))
                         (not (case_vide ?gauche))
                         (est_voisin_droite_de ?gauche ?droite)
                         (est_voisin_gauche_de ?droite ?gauche)
                    )
    )

    (:action deplacer_vers_la_droite
            :parameters (?pion - pions ?gauche - coordonne_case ?droite - coordonne_case)

            :precondition (and
                               (est_voisin_de ?gauche ?droite)
                               (positioner_a ?pion ?gauche)
                               (case_vide ?droite)
                               (not(est_voisin_haut_de ?droite ?gauche))
                               (not(est_voisin_bas_de ?gauche ?droite))
                               (est_voisin_droite_de ?droite ?gauche)
                               (est_voisin_gauche_de ?gauche ?droite)
                          )

            :effect (and
                         (positioner_a ?pion ?droite)
                         (case_vide ?gauche)
                         (not (positioner_a ?pion ?gauche))
                         (not (case_vide ?droite))
                         (est_voisin_droite_de ?gauche ?droite)
                         (est_voisin_gauche_de ?droite ?gauche)
                    )
    )
)
