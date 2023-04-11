<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
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
  <jsp:useBean id="workdays" scope="session" type="java.util.List<org.webproject.models.Workday>"/>
  <c:forEach var="workday" items="${workdays}">
    <li>
      <c:out value="${workday.master.firstName}"/>
      <c:out value="${workday.workStart}"/>
      <c:out value="${workday.workFinish}"/>
    </li>
  </c:forEach>
</ul>

</body>
</html>