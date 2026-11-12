package pe.upn.billetera.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Estado de la billetera (saldo + historial). Se guarda en la HttpSession.
 * El historial mantiene el movimiento mas reciente en la posicion 0.
 */
public class Billetera implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal saldo = new BigDecimal("250.00");
    private final List<Movimiento> historial = new ArrayList<>();

    public BigDecimal getSaldo() {
        return saldo;
    }

    public List<Movimiento> getHistorial() {
        return historial;
    }

    public void recargar(BigDecimal monto) {
        saldo = saldo.add(monto);
        historial.add(0, new Movimiento(Movimiento.RECARGA, monto));
    }

    public void gastar(BigDecimal monto) {
        saldo = saldo.subtract(monto);
        historial.add(0, new Movimiento(Movimiento.GASTO, monto));
    }
}
