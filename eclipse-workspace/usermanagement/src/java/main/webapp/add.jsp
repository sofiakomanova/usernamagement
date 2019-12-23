<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="user" class="main.java.ua.nure.kn.komanova.usermanagement.User" scope="session"></jsp:useBean>
<html>
<head><title>User management. Add</title></head>
<body>
	<form action="<%=request.getContextPath()%>/add" method="post">
		<input type="hidden" name="id" value="${user.id}">
		First name <input type="text" name="firstName" value=""><br>
		Last name <input type="text" name="lastName" value=""><br>
		Date of birth <input type="text" name="date" value=""><br>
		<input type="submit" name="okButton" value="Ok">
		<input type="submit" name="cancelButton" value="Cancel">
	</form>
	<c:if test='${request.Scope.error != null}'>
	<script type="text/javascript">
		alert('${request.Scope.error}');
	</script>
	</c:if>
</body>
</html>