


För det här programmet har jag använt Eclipse Juno 4.2 med Equinox.

Jag använder consolen i Eclipse så följande bundles är är tillagda i Runtime configurations:

org.apache.felix.gogo.command
org.apache.felix.gogo.runtime
org.apache.felix.gogo.shell
org.eclipse.equinox.console
org.osgi.framework
För att köra detta exempel:

Klona projektet eller ladda ner som zip
Se till att Eclipse har Eclipse Plug-in Development Environment installerat.
Importera projektet i Eclipse som ett plugin projekt
Ställ in rätt run configurations (Run->Run Configurations)
Kör programmet (Run->Run As->OSGi Framework) 
Det kan finnas komplikationer i att ClassLoader inte hittar Activator classerna. 
Detta beror nog på att MANIFEST.MF inte har kännedom av klasserna.

Lösningen är att:

Gör detta för alla bundles.

Öppna MANIEFEST.MF
Välj tabben Runtime.
Tillhöger finns Classpath, där trycker du på Add
lägg till mappen src/
Upggiften

Upggiften gick ut på att använda någon implementation av OSGi ramverket för att generera tal enligt Fibonaccisekvensen. Lösningen skulle antingen bestå av tre(två klienter och en tjänst) eller flera delar(OSGi bundles). Klienterna ska ta del av tjänsten och skriva ut varsit nummer ur sekvensen: 

Client-A: 1
Client-B: 1
Client-A: 2 
....... 

Som extra tillägg kan man lägga till:

Att man använder sig av konfigurationsmöjligheter i OSGi för att styra hur många tal ur sekvensen som varje klient ska hämta.
Att man använder någon typ av persistens för att lagra aktuellt tillstånd för fibbonaccitjänsten för att att den ska kunna startas om utan att börja räkna om från början.
Lösning
Jag beslutade att använda mig av två klienter och en tjänst för att slutföra uppgiften. 
Jag lyckades med att ha två klienter som använder sig av en tjänst och skriver varsit nummer av Fibonacci sekvensen, 
och även med att tjänsten behåller sitt akutella tillstånd när den avslutas så att den inte börjar räkna om. 
Däremot fann jag det lite svårare att hitta ett sätt att kontrollera hur många nummer klienterna ska hämta från tjänsten, 
men har gjort ett upplägg för att det ska vara möjligt, fast jag är inte säker på om det är vad uppgiften kräver.
Som lösning har jag en variabel som heter m_maxIterations som avgör hur många gånger den ska kalla service.getNextFib(). 
Men jag är medveten om att det finns någon annan lösning, kanske genom att använda sig av någon implementering av Configuration Admin Service. Om jag hade mer tid skulle jag definitivt läst på lite mer och utforskat olika möjligheter. 

Tjänsten 

En tjänst som heter FibonacciService som implementerar: 
public interface FibonacciService{ 
public long getNextFib();
} 
Detta interface implementeras senare i FibonacciServiceImpl.java som används i FibonacciActivator.java där 
tjänsten startas. 
Jag har gjort en modifiering av detta interface och lagt till metoden: 

public int getIndex(); 

Denna metod används vid lagring av tjänstens tillstånd när den avslutas. Tjänsten använder sig av ett index 
för att hålla kolla på vilket nummer av Fibonacci sekvensen den ska skicka till klienten. Indexet finns i klassen FibonacciServiceImpl. 
Det finns en read och write metod för att spara tjänstens tillstånd till bundlens privata storage area, 
i det här fallet till en textfil kallad "log.txt". Detta görs när man manuellt stoppar tjänsten genom att skriva "stop (bundle-id) " i konsolen.

Klienten

Klienterna printar ut enligt vad uppgiften begär och gör det med ett mellanrum av 3 sekunder. 
I deras start metod startas en tråd som använder sig av ServiceTracker för att hämta tjänsten och därefter numret. 
Som jag nämnde tidigare finns det en variable (m_maxIterations) som man kan sätta som avgör hur många nummer från Fibonacci sekvensen Klienten ska hämta. Klienten hämtar fyra som default.

Sammanfattning

Det här var en rolig uppgift och det var roligt och lära sig något nytt. Det var utmanande och försöka sig på någonting som inte har så mycket dokumentation på nätet som man är van vid. Som vidare läsning ska jag försöka läsa mer i böcker om OSGi och försöka mig på lite fler uppgifter, först och främst lära mig hur man skulle kunna göra det jag inte riktigt lyckades med i den här uppgiften. Det har varit en rolig upplevelse, tack.


MVH Hatef Tadayon