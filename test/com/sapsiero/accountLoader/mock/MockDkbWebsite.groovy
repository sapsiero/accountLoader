package com.sapsiero.accountLoader.mock

import com.gargoylesoftware.htmlunit.BrowserVersion
import com.sapsiero.accountLoader.Website

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/28/12
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
class MockDkbWebsite extends Website {

    public MockDkbWebsite() {
        super(BrowserVersion.FIREFOX_3_6, '')
    }

    public def result1 = """<accounts banknumber='BYLADEM1001'>
  <account number='ABCDEFGHI' type='Cash' currency='EUR'>
    <balance date='2012-01-28' value='1234.56' />
    <transaction bookingdate='2012-01-24' tradedate='2012-01-24' message='24.01.2012; KREDITKARTENABRECHNUNG; VISA-ABR. 1234567890123456 ' value='-2207.09' currency='EUR' fee='0.0' accountvalue='-2207.09' />
    <transaction bookingdate='2012-01-23' tradedate='2012-01-23' message='23.01.2012; BLABLABLA VEREIN; 123456789012, VNR. 1234567 1234567-1234, ZV TARIF N 123,12 EUR 01.2012 ' value='-150.00' currency='EUR' fee='0.0' accountvalue='-150.00' />
    <transaction bookingdate='2012-01-23' tradedate='2012-01-23' message='23.01.2012; MUELLER MEIER SCHMIDT; VIELEN DANK ' value='1130.40' currency='EUR' fee='0.0' accountvalue='1130.40' />
    <transaction bookingdate='2012-01-16' tradedate='2012-01-16' message='16.01.2012; ABC GMBH; NR 12-12345-1234566 DATUM 15.01.2012, 14.46 UHR 1.TAN 123456 ' value='-1016.32' currency='EUR' fee='0.0' accountvalue='-1016.32' />
    <transaction bookingdate='2012-01-10' tradedate='2012-01-10' message='10.01.2012; DANKE DANKE; 123121121212121212121212121 12121121/1212121212/12 ' value='-56.92' currency='EUR' fee='0.0' accountvalue='-56.92' />
    <transaction bookingdate='2012-01-02' tradedate='2012-01-02' message='02.01.2012; FIRMA ABC; 121212121212121 121212121 HEUTEHEUTE 6,32 ' value='-6.32' currency='EUR' fee='0.0' accountvalue='-6.32' />
    <transaction bookingdate='2012-01-02' tradedate='2012-01-02' message='02.01.2012; FIRMA DEF; 121212121212 12121212/1212/     75,03/AB ' value='-75.03' currency='EUR' fee='0.0' accountvalue='-75.03' />
    <transaction bookingdate='2012-01-02' tradedate='2012-01-02' message='02.01.2012; AMAZON DOWNLOADS; 1212121212121211 ' value='-17.28' currency='EUR' fee='0.0' accountvalue='-17.28' />
    <transaction bookingdate='2011-12-30' tradedate='2011-12-30' message='30.12.2011; SCHMIDT MEIER MUELER; GEMEINSCHAFTSKONTO ' value='-900.00' currency='EUR' fee='0.0' accountvalue='-900.00' />
    <transaction bookingdate='2011-12-30' tradedate='2012-01-01' message='01.01.2012; ; Abrechnung 31.12.2011 Information zur Abrechnung Kontostand am 30.12.2011 3.456,78 +  -------------- Abrechnungszeitraum vom 01. 10.2011 bis 31.12.2011 Zinsen für Guthaben 1,11H  0,5000 v.H. Haben-Zins bis  30.12.2011 Kapitalertragsteuer 1,11S Solidaritätszuschlag 1,11S ' value='3.33' currency='EUR' fee='0.0' accountvalue='3.33' />
  </account>
</accounts>"""

    public def result2 = """<accounts banknumber='BYLADEM1001'>
  <account number='DEFGHIJKL' type='Credit' currency='EUR'>
    <balance date='2012-01-28' value='0.0' />
    <transaction bookingdate='2012-01-21' tradedate='2012-01-20' message='Lastschrift' value='2207.09' currency='EUR' fee='0.0' accountvalue='2207.09' />
  </account>
</accounts>"""

