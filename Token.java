package mx.ipn.escom.compiladores;
public class Token {

    final TipoToken tipo;
    final String lexema;
    final Object literal;
    final int numLinea; //Numero de Linea

    public Token(TipoToken tipo, String lexema, Object literal, int numLinea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.numLinea = numLinea;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Token)) {
            return false;
        }

        return this.tipo == ((Token) obj).tipo;
    }

    @Override
    public String toString(){
        return "Linea [" + numLinea + "]: " + tipo + " " + lexema + " " + literal;
    }
}
