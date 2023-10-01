# Compiler
Compilador del lenguaje x para la asignatura de compiladores

## Installation

### Instalar flex 
En Ubuntu:
```bash
sudo apt-get update
sudo apt-get install flex
```
En arch-linux:
```bash
sudo pacman -S flex
```
## Usage

```bash
lex ficheroLex.l
##Esto genera un archivo lex.yy.c que se puede añadir al proyecto o se puede ejecutar utilizando 
gcc -lfl lex.yy.cc
##No se que es lo de -lfl pero si no falla y luego ejecutamos el fichero a.out que se ha generado
\a.out
##y listo

```
