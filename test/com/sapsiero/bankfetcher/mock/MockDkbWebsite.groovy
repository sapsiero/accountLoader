package com.sapsiero.bankfetcher.mock

import com.gargoylesoftware.htmlunit.BrowserVersion
import com.sapsiero.bankfetcher.Website

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

    def showDebit = false
    def showCredit = false

    @Override
    void eachDocument(Closure closure) {
        if (showDebit)
            closure.call(Website.DocType.TEXT, [type: 'debit', account: 'ABCDEFGHI'], content1)
        if (showCredit)
            closure.call(Website.DocType.TEXT, [type: 'credit', account: 'DEFGHIJKL'], content2)
    }
}
