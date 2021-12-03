# Sockets

School project - Master MIAGE

# Sujet
On se propose de développer, de façon incrémentale, un service simplifié d’authentification basé sur des associations « login/password ». La figure ci-dessous illustre l’objectif fonctionnel à atteindre :

![alt text](https://articulateusercontent.com/rise/courses/mdpw3H3trtXOekyWKdPdidHTHxX7-9wQ/I5Xufgiykj6wA9tB.jpg)

Le service d’authentification (AS) gère localement de façon persistante une liste de paires « login/password ». Deux types de processus distants peuvent lui demander la réalisation d’opérations sur cette liste. Ils occupent un rôle de client de ce service.

Pour une paire « login/password » fournie en entrée par l’utilisateur, un processus client Checker permet d’en vérifier la validité auprès du serveur AS distant. Le second type de processus client, désigné par Manager s’adresse à un utilisateur administrateur. En plus de la fonctionnalité de vérification de validité, il permet de demander à l’AS l’ajout ou la suppression d’une paire ainsi que la modification du password associé à un login au sein d’une paire.

## Exemples d'échanges
```
// Requêtes vers serveur 
CHK username password
DEL username password
MOD username password
ADD username password

// Réponses du serveur
ERROR name_unknown
DONE
GOOD
```

Enfin, toute requête traitée par l’AS doit être journalisée auprès d’un service dédié dénommé Log (L). En plus de la requête elle-même, sont également enregistrés, l’horodatage correspondant ainsi que l’identifiant du client demandeur. Par identifiant, on sous-entend le triplet constitué par l’adresse IP de la machine sur laquelle s’exécute le processus client, le numéro de port de Transport utilisé par le processus client, ainsi que le protocole utilisé pour transporter la requête (TCP ou UDP). La composition des messages numérotés 10 sera détaillée par la suite
