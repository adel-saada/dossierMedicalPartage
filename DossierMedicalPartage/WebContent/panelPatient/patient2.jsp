<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Patient - Mes consultations</title>
        <link href='http://fonts.googleapis.com/css?family=Droid+Sans:400,700' rel='stylesheet'>
        <link rel="stylesheet" href="<c:url value="/inc/main.css"/>">
        <link rel="stylesheet" href="<c:url value="/inc/commun.css"/>">
        <link rel="stylesheet" href="<c:url value="/inc/patient.css"/>">

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
                <li><span id="menu-1"><a href="<c:url value="/panelPatient/patient.jsp"/>">Reservation des consultations</a> </span></li>
                <li><span id="menu-1"><a href="<c:url value="/panelPatient/patient2.jsp"/>"><b>Mes consultations</b></a></span></li>
                <li><span id="menu-1"><a href="<c:url value="/panelPatient/patient3.jsp"/>"> Afficher mon dossier</a></span></li>
            </ul>
        </nav>
        <div id="content">
            <br>
            <p class="info-connexion"> Patient : <c:out value="${sessionScope.sessionPatient.login}"/> </p>
            
   			<div class="consultation-bloc">
   				<div>
	                 <!--  Affichage succès ou erreur  -->        
		           	 <span class="succes" ><c:out value="${consultationForm.resultat}"/></span>      
		             <c:forEach items="${consultationForm.erreurs}" var="erreur">
		             	<ul class="erreur">
		             		<li><c:out value="${erreur.value}"/> </li>
		             	</ul>
		             </c:forEach> 	 								   				
   				</div>
				<div class="consultation-patient">
					<div class="en-tete-consultation">
						<img  src="<c:url value="/inc/icones/rdv.png"/>" alt="logo medecin">														
						<h2>Rendez vous confirmé </h2>
						<hr>
					</div>					
					
					<c:if test="${empty sessionScope.mapConsultationsMedecinsPourUnPatient }">
					<p> ...il n'y a aucun rendez vous </p>
					</c:if>
					
                	<form method="POST" id="patient-form" action="<c:url value="/PanelPatient"/>" >					
						<input type="text" hidden=true name="id-selected" id="id-selected" value="1"/>	
						<c:forEach items="${sessionScope.mapConsultationsMedecinsPourUnPatient}" var="mapConsultation" varStatus="status">
								<div class="consultation" id=<c:out value="${status.count}consultation"/>   >
									<span class="nombre-consultation">
										<c:out value="#${status.count}"/>
									</span>
									<img  src="<c:url value="/inc/icones/medecin-rdv.png"/>" alt="logo medecin">
									<p>Dr <c:out value="${mapConsultation.key.nom }" /> <c:out value="${mapConsultation.key.prenom }" /> </p>
									<p> <c:out value="${mapConsultation.key.specialite }" /> </p>
									<p class="date-importante"><fmt:formatDate value="${mapConsultation.value.dateConsultation }" pattern="EEEE dd MMMM yyyy" dateStyle = "long"/> à <fmt:formatDate value="${mapConsultation.value.heureConsultation }" pattern="HH"/>h<fmt:formatDate value="${mapConsultation.value.heureConsultation }" pattern="mm"/>   </p>
	<%-- 								</div>	
										<p><c:out value="${mapConsultation.key.rue }" /></p>
										<p>  <c:out value="${mapConsultation.key.codePostal }" /> <c:out value="${mapConsultation.key.ville }"/></p>
										<p> <c:out value="${mapConsultation.key.tel }" /></p>								
									<div> --%>
									
									<input type="text" hidden=true name="${status.count}id-medecin-actuel" value="${mapConsultation.key.id}"/>	
									<input type="text" hidden=true name="${status.count}date-consultation-actuelle" value="${mapConsultation.value.dateConsultation}"/>	
									<input type="text" hidden=true name="${status.count}heure-consultation-actuelle" value="${mapConsultation.value.heureConsultation}"/>	
									
																	
									<div id="bouton-consultation">
					                      	<input name = "bouton_Deplacer" value ="DEPLACER RDV" class="bouton-modifier-consultation" id="${status.count}btn-modif" type ="button" onclick="deplacerRdv('${status.count}');" />
					                      	<input name = "bouton_Annuler" value ="ANNULER RDV" class="bouton-annuler-consultation" id="${status.count}btn-supp" type="submit" onclick="initialiseDateAujourdhui(),actionPost('supprimerConsultation','${status.count}');" />
									</div>				
									
									<div class="contener-modification" id="${status.count}contener-modification">
										<!-- Date -->
		                                <input type="date" id="date-consultation" class="dates" name="${status.count}date-consultation-modif" value="${mapConsultation.value.dateConsultation }" autofocus  required   />
										<!-- Time -->
										
										<select id="heure-consultation" name="${status.count}heure-consultation-modif">
									    	<optgroup>
											  <option value="huit" id="08:00">08:00</option>
											  <option value="huit-trente" id="08:30" >08:30</option>
											  <option value="neuf" id="09:00" >09:00</option>
											  <option value="neuf-trente" id="09:30">09:30</option>
											  <option value="dix" id="10:00">10:00</option>
											  <option value="dix-trente" id="10:30">10:30</option>								
											  <option value="onze" id="11:00">11:00</option>
											  <option value="onze-trente" id="11:30">11:30</option>
											  <!-- ne travaille pas de 12h-13h (pause déjeuner) -->
											  <option value="treize" id="13:00">13:00</option>
											  <option value="treize-trente" id="13:30">13:30</option>									  
											  <option value="quatorze" id="14:00">14:00</option>
											  <option value="quatorze-trente" id="14:30">14:30</option>									  
											  <option value="quinze" id="15:00">15:00</option>
											  <option value="quinze-trente" id="15:30">15:30</option>									  
											  <option value="seize" id="16:00">16:00</option>
											  <option value="seize-trente" id="16:30">16:30</option>									  
											  <option value="dix-sept" id="17:00">17:00</option>
											  <option value="dix-sept-trente" id="17:30">17:30</option>									  
											  <option value="dix-huit" id="18:00">18:00</option>
											</optgroup>
										</select>		
						                
						                <input name = "bouton_Valider" value ="VALIDER" class="bouton-valider-consultation" type ="submit" onclick="actionPost('modifierConsultation','${status.count}');" />
										<img  src="<c:url value="/inc/icones/croix-couleur.png"/>" alt="croix couleur" id="image-fermeture_rdv" onclick="fermerDeplacerRdv('${status.count}');">														
	
									</div>								
									<hr>
								</div>	
						</c:forEach>
					</form>
				</div>
				<br>
					
   			</div>
			            
        </div>
        
        <!-- ********************* SCRIPT JAVASCRIPT ********************* -->
        
        
		<script type="text/javascript">
			/*
				Objectif : ajoute un paramètre sur l'url pour que la servlet distingue l'origine du bouton
				précondition : une String : ajouter/modifier/supprimer
			*/
			function actionPost(action){
				var form = document.getElementById("patient-form");
				form.action = "<c:url value='/PanelPatient?action="+action+"'/>";
			}
			
			function actionPost(action, idSelected){
				var form = document.getElementById("patient-form");

				form.action = "<c:url value='/PanelPatient?action="+action+"'/>";
			}		
			/*-----------------------------------------------------------------------------*/
			
			/*
				Objectif : affiche menu modification date du rendez vous		
			*/
			
			function deplacerRdv(increm){
				var contenerModification = document.getElementById(increm+"contener-modification");
				contenerModification.style.display = "initial"; 
				document.getElementById(increm+"btn-modif").disabled = true;

			}
			
			/*
				Objectif : ferme le menu modification date du rendez vous		
			*/			
			function fermerDeplacerRdv(increm){
				var contenerModification = document.getElementById(increm+"contener-modification");
				contenerModification.style.display = "none"; 
				document.getElementById(increm+"btn-modif").disabled = false;				
			}
			
			/*-----------------------------------------------------------------------------*/
			
			
			/*
				Objectif : permettre de fixer la date minimum à aujourd'hui.
				donc d'empecher l'utilisateur de saisir une date antérieure à la date de saisie.
			*/
		
			var aujourdhui = new Date();
			var jour = aujourdhui.getDate();
			var mois = aujourdhui.getMonth()+1; //Janvier commence à 0.
			var annee = aujourdhui.getFullYear();
			if(jour<10){
				jour='0'+jour;
			} 
			if(mois<10){
				mois='0'+mois;
			} 

			aujourdhui = annee+'-'+mois+'-'+jour;
			document.getElementById("date-consultation").setAttribute("min", aujourdhui);

			/*-----------------------------------------------------------------------------*/
			
			/*
				Initialise les champs dates à aujourd'hui
				Son objectif est d'éviter de bloquer le submit du bouton suppression dans le
				cas où un input date (qui est caché) affiche une erreur.
			*/

			function initialiseDateAujourdhui(){
				
				var aujourdhui = new Date();
				var jour = aujourdhui.getDate();
				var mois = aujourdhui.getMonth()+1; //Janvier commence à 0.
				var annee = aujourdhui.getFullYear();
				if(jour<10){
					jour='0'+jour;
				} 
				if(mois<10){
					mois='0'+mois;
				} 

				aujourdhui = annee+'-'+mois+'-'+jour;				
				
				var lesDates = document.getElementsByClassName("dates");
				
				for(var i=0; i<lesDates.length; i++){
					lesDates[i].value = aujourdhui;
				}
				
			}
			
			/*-----------------------------------------------------------------------------*/
			
			/*	
				Objectif : Permettre de remplir les champs input en cliquant sur une ligne du tableau.
				précondition : l'id de la ligne
			*/
			function remplirInput(id) {
				document.getElementById("nom-prenom").textContent = "Dr. "+document.getElementById(id+"nom").textContent+" "+document.getElementById(id+"prenom").textContent;
				document.getElementById("specialite").textContent = document.getElementById(id+"specialite").textContent;
				document.getElementById("telephone").textContent = document.getElementById(id+"tel").textContent;
				document.getElementById("adresse").textContent = document.getElementById(id+"rue").textContent ;
				
				document.getElementById("cp-ville").textContent = document.getElementById(id+"codePostal").textContent + " "+ document.getElementById(id+"ville").textContent;	
				
				document.getElementById("id-medecin-clique").value = document.getElementById(id+"id").textContent;
				
			}
		
		</script>
        
    </body>
