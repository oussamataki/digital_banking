# Projet Spring Boot - Gestion Bancaire

## Introduction

Bienvenue dans le projet Spring Boot de Gestion Bancaire. L'objectif de ce projet est de développer une application de gestion bancaire complète en utilisant le framework Spring Boot. Chaque composant a été soigneusement conçu pour offrir une solution robuste et extensible.

## Contenu du Projet

### 1. Modélisation des Entités

Les entités du système ont été soigneusement définies pour refléter de manière précise les composants d'une institution bancaire. Les entités incluent Customer (client), CurrentAccount (compte courant), SavingAccount (compte d'épargne), BankAccount (compte bancaire) et AccountOperation (opération bancaire).

### 2. Repositories

Des repositories ont été créés pour chaque entité, permettant ainsi la manipulation des données stockées dans la base de données. Les interfaces telles que BankAccountRepository, CustomerRepository et AccountOperationRepository assurent la persistance des informations.

### 3. Enums et DTOs

Des énumérations ont été utilisées pour définir l'état des comptes (AccountStatus) et les types d'opérations (OperationType). Les Data Transfer Objects (DTOs) ont été développés pour faciliter la transmission des données entre les différentes couches de l'application. Parmi eux, on trouve CurrentBankAccountDTO, CustomerDTO, AccountHistoryDTO, AccountOperationDTO, BankAccountDTO, CreditDTO, DebitDTO et TransferRequestDTO.

### 4. Gestion des Exceptions

Nous avons mis en place un système de gestion des exceptions pour traiter divers scénarios, tels que le solde insuffisant (BalanceNotSufficientException), l'absence de compte bancaire (BankAccountNotFoundException) et l'absence de client (CustomerNotFoundException).

### 5. Mappers

Un mapper (BankAccountMapperImpl) a été créé pour assurer la conversion des objets entité en objets DTO .

### 6. Services

Le service (BankAccountServiceImpl) encapsule la logique métier liée aux opérations bancaires, garantissant ainsi la cohérence des données.

### 7. Sécurité

La sécurité de l'application a été renforcée grâce à la configuration de Spring Security. Les classes SecurityConfig et SecurityController ont été mises en place pour gérer l'authentification et l'autorisation des utilisateurs.
![Gestion des Produits](https://github.com/elmehdibenhayoun/digital_banking/blob/main/captures/test.png)

### 8. Partie Web

Les contrôleurs CustomerRestController et BankAccountRestAPI ont été développés pour fournir des points d'accès aux fonctionnalités de l'application via des API REST, facilitant ainsi l'intégration avec d'autres systèmes ou interfaces utilisateur.

## Conclusion

Ce projet constitue une base solide pour le développement d'une application de gestion bancaire moderne. Chaque composant a été intégré de manière cohérente, fournissant une structure robuste pour les développements futurs et les améliorations fonctionnelles.
