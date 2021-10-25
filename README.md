# movesy-mobile

Android based application


# Haladási napló

## 5. hét

A hetet a ```Retrofit``` kliens megírásával kezdtük. Ehhez az adatok kinyerését a response body-ból a ```Moshi``` nevű ORM-mel valósítottuk meg, így minden egyes adat sémához elkészítettük a hozzá tartozó data class-t a moshi annotationok-kel.
Ez után elkészítettük a retrofit interface-t amibe leírtuk a kérések metódusait és a paramétereiket, majd elkészítettük a kódból hívható implementációkat is amihez egy ```ResponseHandler``` mintát vettünk alapul.
Volt egy API interface összezavarodás a backend és közöttünk, ami percek alatt kiderült, hogy a routokban történt változások miatt volt, de a gyors és hatékony kommunikációnak köszönhetően villámgyorsan megoldódott.
Ezután elkészítettük a ```layout``` fileokat.
ELkezdtünk ismerkedni a ```dependency injectionnel``` dagger2 használatával.

## 6. hét

A héten a projekt arhitektúráját megváltoztattuk, hogy megegyezzen a Google által ajánlott ```MVVM``` arhitektúrával.
Így a beérkező adat egy repository-n keresztül cashelődik egy perzistens ```Room``` adatbázisba, mivel a Roomnak van `Live data` támogatása, ezért amint megérkezik a friss adat az Api-ból a nézetek is frissülnek.
A daggert lecseréltük `Hilt`-re.

Teszteket is készítettünk:
- Unit teszteket a Retrofit kliens teszteléséhez, `MockWebServert` használva.
- Integration tesztet a Room adatbázis hívásaira, amiben a Hiltet használjuk a függőségek feloldására.
