# GGTodo API
GGTodo è un'API REST per la gestione di task personali, progettata per consentire agli utenti di creare, aggiornare, visualizzare e gestire task. Il progetto è realizzato con Spring Boot e include endpoint sicuri basati su sessioni per la gestione degli utenti e delle task.

## Caratteristiche
- **Registrazione e Login dell'utente**: Gli utenti possono registrarsi e accedere per gestire le proprie task.
- **Gestione delle task**: Creazione, aggiornamento, eliminazione, completamento e visualizzazione delle task.
- **Validazione dei dati**: Controllo e gestione degli errori di validazione per garantire l'integrità dei dati.
- **Controllo basato su sessione**: Solo gli utenti autenticati possono accedere e gestire le proprie task.


## Tecnologie utilizzate
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven

## Installazione
1. Clona il repository:
    ```bash
    git clone https://github.com/MicheleDiMezzaCutillo/gg-todo.git
    cd GGToDo
    ```
2. **Configura il database**: Il progetto utilizza un database H2 di default per lo sviluppo. Per configurare un altro database, aggiorna le proprietà nel file `application.properties`.
3. **Compila e avvia l'applicazione**:
    ```bash
   mvn spring-boot:run
   ```
4. L'applicazione sarà disponibile su `http://localhost:8080`.

# Endpoint
### Endpoint

### Autenticazione

- **POST** `/api/auth/register` - Registra un nuovo utente.
    - **Body**: `{"username": "user", "password": "password"}`
- **POST** `/api/auth/login` - Esegue il login.
    - **Body**: `{"username": "user", "password": "password"}`
- **GET** `/api/auth/logout` - Esegue il logout dell'utente attuale.
- **GET** `/api/auth/current` - Ottiene il json dell'utente loggato corrente.

### Task

Tutti gli endpoint delle task richiedono che l'utente sia autenticato.

- **POST** `/api/task` - Crea una nuova task.
    - **Body**: `{"title": "Titolo", "description": "Descrizione"}`
- **GET** `/api/task` - Ottiene tutte le task dell'utente.
- **GET** `/api/task/{id}` - Ottiene i dettagli di una task specifica tramite il suo `id`.
- **PUT** `/api/task/{id}` - Aggiorna una task esistente.
    - **Body**: `{"title": "Nuovo Titolo", "description": "Nuova Descrizione"}`
- **PATCH** `/api/task/{id}` - Inverte lo stato di completamento di una task.
- **DELETE** `/api/task/{id}` - Elimina una task.

### Esempi di risposta

Ecco alcune risposte comuni:

- **400 BAD REQUEST**: Se i dati forniti non sono validi o sono mancanti.
- **401 UNAUTHORIZED**: Se l'utente non è autenticato.
- **404 NOT FOUND**: Se la risorsa richiesta non esiste.

## Errori di validazione
Gli errori di validazione restituiscono un messaggio con i dettagli delle violazioni.

# Struttura del Progetto
```graphql
├── src
│   ├── main
│   │   ├── java
│   │   │   └── it.mikedmc.ggtodo
│   │   │       ├── controller      # Contiene i controller REST.
│   │   │       ├── dto             # Contiene i DTO per i dati dell'API.
│   │   │       │   └── builder     # Contiene i builder per interagire con i DTO comodamente.
│   │   │       ├── model           # Contiene le entità di dominio.
│   │   │       ├── repository      # Contiene le interfacce per l'accesso ai dati.
│   │   │       └── service         # Contiene la logica di business.
│   │   └── resources
│   │       └── application.properties # File di configurazione.

```

# Contributi
Sono benvenuti i contributi al progetto! Sentiti libero di inviare una pull request o aprire issue per suggerimenti e bug.