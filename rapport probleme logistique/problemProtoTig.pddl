(define (problem saisie)
  (:domain grue)

(:objects
   crane_grenoble crane_Lyon crane_limoge crane_prototig - grues
   pqt - colis
   lyon limoge - Aeroport
   entrepot_grenoble - Entrepot
   entrepot - Entrepot
   prototig - Entreprise
   camion camion1 - camion
   avion - Avion
   robot0 robot1 robot2 robot3 - Robot
   coque roue  portiere - Pieces
   atelier0 atelier1 atelier2 atelier3 atelier_fin - Atelier

)

(:init
        (place crane_grenoble entrepot_grenoble)
        (place crane_limoge limoge)
        (place crane_prototig prototig)
        (place crane_Lyon lyon)
        (place pqt entrepot_grenoble)
        (place camion entrepot_grenoble)
        (place avion lyon)
        (place camion1 limoge)

        (vide camion1)
        (vide crane_prototig)
        (vide crane_limoge)
        (vide crane_Lyon)
        (vide crane_grenoble)
        (vide camion)
        (vide avion)


;;; pieces dans colis;;;
        (est_dans_colis coque pqt)
        (est_dans_colis roue pqt)
        (est_dans_colis portiere pqt)


;;;ville et aeroport;;;

        (voisin_de lyon entrepot_grenoble)
        (voisin_de entrepot_grenoble lyon)
        (voisin_de lyon limoge)
        (voisin_de limoge lyon)

        (voisin_de prototig limoge)
        (voisin_de limoge prototig)

;;; robot et position;;;

      (place robot0 atelier0)
      (place robot1 atelier1)
      (place robot2 atelier2)
      (place robot3 atelier3)


;;;atelier;;;
        (voisin_de atelier0 prototig)
        (voisin_de prototig atelier0)

        (voisin_de atelier0 atelier1)
        (voisin_de atelier1 atelier0)

        (voisin_de atelier1 atelier2)
        (voisin_de atelier2 atelier1)

        (voisin_de atelier2 atelier3)
        (voisin_de atelier3 atelier2)

        (voisin_de atelier3 atelier_fin)
        (voisin_de atelier_fin atelier3)

        (voisin_de prototig atelier_fin)
        (voisin_de atelier_fin prototig)

)

(:goal (and
            (poser_sur_atelier coque atelier1)
            (poser_sur_atelier portiere atelier2)
            (poser_sur_atelier roue atelier3)

        )
)
)
