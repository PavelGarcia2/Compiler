	INCLUDE library/MACRO.X68
	INCLUDE library/CONST.X68
	INCLUDE library/VAR.X68
;-----------------------------------------------------------
;-----------------------------------------------------------
; Initial program lines, main                               
;-----------------------------------------------------------
; Labels to memory space reserved for variables:            
;-----------------------------------------------------------
Variable0	DS.L 1
Variable1	DS.L 1
Variable2	DS.L 1
Variable3	DS.L 1
Variable4	DS.L 1
Variable5	DS.L 1
Variable6	DS.L 2
Variable7	DS.L 1
Variable8	DS.L 1
Variable9	DS.L 1
Variable10	DS.L 1
Variable11	DS.L 1
Variable12	DS.L 1
Variable13	DS.L 1
Variable14	DS.L 1
Variable15	DS.L 1
Variable16	DS.L 1
Variable17	DC.B 'Declaracion',0
Variable18	DS.L 1
Variable19	DS.L 1
Variable20	DS.L 1
Variable21	DS.L 1
Variable22	DS.L 1
Variable23	DS.L 1
Variable24	DS.L 1
Variable25	DS.L 1
Variable26	DS.L 1
Variable27	DS.L 1
Variable28	DS.L 1
Variable29	DS.L 1
Variable30	DS.L 1
Variable31	DS.L 1
Variable32	DS.L 1
Variable33	DS.L 1
Variable34	DS.L 1
Variable35	DS.L 1
Variable36	DS.L 1
Variable37	DS.L 1
Variable38	DS.L 1
Variable39	DS.L 1
Variable40	DS.L 1
Variable41	DS.L 1
Variable42	DS.L 1
Variable43	DS.L 1
Variable44	DS.L 1
Variable45	DS.L 1
Variable46	DS.L 1
Variable47	DS.L 1
Variable48	DS.L 1
Variable49	DS.L 1
Variable50	DS.L 1
Variable51	DS.L 1
Variable52	DS.L 1
Variable53	DS.L 1
Variable54	DS.L 1
Variable55	DC.B 'Restando',0
Variable56	DS.L 1
Variable57	DS.L 1
Variable58	DS.L 1
Variable59	DS.L 1
Variable60	DS.L 1
Variable61	DS.L 1
Variable62	DS.L 1
Variable63	DS.L 1
Variable64	DS.L 1
Variable65	DS.L 1
Variable66	DS.L 1
Variable67	DS.L 1
Variable68	DS.L 1
Variable69	DS.L 1
Variable70	DS.L 1
Variable71	DS.L 1
Variable72	DS.L 1
Variable73	DS.L 1
Variable74	DS.L 1
Variable75	DS.L 1
Variable76	DS.L 1
Variable77	DS.L 1
Variable78	DS.L 1
Variable79	DS.L 1
Variable80	DS.L 1
Variable81	DS.L 1
SParam0	DS.B 128
	DC.B 0
SParam1	DS.B 128
	DC.B 0
SParam2	DS.B 128
	DC.B 0
SParam3	DS.B 128
	DC.B 0
SParam4	DS.B 128
	DC.B 0
Param0	DS.L 1
Param1	DS.L 1
Param2	DS.L 1
Param3	DS.L 1
Param4	DS.L 1
Param5	DS.L 1
Param6	DS.L 1
Param7	DS.L 1
Param8	DS.L 1
Param9	DS.L 1
;-----------------------------------------------------------
	DS.W 0
;-----------------------------------------------------------
globals:
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 0

	MOVE.L #-2, D0 ; Load variable
	MOVE.L D0, (Variable0) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 1

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable1) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 2

	MOVE.L #-1, D0 ; Load variable
	MOVE.L D0, (Variable2) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 3

	MOVE.L #-1, D0 ; Load variable
	MOVE.L D0, (Variable3) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 4

	MOVE.L #'ÿ', D0 ; Load variable
	MOVE.L D0, (Variable4) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 5

	MOVE.L #2, D0 ; Load variable
	MOVE.L D0, (Variable5) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 7

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable7) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 9

	MOVE.L #4, D0 ; Load variable
	MOVE.L D0, (Variable9) ; Store local variable
