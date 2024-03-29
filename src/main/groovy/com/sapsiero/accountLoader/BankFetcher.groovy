package com.sapsiero.accountLoader

import org.apache.log4j.Logger

def log = Logger.getLogger(this.class)

log.info "Welcome to BankFetcher..."
log.info "Provided the following args: ${args}"

def site, processor, file

Properties configFile = new Properties()
configFile.load(getClass().getClassLoader().getResourceAsStream("config.properties"))

//TODO Create abstract factory
site = new DkbWebsite(configFile, "/home/tim/Documents/Bank/new/${new Date().format('yyyyMMdd')}/")
processor = new DkbContentProcessor(site)
file = new ContentWriter("/home/tim/Documents/Bank/new")
file.ensureFolder()
file.fetch(processor)

site = new CommerzbankWebsite(configFile, "/home/tim/Documents/Bank/new/${new Date().format('yyyyMMdd')}/")
processor = new CommerzbankContentProcessor(site)
file = new ContentWriter("/home/tim/Documents/Bank/new")
file.ensureFolder()
file.fetch(processor)

site = new ConsorsWebsite(configFile, "/home/tim/Documents/Bank/new/${new Date().format('yyyyMMdd')}/")
processor = new ConsorsContentProcessor(site)
file = new ContentWriter("/home/tim/Documents/Bank/new")
file.ensureFolder()
file.fetch(processor)

site = new ZkbWebsite(configFile, "/home/tim/Documents/Bank/new/${new Date().format('yyyyMMdd')}/")
processor = new ZkbContentProcessor(site)
file = new ContentWriter("/home/tim/Documents/Bank/new")
file.ensureFolder()
file.fetch(processor)