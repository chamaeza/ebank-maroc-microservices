# EBank Maroc — Partie 1
Projet pédagogique local, données fictives, sans authentification. Ne pas exposer sur Internet. H2 en mémoire : réinitialisation au redémarrage. Aucun Docker nécessaire.

## Windows / IntelliJ
Ouvrir le pom.xml racine comme projet Maven, sélectionner JDK 21, attendre la synchronisation. Démarrer les classes Application dans cet ordre : discovery, customer, ebank, gateway (attendre chaque démarrage). Ou avec Maven installé, depuis la racine : `mvn clean package` puis dans quatre terminaux `java -jar discovery-service/target/discovery-service-1.0.0.jar` et de même pour customer-service, ebank-service, gateway-service. Un wrapper Windows est fourni si disponible dans l'archive.

## Liens
- Eureka http://localhost:8761
- Swagger Customer http://localhost:8081/swagger-ui/index.html
- Swagger EBank http://localhost:8082/swagger-ui/index.html
- Gateway http://localhost:8888/api/customers et http://localhost:8888/api/accounts
- H2 : http://localhost:8081/h2-console (jdbc:h2:mem:customer-service) ou 8082 (jdbc:h2:mem:ebank-service), utilisateur sa, mot de passe vide.

## Tests
Importer postman/EBank.postman_collection.json. Exécuter dans l'ordre. Les comptes ACC-001 à ACC-003 existent au démarrage, solde 10000 MAD. Crédit 500, débit 100, transfert 200 vers ACC-002. Vérifier soldes et historique. Arrêter Customer Service puis appeler directement http://localhost:8082/api/accounts/ACC-001/details quatre fois : customerStatus UNAVAILABLE, données bancaires conservées. Vérifier http://localhost:8082/actuator/circuitbreakers. La création de compte doit échouer avec 503 pendant la panne. Redémarrer Customer, attendre l'enregistrement Eureka et au moins 15 secondes puis retester. Un client inexistant donne 404 à la création sans ouvrir le circuit. Les identifiants RIB sont volontairement non bancaires. Les opérations de transfert sont atomiques dans la base EBank, pas de transfert interbancaire réel.

## Périmètre
Partie 1 seulement : Customer, EBank, Feign, Resilience4J, Gateway, Eureka, REST. Config Server, Angular, MCP, Telegram et IA réservés aux étapes suivantes. Pas de suppression de client pour éviter les références orphelines. Les soldes initiaux sont des données de démonstration.
