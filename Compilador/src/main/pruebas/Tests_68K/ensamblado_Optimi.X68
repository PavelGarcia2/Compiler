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
Variable5	DC.B 'Menor',0
Variable6	DS.L 1
Variable7	DS.L 1
Variable8	DS.L 1
Variable9	DC.B 'Mayor',0
Variable10	DC.B 'Iguales',0
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

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable0) ; Store local variable
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 1

	MOVE.L #1, D0 ; Load variable
	MOVE.L D0, (Variable1) ; Store local variable
; Instruction of type: CALL
;Operator 1 -> -2

	JMP main ; Declared all globals
main:                                                       
	MOVE.L A7, A6
; Instruction of type: IFMAYORIGUAL
;Operator 1 -> 0, Operator 2 -> t1, Store in -> 4

	MOVE.L (Variable0), D0 ; Load variable
	MOVE.L (Variable1), D1 ; Load variable
	CMP.L D1, D0
	BGE salto0
	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable4) ; Store local variable
	JMP fsalto0 ; Instruction of type: SKIP
salto0:
	MOVE.L #-1, D0 ; Load variable
	MOVE.L D0, (Variable4) ; Store local variable
; Instruction of type: SKIP
fsalto0:

; Instruction of type: IFTRUEGOTO
;Operator 1 -> 4, Operator 2 -> 0, Store in -> -2

	MOVE.L (Variable4), D0 ; Load variable
	MOVE.L #-1,D1
	CMP.L D1, D0
	BEQ e1
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 5

	LEA Variable5, A0
	MOVE.L A0, D0 ; Load variable
; Instruction of type: PRINTLN
;, Store in -> 5

	LEA	Variable5, A0 ; Cargar la string
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

	JMP e2
; Instruction of type: SKIP
;, Store in -> -2

e1:
; Instruction of type: IFMENORIGUAL
;Operator 1 -> 0, Operator 2 -> t1, Store in -> 8

	MOVE.L (Variable0), D0 ; Load variable
	MOVE.L (Variable1), D1 ; Load variable
	CMP.L D1, D0
	BLE salto1
	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable8) ; Store local variable
	JMP fsalto1 ; Instruction of type: SKIP
salto1:
	MOVE.L #-1, D0 ; Load variable
	MOVE.L D0, (Variable8) ; Store local variable
; Instruction of type: SKIP
fsalto1:

; Instruction of type: IFTRUEGOTO
;Operator 1 -> 8, Operator 2 -> 0, Store in -> -2

	MOVE.L (Variable8), D0 ; Load variable
	MOVE.L #-1,D1
	CMP.L D1, D0
	BEQ e4
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

	JMP e2
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
; Instruction of type: SKIP
;, Store in -> -2

e2:
	END globals