    public def result3 = """<accounts banknumber='BYLADEM1001'>
  <account number='ABCDEFGHI' type='Cash' currency='EUR'>
    <balance date='2012-01-28' value='1234.56' />
    <transaction bookingdate='2012-01-24' tradedate='2012-01-24' message='24.01.2012; KREDITKARTENABRECHNUNG; VISA-ABR. 1234567890123456 ' value='-2207.09' currency='EUR' fee='0.0' accountvalue='-2207.09' />
    <transaction bookingdate='2012-01-23' tradedate='2012-01-23' message='23.01.2012; BLABLABLA VEREIN; 123456789012, VNR. 1234567 1234567-1234, ZV TARIF N 123,12 EUR 01.2012 ' value='-150.00' currency='EUR' fee='0.0' accountvalue='-150.00' />
    <transaction bookingdate='2012-01-23' tradedate='2012-01-23' message='23.01.2012; MUELLER MEIER SCHMIDT; VIELEN DANK ' value='1130.40' currency='EUR' fee='0.0' accountvalue='1130.40' />
    <transaction bookingdate='2012-01-16' tradedate='2012-01-16' message='16.01.2012; ABC GMBH; NR 12-12345-1234566 DATUM 15.01.2012, 14.46 UHR 1.TAN 123456 ' value='-1016.32' currency='EUR' fee='0.0' accountvalue='-1016.32' />
    <transaction bookingdate='2012-01-10' tradedate='2012-01-10' message='10.01.2012; DANKE DANKE; 123121121212121212121212121 12121121/1212121212/12 ' value='-56.92' currency='EUR' fee='0.0' accountvalue='-56.92' />
    <transaction bookingdate='2012-01-02' tradedate='2012-01-02' message='02.01.2012; FIRMA ABC; 121212121212121 121212121 HEUTEHEUTE 6,32 ' value='-6.32' currency='EUR' fee='0.0' accountvalue='-6.32' />
    <transaction bookingdate='2012-01-02' tradedate='2012-01-02' message='02.01.2012; FIRMA DEF; 121212121212 12121212/1212/     75,03/AB ' value='-75.03' currency='EUR' fee='0.0' accountvalue='-75.03' />
    <transaction bookingdate='2012-01-02' tradedate='2012-01-02' message='02.01.2012; AMAZON DOWNLOADS; 1212121212121211 ' value='-17.28' currency='EUR' fee='0.0' accountvalue='-17.28' />
    <transaction bookingdate='2011-12-30' tradedate='2011-12-30' message='30.12.2011; SCHMIDT MEIER MUELER; GEMEINSCHAFTSKONTO ' value='-900.00' currency='EUR' fee='0.0' accountvalue='-900.00' />
    <transaction bookingdate='2011-12-30' tradedate='2012-01-01' message='01.01.2012; ; Abrechnung 31.12.2011 Information zur Abrechnung Kontostand am 30.12.2011 3.456,78 +  -------------- Abrechnungszeitraum vom 01. 10.2011 bis 31.12.2011 Zinsen für Guthaben 1,11H  0,5000 v.H. Haben-Zins bis  30.12.2011 Kapitalertragsteuer 1,11S Solidaritätszuschlag 1,11S ' value='3.33' currency='EUR' fee='0.0' accountvalue='3.33' />
  </account>
  <account number='DEFGHIJKL' type='Credit' currency='EUR'>
    <balance date='2012-01-28' value='0.0' />
    <transaction bookingdate='2012-01-21' tradedate='2012-01-20' message='Lastschrift' value='2207.09' currency='EUR' fee='0.0' accountvalue='2207.09' />
  </account>
</accounts>"""

