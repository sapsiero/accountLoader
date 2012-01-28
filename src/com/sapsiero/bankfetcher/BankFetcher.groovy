package com.sapsiero.bankfetcher

println "Welcome to BankFetcher..."
println "Provided the following args: ${args}"


//TODO fix call
//TODO add fileAcceptor
def bank = new DkbWebsite()
println bank.exec()