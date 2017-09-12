<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> 

<% 
	String redirectURL = request.getContextPath() + "/facebookAuth"; 
    response.sendRedirect(redirectURL);
%> 