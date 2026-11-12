<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="en_US" />
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Mi Billetera Digital</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container">
        <span class="navbar-brand fw-semibold">Mi Billetera Digital</span>
        <span class="badge text-bg-secondary">JSP + Servlet</span>
    </div>
</nav>

<div class="container pb-5">

    <!-- Saldo -->
    <div class="card shadow-sm mb-4">
        <div class="card-body">
            <div class="text-uppercase text-muted small fw-semibold">Saldo disponible</div>
            <div class="display-5 fw-bold text-success">
                S/ <fmt:formatNumber value="${billetera.saldo}" pattern="#,##0.00" />
            </div>
            <div class="text-muted small">Después de ${billetera.historial.size()} movimientos válidos</div>
        </div>
    </div>

    <!-- Historial -->
    <h2 class="h5 mb-3">Movimientos recientes</h2>
    <div class="card shadow-sm mb-4">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-dark">
                    <tr>
                        <th>Tipo</th>
                        <th>Monto (S/)</th>
                        <th>Orden</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="mov" items="${billetera.historial}" varStatus="st">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${mov.recarga}">
                                        <span class="badge text-bg-success">Recarga</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge text-bg-danger">Gasto</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="fw-semibold ${mov.recarga ? 'text-success' : 'text-danger'}">
                                ${mov.recarga ? '+' : '-'} <fmt:formatNumber value="${mov.monto}" pattern="#,##0.00" />
                            </td>
                            <td class="text-muted">${st.first ? 'Más reciente' : 'Anterior'}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty billetera.historial}">
                        <tr>
                            <td colspan="3" class="text-center text-muted py-3">Aún no hay movimientos.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Mensaje de error -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">
            <c:out value="${error}" />
        </div>
    </c:if>

    <!-- Nuevo movimiento -->
    <div class="card shadow-sm">
        <div class="card-body">
            <h2 class="h6 mb-3">Nuevo movimiento</h2>
            <form method="post" action="${pageContext.request.contextPath}/" class="row g-3 align-items-end">
                <div class="col-md-4">
                    <label for="tipo" class="form-label">Tipo</label>
                    <select id="tipo" name="tipo" class="form-select">
                        <option value="Recarga">Recarga</option>
                        <option value="Gasto">Gasto</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <label for="monto" class="form-label">Monto (S/)</label>
                    <input type="text" id="monto" name="monto" class="form-control" placeholder="0.00" autocomplete="off">
                </div>
                <div class="col-md-4">
                    <button type="submit" class="btn btn-primary w-100">Registrar movimiento</button>
                </div>
            </form>
        </div>
    </div>

</div>
</body>
</html>
