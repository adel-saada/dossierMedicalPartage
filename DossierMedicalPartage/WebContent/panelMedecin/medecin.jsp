<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Medecin - gestion des consultations</title>
        <link href='http://fonts.googleapis.com/css?family=Droid+Sans:400,700' rel='stylesheet'>
        <link rel="stylesheet" href="<c:url value="/inc/main.css"/>">
        <link rel="stylesheet" href="<c:url value="/inc/commun.css"/>">
        <link rel="stylesheet" href="<c:url value="/inc/medecin.css"/>">
    </head>

    <body>
        <div class="en-tete">
            <div class="titre-header">
                <h1>Toubib</h1> 
            </div>     
            <div class="image-header">  
                <img src="<c:url value="/inc/icones/scientist64.png"/>" alt="logo medecin">
            </div>
            <div class="bouton-deconnexion">
				<form action="<c:url value="/Deconnexion"/>">
					<input src="<c:url value="/inc/icones/disconnected.png"/> " type=image Value=submit >
				</form> 
            </div>
        </div>
        <nav class="menu-gestion">
            <ul >
                <li><span id="menu-1"><a href="<c:url value="/panelMedecin/medecin.jsp"/>"><b>Gestion des consultations</b></a> </span></li>
                <li><span id="menu-1"><a href="<c:url value="/panelMedecin/medecin2.jsp"/>">Les diagnostics (résultats de consultation)</a></span></li>
                <li><span id="menu-1"><a href="<c:url value="/panelMedecin/medecin3.jsp"/>">Prescriptions</a></span></li>
                <li><span id="menu-1"><a href="<c:url value="/panelMedecin/medecin4.jsp"/>">Chercher un medecin</a></span></li>
            </ul>
        </nav>

        <div id="content">
        
            <div>
            	<input class="champ-recherche" type="search" placeholder="rechercher..."/>
                <input class="bouton-recherche" type="button" value="rechercher" />
            </div> 
            <br> 
            <h2 class="titre-liste">Liste des Patients
            <span class="info-connexion"> Medecin : <c:out value="${sessionScope.sessionMedecin.login}"/> </span>
            </h2>
             <div class="liste-contener">
			<table class="liste">
				<tr> 
					<th>numeroSS </th>
					<th>nom</th>
					<th>prenom</th>
					<th>telephone</th>
					<th>sexe</th>
					<th>email</th>
					<th>rue </th>
					<th>ville</th>
					<th>codePostal</th>
				</tr>
				<c:forEach items="${ sessionScope.listePatient }" var="patient" varStatus="status">
				    <tr id="<c:out value="${ patient.id }id"/>" onclick="remplirInput('${ patient.id }');">
					    <td id="<c:out value="${ patient.id }numeroSS" />"> <c:out value="${ patient.numeroSS }" /> </td> 
					    <td id="<c:out value="${ patient.id }nom" />"> <c:out value="${ patient.nom }" /> </td> 
					    <td id="<c:out value="${ patient.id }prenom" />"> <c:out value="${ patient.prenom }" /> </td>
					    <td id="<c:out value="${ patient.id }tel" />"> <c:out value="${ patient.tel }" /> </td>
					    <td id="<c:out value="${ patient.id }sexe" />"> <c:out value="${ patient.sexe }" /> </td>
					    <td id="<c:out value="${ patient.id }email" />"> <c:out value="${ patient.email }" /> </td>
					    <td id="<c:out value="${ patient.id }rue" />"> <c:out value="${ patient.rue }" /> </td>				    
					    <td id="<c:out value="${ patient.id }ville" />"> <c:out value="${ patient.ville }" /> </td> 
					    <td id="<c:out value="${ patient.id }codePostal" />"> <c:out value="${ patient.codePostal }" /> </td> 
				    </tr>
				</c:forEach>
			</table> 		
            </div>
            
            <form action="<c:url value="/PanelMedecin"/>" method="POST" id="medecin-form">					
	           	<div class="redefinition-gauche" >
				<input name ="bouton_charger" disabled value ="VOIR CONSULTATIONS DE ${sessionScope.patientSelection.nom}" class="bouton-visualiser-consultation" id="btn-chargement" type ="submit" onclick="actionPost('Consultation',1);" />
				<input type="text" hidden=true name="id-selected" id="id-selected" value="" />
					
				<div id="consultation-liste">
					<c:if test="${not empty sessionScope.mapConsulDiagPatientMedecin }">
					        <table class="liste">
								<tr> 
									<th colspan=2>Consultations</th>
								</tr>
			
								<c:forEach items="${ sessionScope.mapConsulDiagPatientMedecin }" var="mapConsultation" varStatus="status">
								    <tr  id="${status.count}consultation" onclick="ajouterDate('<fmt:formatDate value="${mapConsultation.key.dateConsultation }" pattern="EEEE dd MMMM yyyy" dateStyle = "long"/>','<fmt:formatDate value="${mapConsultation.key.heureConsultation }" pattern="HH"/>h<fmt:formatDate value="${mapConsultation.key.heureConsultation }" pattern="mm"/>', '${status.count}consultation', '${ sessionScope.mapConsulDiagPatientMedecin.size()}' );" > 
									    <td> 
									    	<fmt:formatDate value="${mapConsultation.key.dateConsultation }" pattern="EEEE dd MMMM yyyy" dateStyle = "long"/> 
									    </td>
									    <td> 
									    <fmt:formatDate value="${mapConsultation.key.heureConsultation }" pattern="HH"/>h<fmt:formatDate value="${mapConsultation.key.heureConsultation }" pattern="mm"/> 
									    </td> 
								    </tr>
								</c:forEach>
							</table> 			
					</c:if>	
					
						<c:if test="${ empty sessionScope.mapConsulDiagPatientMedecin }">
						<p> ...il n'y a aucune consultation </p>
						</c:if>
	
				</div>
				
	            </div>
	            
	            <div class=" redefinition-droite">
	            	<fieldset>Fiche Informations Patient:</fieldset>
		       		<div class="gauche reajustement-modif-medecin">   
		       			<c:if test="${not empty sessionScope.patientSelection.nom}">
			       			<p class="label-info-medecin">
			                    <span id="nom-patient"> <c:out value="${sessionScope.patientSelection.nom}"/></span>   
			                    <span id="prenom-patient"> <c:out value="${sessionScope.patientSelection.prenom}"/> </span>	            	       			
			       			</p>
			       			<p class="label-info-medecin">
			                    <span id="numeroSS"> <c:out value="${sessionScope.patientSelection.numeroSS}"/>	</span>            
			       			</p>
			       			<p class="label-info-medecin">
			                    <span id="adresse-patient"> <c:out value="${sessionScope.patientSelection.rue}"/> </span>  	
			                    <span id="cp-patient"> <c:out value="${sessionScope.patientSelection.codePostal}"/> </span>
			                    <span id="ville-patient"> <c:out value="${sessionScope.patientSelection.ville}"/> </span>
			       			</p>
			       			<p class="label-info-medecin">
			                    <span id="tel-patient"> <c:out value="${sessionScope.patientSelection.tel}"/> </span>                          
			       			</p>
			       			<p class="label-info-medecin">
			                    <span id="email-patient"> <c:out value="${sessionScope.patientSelection.email}"/> </span>
			       			</p>		       			       							
		       			</c:if>
		       			<c:if test="${empty sessionScope.patientSelection.nom}">
			       			<p class="label-info-medecin">
			                    <span id="nom-patient"> <c:out value="${sessionScope.listePatient[0].nom}"/></span>   
			                    <span id="prenom-patient"> <c:out value="${sessionScope.listePatient[0].prenom}"/> </span>	            	       			
			       			</p>
			       			<p class="label-info-medecin">
			                    <span id="numeroSS"> <c:out value="${sessionScope.listePatient[0].numeroSS}"/>	</span>            
			       			</p>
			       			<p class="label-info-medecin">
			                    <span id="adresse-patient"> <c:out value="${sessionScope.listePatient[0].rue}"/> </span>  	
			                    <span id="cp-patient"> <c:out value="${sessionScope.listePatient[0].codePostal}"/> </span>
			                    <span id="ville-patient"> <c:out value="${sessionScope.listePatient[0].ville}"/> </span>
			       			</p>
			       			<p class="label-info-medecin">
			                    <span id="tel-patient"> <c:out value="${sessionScope.listePatient[0].tel}"/> </span>                          
			       			</p>
			       			<p class="label-info-medecin">
			                    <span id="email-patient"> <c:out value="${sessionScope.listePatient[0].email}"/> </span>
			       			</p>		       			       							
		       			</c:if>		       			
		          	</div> 		
					<div>
		       			<p>
		       				<b><span id="date-consultation-patient"></span></b>
		       			</p>
					</div>								
	          	</div>
            </form>
            
        </div>
        
        <!-- ********************* SCRIPT JAVASCRIPT ********************* -->
        
        
		<script type="text/javascript">
		
			/*	
				Objectif : Permettre de remplir les champs input en cliquant sur une ligne du tableau.
				précondition : l'id de la ligne
			*/
			function remplirInput(id) {
					
				document.getElementById("numeroSS").textContent = document.getElementById(id+"numeroSS").textContent;
				document.getElementById("nom-patient").textContent = document.getElementById(id+"nom").textContent;
				document.getElementById("email-patient").textContent = document.getElementById(id+"email").textContent;
				document.getElementById("cp-patient").textContent = document.getElementById(id+"codePostal").textContent;
				document.getElementById("tel-patient").textContent = document.getElementById(id+"tel").textContent;
				document.getElementById("prenom-patient").textContent = document.getElementById(id+"prenom").textContent;
				document.getElementById("adresse-patient").textContent = document.getElementById(id+"rue").textContent;
				document.getElementById("ville-patient").textContent = document.getElementById(id+"ville").textContent;
		
				document.getElementById("id-selected").value = id;
				
				document.getElementById("consultation-liste").style.visibility="hidden";
				document.getElementById("date-consultation-patient").style.visibility="hidden";

				document.getElementById("btn-chargement").disabled = false;
				document.getElementById("btn-chargement").value = "VOIR CONSULTATIONS DE "+document.getElementById(id+"nom").textContent;
			}
			
			/*-----------------------------------------------------------------------------*/

			/*
				Objectif : ajoute deux paramètres sur l'url pour que la servlet distingue l'origine du bouton
				(le type d'action et la page)
			*/
			function actionPost(action, numeroPage){
				
				var form = document.getElementById("medecin-form");
				form.action = "<c:url value='/PanelMedecin?action="+action+"&&page="+numeroPage+"'/>  ";
			}
			
			
			/*-----------------------------------------------------------------------------*/
			
			/*
				Ajout une date dans la fiche information
				+ colorise la ligne selectionnée du tableau consultation.
			*/
			function ajouterDate(date, heure,idLigne, totalLigne) {
				
				var increm = 0;				
				while(increm < totalLigne){
					document.getElementById((increm+1)+"consultation").style.color = "black";
					increm++;
				}			
				var ligneTableau = document.getElementById(idLigne);
				ligneTableau.style.color = "#e6893f";

				document.getElementById("date-consultation-patient").textContent = "Date de consultation : "+date+" "+heure;
			}
		</script>
        
    </body>