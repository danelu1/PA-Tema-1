.PHONY: build clean

build: Feribot.class Nostory.class Semnale.class Sushi.class Badgpt.class

run-p1:
	 java Feribot

run-p2:
	 java Nostory

run-p3:
	 java Sushi

run-p4:
	 java Semnale

run-p5:
	java Badgpt

Feribot.class: Feribot.java
		javac $^

Nostory.class: Nostory.java
		javac $^

Sushi.class: Sushi.java
		javac $^

Semnale.class: Semnale.java
		javac $^

Badgpt.class: Badgpt.java
		javac $^

clean:
	rm -f *.class
