<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Connexion</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/connexion.css"/>" />
		<link href='http://fonts.googleapis.com/css?family=Droid+Sans:400,700' rel='stylesheet'>
    </head>
	<body>
	
		<div class="en-tete">
			<div class="logo">
				<h1>Toubib</h1>			
				<img src="<c:url value="/inc/icones/scientist.png"/>" alt="logo medecin">
			</div>
		</div>
		
		<div id="content">
			<form action="<c:url value="/Connexion"/>" method="POST" id="login-form">
				<fieldset>
					<p>
						<label for="login-username">identifiant</label>
						<input type="text" id="login-username" name="login-username" value="<c:out value="${admin.login}"/>" class="round full-width-input" placeholder="identifiant" autofocus />
					</p>
					<p>
						<label for="login-password">mot de passe</label>
						<input type="password" id="login-password" name="login-password" value=""  class="round full-width-input" placeholder="mot de passe" />
					</p>
					
					<p class="center">
					    <label for="admin-radio" class="radio-inline">					    
						<input type="radio" id="admin-radio" name="login-radio" value="admin" <c:if test="${categorieLogin}"> checked </c:if> >Admin
						</label>
					
					    <label for="patient-radio" class="radio-inline">
					    <input type="radio" id="patient-radio" name="login-radio" value="patient" <c:if test="${categorieLogin}"> checked </c:if> >Patient
					    </label>
					    					
					    <label for="medecin-radio" class="radio-inline">
					    <input type="radio" id="medecin-radio" name="login-radio" value="medecin" <c:if test="${categorieLogin}"> checked </c:if> >Médecin
					    </label>
					    
					</p>
					
					<p>J'ai <a href="#">oublié mon mot de passe</a>.</p>
					
					
					<div class="bouton-submit">
						<input type="submit" value="connexion" />
					</div>
					
					<p class="center">
						<span class="${empty adminForm.erreurs ? 'succes' : 'erreur'}"><c:out value="${adminForm.resultat}"/></span>
						<span class="${empty patientForm.erreurs ? 'succes' : 'erreur'}"><c:out value="${patientForm.resultat}"/></span>
						<span class="${empty medecinForm.erreurs ? 'succes' : 'erreur'}"><c:out value="${medecinForm.resultat}"/></span>
		               	<span class="erreur"><c:out value="${erreurRadio}"/> </span>
	               	</p>
				</fieldset>
			</form>
		</div> 
		
		<!--  à supprimer, sert juste d'affichage pour les test -->
		<div>
			<p>admin : pass </p>
			<p>medecin9 : FIP49XEB1DC</p>
			<p>login80 : IXP89CAF2XF</p>
		</div>
	
	</body>
</html>