; Instruction of type: MULTIPLICACION
;Operator 1 -> 7, Operator 2 -> t9, Store in -> 8

	JMP main ; Declared all globals
	MOVE.L (Variable7), D0 ; Load variable
	MOVE.L (Variable9), D1 ; Load variable
	MULTM D0, D1
	MOVE.L D1, (Variable8) ; Store local variable
; Instruction of type: IND_ASS
;Operator 1 -> -2, Operator 2 -> t8, Store in -> 6

	LEA	(Variable6), A0
	MOVE.L (Variable8), D0 ; Load variable
	ADD.L D0, A0 ; D0 = @A[B]
	MOVE.L #1, D2 ; Load variable
	MOVE.L D2, (A0) ; Store C IN A[B]
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 10

	MOVE.L #1, D0 ; Load variable
	MOVE.L D0, (Variable10) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 11

	MOVE.L #2, D0 ; Load variable
	MOVE.L D0, (Variable11) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 12

	MOVE.L #3, D0 ; Load variable
	MOVE.L D0, (Variable12) ; Store local variable
; Instruction of type: SUMA
;Operator 1 -> 11, Operator 2 -> t12, Store in -> 13

	MOVE.L (Variable11), D0 ; Load variable
	MOVE.L (Variable12), D1 ; Load variable
	ADDM D0, D1
	MOVE.L D1, (Variable13) ; Store local variable
; Instruction of type: SUMA
;Operator 1 -> 10, Operator 2 -> t13, Store in -> 14

	MOVE.L (Variable10), D0 ; Load variable
	MOVE.L (Variable13), D1 ; Load variable
	ADDM D0, D1
	MOVE.L D1, (Variable14) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 14, Store in -> 15

	MOVE.L (Variable14), D0 ; Load variable
	MOVE.L D0, (Variable15) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 16

	MOVE.L #'', D0 ; Load variable
	MOVE.L D0, (Variable16) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 17

	LEA Variable17, A0
	MOVE.L A0, D0 ; Load variable
	MOVE.L D0, A0 ; Param gest
	LEA Variable17, A1 ; Param gest
.loop99:
	MOVE.B (A0)+, (A1)+ 
	TST.B (A0) 
	BNE .loop99
	MOVE.B #0, (A1)+ 
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 16

	MOVE.L #'a', D0 ; Load variable
	MOVE.L D0, (Variable16) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 18

	MOVE.L #1, D0 ; Load variable
	MOVE.L D0, (Variable18) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 19

	MOVE.L #23, D0 ; Load variable
	MOVE.L D0, (Variable19) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 20

	MOVE.L #3, D0 ; Load variable
	MOVE.L D0, (Variable20) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 21

	MOVE.L #4, D0 ; Load variable
	MOVE.L D0, (Variable21) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 22

	MOVE.L #2, D0 ; Load variable
	MOVE.L D0, (Variable22) ; Store local variable
; Instruction of type: DIVISION
;Operator 1 -> 21, Operator 2 -> t22, Store in -> 23

	MOVE.L (Variable21), D0 ; Load variable
	MOVE.L (Variable22), D1 ; Load variable
	DIVM D1, D0
	AND.L #$0000FFFF, D1
	MOVE.L D1, (Variable23) ; Store local variable
; Instruction of type: RESTA
;Operator 1 -> 20, Operator 2 -> t23, Store in -> 24

	MOVE.L (Variable20), D0 ; Load variable
	MOVE.L (Variable23), D1 ; Load variable
	SUBM D0, D1
	MOVE.L D1, (Variable24) ; Store local variable
; Instruction of type: SUMA
;Operator 1 -> 19, Operator 2 -> t24, Store in -> 25

	MOVE.L (Variable19), D0 ; Load variable
	MOVE.L (Variable24), D1 ; Load variable
	ADDM D0, D1
	MOVE.L D1, (Variable25) ; Store local variable
; Instruction of type: MULTIPLICACION
;Operator 1 -> 18, Operator 2 -> t25, Store in -> 26

	MOVE.L (Variable18), D0 ; Load variable
	MOVE.L (Variable25), D1 ; Load variable
	MULTM D0, D1
	MOVE.L D1, (Variable26) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 26, Store in -> 15

	MOVE.L (Variable26), D0 ; Load variable
	MOVE.L D0, (Variable15) ; Store local variable
