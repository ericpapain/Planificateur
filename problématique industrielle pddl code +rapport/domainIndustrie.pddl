(define (domain industrial)
    (:requirements :strips :typing :negative-preconditions )

    (:types griper robot  piece conveyor tray - object
            processing_unit - location
            operation - operations

    )

    (:constants
      endOp - operation
    )

    (:predicates

              (for_pu ?gp - griper ?pu - processing_unit)
              (for_conv ?gp - griper ?conv - conveyor)
              (for_rob ?gp - griper ?r - robot)
                ;; hold tray by griper
              (hold_by ?t - tray ?gp - griper)
                ;; a la place
              (at_place ?ob - object ?loc - location)
                ;; piece dans le plateau
              (in ?p - piece ?t - tray)
                ;; plateau sur le convoyeur
              (on ?t - tray ?conv - conveyor)
                ;; appartient a l'unité
              (belong ?conv - conveyor ?pu - processing_unit)
                ;; object vide
              (empty ?ob - object)
                ;; plateau au dessus du robot
              (above_to ?t - tray ?r - robot)
                ;; tray compatible with processing_unit
              (compatible ?t - tray ?pu - processing_unit)
                ;;end of treatment process
              ;;(treatment_complet ?t - tray)

              (avaible ?pu - processing_unit)
              (inside ?t - tray ?pu - processing_unit)

              (init_op ?op - operation)
              (lastOperation ?op - operation)
              (come_before ?opCurrent - operation ?opNext - operation)
              (do ?op - operation ?t - tray)
              (achieve_by ?op - operation ?pu - processing_unit)
              (come_end ?opNext - operation ?endOp - operation)

    )

    ;;;;;;;;;;;;;;;;;;;;;;;;;;pick up tray;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

    (:action pick_up_tray_from_process_unit
      :parameters (?gp - griper ?t - tray ?pu - processing_unit ?pos - location)
      :precondition (and
                          (at_place ?gp ?pos)
                          (at_place ?pu ?pos)
                          (empty ?gp)
                          (not(avaible ?pu))
                          (inside ?t ?pu)
                          (not(hold_by ?t ?gp))
                          (for_pu ?gp ?pu)
                     )
      :effect (and
                    (not(empty ?gp))
                    (avaible ?pu)
                    (not(inside ?t ?pu))
                    (hold_by ?t ?gp)
      )
    )

    ;;;;;;;;;;;;;;;;;;;;;;;;;;load convoyor with tray;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

    (:action pick_up_tray_from_robot
      :parameters (?gp - griper ?t - tray ?r - robot ?pos - location)
      :precondition (and
                          (at_place ?gp ?pos)
                          (at_place ?r ?pos)
                          (empty ?gp)
                          (above_to ?t ?r)
                          (not(empty ?r))
                          (not(hold_by ?t ?gp))

                          (for_rob ?gp ?r)
                     )
      :effect (and
                    (not(empty ?gp))
                    (not(above_to ?t ?r))
                    (empty ?r)
                    (hold_by ?t ?gp)
      )
    )
    ;;;;;;;;;;;;;;;;;;;;;;;;;;load processing unit with tray;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

    (:action pick_up_tray_from_conv
      :parameters (?gp - griper ?t - tray ?conv - conveyor ?pos - location)
      :precondition (and
                          (at_place ?gp ?pos)
                          (at_place ?conv ?pos)
                          (empty ?gp)
                          (on ?t ?conv)
                          (not(empty ?conv))
                          (not(hold_by ?t ?gp))

                          (for_conv ?gp ?conv)
                     )
      :effect (and
                    (not(empty ?gp))
                    (empty ?conv)
                    (not(on ?t ?conv))
                    (hold_by ?t ?gp)
      )
    )

  ;;;;;;;;;;;;;;;;;;;;;;;;;;load robot with tray;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

    (:action load_robot
      :parameters (?gp - griper ?r - robot ?t - tray ?pos - location)
      :precondition (and
                          (at_place ?r ?pos)
                          (at_place ?gp ?pos)
                          (not(above_to ?t ?r))
                          (empty ?r)
                          (not(empty ?gp))
                     )
      :effect (and
                  (not(empty ?r))
                  (above_to ?t ?r)
                  (empty ?gp)
      )
    )

    ;;;;;;;;;;;;;;;;;;;;;;;;; move robot with tray from unitie A to unitie B ;;;;;;;;;;;;;;;;;;;;

    (:action move_robot_to
      :parameters (?r - robot ?pos1 - location ?pos2 - location)
      :precondition (and
                          (at_place ?r ?pos1)
                          (not(empty ?r))
                     )
      :effect (and
                  (at_place ?r ?pos2)
                  (not(at_place ?r ?pos1))
      )
    )

    ;;;;;;;;;;;;;;;;;; unload robot to the new processus unit for treatment ;;;;;;;;;;;;;;;;;;;;

    ;;(:action unload_robot
    ;;  :parameters (?r - robot ?t - tray ?gp - griper ?pos - location)
    ;;  :precondition (and
    ;;                    (above_to ?t ?r)
    ;;                    (at_place ?r ?pos)
    ;;                    (at_place ?gp ?pos)
    ;;                    (not(empty ?r))
    ;;                    (empty ?gp)
    ;;                 )
    ;;  :effect (and
    ;;                (not(above_to ?t ?r))
    ;;                (empty ?r)
    ;;                (not(empty ?gp))
    ;;  )
    ;;)

    ;;;;;;;;;;;;;;;;;;;;;;;

    (:action load_conveyor
      :parameters (?conv - conveyor ?gp - griper ?t - tray ?pos - location)
      :precondition (and
                        (at_place ?conv ?pos)
                        (at_place ?gp ?pos)
                        (not(on ?t ?conv))
                        (empty ?conv)
                        (not(empty ?gp))
                        (hold_by ?t ?gp)
                        (for_conv ?gp ?conv)
                     )
      :effect (and
                    (on ?t ?conv)
                    (not(empty ?conv))
                    (empty ?gp)
                    (not(hold_by ?t ?gp))
      )
    )

    ;;;;;;;;;;;;;;;;;;;;;;;load processing unit;;;;;;;;;;;;;;;;;;;;;;;;;;;

    (:action load_processing_unit
      :parameters (?pu - processing_unit ?gp - griper ?t - tray ?pos - location)
      :precondition (and
                        (at_place ?pu ?pos)
                        (at_place ?gp ?pos)
                        (not(inside ?t ?pu))
                        (avaible ?pu)
                        (not(empty ?gp))
                        (hold_by ?t ?gp)
                        (compatible ?t ?pu)
                        (for_pu ?gp ?pu)
                     )
      :effect (and
                    (inside ?t ?pu)
                    (not(avaible ?pu))
                    (empty ?gp)
                    (not(hold_by ?t ?gp))
      )
    )

    ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; operation pieces by processing unit step  1 for treatment;;;;;;;;;;

    (:action do_first_operation
      :parameters (?opCurrent - operation ?t - tray ?pu - processing_unit ?opNext - operation)
      :precondition (and
                          (inside ?t ?pu)
                          (init_op ?opCurrent)
                          (achieve_by ?opCurrent ?pu)
                          (come_before ?opCurrent ?opNext)
                          (not(do ?opCurrent ?t))
                          (not(do ?opNext ?t))
                          ;;(operation_order ?opCurrent ?opNext ?t)
                     )
      :effect (and
                    (do ?opCurrent ?t)
      )
    )
        ;;;;;;;;

        (:action do_next_operation
          :parameters (?opNext - operation ?t - tray ?pu - processing_unit ?opCurrent - operation)
          :precondition (and
                              (inside ?t ?pu)
                              (achieve_by ?opNext ?pu)
                              (come_before ?opCurrent ?opNext)
                              (do ?opCurrent ?t)
                              (not(do ?opNext ?t))
                              ;;(operation_order ?opCurrent ?opNext ?t)
                         )
          :effect (and
                        (do ?opNext ?t)
          )
        )

    ;;;;;;;;;;;;;

    (:action last_operation
      :parameters (?pu - processing_unit ?opCurrent - operation ?opNext - operation ?t - tray)
      :precondition (and
                          (inside ?t ?pu)
                          (lastOperation ?opNext)
                          (achieve_by ?opNext ?pu)
                          (come_before ?opCurrent ?opNext)
                          (come_end ?opNext endOp)
                          (do ?opCurrent ?t)
                          (not(do ?opNext ?t))
                          ;;(operation_order ?opCurrent ?opNext ?t)
                     )
      :effect (and
                    (do ?opNext ?t)
      )
    )

)
