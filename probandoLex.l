%{
    #include <stdio.h>   
%}

%%
[0-9]+  {printf("Number \n");}
[a-zA-Z][a-zA-Z0-9]*    {printf("Word\n");}
%%

int main(){
yylex();
}