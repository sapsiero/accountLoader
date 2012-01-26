package com.sapsiero.bankfetcher

println "Welcome to BankFetcher..."
println "Provided the following args: ${args}"

def bank = new DkbBank()
println bank.exec()