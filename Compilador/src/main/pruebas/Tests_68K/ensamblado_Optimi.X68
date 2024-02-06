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
Variable6	DS.L 1
Variable7	DS.L 1
Variable8	DS.L 1
Variable9	DS.L 1
Variable10	DS.L 1
Variable11	DS.L 1
Variable12	DS.L 1
Variable13	DS.L 1
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
; Instruction of type: SKIP
;, Store in -> -2

	JMP main ; Declared all globals
e0:
; Instruction of type: PREAMBULO
;, Store in -> -2

	LEA DISP, A0
	MOVE.L 4(A0), -(A7)
	MOVE.L A6, -(A7)
	MOVE.L A7, A6 ;BP = SP
	MOVE.L A6, 0(A7) ; DISP(prof) = BP
	MOVE.L A6, D0 ; D0=EBP
	MOVE.L (Param0), (Variable0) ; Param gest
	MOVE.L (Param1), (Variable1) ; Param gest
; Instruction of type: SUMA
;Operator 1 -> 0, Operator 2 -> t1, Store in -> 5

	MOVE.L (Variable0), D0 ; Load variable
	MOVE.L (Variable1), D1 ; Load variable
	ADDM D0, D1
	MOVE.L D1, (Variable5) ; Store local variable
; Instruction of type: PRINTLN
;, Store in -> 5

	MOVE.L (Variable5), D1 ; Load variable
	MOVE.L #3, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
	MOVE.B #11, D0 ; Next line prepare
	MOVE.L #$00FF, D1 ; Request current coordinates
	TRAP #15 ; Get them in D1.L (HIGH=COL, LOW = ROW)
	AND.L #$00FF, D1 ; We want always column = 0
	ADDQ.B #1, D1 ; We increment the current row by 1
	TRAP #15 ; Set new coordinates (next line ready)
; Instruction of type: RETURN
;Operator 1 -> 5, Store in -> 0

	MOVE.L A6, A7 ; SP = BP, return to state before PMB
	MOVE.L (A7)+, A6 ; BP = old BP
	LEA DISP, A0 ; A0 = @DISP
	MOVE.L (A7)+, 4(A0) ; DISP[prof] = old value
	MOVE.L (Variable5), D5 ; Load variable
	RTS ; Return
; Instruction of type: CALL
;Operator 1 -> -2

main:                                                       
	MOVE.L A7, A6
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 6

	MOVE.L #1, D0 ; Load variable
	MOVE.L D0, (Variable6) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 7

	MOVE.L #' ', D0 ; Load variable
	MOVE.L D0, (Variable7) ; Store local variable
; Instruction of type: SUMA
;Operator 1 -> -2, Operator 2 -> 2, Store in -> 10

	MOVE.L #2, D0 ; Load variable
	MOVE.L #2, D1 ; Load variable
	ADDM D0, D1
	MOVE.L D1, (Variable10) ; Store local variable
; Instruction of type: PARAM_SIMPLE
;, Store in -> 10

	SUB.L #40, A6
	MOVE.L A6, A7
	MOVE.L (Variable10), D0 ; Load variable
	MOVE.L D0, (Variable10) 
	MOVE.L (Variable10), (Param0) ; Param
; Instruction of type: SUMA
;Operator 1 -> -2, Operator 2 -> 2, Store in -> 13

	MOVE.L #1, D0 ; Load variable
	MOVE.L #2, D1 ; Load variable
	ADDM D0, D1
	MOVE.L D1, (Variable13) ; Store local variable
; Instruction of type: PARAM_SIMPLE
;, Store in -> 13

	SUB.L #40, A6
	MOVE.L A6, A7
	MOVE.L (Variable13), D0 ; Load variable
	MOVE.L D0, (Variable13) 
	MOVE.L (Variable13), (Param1) ; Param
; Instruction of type: CALL
;Operator 1 -> 5, Store in -> 0

	JSR e0 ; GOTO 0
	MOVE.L D5, (Variable5) ; Store local variable
	MOVE.L A6, A7
; Instruction of type: COPIA
;Operator 1 -> 5, Store in -> 6

	MOVE.L (Variable5), D0 ; Load variable
	MOVE.L D0, (Variable6) ; Store local variable
; Instruction of type: PRINTLN
;, Store in -> 6

	MOVE.L (Variable6), D1 ; Load variable
	MOVE.L #3, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
	MOVE.B #11, D0 ; Next line prepare
	MOVE.L #$00FF, D1 ; Request current coordinates
	TRAP #15 ; Get them in D1.L (HIGH=COL, LOW = ROW)
	AND.L #$00FF, D1 ; We want always column = 0
	ADDQ.B #1, D1 ; We increment the current row by 1
	TRAP #15 ; Set new coordinates (next line ready)
	END globals