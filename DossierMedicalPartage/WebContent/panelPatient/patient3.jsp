<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Patient - Afficher mon dossier</title>
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
                <li><span id="menu-1"><a href="<c:url value="/panelPatient/patient2.jsp"/>">Mes consultations</a></span></li>
                <li><span id="menu-1"><a href="<c:url value="/panelPatient/patient3.jsp"/>"><b>Afficher mon dossier</b></a></span></li>
            </ul>
        </nav>
        <div id="content">
            <br>
            <p class="info-connexion"> Patient : <c:out value="${sessionScope.sessionPatient.login}"/> </p>
            
   			<div class="consultation-bloc">
   				<div>
	                 <!--  Affichage succès ou erreur  -->        
		           	 <span class="succes" ><c:out value="${patientForm.resultat}"/></span>      
		             <c:forEach items="${patientForm.erreurs}" var="erreur">
		             	<ul class="erreur">
		             		<li><c:out value="${erreur.value}"/> </li>
		             	</ul>
		             </c:forEach> 	 								   				
   				</div>
				<div class="information-patient">
					<div class="en-tete-consultation">
						<img  src="<c:url value="/inc/icones/users.png"/>" alt="logo utilisateur">														
						<h2>Mes informations </h2>
						<hr>
					</div>					
					
                	<form action="<c:url value="/PanelPatient"/>" method="POST" id="patient-form">		
                	
                		<div class="gauche reajustement-modif-patient">   
                			<p>                  	
                                <label class="label-info-patient">Votre Numéro SS : </label>
                                <c:out value="${sessionScope.sessionPatient.numeroSS}"/>
                            </p>
                            <p>
                                <label class="label-info-patient">Votre Nom :</label>
                                <c:out value="${sessionScope.sessionPatient.nom}"/>
                            </p>
                            <p>	    
                                <label class="label-info-patient">Votre Email :</label>
                                <c:out value="${sessionScope.sessionPatient.email}"/>
                            </p>
                            <p>		 	 
                                <label class="label-info-patient">Votre Code Postal :</label>
                                <c:out value="${sessionScope.sessionPatient.codePostal}"/>
                            </p>
                            <p>			
								<label class="label-info-patient">Votre Sexe :</label>	  
                                <c:out value="${sessionScope.sessionPatient.sexe}"/> 
                            </p>                                                                                   
                		</div> 			
						<div class="droite reajustement-modif-patient">
							<p>
                                <label class="label-info-patient">Votre Nr.Telephone : </label>
                                <c:out value="${sessionScope.sessionPatient.tel}"/>
                            </p>
                            <p>	                             
                                <label class="label-info-patient">Votre Prénom :</label>
                                <c:out value="${sessionScope.sessionPatient.prenom}"/>
                            </p>
                            <p>	              
                                <label class="label-info-patient">Votre Rue : </label>
                                <c:out value="${sessionScope.sessionPatient.rue}"/>
                            </p>
                            <p>	    	
                                <label class="label-info-patient">Votre Ville : </label>
                                <c:out value="${sessionScope.sessionPatient.ville}"/>
                            </p>
						</div>
									
				
						<div id="bouton-consultation">
		                      	<input name = "bouton_modifier" value ="MODIFIER INFORMATIONS" class="bouton-modifier-consultation" id="btn-modif" type ="button" onclick="ouvrirModif();" />
						</div>								
									
						<div class="contener-modification" id="contener-modification">						
							<div class="formulaire" >
	                        	<p class="gauche">
	                        	    <span> <input type="hidden" id="id-patient" name="id-patient" value="<c:out value="${sessionScope.sessionPatient.id}"/>" ></span>
	                        	
	                                <label for="numeroSS">Votre Numéro SS : </label>
	                                <input type="text" id="numeroSS" name="numeroSS" value="<c:out value="${sessionScope.sessionPatient.numeroSS}"/>" autofocus required />
	                                <br>
	
	                                <label for="nom-patient">Votre Nom :  </label>
	                                <input type="text" id="nom-patient" name="nom-patient" value="<c:out value="${sessionScope.sessionPatient.nom}"/>" autofocus required />
	                                 <br>	    
	                                <label for="email-patient">Votre Email :  </label>
	                                <input type="email" id="email-patient" name="email-patient" value="<c:out value="${sessionScope.sessionPatient.email}"/>" autofocus required />
	                                 <br>	 	  
	                                <label for="cp-patient">Votre Code Postal :  </label>
	                                <input type="text" id="cp-patient" name="cp-patient" value="<c:out value="${sessionScope.sessionPatient.codePostal}"/>" autofocus required />
	                                 <br>		
									<label for="sexe">Sexe :</label>	                                 
                                		<input type="radio" name="sexe" id="sexeH" ${sessionScope.sessionPatient.sexe=='H'.charAt(0) ? 'checked' : ''} />Homme <input type="radio" name="sexe" id="sexeF" ${sessionScope.sessionPatient.sexe=='F'.charAt(0) ? 'checked' : ''} />Femme<br />	                                 
	                        	</p>
	                            <p class="droite">	
	                                <label for="tel-patient">Votre Nr.Telephone : </label>
	                                <input type="text" id="tel-patient" name="tel-patient" value="<c:out value="${sessionScope.sessionPatient.tel}"/>" autofocus required />
	                                <br>
	                                
	                                <label for="prenom-patient">Votre Prénom : </label>
	                                <input type="text" id="prenom-patient" name="prenom-patient" value="<c:out value="${sessionScope.sessionPatient.prenom}"/>" autofocus required />
	                                <br>              
	                                
	                                <label for="rue-patient">Votre Rue : </label>
	                                <input type="text" id="adresse-patient" name="adresse-patient" value="<c:out value="${sessionScope.sessionPatient.rue}"/>" autofocus required />
	                                <br>    	
	                                <label for="ville-patient">Votre Ville : </label>
	                                <input type="text" id="ville-patient" name="ville-patient" value="<c:out value="${sessionScope.sessionPatient.ville}"/>" autofocus required />
	                                <br>   	                                                                                                      	                                
	                            </p>
	                       </div>   
			                <input name = "bouton_Valider" value ="VALIDER MODIFICATIONS" class="bouton-valider-info-patient" type ="submit" onclick="actionPost('modifierPatient');" />
							<img  src="<c:url value="/inc/icones/croix-couleur.png"/>" alt="croix couleur" id="image-fermeture_rdv" onclick="fermerModif();"/>														
					  	</div>								
						<hr>
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
			/*-----------------------------------------------------------------------------*/
			
			/*
				Objectif : affiche menu modification date du rendez vous		
			*/
			
			function ouvrirModif(){
				var contenerModification = document.getElementById("contener-modification");
				contenerModification.style.display = "initial"; 
				document.getElementById("btn-modif").disabled = true;

			}
			
			/*
				Objectif : ferme le menu modification date du rendez vous		
			*/			
			function fermerModif(){
				var contenerModification = document.getElementById("contener-modification");
				contenerModification.style.display = "none"; 
				document.getElementById("btn-modif").disabled = false;				
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
		
		</script>
        
    </body>
