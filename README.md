# movesy-mobile

Android based application


# Haladási napló

## 5. hét

A hetet a ```Retrofit``` kliens megírásával kezdtük. Ehhez az adatok kinyerését a response body-ból a ```Moshi``` nevű ORM-mel valósítottuk meg, így minden egyes adat sémához elkészítettük a hozzá tartozó data class-t a moshi annotationok-kel.
Ez után elkészítettük a retrofit interface-t amibe leírtuk a kérések metódusait és a paramétereiket, majd elkészítettük a kódból hívható implementációkat is amihez egy ```ResponseHandler``` mintát vettünk alapul.
Volt egy API interface összezavarodás a backend és közöttünk, ami percek alatt kiderült, hogy a routokban történt változások miatt volt, de a gyors és hatékony kommunikációnak köszönhetően villámgyorsan megoldódott.
Ezután elkészítettük a ```layout``` fileokat.
Elkezdtünk ismerkedni a ```dependency injectionnel``` dagger2 használatával.

## 6. hét

A héten a projekt arhitektúráját megváltoztattuk, hogy megegyezzen a Google által ajánlott ```MVVM``` arhitektúrával.
Így a beérkező adat egy repository-n keresztül cashelődik egy perzistens ```Room``` adatbázisba, mivel a Roomnak van `Live data` támogatása, ezért amint megérkezik a friss adat az Api-ból a nézetek is frissülnek.
A daggert lecseréltük `Hilt`-re.
Rájöttünk, hogy a callback-ek helyett sokkal jobban tesztelhető kódot kapunk ha suspend-et használunk.

Teszteket is készítettünk:
- Unit teszteket a Retrofit kliens teszteléséhez, `MockWebServert` használva.
- Integration tesztet a Room adatbázis hívásaira, amiben a Hiltet használjuk a függőségek feloldására.

## 7-8. hét

Ebben a két hétben elkészítettük a különböző oldalak közötti navigációt a `Navigation Component` segítségével, ebből kettőt is használunk a külső főmenühöz és annak a lapjaihoz egyet,  a belső MyOrders menüpont és benne navigálható nézetekhez még egyet.
Elkészítettük emellett a `RecycleViewAdaptereket` így a listák már működnek és az elemek lenyithatók.
A nézetekhez `Fragmenteket` és `ViewModeleket` elkészítettünk és már a logikát is elkezdtük hozzáadni a viewmodelekhez.

## 9. hét

Ezen a héten az authenticationt integráltuk be az applikációba. 
Egy `Interceptor` mintát hasznáva oldottuk meg végül a token headerbe illesztését, így minden üzenetre felkerül az azonosítás minden nagyobb kódmódosítás nélkül.
Emellett a `MeditatorLiveDatát` is felfedeztük, ami nagyon hasznos a komplexebb osztályok figyelésére.
A locationok és a címek feloldását `Geocoding` használatával elkészítettük. 

