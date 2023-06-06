<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>My Beauty Salon</title>
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
  <div class="jumbotron text-center">
    <h1>Welcome to Schedule!</h1>
    <p>There is you can see workdays on this week.</p>
  </div>
</div>

<p> Workdays in our Beauty Salon: </p>
<ul>
  <div>
    <table class="table table-striped">
      <thead>
      <tr>
        <th>Master Name</th>
        <th>Workday start</th>
        <th>Workday finish</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${listWorkday}" var="workday">
        <tr>
          <td>${workday.master.firstName}</td>
          <td>${workday.workStart}</td>
          <td>${workday.workFinish}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    <div class="d-flex justify-content-center">
      <nav>
        <ul class="pagination">
          <c:choose>
            <c:when test="${page > 1}">
              <li class="page-item">
                <a class="page-link" href="${page - 1}" tabindex="-1">Previous</a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">Previous</a>
              </li>
            </c:otherwise>
          </c:choose>

          <c:if test="${startPage > 1}">
            <li class="page-item disabled">
              <a class="page-link" href="#">...</a>
            </li>
          </c:if>

          <c:forEach begin="${startPage}" end="${endPage}" var="i">
            <c:choose>
              <c:when test="${i == page}">
                <li class="page-item active"><a class="page-link" href="${i}">${i}</a></li>
              </c:when>
              <c:otherwise>
                <li class="page-item"><a class="page-link" href="${i}">${i}</a></li>
              </c:otherwise>
            </c:choose>
          </c:forEach>

          <c:if test="${endPage < totalPages}">
            <li class="page-item disabled">
              <a class="page-link" href="#">...</a>
            </li>
          </c:if>

          <c:choose>
            <c:when test="${page < totalPages}">
              <li class="page-item">
                <a class="page-link" href="${page + 1}">Next</a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="page-item disabled">
                <a class="page-link" href="#">Next</a>
              </li>
            </c:otherwise>
          </c:choose>
        </ul>
      </nav>
    </div>
  </div>
</ul>

</body>
</html>