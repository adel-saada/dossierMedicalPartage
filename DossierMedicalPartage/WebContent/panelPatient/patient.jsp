<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Patient - gestion des consultations</title>
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
                <li><span id="menu-1"><a href="<c:url value="/panelPatient/patient.jsp"/>"><b>Reservation des consultations</b></a> </span></li>
                <li><span id="menu-1"><a href="<c:url value="/panelPatient/patient2.jsp"/>"> Mes consultations</a></span></li>
                <li><span id="menu-1"><a href="<c:url value="/panelPatient/patient3.jsp"/>"> Afficher mon dossier</a></span></li>
            </ul>
        </nav>

        <div id="content">
        
            <div class="barre-recherche">
            	<input class="champ-recherche" type="search" placeholder="rechercher..."/>
                <input class="bouton-recherche" type="button" value="rechercher" />
            </div>
            <br>
            <h2 class="titre-liste">Liste des Médecins
            <span class="info-connexion"> Patient : <c:out value="${sessionScope.sessionPatient.login}"/> </span>
            </h2>
             <div class="liste-contener">
			<table class="liste">
				<tr> 
					<th>id</th>
					<th>login</th>
					<th>nom</th>
					<th>prenom</th>
					<th>Specialite</th>
					<th>telephone</th>
					<th>email</th>
					<th>rue </th>
					<th>ville</th>
					<th>codePostal</th>
				</tr>
	 
				<c:forEach items="${ sessionScope.listeMedecin }" var="medecin" varStatus="status">
				    <tr id="<c:out value="${ medecin.id }"/>" onclick="remplirInput(${ medecin.id });" >
				    <td id="<c:out value="${ medecin.id }id" />"> <c:out value="${ medecin.id }" /> </td> 
				    <td id="<c:out value="${ medecin.id }login" />"> <c:out value="${ medecin.login }"/> </td> 
				    <td id="<c:out value="${ medecin.id }nom" />"> <c:out value="${ medecin.nom }" /> </td>
				    <td id="<c:out value="${ medecin.id }prenom" />"> <c:out value="${ medecin.prenom }" /> </td>
				    <td id="<c:out value="${ medecin.id }specialite" />"> <c:out value="${ medecin.specialite }" /> </td>
				    <td id="<c:out value="${ medecin.id }tel" />"> <c:out value="${ medecin.tel }" /> </td>
				    <td id="<c:out value="${ medecin.id }email" />"> <c:out value="${ medecin.email }" /> </td>
				    <td id="<c:out value="${ medecin.id }rue" />"> <c:out value="${ medecin.rue }" /> </td>				    
				    <td id="<c:out value="${ medecin.id }ville" />"> <c:out value="${ medecin.ville }" /> </td> 
				    <td id="<c:out value="${ medecin.id }codePostal" />"> <c:out value="${ medecin.codePostal }" /> </td>
				    </tr>
				</c:forEach>
			</table> 
			

            </div>
            
			<div class="information-utilisateur redefinition-gauche">
				<fieldset>Fiche Informations médecin</fieldset>	
				<table class="liste">
					<c:if test="${ not empty sessionScope.medecinSelection.nom}">
				
						<tr> 
							<th>nom prenom</th>
							<td id="nom-prenom"> Dr. <c:out value="${sessionScope.medecinSelection.nom}"/> <c:out value="${sessionScope.medecinSelection.prenom}"/></td>
						</tr>
						<tr>
							<th>spécialité</th>
							<td id="specialite"><c:out value="${sessionScope.medecinSelection.specialite}"/></td>
						</tr>
						<tr>
							<th>téléphone</th>
							<td id="telephone"> <c:out value="${sessionScope.medecinSelection.tel}"/> </td>
						</tr>
						<tr>
							<th>adresse</th>
							<td id="adresse"><c:out value="${sessionScope.medecinSelection.rue}"/></td>
						</tr>
						<tr>
							<th>CP, Ville</th>
							<td id="cp-ville"> <c:out value="${sessionScope.medecinSelection.codePostal}"/> <c:out value="${sessionScope.medecinSelection.ville}"/></td>
						</tr>
					</c:if>					
					<c:if test="${empty sessionScope.medecinSelection.nom}">
				
						<tr> 
							<th>nom prenom</th>
							<td id="nom-prenom"> Dr. <c:out value="${sessionScope.listeMedecin[0].nom}"/> <c:out value="${sessionScope.listeMedecin[0].prenom}"/></td>
						</tr>
						<tr>
							<th>spécialité</th>
							<td id="specialite"><c:out value="${sessionScope.listeMedecin[0].specialite}"/></td>
						</tr>
						<tr>
							<th>téléphone</th>
							<td id="telephone"> <c:out value="${sessionScope.listeMedecin[0].tel}"/> </td>
						</tr>
						<tr>
							<th>adresse</th>
							<td id="adresse"><c:out value="${sessionScope.listeMedecin[0].rue}"/></td>
						</tr>
						<tr>
							<th>CP, Ville</th>
							<td id="cp-ville"> <c:out value="${sessionScope.listeMedecin[0].codePostal}"/> <c:out value="${sessionScope.listeMedecin[0].ville}"/></td>
						</tr>
					</c:if>
				</table>			
 				<img class="image-info" src="<c:url value="/inc/icones/medecin64.png"/>" alt="logo medecin">								
		   </div>

            <div class="information-utilisateur redefinition-droite" >
                <form action="<c:url value="/PanelPatient"/>" method="POST" id="patient-form">
                    <fieldset>
                        <legend>Réserver une consultation</legend> 
                        <!--  Permet de récupérer des valeurs via Javascript -->
                        <span> <input type="hidden" id="id-medecin-clique" name="id-medecin-clique" value= "<c:out value="${sessionScope.listeMedecin[0].id}"/>" /> </span>
                        <span> <input type="hidden" id="id-date-clique" name="id-date-clique" value= "2025-10-10" /> </span>                        
                        <br>
                        <div class="formulaire" >
                        	<p class="gauche">
                                <label for="numeroSS">Votre Numéro SS : </label>
                                <input type="text" id="numeroSS" name="numeroSS" value="<c:out value="${sessionScope.sessionPatient.numeroSS}"/>" disabled />
                                <br>

                                <label for="nom-patient">Votre Nom    :  </label>
                                <input type="text" id="nom-patient" name="nom-patient" value="<c:out value="${sessionScope.sessionPatient.nom}"/>"  disabled/>
                                 <br>

                                <label for="date-consultation">Date : </label>
                                <input type="date" id="date-consultation" name="date-consultation" value="${sessionScope.dateConsultation }" autofocus  required   />
                                <br>    
                  	
                        	</p>
                            <p class="droite">

                                <label for="tel-patient">Votre Nr.Telephone : </label>
                                <input type="text" id="tel-patient" name="tel-patient" value="<c:out value="${sessionScope.sessionPatient.tel}"/>" disabled/>
                                <br>
                                
                                <label for="prenom-patient">Votre Prénom  : </label>
                                <input type="text" id="prenom-patient" name="prenom-patient" value="<c:out value="${sessionScope.sessionPatient.prenom}"/>"  disabled/>
                                <br>                                                    
                                
                                <label for="heure-consultation">Heure : </label>
                                 <select id="heure-consultation" name="heure-consultation">
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
                                <br>                                                   
                            </p>
                        </div>                      
                    </fieldset>
	                 <!--  Affichage succès ou erreur  -->        
		           	 <span class="succes" ><c:out value="${consultationForm.resultat}"/></span>      
		             <c:forEach items="${consultationForm.erreurs}" var="erreur">
		             	<ul class="erreur">
		             		<li><c:out value="${erreur.value}"/> </li>
		             	</ul>
		             </c:forEach>                     
                    <div class="boutons-valider" >
                        <input name = "bouton_Ajouter" value = "Ajouter"  class="bouton-ajouter-consultation" type ="submit" onclick="actionPost('ajouterConsultation');" />
                    </div>
                </form>            
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
				Objectif : Permettre de remplir les champs input en cliquant sur une ligne du tableau.
				précondition : l'id de la ligne
			*/
			function remplirInput(id) {
				document.getElementById("nom-prenom").textContent = "Dr. "+document.getElementById(id+"nom").textContent+" "+document.getElementById(id+"prenom").textContent;
				document.getElementById("specialite").textContent = document.getElementById(id+"specialite").textContent;
				document.getElementById("telephone").textContent = document.getElementById(id+"tel").textContent;
				document.getElementById("adresse").textContent = document.getElementById(id+"rue").textContent ;
				
				document.getElementById("cp-ville").textContent = document.getElementById(id+"codePostal").textContent + " "+ document.getElementById(id+"ville").textContent;	
				
				document.getElementById("id-medecin-clique").value = id;
				
			}	
		</script>
        
    </body>
