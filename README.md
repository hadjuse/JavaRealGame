# JavaRealGame
## Présentation du jeu

C'est un jeu prototype dans lequel on incarne un petit ange 
qui se retrouve dans un donjon et qui doit battre des monstres
dans différent monde qu'on peut accéder en cliquant sur des
**boutons** numérotés en fonction du monde. On peut également intéragir avec uhn marchand,
Des PNJ et attaquer des monstres
## Installation, Configuration et Environnement

Le fichier jar est compilé sous **open-jdk-20** file 64.0 <br>
- Créer un dossier contenant le repository.
- Se placer dans le repo est exécuté cette commande
```bash
git clone https://github.com/hadjuse/JavaRealGame.git
```
Il existe déjà un exécutable JavaRealGame.jar contenu dans le repo
- Se placer dans le dossier contenant le fichier jar
  (Donc normalement le dossier actuel)
- Entrer la commande:
```bash
java --module-path "PATH_TO_javafx-sdk-22\javafx-sdk-22\lib" --add-modules javafx.controls,javafx.fxml,javafx.swing,javafx.base,javafx.graphics,javafx.media,javafx.web -jar JavaRealGame.jar
```

## Comment jouer

Voici une liste des entités présentes sur le jeu et les intéractions possibles.
### Joueur

- Déplacement : Les claviers ZQSD ;
- Attaque : Cliquer sur les monstres ;
- Inventaire : Apppuyer sur P pour ouvrir l'inventaire
- Porte bouton : Parfois, il faudra une potion spéciale pour ouvrir
des portes, mais sinon il faut cliquer sur les boutons pour changer de monde ;

### Monstres

- Déplacement : aucun
- Attaque : Si le joueur touche un monstre, le joueur perd de la vie en fonction des différents dégâts
des différents types de monstres.
- Inventaire : Accessible seulement par les intéractions
écrits dans le programme

### Les Mondes
Un monde est un monde de tuile de 16X16 délimités par des murs.
<br>
Contenant :
- Mur ; 
- Porte (boutons) ;
- Spikes ;
- Monstres ;
- Joueur ;
- PNJ ;
### Les PNJ
On clique sur les pnj pour intéragir avec eux.
Il y en a 2 :
- Le marchant ou négociant 
- Et un PNJ pour le DS (à configurer ce jour la)

- Déplacement : aucun ;
- Attaque : Aucun ;
- Inventaire : Accessible en cliquant sur le PNJ (marchant Actuellement) ;

Si le jeu ne s'exécute pas, c'est forcément un problème de chemin
Relatif.