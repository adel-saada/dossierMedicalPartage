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
                <li><span id="menu-1"><a href="<c:url value="/panelMedecin/medecin.jsp"/>">Gestion des consultations</a> </span></li>
                <li><span id="menu-1"><a href="<c:url value="/panelMedecin/medecin2.jsp"/>">Les diagnostics (résultats de consultation)</a></span></li>
                <li><span id="menu-1"><a href="<c:url value="/panelMedecin/medecin3.jsp"/>">Prescriptions</a></span></li>
                <li><span id="menu-1"><a href="<c:url value="/panelMedecin/medecin4.jsp"/>"><b>Chercher un medecin</b></a></span></li>
            </ul>
        </nav>

        <div id="content">
        
            <div>
            	<input class="champ-recherche" type="search" placeholder="rechercher..."/>
                <input class="bouton-recherche" type="button" value="rechercher" />
            </div> 
            <br> 
            <h2 class="titre-liste">Liste des Medecins
            <span class="info-connexion"> Medecin : <c:out value="${sessionScope.sessionMedecin.login}"/> </span>
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
				<c:forEach items="${sessionScope.listeMedecin}" var="medecin" varStatus="status"> 
				
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
            
			<div class=" redefinition-gauche">
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
     
        </div>
        
        <!-- ********************* SCRIPT JAVASCRIPT ********************* -->
        
        
		<script type="text/javascript">
			
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
