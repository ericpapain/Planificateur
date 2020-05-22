(define (problem industrial)
  (:domain industrial)

  (:objects
           gp_0 gp_1 gp_2 gp_3 gp_4 gp_5 gp_6 gp_7 gp_8 gp_9 gp_10 - griper
           r_0 - robot
           conv_0 conv_1 conv_2 conv_3 conv_4 conv_5 conv_6 conv_7 conv_8 conv_9 conv_10 - conveyor
           loc_Init loc_1 loc_2 loc_3 loc_4 loc_5 - location
           tray_0 - tray
           piece_0 - piece
           pu_Init pu_1 pu_2 pu_3 pu_4 pu_5 - processing_unit
           opA0 opA1 opA2 - operation
           opB0 opB1 opB2 - operation
           opC0 opC1 opC2 opC3 - operation
           opD0 opD1 opD2 opD3 opD4 - operation
           opE0 - operation
  )

  (:init
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;gripper config;;;;;;;;;;;;;;;;
            ;;position of gripper----------------------------
          (at_place gp_0 loc_Init)
          (empty gp_0)
              ;;(for_rob gp_0 r_0)
              (for_pu gp_0 pu_Init)

              (at_place r_0 loc_Init)
              (at_place pu_Init loc_Init)

              (empty r_0)
              (in piece_0 tray_0)
              (compatible tray_0 pu_Init)
              (inside tray_0 pu_Init)



            ;;loc 1----------------------------
          (at_place gp_1 loc_1)
          (empty gp_1)
              (for_conv gp_1 conv_0)
              (for_rob gp_1 r_0)
            ;;
          (at_place gp_2 loc_1)
          (empty gp_2)
              (for_conv gp_2 conv_0)
              (for_pu gp_2 pu_1)
              ;;
              (empty conv_0)
              (at_place conv_0 loc_1)
              (at_place pu_1 loc_1)
              (belong conv_0 pu_1)
              (compatible tray_0 pu_1)
              (avaible pu_1)

          ;;loc 2----------------------------
          (at_place gp_3 loc_2)
          (empty gp_3)
            (for_rob gp_3 r_0)
            (for_conv gp_3 conv_1)
          ;;
          (at_place gp_4 loc_2)
          (empty gp_4)
            (for_conv gp_4 conv_1)
            (for_pu gp_4 pu_2)

            ;;
            (empty conv_1)
            (at_place conv_1 loc_2)
            (at_place pu_2 loc_2)
            (belong conv_1 pu_2)
            (compatible tray_0 pu_2)
            (avaible pu_2)
          ;;loc 3----------------------------
          (at_place gp_5 loc_3)
          (empty gp_5)
            (for_conv gp_5 conv_2)
            (for_rob gp_5 r_0)
          ;;
          (at_place gp_6 loc_3)
          (empty gp_6)
            (for_conv gp_6 conv_2)
            (for_pu gp_6 pu_3)
            ;;
            (empty conv_2)
            (at_place conv_2 loc_3)
            (at_place pu_3 loc_3)
            (belong conv_2 pu_3)
            (compatible tray_0 pu_3)
            (avaible pu_3)


            ;;loc 4----------------------------
            (at_place gp_7 loc_4)
            (empty gp_7)
              (for_conv gp_7 conv_3)
              (for_rob gp_7 r_0)
            ;;
            (at_place gp_8 loc_4)
            (empty gp_8)
              (for_conv gp_8 conv_3)
              (for_pu gp_8 pu_4)
              ;;
              (empty conv_3)
              (at_place conv_3 loc_4)
              (at_place pu_4 loc_4)
              (belong conv_3 pu_4)
              (compatible tray_0 pu_4)
              (avaible pu_4)
            ;;loc 5----------------------------
            (at_place gp_9 loc_5)
            (empty gp_9)
            (for_rob gp_9 r_0)
            (for_conv gp_9 conv_4)
            ;;
            (at_place gp_10 loc_5)
            (empty gp_10)
            (for_pu gp_10 pu_5)
            (for_conv gp_10 conv_4)
            ;;
            (empty conv_4)
            (at_place conv_4 loc_5)
            (at_place pu_5 loc_5)
            (belong conv_4 pu_5)
            (compatible tray_0 pu_5)
            (avaible pu_5)
            ;;
            ;;;;;;;;;;;;;




            ;;operation execute by the first processing unit
          (init_op opA0)
          (achieve_by opA0 pu_1)
          (achieve_by opA1 pu_1)
          (achieve_by opA2 pu_1)

            ;;operation execute by the second processing unit
          (achieve_by opB0 pu_2)
          (achieve_by opB1 pu_2)
          (achieve_by opB2 pu_2)

            ;;operation execute by the third processus unit
          (achieve_by opC0 pu_3)
          (achieve_by opC1 pu_3)
          (achieve_by opC2 pu_3)
          (achieve_by opC3 pu_3)

            ;;operation execute by the foutrh processing unit
          (achieve_by opD0 pu_4)
          (achieve_by opD1 pu_4)
          (achieve_by opD2 pu_4)
          (achieve_by opD3 pu_4)
          (achieve_by opD4 pu_4)

            ;;operation execute by the fitht processing unité
          (achieve_by opE0 pu_5)

            ;;define the order of operations for each processing unit
          (come_before opA0 opA1)
          (come_before opA1 opA2)
          (come_before opA2 opB0)
          ;;
          (come_before opB0 opB1)
          (come_before opB1 opB2)
          ;;
          (come_before opB2 opC0)
          (come_before opC0 opC1)
          (come_before opC1 opC2)
          (come_before opC2 opC3)
          ;;
          (come_before opC3 opD0)
          (come_before opD0 opD1)
          (come_before opD1 opD2)
          (come_before opD2 opD3)
          (come_before opD3 opD4)
          ;;
          (come_before opD4 opE0)
          ;;
          (lastOperation opE0)
          (come_end opD4 endOp)

  )

  (:goal (and
              ;;(at_place r_0 loc_1)
              ;;(on tray_0 conv_0)
              ;;(inside tray_0 pu_1)
              ;;(avaible pu_Init)
              ;;(hold_by tray_0 gp_1)
              ;;(empty r_0)
              ;;(inside tray_0 pu_1)
              ;;(hold_by tray_0 gp_2)
              ;;(hold_by tray_0 gp_10)

              (do opE0 tray_0)
              (above_to tray_0 r_0)
          )
  )

)
