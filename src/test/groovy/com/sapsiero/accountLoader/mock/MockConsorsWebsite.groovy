package com.sapsiero.accountLoader.mock

import com.gargoylesoftware.htmlunit.BrowserVersion
import com.sapsiero.accountLoader.Website

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/28/12
 * Time: 2:02 PM
 *
 */
class MockConsorsWebsite extends Website {

    public MockConsorsWebsite() {
        super(BrowserVersion.INTERNET_EXPLORER_8, '')
    }

    public def result2 = """<accounts banknumber='CSDBDE71'>
  <account number='DEFGHIJKL' type='callmoney'>
    <balance date='2012-01-29' value='3.36' />
    <transaction bookingdate='2011-12-30' tradedate='2011-12-31' message='Abschluss' value='4.56' />
    <transaction bookingdate='2011-12-30' tradedate='2011-12-31' message='Steuer' value='-1.20' />
    <transaction bookingdate='2011-11-16' tradedate='2011-11-16' message='ABC; Überweisung; Geld' value='-39.78' />
  </account>
</accounts>"""

    public def result1 = """<accounts banknumber='CSDBDE71'>
  <account number='ABCDEFGHI' type='debit'>
    <balance date='2012-01-29' value='134.77' />
    <transaction bookingdate='2011-12-30' tradedate='2011-12-30' message='Steuer; Nachtr. Verlustverrechnung vom 31.12.2011 Nr. 00001 1234567890/12345/1234 SOLIDARITAETSZUSCHLAG' value='-0.06' />
    <transaction bookingdate='2011-12-30' tradedate='2011-12-30' message='Steuer-Gutschrift; Nachtr. Verlustverrechnung vom 31.12.2011 Nr. 00001 1234567890/12345/1234 KAPITALERTRAGSTEUER' value='0.01' />
    <transaction bookingdate='2011-12-30' tradedate='2011-12-31' message='Abschluss' value='-9.38' />
    <transaction bookingdate='2011-12-30' tradedate='2011-12-31' message='Steuer' value='-0.52' />
    <transaction bookingdate='2011-11-18' tradedate='2011-11-18' message='ABC; Überweisung; Geld' value='-500.00' />
    <transaction bookingdate='2011-11-17' tradedate='2011-11-21' message='Effekten; WP-ABRECHNUNG 1234567890123 Verkauf WKN: ABCDEF ABC BANK CALL12 EO/SF' value='644.72' />
    <transaction bookingdate='2011-11-16' tradedate='2011-11-16' message='ABC; Überweisung; Geld' value='-1900.72' />
    <transaction bookingdate='2011-11-16' tradedate='2011-11-16' message='ABC; Überweisungsgutschrift; Geld' value='39.78' />
  </account>
</accounts>"""

    public def result3 = """<accounts banknumber='CSDBDE71'>
  <account number='ABCDEFGHI' type='debit'>
    <balance date='2012-01-29' value='134.77' />
    <transaction bookingdate='2011-12-30' tradedate='2011-12-30' message='Steuer; Nachtr. Verlustverrechnung vom 31.12.2011 Nr. 00001 1234567890/12345/1234 SOLIDARITAETSZUSCHLAG' value='-0.06' />
    <transaction bookingdate='2011-12-30' tradedate='2011-12-30' message='Steuer-Gutschrift; Nachtr. Verlustverrechnung vom 31.12.2011 Nr. 00001 1234567890/12345/1234 KAPITALERTRAGSTEUER' value='0.01' />
    <transaction bookingdate='2011-12-30' tradedate='2011-12-31' message='Abschluss' value='-9.38' />
    <transaction bookingdate='2011-12-30' tradedate='2011-12-31' message='Steuer' value='-0.52' />
    <transaction bookingdate='2011-11-18' tradedate='2011-11-18' message='ABC; Überweisung; Geld' value='-500.00' />
    <transaction bookingdate='2011-11-17' tradedate='2011-11-21' message='Effekten; WP-ABRECHNUNG 1234567890123 Verkauf WKN: ABCDEF ABC BANK CALL12 EO/SF' value='644.72' />
    <transaction bookingdate='2011-11-16' tradedate='2011-11-16' message='ABC; Überweisung; Geld' value='-1900.72' />
    <transaction bookingdate='2011-11-16' tradedate='2011-11-16' message='ABC; Überweisungsgutschrift; Geld' value='39.78' />
  </account>
  <account number='DEFGHIJKL' type='callmoney'>
    <balance date='2012-01-29' value='3.36' />
    <transaction bookingdate='2011-12-30' tradedate='2011-12-31' message='Abschluss' value='4.56' />
    <transaction bookingdate='2011-12-30' tradedate='2011-12-31' message='Steuer' value='-1.20' />
    <transaction bookingdate='2011-11-16' tradedate='2011-11-16' message='ABC; Überweisung; Geld' value='-39.78' />
  </account>
</accounts>"""

    public def result4 = """"""

    private def content1 = """"123456789;134,77 EUR;134,77 EUR;29.01.2012;15:28;
30.12.2011;30.12.2011;;Steuer;Nachtr. Verlustverrechnung vom 31.12.2011 Nr. 00001 1234567890/12345/1234 SOLIDARITAETSZUSCHLAG;-0,06;
30.12.2011;30.12.2011;;Steuer-Gutschrift;Nachtr. Verlustverrechnung vom 31.12.2011 Nr. 00001 1234567890/12345/1234 KAPITALERTRAGSTEUER;+0,01;
30.12.2011;31.12.2011;;Abschluss;;-9,38;
30.12.2011;31.12.2011;;Steuer;;-0,52;
18.11.2011;18.11.2011;ABC;Überweisung;Geld;-500,00;
17.11.2011;21.11.2011;;Effekten;WP-ABRECHNUNG 1234567890123 Verkauf WKN: ABCDEF ABC BANK CALL12 EO/SF;+644,72;
16.11.2011;16.11.2011;ABC;Überweisung;Geld;-1.900,72;
16.11.2011;16.11.2011;ABC;Überweisungsgutschrift;Geld;+39,78;"""


    private def content2 = """"123456788;3,36 EUR;3,36 EUR;29.01.2012;15:28;
30.12.2011;31.12.2011;;Abschluss;;+4,56;
30.12.2011;31.12.2011;;Steuer;;-1,20;
16.11.2011;16.11.2011;ABC;Überweisung;Geld;-39,78;"""

    def showDebit = false
    def showCallMoney = false

    @Override
    void eachDocument(Closure closure) {
        if (showDebit)
            closure.call(Website.DocType.TEXT, [type: 'debit', account: 'ABCDEFGHI'], content1)
        if (showCallMoney)
            closure.call(Website.DocType.TEXT, [type: 'callmoney', account: 'DEFGHIJKL'], content2)
    }
}