    public def result4 = """<accounts banknumber='BYLADEM1001'>
  <account number='DEFGHIJKL' type='Credit' currency='EUR'>
    <balance date='2012-01-28' value='0.0' />
    <transaction bookingdate='2012-01-02' tradedate='2011-12-31' message='BOOK' value='-27.60' currency='USD' fee='-0.37' accountvalue='-21.74' />
    <transaction bookingdate='2011-12-29' tradedate='2011-12-28' message='JKL' value='-207.60' currency='EUR' fee='0.0' accountvalue='-207.60' />
    <transaction bookingdate='2011-12-29' tradedate='2011-12-28' message='JKL' value='-20.00' currency='EUR' fee='0.0' accountvalue='-20.00' />
    <transaction bookingdate='2011-12-23' tradedate='2011-12-22' message='Lastschrift' value='663.49' currency='EUR' fee='0.0' accountvalue='663.49' />
    <transaction bookingdate='2011-12-19' tradedate='2011-12-17' message='BANK ABC' value='-200.00' currency='EUR' fee='0.0' accountvalue='-200.00' />
    <transaction bookingdate='2011-12-19' tradedate='2011-12-18' message='BANK ABC' value='-200.00' currency='EUR' fee='0.0' accountvalue='-200.00' />
    <transaction bookingdate='2011-12-12' tradedate='2011-12-09' message='BANK GHI' value='-200.00' currency='EUR' fee='0.0' accountvalue='-200.00' />
    <transaction bookingdate='2011-11-28' tradedate='2011-11-25' message='CINEMA' value='-17.60' currency='EUR' fee='0.0' accountvalue='-17.60' />
    <transaction bookingdate='2011-11-28' tradedate='2011-11-26' message='BOOKSTORE' value='-28.17' currency='USD' fee='-0.37' accountvalue='-21.68' />
    <transaction bookingdate='2011-11-23' tradedate='2011-11-22' message='Lastschrift' value='322.30' currency='EUR' fee='0.0' accountvalue='322.30' />
    <transaction bookingdate='2011-11-23' tradedate='2011-11-22' message='Interest' value='86.52' currency='EUR' fee='0.0' accountvalue='86.52' />
    <transaction bookingdate='2011-11-23' tradedate='2011-11-22' message='TAX' value='-21.15' currency='EUR' fee='0.0' accountvalue='-21.15' />
    <transaction bookingdate='2011-11-23' tradedate='2011-11-22' message='TAX' value='-1.16' currency='EUR' fee='0.0' accountvalue='-1.16' />
    <transaction bookingdate='2011-11-23' tradedate='2011-11-22' message='TAX' value='-1.90' currency='EUR' fee='0.0' accountvalue='-1.90' />
    <transaction bookingdate='2011-11-22' tradedate='2011-11-21' message='SHOPPING' value='-11.50' currency='CHF' fee='-0.16' accountvalue='-9.47' />
    <transaction bookingdate='2011-11-22' tradedate='2011-11-21' message='BANK DEF' value='-165.36' currency='EUR' fee='0.0' accountvalue='-165.36' />
    <transaction bookingdate='2011-11-21' tradedate='2011-11-20' message='ABCDS' value='-3.99' currency='EUR' fee='0.0' accountvalue='-3.99' />
    <transaction bookingdate='2011-11-21' tradedate='2011-11-20' message='BANK ABC' value='-230.00' currency='EUR' fee='0.0' accountvalue='-230.00' />
    <transaction bookingdate='2011-11-17' tradedate='2011-11-17' message='Auszahlung' value='-7669.84' currency='EUR' fee='0.0' accountvalue='-7669.84' />
    <transaction bookingdate='2011-11-11' tradedate='2011-11-10' message='BANK ABC' value='-200.00' currency='EUR' fee='0.0' accountvalue='-200.00' />
    <transaction bookingdate='2011-11-03' tradedate='2011-11-02' message='BANK ABC' value='-200.00' currency='EUR' fee='0.0' accountvalue='-200.00' />
    <transaction bookingdate='2011-11-02' tradedate='2011-11-01' message='ABCDS' value='-2.06' currency='EUR' fee='0.0' accountvalue='-2.06' />
  </account>
</accounts>"""

