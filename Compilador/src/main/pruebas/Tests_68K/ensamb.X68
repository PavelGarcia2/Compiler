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

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable0) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 1

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable1) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 2

	MOVE.L #3, D0 ; Load variable
	MOVE.L D0, (Variable2) ; Store local variable
; Instruction of type: IFIGUALI
;Operator 1 -> 1, Operator 2 -> t2, Store in -> 3

	MOVE.L (Variable1), D0 ; Load variable
	MOVE.L (Variable2), D1 ; Load variable
	CMP.L D0, D1
	BEQ salto0
	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable3) ; Store local variable
	JMP fsalto0; Instruction of type: SKIP
salto0:
	MOVE.L #-1, D0 ; Load variable
	MOVE.L D0, (Variable3) ; Store local variable
; Instruction of type: SKIP
fsalto0:

; Instruction of type: IFTRUEGOTO
;Operator 1 -> 3, Operator 2 -> 0, Store in -> -2

	MOVE.L (Variable3), D0 ; Load variable
	MOVE.L #-1,D1
	CMP.L D0, D1
	BEQ 0
; Instruction of type: GOTO
;, Store in -> -2

	JMP 1
; Instruction of type: SKIP
;, Store in -> -2

0:
; Instruction of type: PRINT
;, Store in -> -2

	LEA	"Es par", A0 ; Cargar la string
	MOVE.L A0, A1 ; Ready text
	MOVE.L #14, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
; Instruction of type: SKIP
;, Store in -> -2

1:
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 4

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable4) ; Store local variable
; Instruction of type: IFTRUEGOTO
;Operator 1 -> 4, Operator 2 -> 0, Store in -> -2

	MOVE.L (Variable4), D0 ; Load variable
	MOVE.L #-1,D1
	CMP.L D0, D1
	BEQ 2
; Instruction of type: GOTO
;, Store in -> -2

	JMP 3
; Instruction of type: SKIP
;, Store in -> -2

2:
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 0

	MOVE.L #5, D0 ; Load variable
	MOVE.L D0, (Variable0) ; Store local variable
; Instruction of type: SKIP
;, Store in -> -2

3:
	END globals