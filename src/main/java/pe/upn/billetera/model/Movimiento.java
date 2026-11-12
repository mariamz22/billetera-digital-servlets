package pe.upn.billetera.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Un movimiento valido de la billetera: tipo (Recarga o Gasto) y monto.
 */
public class Movimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String RECARGA = "Recarga";
    public static final String GASTO = "Gasto";

    private final String tipo;
    private final BigDecimal monto;

    public Movimiento(String tipo, BigDecimal monto) {
        this.tipo = tipo;
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public boolean isRecarga() {
        return RECARGA.equals(tipo);
    }
}
