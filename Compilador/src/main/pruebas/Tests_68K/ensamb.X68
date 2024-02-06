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
Variable8	DC.B 'Es par',0
Variable9	DC.B 'Es par',0
Variable10	DC.B 'Es par',0
Variable11	DS.L 1
Variable12	DS.L 1
Variable13	DS.L 1
Variable14	DC.B 'Es impar',0
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

	MOVE.L #23, D0 ; Load variable
	MOVE.L D0, (Variable0) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 1

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable1) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 2

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable2) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 0, Store in -> 3

	MOVE.L (Variable0), D0 ; Load variable
	MOVE.L D0, (Variable3) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 4

	MOVE.L #2, D0 ; Load variable
	MOVE.L D0, (Variable4) ; Store local variable
; Instruction of type: MODULO
;Operator 1 -> 3, Operator 2 -> t4, Store in -> 5

	MOVE.L (Variable3), D0 ; Load variable
	MOVE.L (Variable4), D1 ; Load variable
	MODM D1, D0
	MOVE.L D1, (Variable5) ; Store local variable
; Instruction of type: IFIGUALI
;Operator 1 -> 2, Operator 2 -> t5, Store in -> 6

	MOVE.L (Variable2), D0 ; Load variable
	MOVE.L (Variable5), D1 ; Load variable
	CMP.L D1, D0
	BEQ salto0
	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable6) ; Store local variable
	JMP fsalto0 ; Instruction of type: SKIP
salto0:
	MOVE.L #-1, D0 ; Load variable
	MOVE.L D0, (Variable6) ; Store local variable
; Instruction of type: SKIP
fsalto0:

; Instruction of type: IFTRUEGOTO
;Operator 1 -> 6, Operator 2 -> 0, Store in -> -2

	MOVE.L (Variable6), D0 ; Load variable
	MOVE.L #-1,D1
	CMP.L D1, D0
	BEQ e0
; Instruction of type: GOTO
;, Store in -> -2

	JMP e1
; Instruction of type: SKIP
;, Store in -> -2

e0:
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 7

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable7) ; Store local variable
; Instruction of type: IFTRUEGOTO
;Operator 1 -> 7, Operator 2 -> 0, Store in -> -2

	MOVE.L (Variable7), D0 ; Load variable
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
;Operator 1 -> -2, Store in -> 8

	LEA Variable8, A0
	MOVE.L A0, D0 ; Load variable
; Instruction of type: PRINTLN
;, Store in -> 8

	LEA	Variable8, A0 ; Cargar la string
	MOVE.L A0, A1 ; Ready text
	MOVE.L #14, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
	MOVE.B #11, D0 ; Next line prepare
	MOVE.L #$00FF, D1 ; Request current coordinates
	TRAP #15 ; Get them in D1.L (HIGH=COL, LOW = ROW)
	AND.L #$00FF, D1 ; We want always column = 0
	ADDQ.B #1, D1 ; We increment the current row by 1
	TRAP #15 ; Set new coordinates (next line ready)
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 9

	LEA Variable9, A0
	MOVE.L A0, D0 ; Load variable
; Instruction of type: PRINTLN
;, Store in -> 9

	LEA	Variable9, A0 ; Cargar la string
	MOVE.L A0, A1 ; Ready text
	MOVE.L #14, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
	MOVE.B #11, D0 ; Next line prepare
	MOVE.L #$00FF, D1 ; Request current coordinates
	TRAP #15 ; Get them in D1.L (HIGH=COL, LOW = ROW)
	AND.L #$00FF, D1 ; We want always column = 0
	ADDQ.B #1, D1 ; We increment the current row by 1
	TRAP #15 ; Set new coordinates (next line ready)
; Instruction of type: GOTO
;, Store in -> -2

	JMP e4
; Instruction of type: SKIP
;, Store in -> -2

e3:
; Instruction of type: SKIP
;, Store in -> -2

e4:
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 10

	LEA Variable10, A0
	MOVE.L A0, D0 ; Load variable
; Instruction of type: PRINTLN
;, Store in -> 10

	LEA	Variable10, A0 ; Cargar la string
	MOVE.L A0, A1 ; Ready text
	MOVE.L #14, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
	MOVE.B #11, D0 ; Next line prepare
	MOVE.L #$00FF, D1 ; Request current coordinates
	TRAP #15 ; Get them in D1.L (HIGH=COL, LOW = ROW)
	AND.L #$00FF, D1 ; We want always column = 0
	ADDQ.B #1, D1 ; We increment the current row by 1
	TRAP #15 ; Set new coordinates (next line ready)
; Instruction of type: COPIA
;Operator 1 -> 0, Store in -> 11

	MOVE.L (Variable0), D0 ; Load variable
	MOVE.L D0, (Variable11) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 12

	MOVE.L #2, D0 ; Load variable
	MOVE.L D0, (Variable12) ; Store local variable
; Instruction of type: MODULO
;Operator 1 -> 11, Operator 2 -> t12, Store in -> 13

	MOVE.L (Variable11), D0 ; Load variable
	MOVE.L (Variable12), D1 ; Load variable
	MODM D1, D0
	MOVE.L D1, (Variable13) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> 13, Store in -> 1

	MOVE.L (Variable13), D0 ; Load variable
	MOVE.L D0, (Variable1) ; Store local variable
; Instruction of type: PRINT
;, Store in -> 1

	MOVE.L (Variable1), D1 ; Load variable
	MOVE.L #3, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 0

	MOVE.L #3, D0 ; Load variable
	MOVE.L D0, (Variable0) ; Store local variable
; Instruction of type: GOTO
;, Store in -> -2

	JMP e5
; Instruction of type: SKIP
;, Store in -> -2

e1:
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 14

	LEA Variable14, A0
	MOVE.L A0, D0 ; Load variable
; Instruction of type: PRINT
;, Store in -> 14

	LEA	Variable14, A0 ; Cargar la string
	MOVE.L A0, A1 ; Ready text
	MOVE.L #14, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 1

	MOVE.L #5, D0 ; Load variable
	MOVE.L D0, (Variable1) ; Store local variable
; Instruction of type: SKIP
;, Store in -> -2

e5:
	END globals