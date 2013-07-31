

OSGiSample
==========

För det här programmet har jag använt Eclipse Juno 4.2 med Equinox.


Jag använder consolen i Eclipse så följande bundles är är tillagda i Runtime configurations:
<ul>
  <li>org.apache.felix.gogo.command</li>
  <li>org.apache.felix.gogo.runtime</li>
  <li>org.apache.felix.gogo.shell</li>
  <li>org.eclipse.equinox.console</li>
  <li>org.osgi.framework</li>
</ul>

<strong>För att köra detta exempel:</strong>

1. Klona projektet eller ladda ner som zip
2. Se till att Eclipse har Eclipse Plug-in Development Environment installerat.
3. Importera projektet i Eclipse som ett plugin projekt
4. Ställ in rätt run configurations (Run->Run Configurations)
5. Kör programmet (Run->Run As->OSGi Framework)
<br/ >


Det kan finnas komplikationer i att ClassLoader inte hittar Activator classerna. <br/ >
Detta beror nog på att MANIFEST.MF inte har kännedom av klasserna.<br/ ><br/ >
Lösningen är att:

1. Öppna MANIEFEST.MF
2. Välj tabben Runtime.
3. Tillhöger finns Classpath, där trycker du på Add
4. lägg till mappen src/

<strong><i>Gör detta för alla bundles.</i></strong>

<strong>Upggiften</strong>

Upggiften gick ut på att använda någon implementation av OSGi ramverket för att generera tal 
enligt Fibonaccisekvensen.
Lösningen skulle antingen bestå av tre(två klienter och en tjänst) eller flera delar(OSGi bundles).
Klienterna ska ta del av tjänsten och skriva ut varsit nummer ur sekvensen:
<br/ >
<br/ >
Client-A: 1<br/ >
Client-B: 1<br/ >
Client-A: 2 <br/ >
....... 
<br/ >

Som extra tillägg kan man lägga till:
<ul>
  <li>Att man använder sig av konfigurationsmöjligheter i OSGi för att styra hur många tal ur sekvensen som
      varje klient ska hämta.</li>
  <li>Att man använder någon typ av persistens för att lagra aktuellt tillstånd för fibbonaccitjänsten för att
      att den ska kunna startas om utan att börja räkna om från början.</li>
 
</ul>


<title><strong>Lösning</strong> </title>

<p>Jag beslutade att använda mig av två klienter och en tjänst för att slutföra uppgiften. <br />
Jag lyckades med att ha två klienter som använder sig av en tjänst och skriver varsit nummer av Fibonacci sekvensen, <br />
och även med att tjänsten behåller sitt akutella tillstånd när den avslutas så att den inte börjar räkna om. <br />
Däremot fann jag det lite svårare att hitta ett sätt att kontrollera hur många nummer klienterna ska hämta från tjänsten, <br />
men har gjort ett upplägg för att det ska vara möjligt, fast jag är inte säker på om det är vad uppgiften kräver.<br />
Som lösning har jag en variabel som heter m_maxIterations som avgör hur många gånger den ska kalla service.getNextFib(). <br /> 
Men jag är medveten om att det finns andra lösningar, men valde denna metod för att avsluta uppgiften så snabbt och effektiv som möjligt. 
Om jag hade mer tid skulle jag definitivt läst på lite mer och utforskat olika möjligheter.
<br />
<br />
<i><strong>Tjänsten</i></strong>
<br/>
<br/>

En tjänst som heter FibonacciService som implementerar: 
<br />
<code> public interface FibonacciService{</code> <br/>
        <code>public long getNextFib();</code><br/>
      <code>}</code>
      
<br />
Detta interface implementeras senare i FibonacciServiceImpl.java som används i FibonacciActivator.java där <br />
tjänsten startas.
<br />
Jag har gjort en modifiering av detta interface och lagt till metoden: 
<br />
<br />
<code>
        public int getIndex();
      </code>
<br />
<br />
Denna metod används vid lagring av tjänstens tillstånd när den avslutas. Tjänsten använder sig av ett index <br />
för att hålla kolla på vilket nummer av Fibonacci sekvensen den ska skicka till klienten. Indexet 
finns i klassen FibonacciServiceImpl. <br />
Det finns en read och write metod för att spara tjänstens tillstånd till bundlens privata storage area, <br />
i det här fallet till en textfil kallad "log.txt". Detta görs när man manuellt stoppar tjänsten genom att skriva
"stop (bundle-id)" i konsolen.
</p>

<i><strong>Klienten</i></strong>
<p>

Klienterna printar ut enligt vad uppgiften begär och gör det med ett mellanrum av 1 sekund. <br/>
I deras start metod startas en tråd som använder sig av ServiceTracker för att hämta tjänsten och därefter numret. <br/>
Som jag nämnde tidigare finns det en variable (m_maxIterations) som man kan sätta som avgör hur många nummer från Fibonacci sekvensen
Klienten ska hämta. Klienten hämtar fyra som default.  
</p>

<strong>Sammanfattning</strong>
<p>
Det här var en rolig uppgift och det var roligt och lära sig något nytt. Det var utmanande och försöka sig på någonting som
inte har så mycket dokumentation på nätet som man är van vid. Som vidare läsning ska jag försöka läsa mer i böcker om OSGi
och försöka mig på lite fler uppgifter, först och främst lära mig hur man skulle kunna göra det jag inte riktigt lyckades 
med i den här uppgiften. Det har varit en rolig upplevelse, tack.
</p>
<br/>
MVH <br/>
Hatef Tadayon
