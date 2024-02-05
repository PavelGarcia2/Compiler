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
Variable1	DS.L 5
Variable2	DS.L 1
Variable3	DS.L 1
Variable4	DS.L 1
Variable5	DS.L 1
Variable6	DS.L 1
Variable7	DS.L 1
Variable8	DS.L 1
Variable9	DS.L 1
Variable10	DS.L 1
Variable11	DS.L 1
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

	MOVE.L #999, D0 ; Load variable
	MOVE.L D0, (Variable0) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 2

	MOVE.L #4, D0 ; Load variable
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
;Operator 1 -> -2, Operator 2 -> t3, Store in -> 1

	LEA	(Variable1), A0
	MOVE.L (Variable3), D0 ; Load variable
	ADD.L D0, A0 ; D0 = @A[B]
	MOVE.L #1, D2 ; Load variable
	MOVE.L D2, (A0) ; Store C IN A[B]
; Instruction of type: CALL
;Operator 1 -> -2

main:                                                       
	MOVE.L A7, A6
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 5

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable5) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 6

	MOVE.L #4, D0 ; Load variable
	MOVE.L D0, (Variable6) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 8

	MOVE.L #4, D0 ; Load variable
	MOVE.L D0, (Variable8) ; Store local variable
; Instruction of type: MULTIPLICACION
;Operator 1 -> 6, Operator 2 -> t8, Store in -> 7

	MOVE.L (Variable6), D0 ; Load variable
	MOVE.L (Variable8), D1 ; Load variable
	MULTM D0, D1
	MOVE.L D1, (Variable7) ; Store local variable
; Instruction of type: IND_ASS
;Operator 1 -> -2, Operator 2 -> t7, Store in -> 1

	LEA	(Variable1), A0
	MOVE.L (Variable7), D0 ; Load variable
	ADD.L D0, A0 ; D0 = @A[B]
	MOVE.L #2315, D2 ; Load variable
	MOVE.L D2, (A0) ; Store C IN A[B]
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 9

	MOVE.L #4, D0 ; Load variable
	MOVE.L D0, (Variable9) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 11

	MOVE.L #4, D0 ; Load variable
	MOVE.L D0, (Variable11) ; Store local variable
; Instruction of type: MULTIPLICACION
;Operator 1 -> 9, Operator 2 -> t11, Store in -> 10

	MOVE.L (Variable9), D0 ; Load variable
	MOVE.L (Variable11), D1 ; Load variable
	MULTM D0, D1
	MOVE.L D1, (Variable10) ; Store local variable
; Instruction of type: IND_VAL
;Operator 1 -> 1, Operator 2 -> t10, Store in -> 5

	LEA (Variable1), A0
	MOVE.L (Variable10), D0 ; Load variable
	ADD.L D0, A0 ; D0 = @B[C]
	MOVE.L (A0), D0 ; D0 = B[C]
	MOVE.L D0, Variable5 ; Store local variable
; Instruction of type: PRINT
;, Store in -> 5

	MOVE.L (Variable5), D1 ; Load variable
	MOVE.L #3, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
	END globals