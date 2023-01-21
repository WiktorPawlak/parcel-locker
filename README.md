# PAS
Podstawy aplikacji sieciowych

## Uruchamianie aplikacji

1. Stworzyć konfigurację serwera GlassFish
   1. w zakładce `Server` ustawić domenę na `domain1`,
   2. w zakładce `Deployment` wybrać dwa artefakty "war exploded" reprezentujące oba moduły.
2. Z poziomu katalogu głównego uruchomić kontener:
```
docker compose up -d
```
3. Uruchomić przygotowaną wcześniej konfigurację.
4. Strona główna powinna znaleźć się pod adresem: `http://localhost:8080/parcel-locker-mvc-1.0-SNAPSHOT/faces/index.xhtml`