; Instruction of type: SKIP
;, Store in -> -2

e0:
; Instruction of type: PREAMBULO
;, Store in -> -2

	LEA DISP, A0
	MOVE.L 4(A0), -(A7)
	MOVE.L A6, -(A7)
	MOVE.L A7, A6 ;BP = SP
	MOVE.L A6, 0(A7) ; DISP(prof) = BP
	MOVE.L A6, D0 ; D0=EBP
	MOVE.L (Param0), (Variable27) ; Param gest
	MOVE.L (Param1), (Variable28) ; Param gest
	MOVE.L (Param2), (Variable29) ; Param gest
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 30

	MOVE.L #1, D0 ; Load variable
	MOVE.L D0, (Variable30) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 31

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable31) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 32

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable32) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 33

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable33) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 31

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable31) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 27, Store in -> 34

	MOVE.L (Variable27), D0 ; Load variable
	MOVE.L D0, (Variable34) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 28, Store in -> 35

	MOVE.L (Variable28), D0 ; Load variable
	MOVE.L D0, (Variable35) ; Store local variable
; Instruction of type: MULTIPLICACION
;Operator 1 -> 34, Operator 2 -> t35, Store in -> 36

	MOVE.L (Variable34), D0 ; Load variable
	MOVE.L (Variable35), D1 ; Load variable
	MULTM D0, D1
	MOVE.L D1, (Variable36) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 36, Store in -> 31

	MOVE.L (Variable36), D0 ; Load variable
	MOVE.L D0, (Variable31) ; Store local variable
; Instruction of type: SKIP
;, Store in -> -2

e1:
; Instruction of type: COPIA
;Operator 1 -> 27, Store in -> 37

	MOVE.L (Variable27), D0 ; Load variable
	MOVE.L D0, (Variable37) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 28, Store in -> 38

	MOVE.L (Variable28), D0 ; Load variable
	MOVE.L D0, (Variable38) ; Store local variable
; Instruction of type: IFMAYOR
;Operator 1 -> 37, Operator 2 -> t38, Store in -> 39

	MOVE.L (Variable37), D0 ; Load variable
	MOVE.L (Variable38), D1 ; Load variable
	CMP.L D1, D0
	BGT salto0 
	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable39) ; Store local variable
	JMP fsalto0 ; Instruction of type: SKIP
salto0:
	MOVE.L #-1, D0 ; Load variable
	MOVE.L D0, (Variable39) ; Store local variable
; Instruction of type: SKIP
fsalto0:

; Instruction of type: IFTRUEGOTO
;Operator 1 -> 39, Operator 2 -> 0, Store in -> -2

	MOVE.L (Variable39), D0 ; Load variable
	MOVE.L #-1,D1
	CMP.L D1, D0
	BEQ e2
; Instruction of type: GOTO
;, Store in -> -2

	JMP e3
; Instruction of type: SKIP
;, Store in -> -2

e2:
; Instruction of type: COPIA
;Operator 1 -> 29, Store in -> 40

	MOVE.L (Variable29), D0 ; Load variable
	MOVE.L D0, (Variable40) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 41

	MOVE.L #23, D0 ; Load variable
	MOVE.L D0, (Variable41) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 27, Store in -> 42

	MOVE.L (Variable27), D0 ; Load variable
	MOVE.L D0, (Variable42) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 28, Store in -> 43

	MOVE.L (Variable28), D0 ; Load variable
	MOVE.L D0, (Variable43) ; Store local variable
; Instruction of type: MULTIPLICACION
;Operator 1 -> 42, Operator 2 -> t43, Store in -> 44

	MOVE.L (Variable42), D0 ; Load variable
	MOVE.L (Variable43), D1 ; Load variable
	MULTM D0, D1
	MOVE.L D1, (Variable44) ; Store local variable
; Instruction of type: IFDIFERENTE
;Operator 1 -> 41, Operator 2 -> t44, Store in -> 45

	MOVE.L (Variable41), D0 ; Load variable
	MOVE.L (Variable44), D1 ; Load variable
	CMP.L D1, D0
	BNE salto1
	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable45) ; Store local variable
	JMP fsalto1  ; Instruction of type: SKIP
