<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Medecin - Les Diagnostics</title>
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
                <li><span id="menu-1"><a href="<c:url value="/panelMedecin/medecin.jsp"/>">Gestion des consultations</a> </span></li>
                <li><span id="menu-1"><a href="<c:url value="/panelMedecin/medecin2.jsp"/>"><b>Les diagnostics (résultats de consultation)</b></a></span></li>
                <li><span id="menu-1"><a href="<c:url value="/panelMedecin/medecin3.jsp"/>">Prescriptions</a></span></li>
                <li><span id="menu-1"><a href="<c:url value="/panelMedecin/medecin4.jsp"/>">Chercher un medecin</a></span></li>            </ul>
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
					<input name ="bouton_charger" disabled value ="VOIR CONSULTATIONS DE ${sessionScope.patientSelection.nom}" class="bouton-visualiser-consultation" id="btn-chargement" type ="submit" onclick="actionPost('Consultation',2);" />
					<input type="text" hidden=true name="id-selected" id="id-selected" value="" />
						
					<div id="consultation-liste">
						<c:if test="${not empty sessionScope.mapConsulDiagPatientMedecin }">
					        <table class="liste modif-table-consultation">
								<tr> 
									<th colspan=2>Consultations</th>
								</tr>
			
								<c:forEach items="${ sessionScope.mapConsulDiagPatientMedecin }" var="mapConsultation" varStatus="status">
								    <tr id="${status.count}consultation"  onclick="remplirDiagnostic('${mapConsultation.value.idDiagnostic}','${mapConsultation.value.descriptifDiagnostic}','${mapConsultation.value.allergiesDiagnostic}','${mapConsultation.value.antecedentsMedicauxDiagnostic}', '${mapConsultation.key.dateConsultation}','${status.count}consultation', '${ sessionScope.mapConsulDiagPatientMedecin.size() }' );" > 
									    <td> 
									    	<fmt:formatDate value="${mapConsultation.key.dateConsultation }" pattern="EEEE dd MMMM yyyy" dateStyle = "long"/> 
									    </td>
									    <td> 
									    <fmt:formatDate value="${mapConsultation.key.heureConsultation }" pattern="HH"/>h<fmt:formatDate value="${mapConsultation.key.heureConsultation }" pattern="mm"/> 
									    </td> 
								    </tr>
								</c:forEach>
							</table> 		
							<p> cliquez sur une consultation pour charger les info du diagnostic</p>
						</c:if>	
						
						<c:if test="${ empty sessionScope.mapConsulDiagPatientMedecin }">
						<p> ...il n'y a aucune consultation </p>
						</c:if>
					</div>
				</div>
					            
	            <div class="redefinition-droite">		       		
		       		<div>
		       			<fieldset>Descriptif du diagnostic médical : </fieldset>	       		
		       			<textarea id="descriptif-diagnostic" name="descriptif-diagnostic" cols="70" rows="1"></textarea>
		       		</div>		       		
		       		<div>
		       			<fieldset>Allergies :</fieldset>
		       			<textarea id="allergie" name="allergie" cols="70" rows="1"></textarea>	       			
		       		</div>			
					<div id="complement-diagnostic">
						<div>
		       				<fieldset>Antécédents médicaux :</fieldset>
		       				<textarea id="antecedent" name="antecedent" cols="40" rows="1"></textarea>
						</div>
						<div class="bloc-date">
		       				<fieldset>Date du diagnostic : </fieldset>
		       				<input type="date" id="date-diagnostic" name="date-diagnostic" disabled  value=""/>
		       				<div class="bouton-modifier-diagnostic">
		       				<input name = "bouton_modifier" hidden=true value ="MODIFIER DIAGNOSTIC" class="bouton-modifier-consultation" id="btn-modif-diagnostic" type ="submit" onclick="actionPost('modifierDiagnostic');" />	       						       				
		       				</div>
						</div>
	                    <span> <input type="hidden" id="id-diagnostic-clique" name="id-diagnostic-clique" value="" ></span>					
					</div>
	                 <!--  Affichage succès ou erreur  -->   
		           	 <span class="succes" ><c:out value="${diagnosticForm.resultat}"/></span>      
		             <c:forEach items="${diagnosticForm.erreurs}" var="erreur">
		             	<ul class="erreur">
		             		<li><c:out value="${erreur.value}"/> </li>
		             	</ul>
		             </c:forEach>  					
    
	          	</div>         	
            </form>            
        </div>
        
        <!-- ********************* SCRIPT JAVASCRIPT ********************* -->
        
        
		<script type="text/javascript">
		
			/*	
				Objectif : Un clic sur la liste permet de cacher la liste des consultations
				et de faire apparaitre le bouton de chargement de la liste des consultations avec le nom de l'utilisateur lié au clic.
			*/
			function remplirInput(id) {
		
				document.getElementById("id-selected").value = id;
				
				document.getElementById("consultation-liste").style.visibility="hidden";
				document.getElementById("btn-modif-diagnostic").hidden = true;
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
				Objectif : Le clic sur la ligne du tableau Consultation permet de charger les informations
				du diagnostic dans les champs appropriés.
			*/
			function remplirDiagnostic(idDiagnostic,descriptif,allergie,antedecent, date, idLigne, totalLigne){
			
				 
				var elementDescriptifDiagnostic = document.getElementById("descriptif-diagnostic");
				var elementAllergie = document.getElementById("allergie");
				var elementAntecedent = document.getElementById("antecedent");
				var elementDateDiagnostic = document.getElementById("date-diagnostic");
				var elementBoutonDiagnostic = document.getElementById("btn-modif-diagnostic");
				
				document.getElementById("id-diagnostic-clique").value = idDiagnostic;

				var increm = 0;
				
				while(increm < totalLigne){
					document.getElementById((increm+1)+"consultation").style.color = "black";
					increm++;
				}
				
				var ligneTableau = document.getElementById(idLigne);
				
				ligneTableau.style.color = "#e6893f";
				
				//la date de saisie du diagnostic ne peut pas être inférieur à la date de la consultation.
				elementDateDiagnostic.setAttribute("min", date); 
				elementBoutonDiagnostic.hidden = false;
				if(idDiagnostic == 0 || descriptif == ""  ){
					elementDescriptifDiagnostic.placeholder = "aucune information";
					elementAllergie.placeholder = "aucune information";
					elementAntecedent.placeholder = "aucune information";
					elementDateDiagnostic.value = date;				
					elementBoutonDiagnostic.value = "AJOUTER DIAGNOSTIC";

				} else {
					elementDescriptifDiagnostic.textContent = descriptif;
					elementAllergie.textContent = allergie;
					elementAntecedent.textContent = antedecent;
					elementDateDiagnostic.value = date;	
					elementBoutonDiagnostic.value = "MODIFIER DIAGNOSTIC";

				} 
			}
			
				
		</script>
        
    </body>