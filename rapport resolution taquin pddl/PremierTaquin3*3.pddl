(define (problem taquin2)

  (:domain taquin)

  ;;déclaration des objets autorisés
  (:objects pos_y1x1 pos_y1x2 pos_y1x3 pos_y2x1 pos_y2x2 pos_y2x3 pos_y3x1 pos_y3x2 pos_y3x3 - coordonne_case
  pion1 pion2 pion3 pion4 pion5 pion6 pion7 pion8 - pions)

  (:init
      ;;coordonne_cases initiales des tuiles
      (case_vide pos_y3x3)
      (positioner_a pion1 pos_y2x2)
      (positioner_a pion2 pos_y1x3)
      (positioner_a pion3 pos_y2x3)
      (positioner_a pion4 pos_y3x2)
      (positioner_a pion5 pos_y3x1)
      (positioner_a pion6 pos_y1x2)
      (positioner_a pion7 pos_y2x1)
      (positioner_a pion8 pos_y1x1)

      ;;definition du cadre
      (est_voisin_de pos_y1x1 pos_y1x2)
      (est_voisin_gauche_de pos_y1x1 pos_y1x2)

      (est_voisin_de pos_y1x2 pos_y1x1)
      (est_voisin_droite_de pos_y1x2 pos_y1x1)

      (est_voisin_de pos_y1x2 pos_y1x3)
      (est_voisin_gauche_de pos_y1x2 pos_y1x3)

      (est_voisin_de pos_y1x3 pos_y1x2)
      (est_voisin_droite_de pos_y1x3 pos_y1x2)

      (est_voisin_de pos_y2x1 pos_y2x2)
      (est_voisin_gauche_de pos_y2x1 pos_y2x2)

      (est_voisin_de pos_y2x2 pos_y2x1)
      (est_voisin_droite_de pos_y2x2 pos_y2x1)

      (est_voisin_de pos_y2x2 pos_y2x3)
      (est_voisin_gauche_de pos_y2x2 pos_y2x3)

      (est_voisin_de pos_y2x3 pos_y2x2)
      (est_voisin_droite_de pos_y2x3 pos_y2x2)

      (est_voisin_de pos_y3x1 pos_y3x2)
      (est_voisin_gauche_de pos_y3x1 pos_y3x2)

      (est_voisin_de pos_y3x2 pos_y3x1)
      (est_voisin_droite_de pos_y3x2 pos_y3x1)

      (est_voisin_de pos_y3x3 pos_y3x2)
      (est_voisin_droite_de pos_y3x3 pos_y3x2)

      (est_voisin_de pos_y3x2 pos_y3x3)
      (est_voisin_gauche_de pos_y3x2 pos_y3x3)

      (est_voisin_de pos_y1x1 pos_y2x1)
      (est_voisin_haut_de pos_y1x1 pos_y2x1)

      (est_voisin_de pos_y2x1 pos_y1x1)
      (est_voisin_bas_de pos_y2x1 pos_y1x1)

      (est_voisin_de pos_y1x2 pos_y2x2)
      (est_voisin_haut_de pos_y1x2 pos_y2x2)

      (est_voisin_de pos_y2x2 pos_y1x2)
      (est_voisin_bas_de pos_y2x2 pos_y1x2)

      (est_voisin_de pos_y1x3 pos_y2x3)
      (est_voisin_haut_de pos_y1x3 pos_y2x3)

      (est_voisin_de pos_y2x3 pos_y1x3)
      (est_voisin_bas_de pos_y2x3 pos_y1x3)

      (est_voisin_de pos_y2x1 pos_y3x1)
      (est_voisin_haut_de pos_y2x1 pos_y3x1)

      (est_voisin_de pos_y3x1 pos_y2x1)
      (est_voisin_bas_de pos_y3x1 pos_y2x1)

      (est_voisin_de pos_y2x2 pos_y3x2)
      (est_voisin_haut_de pos_y2x2 pos_y3x2)

      (est_voisin_de pos_y3x2 pos_y2x2)
      (est_voisin_bas_de pos_y3x2 pos_y2x2)

      (est_voisin_de pos_y2x3 pos_y3x3)
      (est_voisin_haut_de pos_y2x3 pos_y3x3)

      (est_voisin_de pos_y3x3 pos_y2x3)
      (est_voisin_bas_de pos_y3x3 pos_y2x3)
  )

    (:goal (and
    ;;coordonne_cases finales des tuiles
              (positioner_a pion1 pos_y1x1)
              (positioner_a pion2 pos_y1x2)
              (positioner_a pion3 pos_y1x3)
              (positioner_a pion4 pos_y2x1)
              (positioner_a pion5 pos_y2x2)
              (positioner_a pion6 pos_y2x3)
              (positioner_a pion7 pos_y3x1)
              (positioner_a pion8 pos_y3x2)
            )
    )
)