salto1:
	MOVE.L #-1, D0 ; Load variable
	MOVE.L D0, (Variable45) ; Store local variable
; Instruction of type: SKIP
fsalto1:

; Instruction of type: AND
;Operator 1 -> 40, Operator 2 -> t45, Store in -> 46

	MOVE.L (Variable40), D0 ; Load variable
	MOVE.L (Variable45), D1 ; Load variable
	ANDM D0, D1
	MOVE.L D1, (Variable46) ; Store local variable
; Instruction of type: NOT
;, Store in -> 46

	MOVE.L (Variable46), D1 ; Load variable
	NOTM D1  
	MOVE.L D1, (Variable46) ; Store local variable
; Instruction of type: IFTRUEGOTO
;Operator 1 -> 46, Operator 2 -> 0, Store in -> -2

	MOVE.L (Variable46), D0 ; Load variable
	MOVE.L #-1,D1
	CMP.L D1, D0
	BEQ e4
; Instruction of type: GOTO
;, Store in -> -2

	JMP e5
; Instruction of type: SKIP
;, Store in -> -2

e4:
; Instruction of type: COPIA
;Operator 1 -> 31, Store in -> 47

	MOVE.L (Variable31), D0 ; Load variable
	MOVE.L D0, (Variable47) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 30, Store in -> 48

	MOVE.L (Variable30), D0 ; Load variable
	MOVE.L D0, (Variable48) ; Store local variable
; Instruction of type: RESTA
;Operator 1 -> 47, Operator 2 -> t48, Store in -> 49

	MOVE.L (Variable47), D0 ; Load variable
	MOVE.L (Variable48), D1 ; Load variable
	SUBM D0, D1
	MOVE.L D1, (Variable49) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 49, Store in -> 31

	MOVE.L (Variable49), D0 ; Load variable
	MOVE.L D0, (Variable31) ; Store local variable
; Instruction of type: GOTO
;, Store in -> -2

	JMP e6
; Instruction of type: SKIP
;, Store in -> -2

e5:
; Instruction of type: SKIP
;, Store in -> -2

e6:
; Instruction of type: SKIP
;, Store in -> -2

e7:
; Instruction of type: COPIA
;Operator 1 -> 32, Store in -> 50

	MOVE.L (Variable32), D0 ; Load variable
	MOVE.L D0, (Variable50) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 27, Store in -> 51

	MOVE.L (Variable27), D0 ; Load variable
	MOVE.L D0, (Variable51) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 28, Store in -> 52

	MOVE.L (Variable28), D0 ; Load variable
	MOVE.L D0, (Variable52) ; Store local variable
; Instruction of type: MULTIPLICACION
;Operator 1 -> 51, Operator 2 -> t52, Store in -> 53

	MOVE.L (Variable51), D0 ; Load variable
	MOVE.L (Variable52), D1 ; Load variable
	MULTM D0, D1
	MOVE.L D1, (Variable53) ; Store local variable
; Instruction of type: IFMAYORIGUAL
;Operator 1 -> 50, Operator 2 -> t53, Store in -> 54

	MOVE.L (Variable50), D0 ; Load variable
	MOVE.L (Variable53), D1 ; Load variable
	CMP.L D1, D0
	BGE salto2
	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable54) ; Store local variable
	JMP fsalto2 ; Instruction of type: SKIP
salto2:
	MOVE.L #-1, D0 ; Load variable
	MOVE.L D0, (Variable54) ; Store local variable
; Instruction of type: SKIP
fsalto2:

; Instruction of type: IFTRUEGOTO
;Operator 1 -> 54, Operator 2 -> 0, Store in -> -2

	MOVE.L (Variable54), D0 ; Load variable
	MOVE.L #-1,D1
	CMP.L D1, D0
	BEQ e8
; Instruction of type: GOTO
;, Store in -> -2

	JMP e9
; Instruction of type: SKIP
;, Store in -> -2

e8:
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 55

	LEA Variable55, A0
	MOVE.L A0, D0 ; Load variable
; Instruction of type: PRINT
;, Store in -> 55

	LEA	Variable55, A0 ; Cargar la string
	MOVE.L A0, A1 ; Ready text
	MOVE.L #14, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
