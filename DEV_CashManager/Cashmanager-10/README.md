
<img src="Documentation/Images/money.png" width="150" title="Logo de CashManager">

## Cashmanager-10 - A payment service with an Android terminal


The product in itself is very simple; here we insist on Quality. The project will be quite
a journey in learning many aspects of application development: Oriented Object Programming, Mobile Apps, Design Pattern, Code Coverage and more generally developing
robust, reliable, multiple-uses code.

------------------------------------

- Nom : CashManager
- Type : Application Android
- État : En développement
- Développeur(s) : 
  - Jérémy MEURIN (Epitech Lille)
  - Bema SYLLA (Epitech Lille)
  - Richard LA (Epitech Strasbourg)
  - Louis Chevillotte (Epitech Rennes)
  

## Documentation

- Cahier des charges : [ici](/Documentation/CDC-CashManager_Epitech.pdf)
- MCD (Modèle conceptuel de donnée) : [ici](/Documentation/MCD.pdf)
- Maquettes : --
- Livret d'utilisation : --  


## Description

C'est un projet d'achat de produits parmi une banque de produits avec une simulation de payement.
Les achats se feront par scan de code barres.

## Dépendances

- Hibernates  - [Téléchargement](http://hibernate.org/)
- jbdc.Driver - Version 8.4
- Java 8 - [Téléchargement](https://www.java.com/fr/download/)

## Installation

```java
git init //Initialisation de Git

git clone git@github.com:EpitechMscPro2020/Cashmanager-10.git //Clonage du répo (SSO on !!)

cd Cashmanager-10/ //Déplacement dans le dossier

mvn clean install //pour installer les dépendances
mvn spring-boot:run //pour lancer le projet (si tout se passe bien, il te créer la BDD et la table)

```

