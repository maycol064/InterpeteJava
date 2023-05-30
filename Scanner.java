package mx.ipn.escom.compiladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String source;
    private final List<Token> tokens;
    public static int numLinea ;
    private final StringBuilder lexema;

    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and", TipoToken.AND);
        palabrasReservadas.put("false", TipoToken.FALSE );
        palabrasReservadas.put("for", TipoToken.FOR );
        palabrasReservadas.put("func", TipoToken.FUNC); //definir funciones
        palabrasReservadas.put("if", TipoToken.IF );
        palabrasReservadas.put("null", TipoToken.NULL);
        palabrasReservadas.put("or", TipoToken.OR);
        palabrasReservadas.put("print", TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("super", TipoToken.SUPER);
        palabrasReservadas.put("this", TipoToken.THIS);
        palabrasReservadas.put("true", TipoToken.TRUE);
        palabrasReservadas.put("var", TipoToken.VAR); //definir variables
        palabrasReservadas.put("while", TipoToken.WHILE);
    }

    Scanner(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();
        numLinea = 1;
        this.lexema = new StringBuilder();
    }

    List<Token> scanTokens() {
        int estado = 0;

        for (int i = 0; i <= source.length(); i++) {
            char caracter = caracterActual(i, source.length());
            numLinea = incremento(caracter);

            switch (estado) {
                case 0:
                    if (caracter != '\0') { //Si el caracter actual no es nulo
                        if (caracter == '(') {
                            tokens.add(new Token(TipoToken.PAR_IZQ, "(", null, numLinea));
                            estado = 0;
                            lexema.append(caracter);

                        } else if (caracter == ')') {
                            tokens.add(new Token(TipoToken.PAR_DER, ")", null, numLinea));
                            estado = 0;
                            lexema.append(caracter);

                        } else if (caracter == '{') {
                            tokens.add(new Token(TipoToken.LLA_IZQ, "{", null, numLinea));
                            estado = 0;
                            lexema.append(caracter);

                        } else if (caracter == '}') {
                            tokens.add(new Token(TipoToken.LLA_DER, "}", null, numLinea));
                            estado = 0;
                            lexema.append(caracter);

                        } else if (caracter == ',') {
                            tokens.add(new Token(TipoToken.COMA, ",", null, numLinea));
                            estado = 0;
                            lexema.append(caracter);

                        } else if (caracter == '.') {
                            tokens.add(new Token(TipoToken.PUNTO, ".", null, numLinea));
                            estado = 0;
                            lexema.append(caracter);

                        } else if (caracter == ';') {
                            tokens.add(new Token(TipoToken.PUN_COMA, ";", null, numLinea));
                            estado = 0;
                            lexema.append(caracter);

                        } else if (caracter == '-') {
                            tokens.add(new Token(TipoToken.MENOS, "-", null, numLinea));
                            estado = 0;
                            lexema.append(caracter);

                        } else if (caracter == '+') {
                            tokens.add(new Token(TipoToken.MAS, "+", null, numLinea));
                            estado = 0;
                            lexema.append(caracter);

                        } else if (caracter == '*') {
                            estado = 15;
                            lexema.append(caracter);

                        } else if (caracter == '/') {
                            estado = 1;
                            lexema.append(caracter);

                        } else if (caracter == '!') {
                            estado = 5;
                            lexema.append(caracter);

                        } else if (caracter == '=') {
                            estado = 6;
                            lexema.append(caracter);

                        } else if (caracter == '<') {
                            estado = 7;
                            lexema.append(caracter);

                        } else if (caracter == '>') {
                            estado = 8;
                            lexema.append(caracter);

                        } else if (caracter >= '0' && caracter <= '9') {
                            estado = 9;
                            lexema.append(caracter);

                        } else if (caracter >= 'a' && caracter <= 'z' || caracter >= 'A' && caracter <= 'Z') {
                            estado = 12;
                            lexema.append(caracter);

                        } else if (caracter == '"') {
                            estado = 13;
                            lexema.append(caracter);
                        }else if (caracter == ' ' || caracter == '\t' || caracter == '\n' || caracter == '\r') {
                            estado = 14;
                            lexema.append(caracter);
                        }else {
                            throw new RuntimeException("No se puede concatenar: " + caracter);
                        }
                    }
                    break;
                case 1:
                    if (caracter == '/') {
                        estado = 2;
                    } else if (caracter == '*'){
                        estado = 3;
                    }
                    else{
                        tokens.add(new Token(TipoToken.DIV, "/", null, numLinea));
                    }
                    break;
                case 2:
                    if (caracter != '\n') {
                        //tokens.add(new Token(TipoToken.COMENT,"//",null,numLinea));
                        lexema.append(caracter);
                    } else{
                        estado = 0;
                        lexema.delete(0,lexema.length());
                    }
                    break;
                case 3:
                    if (caracter == '*'){
                        estado=4;
                    }
                    else {
                        lexema.append(caracter);
                    }
                    break;
                case 4:
                    if (caracter == '/') {
                        estado = 0;
                        lexema.delete(0, lexema.length());
                    }
                    lexema.append(caracter); //COMENTARIOS
                    break;

                case 5:
                    if (caracter == '=') {
                        tokens.add(new Token(TipoToken.DIFERENTE, "!=", null, numLinea));
                        estado = 0;
                        lexema.append(caracter);
                    } else {
                        tokens.add(new Token(TipoToken.NEGACION, "!", null, numLinea));
                        estado = 0;
                    }
                    break;
                case 6:
                    if (caracter == '=') {
                        tokens.add(new Token(TipoToken.DOB_IGUAL, "==", null, numLinea));
                        estado = 0;
                        lexema.append(caracter);
                    } else {
                        tokens.add(new Token(TipoToken.IGUAL, "=", null, numLinea));
                        estado = 0;

                    }
                    break;
                case 7:
                    if (caracter == '=') {
                        tokens.add(new Token(TipoToken.MEN_IGUAL, "<=", null, numLinea));
                        estado = 0;
                        lexema.append(caracter);
                    } else {
                        tokens.add(new Token(TipoToken.MENOR, "<", null, numLinea));
                        estado = 0;
                    }
                    break;
                case 8:
                    if (caracter == '=') {
                        tokens.add(new Token(TipoToken.MAY_IGUAL, ">=", null, numLinea));
                        estado = 0;
                        lexema.append(caracter);
                    } else {
                        tokens.add(new Token(TipoToken.MAYOR, ">", null, numLinea));
                        estado = 0;
                    }
                    break;
                //NUMEROS ENTEROS Y DECIMALES
                case 9:
                    if (caracter >= '0' && caracter <= '9'){
                        lexema.append(caracter);
                        //estado = 9;
                    }
                    else if(caracter == '.'){
                        lexema.append(caracter);
                        estado = 10;
                    } else {
                        i--;
                        tokenCadenas(TipoToken.NUMERO, lexema.toString(), Integer.parseInt(lexema.toString()));
                        estado = 0;
                    }
                    break;

                case 10:
                    if (caracter >= '0' && caracter <= '9') {
                        estado = 11;
                        lexema.append(caracter);
                    } else {
                        throw new RuntimeException("No se puede concatenar: " + lexema);
                    }
                    break;

                case 11:
                    if (caracter >= '0' && caracter <= '9') {
                        lexema.append(caracter);
                    } else {
                        i--;
                        estado = 0;
                        tokenCadenas(TipoToken.NUMERO, lexema.toString(), Float.parseFloat(lexema.toString()));
                    }
                    break;

                //IDENTIFICADORES
                case 12:
                    if (caracter >= 'a' && caracter <= 'z' ||
                        caracter >= 'A' && caracter <= 'Z' ||
                        caracter >= '0' && caracter <= '9') {
                        lexema.append(caracter);
                    }
                    else {
                        i--;
                        estado = 0;
                        tokenID(TipoToken.IDENTIFICADOR, lexema.toString());
                    }
                    break;

                //CADENAS
                case 13:
                    if (caracter != '"' && caracter != '\0') {
                        lexema.append(caracter);
                    } else if (caracter == '\0') {
                        throw new RuntimeException("No se puede concatenar: " + lexema);
                    } else {
                        estado = 0;
                        lexema.append(caracter);
                        tokenCadenas(TipoToken.CADENA, lexema.toString(), lexema.substring(1, lexema.length() - 1));
                    }
                    break;
                case 14:
                    if (caracter == ' ' || caracter == '\t' || caracter == '\n' || caracter == '\r') {
                        lexema.append(caracter);
                    } else {
                        i--;
                        estado = 0;
                        lexema.delete(0, lexema.length());
                    }
                    break;

                //SUMA, RESTA, MULTIPLICACION Y DIVISION
                case 15:
                    estado = 0;

                    i--;
                    tokenID(TipoToken.MULT, lexema.toString());

                    break;
            }
        }
        tokens.add(new Token(TipoToken.EOF, "",null , this.numLinea));
        return tokens;
    }



    private void tokenID(TipoToken tipo, String lexema) {
        if (tipo == TipoToken.IDENTIFICADOR) {
            tipo = palabrasReservadas.getOrDefault(lexema, TipoToken.IDENTIFICADOR);
        }
        tokens.add(new Token(tipo, lexema, null, numLinea));
        this.lexema.delete(0, this.lexema.length());
    }

    private void tokenCadenas(TipoToken tipo, String lexema, Object literal) {
        tokens.add(new Token(tipo, lexema, literal, numLinea));
        this.lexema.delete(0, this.lexema.length());
    }

    private int incremento(char actual) {
        if (actual == '\n')
            numLinea++;
        return numLinea;
    }

    private char caracterActual(int index, int longitud) {
        if (index >= longitud) {
            return '\0';
        }
        return source.charAt(index);
    }
}

/*
Signos o símbolos del lenguaje:
(
)
{
}
,
.
;
-
+
*
/
!
!=
=
==
<
<=
>
>=
// -> comentarios (no se genera token)
/* ... * / -> comentarios (no se genera token)
Identificador,
Cadena
Numero
Cada palabra reservada tiene su nombre de token

 */
