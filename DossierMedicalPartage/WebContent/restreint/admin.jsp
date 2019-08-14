<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Admin - Gestion des patients</title>
        <link href='http://fonts.googleapis.com/css?family=Droid+Sans:400,700' rel='stylesheet'>
        <link rel="stylesheet" href="<c:url value="/inc/main.css"/>">
        <link rel="stylesheet" href="<c:url value="/inc/commun.css"/>">

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
                <li><span id="menu-1"><a href="<c:url value="/restreint/admin.jsp"/>"><b>Gestion des patients</b></a> </span></li>
                <li><span id="menu-1"><a href="<c:url value="/restreint/admin2.jsp"/>"> Gestion des médecins</a></span></li>
            </ul>
        </nav>

        <div id="content">
            <h2 class="titre-liste">Liste des Patients
            <span class="info-connexion"> Admin : <c:out value="${sessionScope.sessionAdmin.login}"/> </span>
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
				    <tr id="<c:out value="${ patient.id }id"/>" onclick="remplirInput(${ patient.id });">
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

            <div class="information-utilisateur">
                <form action="<c:url value="/PanelAdmin"/>" method="POST" id="admin-form">              
                    <fieldset>
                        <legend>Fiche Informations Patient</legend> <br>

                        <div class="formulaire">
                        	<span> <input type="hidden" id="id-patient" name="id-patient" value=""></span>
                            <p class="gauche">
                                <label for="numeroSS">numero SS : </label>
                                <input type="text" id="numeroSS" name="numeroSS" value="" autofocus required />
                                <br>

                                <label for="nom-patient">nom    :  </label>
                                <input type="text" id="nom-patient" name="nom-patient" value="" autofocus required/>
                                 <br>


                                <label for="prenom-patient">prenom  : </label>
                                <input type="text" id="prenom-patient" name="prenom-patient" value="" autofocus required/>
                                <br>

                                <label for="tel-patient">Telephone : </label>
                                <input type="text" id="tel-patient" name="tel-patient" value="" autofocus  required/>
                                <br>

                                <label for="sexe">Sexe :</label><input type="radio" name="sexe" id="sexeH" checked />Homme <input type="radio" name="sexe" id="sexeF" />Femme<br />
                            </p>
                            <p class="droite">
                                <label for="email-patient">Email : </label>
                                <input type="email" id="email-patient" name="email-patient" value="" autofocus required/>
                                <br>							
                                <label for="adresse-patient">Adresse    : </label>
                                <input type="text" id="adresse-patient" name="adresse-patient" value="" autofocus required />
                                <br>

                                <label for="ville-patient">Ville  : </label>
                                <input type="text" id="ville-patient" name="ville-patient" value="" autofocus required />
                                <br>

                                <label for="cp-patient">CP : </label>
                                <input type="text" id="cp-patient" name="cp-patient" value="" autofocus required />
                                <br>                     
                            </p>
                            
                        </div>        
                         <!--  Affichage succès ou erreur  -->        
			           	 <span class="succes" ><c:out value="${patientForm.resultat}"/></span>      
			             <c:forEach items="${patientForm.erreurs}" var="erreur">
			             	<ul class="erreur">
			             		<li><c:out value="${erreur.value}"/> </li>
			             	</ul>
			             </c:forEach> 

                    </fieldset>
                    <div class="boutons-valider">
                        <input name = "bouton_Valider" value = "Ajouter" id="submit-ajouter" type = "submit" onclick="actionPost('ajouterPatient');" />
                        <input name = "bouton_Valider" value = "Modifier"  id="submit-modifier" type = "submit" onclick="actionPost('modifierPatient');" />
                        <input name = "bouton_Valider" value = "Supprimer" id="submit-supprimer" type = "submit" onclick="actionPost('supprimerPatient');"/>
                    </div>
                </form>            
            </div>
	        <div class="barre-recherche">
	            <input class="champ-recherche" type="search" placeholder="rechercher..."/>
	             <input class="bouton-recherche" type="submit" value="rechercher" name="rechercher" />
	        </div>
        </div>
        
        
        
		<script type="text/javascript">
		
			/*	
				Objectif : Permettre de remplir les champs input en cliquant sur une ligne du tableau.
				précondition : l'id de la ligne
			*/
			function remplirInput(id) {
					
				document.getElementById("id-patient").value = id;	
					
				document.getElementById("numeroSS").value = document.getElementById(id+"numeroSS").textContent;
				document.getElementById("nom-patient").value = document.getElementById(id+"nom").textContent;
				document.getElementById("prenom-patient").value = document.getElementById(id+"prenom").textContent;
				document.getElementById("tel-patient").value = document.getElementById(id+"tel").textContent;
	
				var sexe = document.getElementById(id+"sexe").textContent;
					if(sexe.trim() === "H"){
					document.getElementById("sexeH").checked = true;
				} else {
					document.getElementById("sexeF").checked = true;
				} 
				document.getElementById("email-patient").value = document.getElementById(id+"email").textContent;
				document.getElementById("cp-patient").value = document.getElementById(id+"codePostal").textContent;				
				document.getElementById("adresse-patient").value = document.getElementById(id+"rue").textContent;
				document.getElementById("ville-patient").value = document.getElementById(id+"ville").textContent;
			}
			
			/*
				Objectif : ajoute un paramètre sur l'url pour que la servlet distingue l'origine du bouton
				précondition : une String : ajouter/modifier/supprimer
			*/
			function actionPost(action){
				var form = document.getElementById("admin-form");
				form.action = "<c:url value='/PanelAdmin?action="+action+"'/>";
			}
		
		</script>
        
    </body>
</html>