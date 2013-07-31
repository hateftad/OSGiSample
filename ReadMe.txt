


F�r det h�r programmet har jag anv�nt Eclipse Juno 4.2 med Equinox.

Jag anv�nder consolen i Eclipse s� f�ljande bundles �r �r tillagda i Runtime configurations:

org.apache.felix.gogo.command
org.apache.felix.gogo.runtime
org.apache.felix.gogo.shell
org.eclipse.equinox.console
org.osgi.framework
F�r att k�ra detta exempel:

Klona projektet eller ladda ner som zip
Se till att Eclipse har Eclipse Plug-in Development Environment installerat.
Importera projektet i Eclipse som ett plugin projekt
St�ll in r�tt run configurations (Run->Run Configurations)
K�r programmet (Run->Run As->OSGi Framework) 
Det kan finnas komplikationer i att ClassLoader inte hittar Activator classerna. 
Detta beror nog p� att MANIFEST.MF inte har k�nnedom av klasserna.

L�sningen �r att:

G�r detta f�r alla bundles.

�ppna MANIEFEST.MF
V�lj tabben Runtime.
Tillh�ger finns Classpath, d�r trycker du p� Add
l�gg till mappen src/
Upggiften

Upggiften gick ut p� att anv�nda n�gon implementation av OSGi ramverket f�r att generera tal enligt Fibonaccisekvensen. L�sningen skulle antingen best� av tre(tv� klienter och en tj�nst) eller flera delar(OSGi bundles). Klienterna ska ta del av tj�nsten och skriva ut varsit nummer ur sekvensen: 

Client-A: 1
Client-B: 1
Client-A: 2 
....... 

Som extra till�gg kan man l�gga till:

Att man anv�nder sig av konfigurationsm�jligheter i OSGi f�r att styra hur m�nga tal ur sekvensen som varje klient ska h�mta.
Att man anv�nder n�gon typ av persistens f�r att lagra aktuellt tillst�nd f�r fibbonaccitj�nsten f�r att att den ska kunna startas om utan att b�rja r�kna om fr�n b�rjan.
L�sning
Jag beslutade att anv�nda mig av tv� klienter och en tj�nst f�r att slutf�ra uppgiften. 
Jag lyckades med att ha tv� klienter som anv�nder sig av en tj�nst och skriver varsit nummer av Fibonacci sekvensen, 
och �ven med att tj�nsten beh�ller sitt akutella tillst�nd n�r den avslutas s� att den inte b�rjar r�kna om. 
D�remot fann jag det lite sv�rare att hitta ett s�tt att kontrollera hur m�nga nummer klienterna ska h�mta fr�n tj�nsten, 
men har gjort ett uppl�gg f�r att det ska vara m�jligt, fast jag �r inte s�ker p� om det �r vad uppgiften kr�ver.
Som l�sning har jag en variabel som heter m_maxIterations som avg�r hur m�nga g�nger den ska kalla service.getNextFib(). 
Men jag �r medveten om att det finns n�gon annan l�sning, kanske genom att anv�nda sig av n�gon implementering av Configuration Admin Service. Om jag hade mer tid skulle jag definitivt l�st p� lite mer och utforskat olika m�jligheter. 

Tj�nsten 

En tj�nst som heter FibonacciService som implementerar: 
public interface FibonacciService{ 
public long getNextFib();
} 
Detta interface implementeras senare i FibonacciServiceImpl.java som anv�nds i FibonacciActivator.java d�r 
tj�nsten startas. 
Jag har gjort en modifiering av detta interface och lagt till metoden: 

public int getIndex(); 

Denna metod anv�nds vid lagring av tj�nstens tillst�nd n�r den avslutas. Tj�nsten anv�nder sig av ett index 
f�r att h�lla kolla p� vilket nummer av Fibonacci sekvensen den ska skicka till klienten. Indexet finns i klassen FibonacciServiceImpl. 
Det finns en read och write metod f�r att spara tj�nstens tillst�nd till bundlens privata storage area, 
i det h�r fallet till en textfil kallad "log.txt". Detta g�rs n�r man manuellt stoppar tj�nsten genom att skriva "stop (bundle-id) " i konsolen.

Klienten

Klienterna printar ut enligt vad uppgiften beg�r och g�r det med ett mellanrum av 3 sekunder. 
I deras start metod startas en tr�d som anv�nder sig av ServiceTracker f�r att h�mta tj�nsten och d�refter numret. 
Som jag n�mnde tidigare finns det en variable (m_maxIterations) som man kan s�tta som avg�r hur m�nga nummer fr�n Fibonacci sekvensen Klienten ska h�mta. Klienten h�mtar fyra som default.

Sammanfattning

Det h�r var en rolig uppgift och det var roligt och l�ra sig n�got nytt. Det var utmanande och f�rs�ka sig p� n�gonting som inte har s� mycket dokumentation p� n�tet som man �r van vid. Som vidare l�sning ska jag f�rs�ka l�sa mer i b�cker om OSGi och f�rs�ka mig p� lite fler uppgifter, f�rst och fr�mst l�ra mig hur man skulle kunna g�ra det jag inte riktigt lyckades med i den h�r uppgiften. Det har varit en rolig upplevelse, tack.


MVH Hatef Tadayon