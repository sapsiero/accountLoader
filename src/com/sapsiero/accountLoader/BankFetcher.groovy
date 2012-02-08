package com.sapsiero.accountLoader

import org.apache.log4j.Logger

def log = Logger.getLogger(this.class)

log.info "Welcome to BankFetcher..."
log.info "Provided the following args: ${args}"

//TODO Create abstract factory
//def site = new DkbWebsite("/home/tim/Documents/Bank/Dkb/new/${new Date().format('yyyyMMdd')}/")
//def processor = new DkbContentProcessor(site)
//def file = new ContentWriter("/home/tim/Documents/Bank/new")
//file.ensureFolder()
//file.fetch(processor)

//def site = new CommerzbankWebsite("/home/tim/Documents/Bank/Dkb/new/${new Date().format('yyyyMMdd')}/")
//def processor = new CommerzbankContentProcessor(site)
//def file = new ContentWriter("/home/tim/Documents/Bank/new")
//file.ensureFolder()
//file.fetch(processor)

//def site = new ConsorsWebsite("/home/tim/Documents/Bank/Dkb/new/${new Date().format('yyyyMMdd')}/")
//def processor = new ConsorsContentProcessor(site)
//def file = new ContentWriter("/home/tim/Documents/Bank/new")
//file.ensureFolder()
//file.fetch(processor)

def site = new ZkbWebsite("/home/tim/Documents/Bank/Dkb/new/${new Date().format('yyyyMMdd')}/")
site.eachDocument { docType, docAttr, doc ->
}