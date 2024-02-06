	INCLUDE library/MACRO.X68
	INCLUDE library/CONST.X68
	INCLUDE library/VAR.X68
;-----------------------------------------------------------
;-----------------------------------------------------------
; Initial program lines, main                               
;-----------------------------------------------------------
; Labels to memory space reserved for variables:            
;-----------------------------------------------------------
Variable0	DC.B '',0
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

	LEA Variable0, A0
	MOVE.L A0, D0 ; Load variable
	MOVE.L D0, A0 ; Param gest
	LEA Variable0, A1 ; Param gest
.loop99:
	MOVE.B (A0)+, (A1)+ 
	TST.B (A0) 
	BNE .loop99
	MOVE.B #0, (A1)+ 
; Instruction of type: CALL
;Operator 1 -> -2

	JMP main ; Declared all globals
main:                                                       
	MOVE.L A7, A6
; Instruction of type: IN
;, Store in -> 0

	MOVE.L #5, D0 ; Prepare read
	TRAP #15 ; Expect input
	AND.L #$00FF, D1 ; Mask upper word (we read char = 2 bytes)
; Instruction of type: PRINT
;, Store in -> 0

	LEA	Variable0, A0 ; Cargar la string
	MOVE.L A0, A1 ; Ready text
	MOVE.L #14, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
	END globals