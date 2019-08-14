<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Admin - Gestion des medecins</title>
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
                <li><span id="menu-1"><a href="<c:url value="/restreint/admin.jsp"/>">Gestion des patients</a> </span></li>
                <li><span id="menu-1"><a href="<c:url value="/restreint/admin2.jsp"/>"><b> Gestion des médecins</b></a></span></li>
            </ul>
        </nav>

        <div id="content">

            <h2 class="titre-liste">Liste des Médecins
            <span class="info-connexion"> Admin : <c:out value="${sessionScope.sessionAdmin.login}"/> </span>
            </h2>
            	
            <div class="liste-contener">
			<table class="liste">
				<tr> 
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

            <div class="information-utilisateur">
                <form action="<c:url value="/PanelAdmin"/>" method="POST" id="admin-form">
                    <fieldset>
                        <legend>Fiche Informations Médecin</legend> <br>

                        <div class="formulaire">
                        	<span> <input type="hidden" id="id-medecin" name="id-medecin" value=""></span>
                            <p class="gauche">

                                <label for="nom-medecin">nom    :  </label>
                                <input type="text" id="nom-medecin" name="nom-medecin" value="" autofocus />
                                 <br>


                                <label for="prenom-medecin">prenom  : </label>
                                <input type="text" id="prenom-medecin" name="prenom-medecin" value="" autofocus />
                                <br>

                                <label for="specialiste-select">Spécialiste  : </label>
                                <select id="specialiste-select" name="specialiste-select">
                                    <option value="autre" id="autre" >choisir spécialité</option>
                                    <option value="medecin" id="medecin">Médecin généraliste</option>
                                    <option value="chirurgien" id="chirurgien" >Chirurgien</option>
                                    <option value="osteopathe" id="osteopathe" >Ostéopathe</option>
                                    <option value="kine" id="kine">Kinésitérapeute</option>
                                    <option value="anesthesiste" id="anesthesiste">Anesthésiste</option>
                                    <option value="dermato" id="dermato" >Dermatologie</option>
                                </select>
                                <br>

                                <label for="tel-medecin">Telephone : </label>
                                <input type="text" id="tel-medecin" name="tel-medecin" value="" autofocus />
                                <br>
                                <label for="sexe" class="hidden"></label><input class="hidden" type="radio" name="sexe" id="sexeH" checked /><input class="hidden" type="radio" name="sexe" id="sexeF" />                         
								<br>
                            </p>
                            <p class="droite">
                                <label for="email-medecin">Email : </label>
                                <input type="text" id="email-medecin" name="email-medecin" value="" autofocus />
                                <br>

                                <label for="adresse-medecin">Adresse    : </label>
                                <input type="text" id="adresse-medecin" name="adresse-medecin" value="" autofocus />
                                <br>
 
                                <label for="ville-medecin">Ville  : </label>
                                <input type="text" id="ville-medecin" name="ville-medecin" value="" autofocus />
                                <br>

                                <label for="cp-medecin">CP : </label>
                                <input type="text" id="cp-medecin" name="cp-medecin" value="" autofocus />
                                <br>    
                            </p>
                        </div>
                        
                         <!--  Affichage succès ou erreur  -->        
			           	 <span class="succes" ><c:out value="${medecinForm.resultat}"/></span>      
			             <c:forEach items="${medecinForm.erreurs}" var="erreur">
			             	<ul class="erreur">
			             		<li><c:out value="${erreur.value}"/> </li>
			             	</ul>
			             </c:forEach>                         
                                              
                    </fieldset>
                    <div class="boutons-valider">
                        <input name = "bouton_Valider" value = "Ajouter" id="submit-ajouter" type = "submit" onclick="actionPost('ajouterMedecin');" />
                        <input name = "bouton_Valider" value = "Modifier"  id="submit-modifier" type = "submit" onclick="actionPost('modifierMedecin');" />
                        <input name = "bouton_Valider" value = "Supprimer" id="submit-supprimer" type = "submit" onclick="actionPost('supprimerMedecin');"/>
                    </div>
                </form>            
            </div>
	        <div class="barre-recherche">
	            <input class="champ-recherche" type="search" placeholder="rechercher..."/>
	             <input class="bouton-recherche" type="submit" value="rechercher" name="rechercherMedecin" />
	        </div>
        </div>
        
		<script type="text/javascript">
		
			/*	
				Objectif : Permettre de remplir les champs input en cliquant sur une ligne du tableau.
				précondition : l'id de la ligne
			*/
			function remplirInput(id) {
					
				document.getElementById("id-medecin").value = id;	

				document.getElementById("nom-medecin").value = document.getElementById(id+"nom").textContent;
				document.getElementById("prenom-medecin").value = document.getElementById(id+"prenom").textContent;
				
				var specialite = document.getElementById(id+"specialite").textContent;
				switch(specialite.trim() ){
				case "médecin généraliste" :
					document.getElementById("medecin").selected = true; 
					break;
				case "chirurgien" :
					document.getElementById("chirurgien").selected = true; 
					break;
				case "ostéopathe" :
					document.getElementById("osteopathe").selected = true; 
					break;
				case "kinésithérapeute" :
					document.getElementById("kine").selected = true; 
					break;
				case "anesthésiste" :
					document.getElementById("anesthesiste").selected = true; 
					break;
				case "dérmatologue" :
					document.getElementById("dermato").selected = true; 
					break;
				}
				
				document.getElementById("tel-medecin").value = document.getElementById(id+"tel").textContent;
				document.getElementById("email-medecin").value = document.getElementById(id+"email").textContent;
				document.getElementById("cp-medecin").value = document.getElementById(id+"codePostal").textContent;				
				document.getElementById("adresse-medecin").value = document.getElementById(id+"rue").textContent;
				document.getElementById("ville-medecin").value = document.getElementById(id+"ville").textContent;
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