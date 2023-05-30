package mx.ipn.escom.compiladores;
public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)


    //PALABRAS RESERVADAS
    AND, FALSE, FOR, FUNC, IF,
    NULL, OR, PRINT, RETURN, SUPER,
    THIS,TRUE, VAR, WHILE,

    // TIPO TOKEN ID, CADENAS Y NUMEROS
    IDENTIFICADOR, CADENA, NUMERO,

    //SIGNO DEL LENGUAJE
    PAR_IZQ, PAR_DER, LLA_IZQ, LLA_DER, COMA, PUNTO,
    PUN_COMA, MENOS, MAS, MULT, DIV, NEGACION,  DIFERENTE, IGUAL,
    DOB_IGUAL, MENOR, MEN_IGUAL, MAYOR, MAY_IGUAL,

    //FINAL DE CADENA
    EOF
}
