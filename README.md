# movesy-mobile

Android based application


# Haladási napló

## 5. hét

A hetet a ```Retrofit``` kliens megírásával kezdük. Ehhez az adatok kinyerését a response body-ból a ```Moshi``` nevű ORM-mel valósítottuk meg, így minden egyes adat sémához elkészítettük a hozzá tartozó data class-t a moshi annotationok-kel.
Ez után elkészítettük a retrofit interface-t amibe leírtuk a kérések metódusait és a paramétereiket, majd elkészítettük a kódból hívható implementációkat is amihez egy ```ResponseHandler``` mintát vettünk alapul.
Volt egy API interface összezavarodás a backend és közöttünk, ami percek alatt kiderült, hogy a routokban történt változások miatt volt, de a gyors és hatékony kommunikációnak köszönhetően villámgyorsan megoldódott.
Ezután elkészítettük a ```layout``` fileokat.
ELkezdtünk ismerkedni a ```dependency injectionnel``` dagger2 használatával.
