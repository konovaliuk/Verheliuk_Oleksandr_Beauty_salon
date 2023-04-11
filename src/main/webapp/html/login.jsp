<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Login</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<c:if test="${empty sessionScope.user}">
  <jsp:include page="/html/navbarnotloginned.jsp"/>
</c:if>
<c:if test="${not empty sessionScope.user}">
  <jsp:include page="/html/navbarloginned.jsp"/>
</c:if>

<div class="container">
  <div class="row mt-5 justify-content-center">
    <div class="col-lg-6">
      <div class="card">
        <div class="card-header">
          <h3 class="card-title">Login</h3>
        </div>
        <div class="card-body">
          <form method="post" action="/html/login?command=login">
            <div class="mb-3">
              <label for="login" class="form-label">Username:</label>
              <input type="text" class="form-control" id="login" name="login" autocomplete="off"
                     required>
            </div>
            <div class="mb-3">
              <label for="password" class="form-label">Password:</label>
              <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="d-grid gap-2">
              <button type="submit" class="btn btn-primary">Log in</button>
            </div>
          </form>
          <c:if test="${not empty requestScope.error}">
            <div class="alert alert-danger mt-3" role="alert">
              <c:out value="${requestScope.error}"/>
            </div>
          </c:if>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>