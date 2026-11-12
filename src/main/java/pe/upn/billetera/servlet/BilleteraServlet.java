package pe.upn.billetera.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import pe.upn.billetera.model.Billetera;
import pe.upn.billetera.model.Movimiento;

/**
 * Servlet controlador.
 *  - GET : muestra saldo e historial (forward a la vista JSP).
 *  - POST: valida y procesa un nuevo movimiento (Recarga / Gasto).
 */
@WebServlet("")
public class BilleteraServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String ATTR_BILLETERA = "billetera";
    private static final String VISTA = "/WEB-INF/views/billetera.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        obtenerBilletera(request);
        request.getRequestDispatcher(VISTA).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Billetera billetera = obtenerBilletera(request);

        String tipo = request.getParameter("tipo");
        String montoTxt = request.getParameter("monto");

        String error = procesar(billetera, tipo, montoTxt);
        if (error != null) {
            request.setAttribute("error", error);
        }
        request.getRequestDispatcher(VISTA).forward(request, response);
    }

    /** Devuelve la billetera de la sesion; la crea (S/ 250.00, historial vacio) si no existe. */
    private Billetera obtenerBilletera(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Billetera billetera = (Billetera) session.getAttribute(ATTR_BILLETERA);
        if (billetera == null) {
            billetera = new Billetera();
            session.setAttribute(ATTR_BILLETERA, billetera);
        }
        return billetera;
    }

    /**
     * Valida y aplica el movimiento.
     * @return mensaje de error, o null si el movimiento fue valido.
     *         Si hay error no se modifica ni el saldo ni el historial.
     */
    private String procesar(Billetera billetera, String tipo, String montoTxt) {
        if (!Movimiento.RECARGA.equals(tipo) && !Movimiento.GASTO.equals(tipo)) {
            return "Seleccione un tipo de movimiento valido.";
        }

        if (montoTxt == null || montoTxt.trim().isEmpty()) {
            return "Ingrese un monto.";
        }

        BigDecimal monto;
        try {
            monto = new BigDecimal(montoTxt.trim()).setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            return "El monto debe ser un valor numerico.";
        }

        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            return "El monto debe ser mayor que cero.";
        }

        if (Movimiento.GASTO.equals(tipo)) {
            if (monto.compareTo(billetera.getSaldo()) > 0) {
                return "Saldo insuficiente.";
            }
            billetera.gastar(monto);
        } else {
            billetera.recargar(monto);
        }
        return null;
    }
}