    private def content1 = """"Kontonummer:";"1234567890 / Internet-Konto";

"Von:";"29.12.2011";
"Bis:";"28.01.2012";
"Kontostand vom:";"1.234,56";

"Buchungstag";"Wertstellung";"Buchungstext";"Auftraggeber/Begünstigter";"Verwendungszweck";"Kontonummer";"BLZ";"Betrag (EUR)";
"24.01.2012";"24.01.2012";"EIGENE KREDITKARTENABRECHN.";"KREDITKARTENABRECHNUNG";"VISA-ABR. 1234567890123456 ";"1234567890";"12345678";"-2.207,09";
"23.01.2012";"23.01.2012";"LASTSCHRIFT";"BLABLABLA VEREIN";"123456789012, VNR. 1234567 1234567-1234, ZV TARIF N 123,12 EUR 01.2012 ";"123456789";"12345678";"-150,00";
"23.01.2012";"23.01.2012";"UEBERWEISUNGSGUTSCHRIFT";"MUELLER MEIER SCHMIDT";"VIELEN DANK ";"123456789";"12345678";"1.130,40";
"16.01.2012";"16.01.2012";"ONLINE-UEBERWEISUNG";"ABC GMBH";"NR 12-12345-1234566 DATUM 15.01.2012, 14.46 UHR 1.TAN 123456 ";"123456789";"12345678";"-1.016,32";
"10.01.2012";"10.01.2012";"LASTSCHRIFT";"DANKE DANKE";"123121121212121212121212121 12121121/1212121212/12 ";"121212211";"12121212";"-56,92";
"02.01.2012";"02.01.2012";"LASTSCHRIFT";"FIRMA ABC";"121212121212121 121212121 HEUTEHEUTE 6,32 ";"121212121";"12122121";"-6,32";
"02.01.2012";"02.01.2012";"LASTSCHRIFT";"FIRMA DEF";"121212121212 12121212/1212/     75,03/AB ";"12121";"12121211";"-75,03";
"02.01.2012";"02.01.2012";"LASTSCHRIFT";"AMAZON DOWNLOADS";"1212121212121211 ";"1212121121";"12121211";"-17,28";
"30.12.2011";"30.12.2011";"DAUERAUFTRAG";"SCHMIDT MEIER MUELER";"GEMEINSCHAFTSKONTO ";"121212121";"12121211";"-900,00";
"30.12.2011";"01.01.2012";"ABSCHLUSS";"";"Abrechnung 31.12.2011 Information zur Abrechnung Kontostand am 30.12.2011 3.456,78 +  -------------- Abrechnungszeitraum vom 01. 10.2011 bis 31.12.2011 Zinsen für Guthaben 1,11H  0,5000 v.H. Haben-Zins bis  30.12.2011 Kapitalertragsteuer 1,11S Solidaritätszuschlag 1,11S ";"";"";"3,33";"""


    private def content2 = """"Kreditkarte:";"1234********5678 Kreditkarte";

"Zeitraum:";"Seit der letzten Abrechnung";
"Saldo:";"0 EUR";
"Datum:";"26.01.2012";

"Umsatz abgerechnet";"Wertstellung";"Belegdatum";"Umsatzbeschreibung";"Betrag (EUR)";"Ursprünglicher Betrag";
"Nein";"21.01.2012";"20.01.2012";"Lastschrift";"2.207,09";"";"""

    private def content3 =""""Kreditkarte:";"1234********5678 Kreditkarte";

"Von:";"01.11.2011";
"Bis:";"31.12.2011";
"Saldo:";"0 EUR";
"Datum:";"26.01.2012";

"Umsatz abgerechnet";"Wertstellung";"Belegdatum";"Umsatzbeschreibung";"Betrag (EUR)";"Ursprünglicher Betrag";
"Ja";"02.01.2012";"31.12.2011";"BOOK";"-21,37";"-27,60 USD";
"Ja";"02.01.2012";"31.12.2011";"1,75% für Auslandseinsatz";"-0,37";"";
"Ja";"29.12.2011";"28.12.2011";"JKL";"-207,60";"";
"Ja";"29.12.2011";"28.12.2011";"JKL";"-20,00";"";
"Ja";"23.12.2011";"22.12.2011";"Lastschrift";"663,49";"";
"Ja";"19.12.2011";"17.12.2011";"BANK ABC";"-200,00";"";
"Ja";"19.12.2011";"18.12.2011";"BANK ABC";"-200,00";"";
"Ja";"12.12.2011";"09.12.2011";"BANK GHI";"-200,00";"";
"Ja";"28.11.2011";"25.11.2011";"CINEMA";"-17,60";"";
"Ja";"28.11.2011";"26.11.2011";"BOOKSTORE";"-21,31";"-28,17 USD";
"Ja";"28.11.2011";"26.11.2011";"1,75% für Auslandseinsatz";"-0,37";"";
"Ja";"23.11.2011";"22.11.2011";"Lastschrift";"322,30";"";
"Ja";"23.11.2011";"22.11.2011";"Interest";"86,52";"";
"Ja";"23.11.2011";"22.11.2011";"TAX";"-21,15";"";
"Ja";"23.11.2011";"22.11.2011";"TAX";"-1,16";"";
"Ja";"23.11.2011";"22.11.2011";"TAX";"-1,90";"";
"Ja";"22.11.2011";"21.11.2011";"SHOPPING";"-9,31";"-11,50 CHF";
"Ja";"22.11.2011";"21.11.2011";"BANK DEF";"-165,36";"";
"Ja";"22.11.2011";"21.11.2011";"1,75% für Auslandseinsatz";"-0,16";"";
"Ja";"21.11.2011";"20.11.2011";"ABCDS";"-3,99";"";
"Ja";"21.11.2011";"20.11.2011";"BANK ABC";"-230,00";"";
"Ja";"17.11.2011";"17.11.2011";"Auszahlung";"-7.669,84";"";
"Ja";"11.11.2011";"10.11.2011";"BANK ABC";"-200,00";"";
"Ja";"03.11.2011";"02.11.2011";"BANK ABC";"-200,00";"";
"Ja";"02.11.2011";"01.11.2011";"ABCDS";"-2,06";"";"""

