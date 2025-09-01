# Pécs Music Society Hivatalos Weboldala 

## A Readme tartalma:
- [Linkek]()
- [Adatbázis Terv]()
- [Backend]()
- [Frontend]()
- [Elérhetőség]()

## Linkek: 
- [Web Design](https://www.figma.com/design/fxJIfoxjFc7otjqGpiIQpa/proba_szoba_foglalo?node-id=325-383&t=zvvwznQyB1gXBha0-1)
- [Világos téma szín paletta](")
- [Sötét téma szín paletta](https://www.figma.com/design/iIxDa109pAa3JMpGKGZmu7/Color-wheel-palette-generator--Community-?node-id=0-1&p=f&t=4mIeJTMvo7smcMGR-0)
- [Bootstra dokumentáció](https://material.angular.dev/guide/getting-started)
- [Angular dokumentáció](https://angular.dev/overview)
- [Material dokumentáció](https://material.angular.dev/guide/getting-started)

## Adatbázis:

### Adatbázis terv:
!["Adatbázis terv"](./database_plan.png)

### Tárolt eljárások:
- **addReview():**
- **cancelReservation():**
- **closeBetweenTwoDate():**
- **closeSingleDay():**
- **getReservationByDate():**
- **getReservationByUserId():**
- **getReservedDateByMonth():**
- **getReservedDatesByDate():**
- **login():**
- **makeReservation():**
- **register():**

### Triggerek:
- **checkFullDay**: Figyeli, hogy az adott napon van-e 


## Backend: 
A spring boot projekt a következő **dependency**-ket tartalmazza: 
- Spring Web
- Spring Data JPA
- MySQL Driver
- Spring Boot Actuator
- Spring Boot DevTools
- Java Mail Sender
- Spring Security
- Javax Validation

A spring boot projekt a következő **controller**-eket tartalmazza:
- DeviceController
- NewsController
- OtherStuffController
- ReservationController
- UserController

A spring boot projekt a következő **service**-eket tartalmazza:
- DeviceService
- NewsService
- OtherStuffService
- ReservationService
- UserService

### Endpointok:

## Frontend:
A weblap a következő **componentekre** bontódik fel:
- **navbar:**
- **footer:**
- **home-page:**
- **news-card:**
- **review:**
    - **review-card:**
- **equipments:**
- **price-list:**
- **login-page:**
- **registration-page:**
- **profile-page:**
- **admin-page:**
- **appointment-selector:**
- **gallery:**
- **password-reset-page:**
- **reservation-card:**
- **reservation-maker-page:**
- **reservation-form:**
- **reservation-finalize:**

A frontend projektben a következő **service**-ek találhatóak meg:
- **device-service**
- **news-service**
- **other-stuff-service**
- **reservation-service**
- **user-service**

A frontent projektben a következő **model**-ek találhatóak meg:
- device
- deviceCategory
- galleryImage
- newsDetails
- reservation
- reservationType
- reservedDates
- reviewDetails
- user

## Elérhetőség:
- [Github]("")
- [Facebook]("")
- [Email]("")
- [LinkedIn]("")
- [Discord]("")