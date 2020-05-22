(define (domain grue)
    (:requirements :strips :typing :negative-preconditions )

    (:types colis  Pieces grues Transport - object
            camion Avion Robot - Transport
           Atelier Entreprise Entrepot  Aeroport Ville position - location

    )

    (:predicates
              (place ?objt - object ?loc - location)
              (est_dans ?pos - position ?Ville - Ville)
              (voisin_de ?loc - location ?loc - location)
              (vide ?objt - object)
              (libre ?atelier - Atelier)
              (est_dans_colis ?piece - Pieces ?pqt - colis)
              (poser_sur ?pqt - colis ?transport - Transport)

              (poser_sur_atelier ?objt - object ?atelier - Atelier)

              (tenue_par_grue ?pqt - colis ?grue - grues)
              (ouvert ?obt - object)
              (tenue_par_robot ?piece - Pieces ?robot - Robot)
    )

(:action prendre_colis_du_sol_par_grue
  :parameters (?grue - grues ?pqt - colis ?pos - location)
  :precondition (and
                      (place ?pqt ?pos)
                      (place ?grue ?pos)
                      (vide ?grue)
                 )

  :effect (and
              (not(place ?pqt ?pos))
              (not(vide ?grue))
              (tenue_par_grue ?pqt ?grue)
  )
)

    ;;deposer colis au sol

(:action deposer_colis_tenu_par_grue_au_sol
  :parameters (?grue - grues ?pqt - colis ?pos - location)
  :precondition (and
                      ;;(not(place ?pqt ?pos))
                      (place ?grue ?pos)
                      (not(vide ?grue))
                      (tenue_par_grue ?pqt ?grue)
                 )

  :effect (and
              (vide ?grue)
              (place ?pqt ?pos)
              (not(tenue_par_grue ?pqt ?grue))
  )
)

;; chargement du camion

(:action deposer_colis_sur_le_camion
  :parameters (?grue - grues ?pqt - colis ?camion - camion ?pos - location)
  :precondition (and
                      ;;(not(place ?pqt ?pos))
                      (place ?grue ?pos)
                      (place ?camion ?pos)
                      (vide ?camion)
                      (not(vide ?grue))

                      (tenue_par_grue ?pqt ?grue)
                      ;;(not(poser_sur ?pqt ?camion))
                 )

  :effect (and
              (not(vide ?camion))
              (vide ?grue)
              (poser_sur ?pqt ?camion)
              (not(tenue_par_grue ?pqt ?grue))
  )
)

;; saisir le colis sur le camion afin de le mettre au sol

(:action saisi_colis_du_camion_par_grue
  :parameters (?grue - grues ?pqt - colis ?camion - camion ?pos - location)
  :precondition (and
                      (place ?grue ?pos)
                      (place ?camion ?pos)
                      (vide ?grue)
                      (poser_sur ?pqt ?camion)
                 )

  :effect (and
              (not(vide ?grue))
              (not(poser_sur ?pqt ?camion))
              (vide ?camion)
              (tenue_par_grue ?pqt ?grue)
  )
)

;;;deplacement de camion d'une position A a une position B

(:action deplacer_camion_de
  :parameters (?pos1 - position ?pos2 - position ?camion - camion)
  :precondition (and
                      (place ?camion ?pos1)
                      (voisin_de ?pos1 ?pos2)
                      (voisin_de ?pos2 ?pos1)
                 )
  :effect (and
              (not(place ?camion ?pos1))
              (place ?camion ?pos2)
  )
)


    ;;;;;;;;;;;;;;;;;;;;;;;;;;;;; au tour de l'avion;;;;;;;;;;;;;;;;;;;;;;

    ;; chargement de l'avion

(:action deposer_colis_dans_l_avion
  :parameters (?grue - grues ?pqt - colis ?avion - avion ?pos - location)
  :precondition (and
                      ;;(not(place ?pqt ?pos))
                      (place ?grue ?pos)
                      (place ?avion ?pos)
                      (vide ?avion)
                      (not(vide ?grue))
                      (tenue_par_grue ?pqt ?grue)
                      ;;(not(poser_sur ?pqt ?avion))
                 )

  :effect (and
              (not(vide ?avion))
              (vide ?grue)
              (poser_sur ?pqt ?avion)
              (not(tenue_par_grue ?pqt ?grue))
  )
)

  ;; saisir le colis sur l'avion afin de le mettre au sol

(:action saisi_colis_de_l_avion_par_grue
  :parameters (?grue - grues ?pqt - colis ?pos - location ?avion - avion)
  :precondition (and
                      (place ?grue ?pos)
                      (place ?avion ?pos)
                      (vide ?grue)
                      (poser_sur ?pqt ?avion)
                 )
  :effect (and
              (not(vide ?grue))
              (not(poser_sur ?pqt ?avion))
              (vide ?avion)
              (tenue_par_grue ?pqt ?grue)
  )
)

;;;;;;;;;;;;;; prendre colis de l'entrepot vers l'Aeroport ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(:action deplacer_colis_par_camion_de_entrepot_vers_aeroport
  :parameters (?entrepot - Entrepot ?aero - Aeroport ?camion - camion)
  :precondition (and
                      (place ?camion ?entrepot)
                      (voisin_de ?entrepot ?aero)
                      (voisin_de ?aero ?entrepot)
                 )

  :effect (and
              (not(vide ?camion))
              (place ?camion ?aero)
  )
)

;;;;;;;;;;;;;; prendre colis de l'aeroport 1  vers l'Aeroport 2;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(:action faire_voler_avion_de
  :parameters (?avion - Avion ?aeoro1 - Aeroport ?aeoro2 - Aeroport)
  :precondition (and
                      (place ?avion ?aeoro1)
                      (voisin_de ?aeoro1 ?aeoro2)
                      (voisin_de ?aeoro2 ?aeoro1)
                 )
  :effect (and
              (not(vide ?avion))
              (place ?avion ?aeoro2)
  )
)

;;;;;;;;;;;;;; prendre colis de l'aeroport vers l'entreprise ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(:action deplacer_colis_par_camion_de_aeroport_vers_entreprise
  :parameters (?aero - Aeroport ?entreprise - Entreprise ?camion - camion)
  :precondition (and
                      (place ?camion ?aero)
                      (voisin_de ?entreprise ?aero)
                      (voisin_de ?aero ?entreprise)
                 )

  :effect (and
              (not(vide ?camion))
              (place ?camion ?entreprise)
  )
)

;;;;;;;;;;colis arrivé a l'entreprise;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;ouverture du colis;;;;;;;;;;;;;

(:action ouvrir_colis_par_robot
  :parameters (?pqt - colis ?robot - Robot ?atelier - Atelier)
  :precondition (and
                      (place ?robot ?atelier)
                      (poser_sur_atelier ?pqt ?atelier)
                      (not(ouvert ?pqt))
                 )

  :effect (and
              (ouvert ?pqt)
  )
)

;;;;;;saisie de la pieces du colis ;;;;;;;;;

(:action saisir_piece_dans_colis_par_robot
  :parameters (?robot - Robot ?piece - Pieces ?pqt - colis ?atelier - Atelier ?entreprise - Entreprise)
  :precondition (and
                      (place ?robot ?atelier)
                      (poser_sur_atelier ?pqt ?atelier)
                      (ouvert ?pqt)
                      (est_dans_colis ?piece ?pqt)
                      (voisin_de ?atelier ?entreprise)
                      (voisin_de ?entreprise ?atelier)
                 )

  :effect (and
                (tenue_par_robot ?piece ?robot)
  )
)
;;; deposer sur atelier suivant ;;;;;;;;;
(:action deposer_piece_dans_atelier
  :parameters (?robot - Robot ?atelier - Atelier ?piece - Pieces)
  :precondition (and
                      (tenue_par_robot ?piece ?robot)
                      (not(vide ?robot))
                 )

  :effect (and
                (poser_sur_atelier ?piece ?atelier)
  )
)

;;;;; chargement premier atelier par le colis et la grue;;;

(:action deposer_colis_sur_atelier
  :parameters (?atelier - Atelier ?pqt - colis ?grue - grues ?pos - location ?camion - camion)
  :precondition (and
                      (place ?grue ?pos)
                      (place ?camion ?pos)
                      (not(vide ?grue))
                      (tenue_par_grue ?pqt ?grue)
                      (voisin_de ?atelier ?pos)
                      (voisin_de ?pos ?atelier)
                 )
  :effect (and
              (vide ?grue)
              (not(tenue_par_grue ?pqt ?grue))
              (poser_sur_atelier ?pqt ?atelier)
  )
)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;insertion de la peinture atellier 1;;;;;;

(:action mettre_peinture
  :parameters (?robot - Robot ?atelier - Atelier ?piece - Pieces)
  :precondition (and
                      (poser_sur_atelier ?piece ?atelier)
                      (not(vide ?robot))
                 )

  :effect (and
                (poser_sur_atelier ?piece ?atelier)
  )
)


)
