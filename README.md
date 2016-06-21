# Traduction
Edition des fichiers de traduction (type resource bundle en java) sous le format Excel. Le programme compare les clefs présentent 
dans le fichier par défaut avec les clefs dans les différentes langues.

L'application utilise une base de donnée pour garder des noms de clefs. A sauvegarder après export.

## Configuration
Configuration dans le fichier  : config.properties
lien vers les fichiers appCommons, appFaces ....
lien vers la BDD, H2

Liste des langues à générer : build.gradle paramétre
ext { langs=[...] }

## Génération des fichiers Excel
Lancement de la tache ou des taches gradle nommée(s) :
exportTrad[*]

Les fichiers sont générés dans output (fichier config.properties)

## Intégration des fichiers Excel
Lancement de la tache gradle nommée :
createProperties

Les fichiers XLS qui seront transformé en fichier properties doivent être
dans le répertoire xls.path (fichier config.properties)
Les résultats sont dans output (fichier config.properties)


