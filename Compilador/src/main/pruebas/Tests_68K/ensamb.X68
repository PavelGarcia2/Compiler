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
Variable12	DC.B 'Valor i  ',0
Variable13	DS.L 1
Variable14	DS.L 1
Variable15	DS.L 1
Variable16	DS.L 1
Variable17	DS.L 1
Variable18	DS.L 1
Variable19	DS.L 1
Variable20	DS.L 1
Variable21	DS.L 1
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

	MOVE.L #2, D0 ; Load variable
	MOVE.L D0, (Variable0) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 1

	MOVE.L #3, D0 ; Load variable
	MOVE.L D0, (Variable1) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 2

	MOVE.L #7, D0 ; Load variable
	MOVE.L D0, (Variable2) ; Store local variable
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
	MOVE.L (Param0), (Variable3) ; Param gest
	MOVE.L (Param1), (Variable4) ; Param gest
	MOVE.L (Param2), (Variable5) ; Param gest
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 6

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable6) ; Store local variable
; Instruction of type: PRINTLN
;, Store in -> 3

	MOVE.L (Variable3), D1 ; Load variable
	MOVE.L #3, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
	MOVE.B #11, D0 ; Next line prepare
	MOVE.L #$00FF, D1 ; Request current coordinates
	TRAP #15 ; Get them in D1.L (HIGH=COL, LOW = ROW)
	AND.L #$00FF, D1 ; We want always column = 0
	ADDQ.B #1, D1 ; We increment the current row by 1
	TRAP #15 ; Set new coordinates (next line ready)
; Instruction of type: PRINTLN
;, Store in -> 4

	MOVE.L (Variable4), D1 ; Load variable
	MOVE.L #3, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
	MOVE.B #11, D0 ; Next line prepare
	MOVE.L #$00FF, D1 ; Request current coordinates
	TRAP #15 ; Get them in D1.L (HIGH=COL, LOW = ROW)
	AND.L #$00FF, D1 ; We want always column = 0
	ADDQ.B #1, D1 ; We increment the current row by 1
	TRAP #15 ; Set new coordinates (next line ready)
; Instruction of type: COPIA
;Operator 1 -> 4, Store in -> 7

	MOVE.L (Variable4), D0 ; Load variable
	MOVE.L D0, (Variable7) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 3, Store in -> 8

	MOVE.L (Variable3), D0 ; Load variable
	MOVE.L D0, (Variable8) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 5, Store in -> 9

	MOVE.L (Variable5), D0 ; Load variable
	MOVE.L D0, (Variable9) ; Store local variable
; Instruction of type: SUMA
;Operator 1 -> 8, Operator 2 -> t9, Store in -> 10

	MOVE.L (Variable8), D0 ; Load variable
	MOVE.L (Variable9), D1 ; Load variable
	ADDM D0, D1
	MOVE.L D1, (Variable10) ; Store local variable
; Instruction of type: SUMA
;Operator 1 -> 7, Operator 2 -> t10, Store in -> 11

	MOVE.L (Variable7), D0 ; Load variable
	MOVE.L (Variable10), D1 ; Load variable
	ADDM D0, D1
	MOVE.L D1, (Variable11) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 11, Store in -> 6

	MOVE.L (Variable11), D0 ; Load variable
	MOVE.L D0, (Variable6) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 12

	LEA Variable12, A0
	MOVE.L A0, D0 ; Load variable
; Instruction of type: PRINT
;, Store in -> 12

	LEA	Variable12, A0 ; Cargar la string
	MOVE.L A0, A1 ; Ready text
	MOVE.L #14, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
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
; Instruction of type: RETURN
;Operator 1 -> 6, Store in -> 0

	MOVE.L A6, A7 ; SP = BP, return to state before PMB
	MOVE.L (A7)+, A6 ; BP = old BP
	LEA DISP, A0 ; A0 = @DISP
	MOVE.L (A7)+, 4(A0) ; DISP[prof] = old value
	MOVE.L (Variable6), D5 ; Load variable
	RTS ; Return
; Instruction of type: SKIP
;, Store in -> -2

e1:
; Instruction of type: PREAMBULO
;, Store in -> -2

	LEA DISP, A0
	MOVE.L 4(A0), -(A7)
	MOVE.L A6, -(A7)
	MOVE.L A7, A6 ;BP = SP
	MOVE.L A6, 0(A7) ; DISP(prof) = BP
	MOVE.L A6, D0 ; D0=EBP
	MOVE.L (Param0), (Variable13) ; Param gest
	MOVE.L (Param1), (Variable14) ; Param gest
