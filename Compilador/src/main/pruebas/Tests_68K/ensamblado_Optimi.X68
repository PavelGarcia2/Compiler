	INCLUDE library/MACRO.X68
	INCLUDE library/CONST.X68
	INCLUDE library/VAR.X68
;-----------------------------------------------------------
;-----------------------------------------------------------
; Initial program lines, main                               
;-----------------------------------------------------------
; Labels to memory space reserved for variables:            
;-----------------------------------------------------------
Variable0	DS.L 15
Variable1	DS.L 2
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
Variable12	DS.L 1
Variable13	DS.L 1
Variable14	DS.L 1
Variable15	DS.L 1
Variable16	DS.L 1
Variable17	DS.L 1
Variable18	DS.L 1
Variable19	DS.L 1
Variable20	DS.L 1
Variable21	DS.L 1
Variable22	DS.L 1
Variable23	DS.L 1
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
;Operator 1 -> -2, Store in -> 2

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable2) ; Store local variable
; Instruction of type: MULTIPLICACION
;Operator 1 -> -2, Operator 2 -> 4, Store in -> 3

	MOVE.L #3, D0 ; Load variable
	MOVE.L #4, D1 ; Load variable
	MULTM D0, D1
	MOVE.L D1, (Variable3) ; Store local variable
; Instruction of type: SUMA
;Operator 1 -> 3, Operator 2 -> 2, Store in -> 6

	MOVE.L (Variable3), D0 ; Load variable
	MOVE.L #2, D1 ; Load variable
	ADDM D0, D1
	MOVE.L D1, (Variable6) ; Store local variable
; Instruction of type: MULTIPLICACION
;Operator 1 -> -2, Operator 2 -> t6, Store in -> 8

	MOVE.L #4, D0 ; Load variable
	MOVE.L (Variable6), D1 ; Load variable
	MULTM D0, D1
	MOVE.L D1, (Variable8) ; Store local variable
; Instruction of type: IND_ASS
;Operator 1 -> -2, Operator 2 -> t8, Store in -> 0

	LEA	(Variable0), A0
	MOVE.L (Variable8), D0 ; Load variable
	ADD.L D0, A0 ; D0 = @A[B]
	MOVE.L #2315, D2 ; Load variable
	MOVE.L D2, (A0) ; Store C IN A[B]
; Instruction of type: MULTIPLICACION
;Operator 1 -> -2, Operator 2 -> 4, Store in -> 10

	MOVE.L #3, D0 ; Load variable
	MOVE.L #4, D1 ; Load variable
	MULTM D0, D1
	MOVE.L D1, (Variable10) ; Store local variable
; Instruction of type: SUMA
;Operator 1 -> 10, Operator 2 -> 2, Store in -> 13

	MOVE.L (Variable10), D0 ; Load variable
	MOVE.L #2, D1 ; Load variable
	ADDM D0, D1
	MOVE.L D1, (Variable13) ; Store local variable
; Instruction of type: MULTIPLICACION
;Operator 1 -> -2, Operator 2 -> t13, Store in -> 15

	MOVE.L #4, D0 ; Load variable
	MOVE.L (Variable13), D1 ; Load variable
	MULTM D0, D1
	MOVE.L D1, (Variable15) ; Store local variable
; Instruction of type: IND_VAL
;Operator 1 -> 0, Operator 2 -> t15, Store in -> 17

	LEA (Variable0), A0
	MOVE.L (Variable15), D0 ; Load variable
	ADD.L D0, A0 ; D0 = @B[C]
	MOVE.L (A0), D0 ; D0 = B[C]
	MOVE.L D0, Variable17 ; Store local variable
; Instruction of type: MULTIPLICACION
;Operator 1 -> -2, Operator 2 -> 4, Store in -> 19

	MOVE.L #1, D0 ; Load variable
	MOVE.L #4, D1 ; Load variable
	MULTM D0, D1
	MOVE.L D1, (Variable19) ; Store local variable
; Instruction of type: IND_ASS
;Operator 1 -> 17, Operator 2 -> t19, Store in -> 1

	LEA	(Variable1), A0
	MOVE.L (Variable19), D0 ; Load variable
	ADD.L D0, A0 ; D0 = @A[B]
	MOVE.L (Variable17), D2 ; Load variable
	MOVE.L D2, (A0) ; Store C IN A[B]
; Instruction of type: MULTIPLICACION
;Operator 1 -> -2, Operator 2 -> 4, Store in -> 22

	MOVE.L #1, D0 ; Load variable
	MOVE.L #4, D1 ; Load variable
	MULTM D0, D1
	MOVE.L D1, (Variable22) ; Store local variable
; Instruction of type: IND_VAL
;Operator 1 -> 1, Operator 2 -> t22, Store in -> 2

	LEA (Variable1), A0
	MOVE.L (Variable22), D0 ; Load variable
	ADD.L D0, A0 ; D0 = @B[C]
	MOVE.L (A0), D0 ; D0 = B[C]
	MOVE.L D0, Variable2 ; Store local variable
; Instruction of type: PRINT
;, Store in -> 2

	MOVE.L (Variable2), D1 ; Load variable
	MOVE.L #3, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
	END globals