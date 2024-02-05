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
Variable8	DC.B 'Antes bucle',0
Variable9	DS.L 1
Variable10	DS.L 1
Variable11	DS.L 1
Variable12	DC.B 'En bucle',0
Variable13	DC.B 'Despues bucle',0
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

	MOVE.L #0, D0 ; Load variable
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
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 3

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable3) ; Store local variable
; Instruction of type: SUMA
;Operator 1 -> 3, Operator 2 -> 1, Store in -> 3

	MOVE.L (Variable3), D0 ; Load variable
	MOVE.L #1, D1 ; Load variable
	ADDM D0, D1
	MOVE.L D1, (Variable3) ; Store local variable
; Instruction of type: RETURN
;Operator 1 -> 3, Operator 2 -> 97, Store in -> 0

	MOVE.L A6, A7 ; SP = BP, return to state before PMB
	MOVE.L (A7)+, A6 ; BP = old BP
	LEA DISP, A0 ; A0 = @DISP
	MOVE.L (A7)+, 4(A0) ; DISP[prof] = old value
	MOVE.L (Variable3), D5 ; Load variable
	RTS ; Return
; Instruction of type: CALL
;Operator 1 -> -2

main:                                                       
	MOVE.L A7, A6
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 7

	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable7) ; Store local variable
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
; Instruction of type: SKIP
;, Store in -> -2

e1:
; Instruction of type: IFMAYORIGUAL
;Operator 1 -> 7, Operator 2 -> 5, Store in -> 11

	MOVE.L (Variable7), D0 ; Load variable
	MOVE.L #5, D1 ; Load variable
	CMP.L D1, D0
	BGE salto0
	MOVE.L #0, D0 ; Load variable
	MOVE.L D0, (Variable11) ; Store local variable
	JMP fsalto0 ; Instruction of type: SKIP
salto0:
	MOVE.L #-1, D0 ; Load variable
	MOVE.L D0, (Variable11) ; Store local variable
; Instruction of type: SKIP
fsalto0:

; Instruction of type: IFTRUEGOTO
;Operator 1 -> 11, Operator 2 -> 0, Store in -> -2

	MOVE.L (Variable11), D0 ; Load variable
	MOVE.L #-1,D1
	CMP.L D1, D0
	BEQ e3
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 12

	LEA Variable12, A0
	MOVE.L A0, D0 ; Load variable
; Instruction of type: PRINTLN
;, Store in -> 12

	LEA	Variable12, A0 ; Cargar la string
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
; Instruction of type: PRINTLN
;, Store in -> 7

	MOVE.L (Variable7), D1 ; Load variable
	MOVE.L #3, D0 ; Prepare display
	TRAP #15
 ; Expect screen visualization
	MOVE.B #11, D0 ; Next line prepare
	MOVE.L #$00FF, D1 ; Request current coordinates
	TRAP #15 ; Get them in D1.L (HIGH=COL, LOW = ROW)
	AND.L #$00FF, D1 ; We want always column = 0
	ADDQ.B #1, D1 ; We increment the current row by 1
	TRAP #15 ; Set new coordinates (next line ready)
; Instruction of type: SUMARAPIDA
;Operator 1 -> 7, Operator 2 -> 1, Store in -> 7

	MOVE.L (Variable7), D1 ; Load variable
	ADDQ.L #1, D1
	MOVE.L D1, (Variable7) ; Store local variable
; Instruction of type: GOTO
;, Store in -> -2

	JMP e1
; Instruction of type: SKIP
;, Store in -> -2

e3:
; Instruction of type: COPIA
;Operator 1 -> -2, Store in -> 13

	LEA Variable13, A0
	MOVE.L A0, D0 ; Load variable
; Instruction of type: PRINTLN
;, Store in -> 13

	LEA	Variable13, A0 ; Cargar la string
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
	END globals