; Instruction of type: COPIA
;Operator 1 -> 13, Store in -> 15

	MOVE.L (Variable13), D0 ; Load variable
	MOVE.L D0, (Variable15) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 14, Store in -> 16

	MOVE.L (Variable14), D0 ; Load variable
	MOVE.L D0, (Variable16) ; Store local variable
; Instruction of type: SUMA
;Operator 1 -> 15, Operator 2 -> t16, Store in -> 17

	MOVE.L (Variable15), D0 ; Load variable
	MOVE.L (Variable16), D1 ; Load variable
	ADDM D0, D1
	MOVE.L D1, (Variable17) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 17, Store in -> 18

	MOVE.L (Variable17), D0 ; Load variable
	MOVE.L D0, (Variable18) ; Store local variable
; Instruction of type: RETURN
;Operator 1 -> 18, Store in -> 1

	MOVE.L A6, A7 ; SP = BP, return to state before PMB
	MOVE.L (A7)+, A6 ; BP = old BP
	LEA DISP, A0 ; A0 = @DISP
	MOVE.L (A7)+, 4(A0) ; DISP[prof] = old value
	MOVE.L (Variable18), D5 ; Load variable
	RTS ; Return
; Instruction of type: CALL
;Operator 1 -> -2

main:                                                       
	MOVE.L A7, A6
; Instruction of type: PARAM_SIMPLE
;, Store in -> 1

	SUB.L #40, A6
	MOVE.L A6, A7
	MOVE.L (Variable1), D0 ; Load variable
	MOVE.L D0, (Variable1) 
	MOVE.L (Variable1), (Param0) ; Param
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 19

	MOVE.L #3, D0 ; Load variable
	MOVE.L D0, (Variable19) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 20

	MOVE.L #45, D0 ; Load variable
	MOVE.L D0, (Variable20) ; Store local variable
; Instruction of type: SUMA
;Operator 1 -> 19, Operator 2 -> t20, Store in -> 21

	MOVE.L (Variable19), D0 ; Load variable
	MOVE.L (Variable20), D1 ; Load variable
	ADDM D0, D1
	MOVE.L D1, (Variable21) ; Store local variable
; Instruction of type: PARAM_SIMPLE
;, Store in -> 21

	SUB.L #40, A6
	MOVE.L A6, A7
	MOVE.L (Variable21), D0 ; Load variable
	MOVE.L D0, (Variable21) 
	MOVE.L (Variable21), (Param1) ; Param
; Instruction of type: CALL
;Operator 1 -> 18, Store in -> 1

	JSR e1 ; GOTO 1
	MOVE.L D5, (Variable18) ; Store local variable
	MOVE.L A6, A7
; Instruction of type: COPIA
;Operator 1 -> 18, Store in -> 2

	MOVE.L (Variable18), D0 ; Load variable
	MOVE.L D0, (Variable2) ; Store local variable
; Instruction of type: PARAM_SIMPLE
;, Store in -> 2

	SUB.L #40, A6
	MOVE.L A6, A7
	MOVE.L (Variable2), D0 ; Load variable
	MOVE.L D0, (Variable2) 
	MOVE.L (Variable2), (Param0) ; Param
; Instruction of type: PARAM_SIMPLE
;, Store in -> 1

	SUB.L #40, A6
	MOVE.L A6, A7
	MOVE.L (Variable1), D0 ; Load variable
	MOVE.L D0, (Variable1) 
	MOVE.L (Variable1), (Param1) ; Param
; Instruction of type: PARAM_SIMPLE
;, Store in -> 0

	SUB.L #40, A6
	MOVE.L A6, A7
	MOVE.L (Variable0), D0 ; Load variable
	MOVE.L D0, (Variable0) 
	MOVE.L (Variable0), (Param2) ; Param
; Instruction of type: CALL
;Operator 1 -> 6, Store in -> 0

	JSR e0 ; GOTO 0
	MOVE.L D5, (Variable6) ; Store local variable
	MOVE.L A6, A7
; Instruction of type: COPIA
;Operator 1 -> 6, Store in -> 2

	MOVE.L (Variable6), D0 ; Load variable
	MOVE.L D0, (Variable2) ; Store local variable
; Instruction of type: PRINT
;, Store in -> 2

	MOVE.L (Variable2), D1 ; Load variable
	MOVE.L #3, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
	END globals