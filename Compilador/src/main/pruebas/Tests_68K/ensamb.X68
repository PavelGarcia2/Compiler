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
; Instruction of type: CALL
;Operator 1 -> -2

	JMP main ; Declared all globals
main:                                                       
	MOVE.L A7, A6
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 0

	MOVE.L #1, D0 ; Load variable
	MOVE.L D0, (Variable0) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 1

	MOVE.L #6, D0 ; Load variable
	MOVE.L D0, (Variable1) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 1, Store in -> 0

	MOVE.L (Variable1), D0 ; Load variable
	MOVE.L D0, (Variable0) ; Store local variable
	END globals