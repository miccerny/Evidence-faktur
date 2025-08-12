# Evidence faktur
 Evidence faktur včetně prodejce a kupujícího

> Aktuální verze projektu je dostupná ve větvi `Login-Registration-authentication`
> Pracuji na větvi `Login-Registration-autorization` - kde probíhá implementace zobrazení osob a faktur dle přihlášeného typu uživatele (anonymní, přihlášený, admin)

## Použité technologie
- **Java 17** - hlavní jazyk projektu.
- **Spring boot** - framework pro rychlý vývoj REST API.
- **PostgreSQL** - relační databáze pro ukládání firem a faktur.
- **React** - frontendová knihovna pro tvorbu interaktivního uživatelského rozhraní.
- **Bootstrap 5** — knihovna pro responzivní a moderní vzhled.
- **HTML/CSS** — struktura a styling uživatelského rozhraní.


## Aplikace obsahuje kompletní správu (CRUD) faktur a firem/osob (např. "Web develop") a faktur (např. "Vytvoření webové stránky"):
 - Vytvoření osoby nebo firmy
 - Vytvoření faktury
 - Zobrazení detailu osoby/firmy
 - Zobrazení detailu faktur
 - Zobrazení seznamu osob/firem
 - Zobrazení seznamu faktur
 - Odstranění oosby/firmy
 - Odstranění faktury
 - Editace faktury
 - Edita osoby/firmy
 - Podpora rozlišení uživatelů
 - Entity jsou uloženeé v databázi
 - Zobrazení statistiky příjmů všech firem/osob
 - Zobrazení všech vystavených nebo přijatých faktur

## Jak spustit projekt
1. Instalace PostgreSQL
2. Naklonování repozitáře
      ```bash
   git clone https://github.com/miccerny/Evidence-faktur.git
  
3. Otevřít projekt v IDE
4. Instalace node.js
5. Instalace knihoven node_modules
6. Spustit

## Ukázky aplikace
### Náhled faktur
![Evidence faktur - screenshot/Osoby, firmy.PNG](https://github.com/miccerny/Evidence-faktur/blob/Login-Registration-authentication/Evidence%20faktur%20-%20screenshot/N%C3%A1hled%20faktur.PNG)

### Náhled osob či firem
![https://github.com/miccerny/Evidence-faktur/blob/Login-Registration-authentication/Evidence%20faktur%20-%20screenshot/Osoby%2C%20firmy.PNG](https://github.com/miccerny/Evidence-faktur/blob/Login-Registration-authentication/Evidence%20faktur%20-%20screenshot/Osoby,%20firmy.PNG?raw=true)

### Přihlášení
![https://github.com/miccerny/Evidence-faktur/blob/Login-Registration-authentication/Evidence%20faktur%20-%20screenshot/P%C5%99ihl%C3%A1%C5%A1en%C3%AD.PNG?raw=true](https://github.com/miccerny/Evidence-faktur/blob/Login-Registration-authentication/Evidence%20faktur%20-%20screenshot/P%C5%99ihl%C3%A1%C5%A1en%C3%AD.PNG?raw=true)

### Statistika faktur
![https://github.com/miccerny/Evidence-faktur/blob/Login-Registration-authentication/Evidence%20faktur%20-%20screenshot/Sattistika%20faktur.PNG?raw=true](https://github.com/miccerny/Evidence-faktur/blob/Login-Registration-authentication/Evidence%20faktur%20-%20screenshot/Sattistika%20faktur.PNG?raw=true)

### Statistika osob
![https://github.com/miccerny/Evidence-faktur/blob/Login-Registration-authentication/Evidence%20faktur%20-%20screenshot/Sattistika%20osob.PNG?raw=true](https://github.com/miccerny/Evidence-faktur/blob/Login-Registration-authentication/Evidence%20faktur%20-%20screenshot/Sattistika%20osob.PNG?raw=true)

##Poznámky
Ukázky aplikace ještě dodám
###Možné rozšíření nebo změna aplikace
- zobrazení faktur jen pro přihlášené uživatele a konkrétnímu uživateli dle ID nebo emailu

