<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container">
	<br>
	<br>
	<br>
		<div class="starter-template">
			<c:if test="${exception ne null}">
				<div>${exception.message}</div><br>
			</c:if>
			<c:if test="${logoutMessage ne null}">
				<div>${logoutMessage}</div><br>
			</c:if>
			
			<form action="/doLogin" method="post">
				<div>
					<label> Email : <input type="text" name="email" />
					</label>
				</div>
				<div>
					<label> Password: <input type="password" name="password" />
					</label>
				</div>
				<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
				<div>
					<input type="submit" value="Sign In" />
				</div>
			</form>
			
			<br><br>
			
			<form action="/connect/facebook" method="POST">
				<input type="hidden" name="scope" value="user_posts,email" /> 
				<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
				<p><button type="submit">Login using Facebook Account</button></p>
			</form>
			
		</div>

</div>