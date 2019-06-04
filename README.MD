Snow Mocks
==========

Zaimplementuj klasę SnowRescueService - to jest twój System Under Test. Wszystkie pozostałe klasy potraktuj jako zależności.

1. Wyślij piaskarkę (sander) tylko jeśli temperatura będzie poniżej 0 st C

2. Wyślij pług (snowplow) tylko jeśli opady śniegu przekroczą 3 mm
	
3. Jeśli pług nawali (SnowplowMalfunctioningException) - wyślij kolejny
		
4. Wyślij dwa pługi tylko jeśli opady śniegu przekroczą 5 mm

5. Jeśli temperatura będzie poniżej -10 i opady śniegu przekroczą 10 mm, wyślij trzy pługi, piaskarkę i powiadom prasę. 



Your task is to implement SnowRescueService class - this is your System Under Test. Treat all other classes as dependencies.

1. Send sander if temperature will be below 0 C

2. Send snowplow if snowfall exceed 3 mm
	
3. If snowplow fails (SnowplowMalfunctioningException) - send another
		
4. Send two snowplows if snowfall exceed 5 mm

5. If temperature drops below -10 and snowfall exceed 10 mm, send three snowplows, a sander and notify press. 
