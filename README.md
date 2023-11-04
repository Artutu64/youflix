# Présentation
YouFlix est une plateforme d'hébergement de bandes-annonces de films, réalisée dans un cadre scolaire, non commercial, et affiliée ni à YouTube ni à Netflix.

# Pages et fonctionnalités
1. Films : visualisation de la bande-annonce et accès aux détails du film (acteurs, synopsis, ...)
2. Recommandations : mise en avant de films adaptés aux goûts de l'utilisateur
3. Favoris : possibilité d'aimer une vidéo et de l'ajouter à ses favoris
4. Recherche : recherche de films par nom, par genre et par acteur
5. Administration : ajout, modification et suppression de films et d'acteurs

# Installation
1. Clonez le dossier sur votre machine
2. Vérifiez que le dossier ```/data``` n'existe pas et dans le cas échéant supprimez le.
3. Démarrez le serveur en utilisant le fichier /src/main/java/fr/cytech/pau/youflix/YouFlixApplication.java
4. Rendez vous sur votre navigateur sur l'url: http://localhost:8080/initvideos
5. Vous pouvez maintenant profiter de YouFlix sur l'url: http://localhost:8080/
6. Après déploiement, supprimez la ligne @PostMapping(path= "/initvideos") du fichier /src/main/java/fr/cytech/pau/youflix/Controllers/InitVideoController.java
