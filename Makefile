# Exemplu de Makefile pentru soluții scrise în Java.

.PHONY: build clean

build: Supercomputer.class Ferate.class Magazin.class

run-p1:
	java Supercomputer
run-p2:
	java Ferate
run-p4:
	java Magazin

# Nu uitați să modificați numele surselor.
Supercomputer.class: Supercomputer.java
	javac $^
Ferate.class: Ferate.java
	javac $^
Magazin.class: Magazin.java
	javac $^


# Vom șterge fișierele bytecode compilate.
clean:
	rm -rf *.class public_tests/*/out