; Instruction of type: COPIA
;Operator 1 -> 31, Store in -> 56

	MOVE.L (Variable31), D0 ; Load variable
	MOVE.L D0, (Variable56) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 30, Store in -> 57

	MOVE.L (Variable30), D0 ; Load variable
	MOVE.L D0, (Variable57) ; Store local variable
; Instruction of type: RESTA
;Operator 1 -> 56, Operator 2 -> t57, Store in -> 58

	MOVE.L (Variable56), D0 ; Load variable
	MOVE.L (Variable57), D1 ; Load variable
	SUBM D0, D1
	MOVE.L D1, (Variable58) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 58, Store in -> 31

	MOVE.L (Variable58), D0 ; Load variable
	MOVE.L D0, (Variable31) ; Store local variable
; Instruction of type: SUMARAPIDA
;Operator 1 -> 32, Operator 2 -> 1, Store in -> 32

	MOVE.L (Variable32), D1 ; Load variable
	ADDQ.L #1, D1
	MOVE.L D1, (Variable32) ; Store local variable
; Instruction of type: GOTO
;, Store in -> -2

	JMP e7
; Instruction of type: SKIP
;, Store in -> -2

e9:
; Instruction of type: GOTO
;, Store in -> -2

	JMP e1
; Instruction of type: SKIP
;, Store in -> -2

e3:
; Instruction of type: RETURN
;Operator 1 -> 31, Store in -> 0

	MOVE.L A6, A7 ; SP = BP, return to state before PMB
	MOVE.L (A7)+, A6 ; BP = old BP
	LEA DISP, A0 ; A0 = @DISP
	MOVE.L (A7)+, 4(A0) ; DISP[prof] = old value
	MOVE.L (Variable31), D5 ; Load variable
	RTS ; Return
; Instruction of type: CALL
;Operator 1 -> -2

main:                                                       
	MOVE.L A7, A6
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 59

	MOVE.L #1, D0 ; Load variable
	MOVE.L D0, (Variable59) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 60

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable60) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 60

	MOVE.L #21, D0 ; Load variable
	MOVE.L D0, (Variable60) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 59, Store in -> 62

	MOVE.L (Variable59), D0 ; Load variable
	MOVE.L D0, (Variable62) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 63

	MOVE.L #3, D0 ; Load variable
	MOVE.L D0, (Variable63) ; Store local variable
; Instruction of type: SUMA
;Operator 1 -> 62, Operator 2 -> t63, Store in -> 64

	MOVE.L (Variable62), D0 ; Load variable
	MOVE.L (Variable63), D1 ; Load variable
	ADDM D0, D1
	MOVE.L D1, (Variable64) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 61

	MOVE.L #64, D0 ; Load variable
	MOVE.L D0, (Variable61) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 66

	MOVE.L #4, D0 ; Load variable
	MOVE.L D0, (Variable66) ; Store local variable
; Instruction of type: MULTIPLICACION
;Operator 1 -> 61, Operator 2 -> t66, Store in -> 65

	MOVE.L (Variable61), D0 ; Load variable
	MOVE.L (Variable66), D1 ; Load variable
	MULTM D0, D1
	MOVE.L D1, (Variable65) ; Store local variable
; Instruction of type: IND_ASS
;Operator 1 -> -2, Operator 2 -> t65, Store in -> 6

	LEA	(Variable6), A0
	MOVE.L (Variable65), D0 ; Load variable
	ADD.L D0, A0 ; D0 = @A[B]
	MOVE.L #2, D2 ; Load variable
	MOVE.L D2, (A0) ; Store C IN A[B]
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 67

	MOVE.L #-1, D0 ; Load variable
	MOVE.L D0, (Variable67) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 68

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable68) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 67, Store in -> 69

	MOVE.L (Variable67), D0 ; Load variable
	MOVE.L D0, (Variable69) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 68, Store in -> 70

	MOVE.L (Variable68), D0 ; Load variable
	MOVE.L D0, (Variable70) ; Store local variable