    private def content4 =""""Kreditkarte:";"1234********5678 Kreditkarte";

"Von:";"01.11.2011";
"Bis:";"31.12.2011";
"Saldo:";"0 EUR";
"Datum:";"26.01.2012";

"Umsatz abgerechnet";"Wertstellung";"Belegdatum";"Umsatzbeschreibung";"Betrag (EUR)";"Ursprünglicher Betrag";
"Ja";"02.01.2012";"31.12.2011";"BOOK";"-21,37";"-27,60 USD";
"Ja";"02.01.2012";"31.12.2011";"1,75% für Auslandseinsatz";"-0,37";"";
"Ja";"29.12.2011";"28.12.2011";"JKL";"-207,60";"";
"Ja";"29.12.2011";"28.12.2011";"JKL";"-20,00";"";
"Ja";"23.12.2011";"22.12.2011";"Lastschrift";"663,49";"";
"Ja";"19.12.2011";"17.12.2011";"BANK ABC";"-200,00";"";
"Ja";"19.12.2011";"18.12.2011";"BANK ABC";"-200,00";"";
"Ja";"12.12.2011";"09.12.2011";"BANK GHI";"-200,00";"";
"Ja";"28.11.2011";"25.11.2011";"CINEMA";"-17,60";"";
"Ja";"28.11.2011";"26.11.2011";"BOOKSTORE";"-21,31";"-28,17 USD";
"Ja";"28.11.2011";"26.11.2011";"1,75% für Auslandseinsatz";"-0,37";"";
"Ja";"23.11.2011";"22.11.2011";"Lastschrift";"322,30";"";
"Ja";"23.11.2011";"22.11.2011";"Interest";"86,52";"";
"Ja";"23.11.2011";"22.11.2011";"TAX";"-21,15";"";
"Ja";"23.11.2011";"22.11.2011";"TAX";"-1,16";"";
"Ja";"23.11.2011";"22.11.2011";"TAX";"-1,90";"";
"Ja";"22.11.2011";"21.11.2011";"SHOPPING";"-9,31";"-11,50 CHF";
"Ja";"22.11.2011";"21.11.2011";"BANK DEF";"-165,36";"";
"Ja";"22.11.2011";"21.11.2011";"1,75% für Auslandseinsatz";"-1,16";"";
"Ja";"21.11.2011";"20.11.2011";"ABCDS";"-3,99";"";
"Ja";"21.11.2011";"20.11.2011";"BANK ABC";"-230,00";"";
"Ja";"17.11.2011";"17.11.2011";"Auszahlung";"-7.669,84";"";
"Ja";"11.11.2011";"10.11.2011";"BANK ABC";"-200,00";"";
"Ja";"03.11.2011";"02.11.2011";"BANK ABC";"-200,00";"";
"Ja";"02.11.2011";"01.11.2011";"ABCDS";"-2,06";"";"""



    def showDebit = false
    def showCredit = false
    def showCreditWithInternational = false
    def showCreditWithInternationalError = false

    @Override
    void eachDocument(Closure closure) {
        if (showDebit)
            closure.call(Website.DocType.TEXT, [type: 'debit', account: 'ABCDEFGHI'], content1)
        if (showCredit)
            closure.call(Website.DocType.TEXT, [type: 'credit', account: 'DEFGHIJKL'], content2)
        if (showCreditWithInternational)
            closure.call(Website.DocType.TEXT, [type: 'credit', account: 'DEFGHIJKL'], content3)
        if (showCreditWithInternationalError)
            closure.call(Website.DocType.TEXT, [type: 'credit', account: 'DEFGHIJKL'], content4)
    }
}
