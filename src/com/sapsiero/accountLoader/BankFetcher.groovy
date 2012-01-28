package com.sapsiero.accountLoader

import org.apache.log4j.Logger

def log = Logger.getLogger(this.class)

log.info "Welcome to BankFetcher..."
log.info "Provided the following args: ${args}"

def site = new DkbWebsite("/home/tim/Documents/Bank/Dkb/new/${new Date().format('yyyyMMdd')}/")
def processor = new DkbContentProcessor(site)
def file = new ContentWriter("/home/tim/Documents/Bank/new")
file.ensureFolder()
file.fetch(processor)