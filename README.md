# Projet Spring Boot - Gestion Bancaire

## Introduction
Bienvenue dans le projet **Spring Boot de Gestion Bancaire**. Ce projet a pour objectif de développer une application de gestion bancaire complète, combinant robustesse et extensibilité grâce au framework Spring Boot.

## Partie Frontend - Angular

Ce projet utilise Angular comme framework front-end, généré avec [Angular CLI](https://github.com/angular/angular-cli) version 17.0.7.

### Démarrage du Serveur de Développement

Exécutez `ng serve` pour lancer un serveur de développement. Naviguez vers `http://localhost:4200/`. L'application se rechargera automatiquement lorsque les fichiers sources sont modifiés.

## Description des Modules

### 1. Interfaces
- **Customers** : Gère les opérations relatives aux clients.
- **Accounts** : Permet de gérer les comptes bancaires.
- **Customers-Accounts** : Gère les relations entre clients et comptes.
- **Login** : Interface d'authentification des utilisateurs.

### 2. Services
- **Customers-Services** : Service pour la gestion des clients.
- **Accounts-Services** : Service pour les opérations sur les comptes bancaires.
- **Auth-Service** : Service d’authentification et de sécurité.

### 3. Intercepteurs
- Gestion des requêtes HTTP sortantes et entrantes.
- Contrôle et gestion des erreurs.

### 4. Guards
- **Authentication Guard** : Protège les routes qui nécessitent une authentification.
- **Authorization Guard** : Gère l’accès aux différentes parties de l’application.

### 5. Définition des Routes
- Navigation fluide et intuitive.
- Protection des routes sensibles par des guards.

### 6. Tests des Composants
Tous les composants, services, intercepteurs et guards ont été testés pour garantir leur bon fonctionnement.

#### Captures d'écran
![Login Admin](https://github.com/elmehdibenhayoun/frontend-banking/blob/master/capture/adminlogin.png)
![Home Admin](https://github.com/elmehdibenhayoun/frontend-banking/blob/master/capture/adminhome.png)
![Gestion des Comptes](https://github.com/elmehdibenhayoun/frontend-banking/blob/master/capture/adminAccount.png)
![Authentification JWT](https://github.com/elmehdibenhayoun/frontend-banking/blob/master/capture/authjwt.png)
![JWT Token](https://github.com/elmehdibenhayoun/frontend-banking/blob/master/capture/jwt.png)

---

## Partie Backend - API Spring Boot

### Contenu du Projet

#### 1. Modélisation des Entités
Les entités du système incluent :
- **Customer** : Représente les clients.
- **CurrentAccount** : Compte courant.
- **SavingAccount** : Compte d’épargne.
- **BankAccount** : Compte bancaire générique.
- **AccountOperation** : Opération bancaire.

#### 2. Repositories
Des repositories sont créés pour chaque entité :
- **CustomerRepository**
- **BankAccountRepository**
- **AccountOperationRepository**

#### 3. Enums et DTOs
- **Enums** :
  - `AccountStatus` : État des comptes.
  - `OperationType` : Types d’opérations.
- **DTOs** :
  - `CustomerDTO`
  - `AccountHistoryDTO`
  - `CurrentBankAccountDTO`
  - `CreditDTO`, `DebitDTO`, `TransferRequestDTO`

#### 4. Gestion des Exceptions
Système de gestion des exceptions pour divers scénarios :
- **BalanceNotSufficientException** : Solde insuffisant.
- **BankAccountNotFoundException** : Compte introuvable.
- **CustomerNotFoundException** : Client introuvable.

#### 5. Mappers
Un mapper (**BankAccountMapperImpl**) permet la conversion entre objets entités et objets DTO.

#### 6. Services
Le service (**BankAccountServiceImpl**) encapsule la logique métier et assure la cohérence des données.

#### 7. Sécurité
- Configuration de Spring Security.
- Classes :
  - **SecurityConfig** : Gestion de la sécurité globale.
  - **SecurityController** : Authentification et autorisation.

![Tests](https://github.com/elmehdibenhayoun/digital_banking/blob/main/captures/test.png)

#### 8. Partie Web
- **CustomerRestController** et **BankAccountRestAPI** : Fournissent des points d'accès REST pour les fonctionnalités de l'application.

## Conclusion
Ce projet constitue une base solide pour développer une application bancaire moderne. Les composants ont été intégrés de manière cohérente, offrant une architecture robuste pour des évolutions futures et des ajouts fonctionnels.