; Instruction of type: AND
;Operator 1 -> 69, Operator 2 -> t70, Store in -> 71

	MOVE.L (Variable69), D0 ; Load variable
	MOVE.L (Variable70), D1 ; Load variable
	ANDM D0, D1
	MOVE.L D1, (Variable71) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 71, Store in -> 72

	MOVE.L (Variable71), D0 ; Load variable
	MOVE.L D0, (Variable72) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 2, Store in -> 73

	MOVE.L (Variable2), D0 ; Load variable
	MOVE.L D0, (Variable73) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 1, Store in -> 74

	MOVE.L (Variable1), D0 ; Load variable
	MOVE.L D0, (Variable74) ; Store local variable
; Instruction of type: IFDIFERENTE
;Operator 1 -> 73, Operator 2 -> t74, Store in -> 75

	MOVE.L (Variable73), D0 ; Load variable
	MOVE.L (Variable74), D1 ; Load variable
	CMP.L D1, D0
	BNE salto3
	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable75) ; Store local variable
	JMP fsalto3  ; Instruction of type: SKIP
salto3:
	MOVE.L #-1, D0 ; Load variable
	MOVE.L D0, (Variable75) ; Store local variable
; Instruction of type: SKIP
fsalto3:

; Instruction of type: IFTRUEGOTO
;Operator 1 -> 75, Operator 2 -> 0, Store in -> -2

	MOVE.L (Variable75), D0 ; Load variable
	MOVE.L #-1,D1
	CMP.L D1, D0
	BEQ e10
; Instruction of type: GOTO
;, Store in -> -2

	JMP e11
; Instruction of type: SKIP
;, Store in -> -2

e10:
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 60

	MOVE.L #1, D0 ; Load variable
	MOVE.L D0, (Variable60) ; Store local variable
; Instruction of type: GOTO
;, Store in -> -2

	JMP e12
; Instruction of type: SKIP
;, Store in -> -2

e11:
; Instruction of type: COPIA
;Operator 1 -> 67, Store in -> 76

	MOVE.L (Variable67), D0 ; Load variable
	MOVE.L D0, (Variable76) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 77

	MOVE.L #-1, D0 ; Load variable
	MOVE.L D0, (Variable77) ; Store local variable
; Instruction of type: IFIGUALI
;Operator 1 -> 76, Operator 2 -> t77, Store in -> 78

	MOVE.L (Variable76), D0 ; Load variable
	MOVE.L (Variable77), D1 ; Load variable
	CMP.L D1, D0
	BEQ salto4
	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable78) ; Store local variable
	JMP fsalto4 ; Instruction of type: SKIP
salto4:
	MOVE.L #-1, D0 ; Load variable
	MOVE.L D0, (Variable78) ; Store local variable
; Instruction of type: SKIP
fsalto4:

; Instruction of type: IFTRUEGOTO
;Operator 1 -> 78, Operator 2 -> 0, Store in -> -2

	MOVE.L (Variable78), D0 ; Load variable
	MOVE.L #-1,D1
	CMP.L D1, D0
	BEQ e13
; Instruction of type: GOTO
;, Store in -> -2

	JMP e14
; Instruction of type: SKIP
;, Store in -> -2

e13:
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 67

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable67) ; Store local variable
; Instruction of type: GOTO
;, Store in -> -2

	JMP e12
; Instruction of type: SKIP
;, Store in -> -2

e14:
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 5

	MOVE.L #2, D0 ; Load variable
	MOVE.L D0, (Variable5) ; Store local variable
; Instruction of type: SKIP
;, Store in -> -2

e12:
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 79

	MOVE.L #2, D0 ; Load variable
	MOVE.L D0, (Variable79) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 81

	MOVE.L #4, D0 ; Load variable
	MOVE.L D0, (Variable81) ; Store local variable
; Instruction of type: MULTIPLICACION
;Operator 1 -> 79, Operator 2 -> t81, Store in -> 80

	MOVE.L (Variable79), D0 ; Load variable
	MOVE.L (Variable81), D1 ; Load variable
	MULTM D0, D1
	MOVE.L D1, (Variable80) ; Store local variable
; Instruction of type: IND_ASS
;Operator 1 -> -2, Operator 2 -> t80, Store in -> 6

	LEA	(Variable6), A0
	MOVE.L (Variable80), D0 ; Load variable
	ADD.L D0, A0 ; D0 = @A[B]
	MOVE.L #11, D2 ; Load variable
	MOVE.L D2, (A0) ; Store C IN A[B]
	END globals