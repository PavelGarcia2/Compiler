	INCLUDE library/MACRO.X68
	INCLUDE library/CONST.X68
	INCLUDE library/VAR.X68
;-----------------------------------------------------------
;-----------------------------------------------------------
; Initial program lines, main                               
;-----------------------------------------------------------
; Labels to memory space reserved for variables:            
;-----------------------------------------------------------
Variable0	DS.L 4
Variable1	DS.L 1
Variable2	DS.L 1
Variable3	DS.L 1
Variable4	DS.L 1
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
;Operator 1 -> -2, Store in -> 1

	MOVE.L #2, D0 ; Load variable
	MOVE.L D0, (Variable1) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 2

	MOVE.L #5, D0 ; Load variable
	MOVE.L D0, (Variable2) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 4

	MOVE.L #4, D0 ; Load variable
	MOVE.L D0, (Variable4) ; Store local variable
; Instruction of type: MULTIPLICACION
;Operator 1 -> 2, Operator 2 -> t4, Store in -> 3

	JMP main ; Declared all globals
	MOVE.L (Variable2), D0 ; Load variable
	MOVE.L (Variable4), D1 ; Load variable
	MULTM D0, D1
	MOVE.L D1, (Variable3) ; Store local variable
; Instruction of type: IND_ASS
;Operator 1 -> -2, Operator 2 -> t3, Store in -> 0

	LEA	(Variable0), A0
	MOVE.L (Variable3), D0 ; Load variable
	ADD.L D0, A0 ; D0 = @A[B]
	MOVE.L #2, D2 ; Load variable
	MOVE.L D2, (A0) ; Store C IN A[B]
; Instruction of type: CALL
;Operator 1 -> -2

main:                                                       
	MOVE.L A7, A6
	END globals