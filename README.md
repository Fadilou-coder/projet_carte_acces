# Projet Carte D'acces
Le présent projet a pour but de digitaliser les cartes d'accès au sein de Orange Digital Center afin de régler définitivement le problème de perte de carte nationale d'identité et d'identification des visiteurs sur le site.
Il permettra aussi de renforcer la sécurité du site et donner une visibilité sur les entrées et sorties sur le site. Pour ce faire, une application mobile sera mise en place.
L’application, après le remplissage des informations du prospect (étudiant, visiteur) générera un autocollant Qr Code (tout petit format) pour ce dernier et lui enverra en même temps, le Qr Code par mail en cas de perte de l’autocollant.
Pour renforcer la sécurité sur le site, les visiteurs bénéficieront de code d'accès à usage unique qui ne seront plus valable dès qu’ils sortent du site même s’ils veulent retourner sur les lieux à l’instant.



## Pour commencer

  1. Récuperer la source
     git clone https://github.com/Fadilou-coder/projet_carte_acces.git
### Pré-requis

Ce qu'il est requis pour commencer avec votre projet...

- Java 8 ou +
- maven 3.3 ou +

### Installation

Installer -xampp pour linux ou windows
          -MAMP pour mac 

## Démarrage

- Ouvrir le projet sur un IDE (intelij de préférence )
- Ouvrir le fichier application.properties:
   Configurer avec votre base de données local pour tester localement 
   exemple: 
        server.servlet.context-path=/api
        spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:8889/carte_Apprenant (
        spring.datasource.username=root
        spring.datasource.password=root
        spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
        spring.jpa.hibernate.ddl-auto=create // pour la premiere fois laisser à create et après vous pouvez changer à "update"
        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
        app.db-init=true // c'est pour les fixture(pour ajouter de fausse données) vous pour mettre ça à false après premiere démarrage avec succès 
        
 -Lancer l'application:
   mvn spring-boot:run
   
 -Regarder la documentation des API avec: 
   http://localhost:8080/api/swagger-ui/
   
   exemple: 
   // POST /login
$ curl -X POST http://localhost:8080/api/login -i
    -H "Accept: application/json"
    -H "Content-Type: application/json"
    -d '{"email": "bacargoudiaby@gmail.com", "password": "password"}'
    

## Fabriqué avec

Spring Boot 


