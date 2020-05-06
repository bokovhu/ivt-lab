# A hajó a legelső lövés leadásakor az elsődleges torpedóvetőt használja

* Bemenet: Egy újonnan inicializált GT4500 példány
* A teszt lépései
  * SINGLE módban leadunk egy lövést
* Elvárt viselkedés
  * Az elsődleges torpedóvető elsütésre kerül pontosan 1 alkalommal
  * A másodlagos torpedóvető nem kerül elsütésre

# Egylövetű üzemmódban a hajó alternál az elsődleges és a másodlagos torpedóvetők használata között

* Bemenet: Egy újonnan inicializált GT4500 példány
* A teszt lépései
  * SINGLE módban leadunk egy lövést
  * Ezután ismét leadunk SINGLE módban egy lövést
  * Végezetül leadunk egy utolsó lövést SINGLE módban
* Elvárt viselkedés
  * Az elsődleges torpedóvető összesen kétszer kerül elsütésre
  * A másodlagos torpedóvető egyszer kerül elsütésre
  * A torpedóvetők elsődleges-másodlagos-elsődleges sorrendben kerülnek elsütésre

# Ha nem sikerült SINGLE módban egy torpedót se kilőni, mert mindkét torpedóvető üres, a tüzelés kimenete HAMIS érték

* Bemenet
  * Egy újonnan inicializált GT4500 példány
  * Két, általunk hamisított torpedóvető, melyeket beinjektáljuk a GT4500 példányba
* A teszt lépései
  * Mindkét torpedóvető üres állapotot jelez
  * Leadunk egy lövést SINGLE módban
* Elvárt viselkedés
  * A lövés leadása HAMIS értékkel tér vissza
  * A tüzelés nem lett megkísérelve egyetlen torpedóvető esetén sem

# Ha kezdeti állapotban SINGLE módban leadott lövéskor az elsődleges torpedóvető hibát jelez, a másodlagos torpedóvető nem kerül elsütésre, és a tüzelés kimenete HAMIS érték

* Bemenet
  * Egy újonnan inicializált GT4500 példány
  * Két, általunk hamisított torpedóvető, melyeket beinjektáljuk a GT4500 példányba
* A teszt lépései
  * Amikor az elsődleges torpedóvető elsütésre kerül, az HAMIS értékkel tér vissza
  * Leadunk egy lövést SINGLE módban
* Elvárt viselkedés
  * A lövés leadása HAMIS értékkel tér vissza
  * Az elsődleges torpedóvető elsütésre kerül
  * A másodlagos torpedóvető nem került elsütésre

# ALL tüzelési mód használatakor meghibásodott elsődleges torpedóvető esetén is elsütésre kerül a másodlagos torpedóvető

* Bemenet
  * Egy újonnan inicializált GT4500 példány
  * Két, általunk hamisított torpedóvető, melyeket beinjektáljuk a GT4500 példányba
* A teszt lépései
  * Amikor az elsődleges torpedóvető elsütésre kerül, az HAMIS értékkel tér vissza
  * A másodlagos torpedóvető elsütésekor IGAZ értékkel tér vissza
  * Leadunk egy lövést ALL módban
* Elvárt viselkedés
  * A lövés leadása IGAZ értékkel tér vissza
  * Az elsődleges torpedóvető elsütésre kerül (de az sikertelen)
  * A másodlagos torpedóvető is elsütésre kerül