Łukasz Lepak, 277324
AAL 17Z, projekt
Tytuł projektu: Generacja spirali ze zbioru punktów
prowadzący: dr inż. Tomasz Gambin
	
Treść projektu:
Dane jest N punktów rozrzuconych na płaszczyźnie o zadanych współrzędnych (x, y). 
Należy skonstruować algorytm, który poprzez odpowiednie połączenie punktów utworzy spiralę, 
która będzie łączyć wszystkie punkty oraz będzie skręcona tylko w jedną stronę 
(brak kątów większych niż 180 stopni po wewnętrznej stronie spirali). 
Porównać czas obliczeń i wyniki różnych metod.

Wywołania programu udostępnione użytkownikowi(wpisywane z konsoli):
1. java AAL.AAL -m1 -a{algorytm}
2. java AAL.AAL -m2 -a{algorytm} -n{ilość_punktów} (-si) (-so)
3. java AAL.AAL -m3 -n{ilość_punktów} -s{skok} -t{ilość_testów} -i{iteracje}
4. java AAL.AAL -help

Opis wywołań:
1. Wywołuje aplikacje w trybie czytania ze standardowego wejścia oraz z wybranym algorytmem do wykonania obliczeń. 
   Wyniki prezentuje graficznie oraz poprzez wypisanie ich na standardowe wyjście. 
   Dzięki temu możliwe jest przekierowanie strumieni wejściowych i wyjściowych do pliku. 
2. Wywołuje aplikacje w trybie losowania danych wejściowych o zdefiniowanej ilości oraz z wybranym algorytmem
   do wykonania obliczeń. Wyniki prezentuje graficznie. Pozwala zdefiniować opcje do zapisania danych wejściowych
   lub/i wyjściowych na standardowe wyjście.
3. Wywyołuje aplikacje w trybie mierzenia czasu działania algorytmów oraz sprawdzania ich złożoności obliczeniowej.
   Obliczenia zaczynają się od zdefiniowanej wielkości danych, zwiększają się o zdefiniowany skok, wykonuje się podana
   ilość testów, a czas obliczeń dla określonej wielkości danych wejściowych jest uśredniany na podstawie ilości iteracji.
   Wynikiem działania tego trybu jest wydruk na standardowe wyjście określający dla wszystkich algorytmów
   wielkość danych wejściowych, średni czas wykonania w milisekundach oraz wartość współczynnika q.
4. Wypisuje na ekran możliwe opcje wywołania programu.

Opcje wywołań:
 -m1, -m2, -m3, -help - wybór opcji wykonania programu.
 -a{algorytm} - algorytm, który zostanie użyty do wykonania obliczeń, zmienna {algorytm} może przyjmować wartości
                CH lub RR. (CH - metoda otoczek wypukłych, RR - metoda rosnących promieni)
 -n{ilośc_punktów} - definiuje wielkość danych wejściowych losowanych (dla trybu -m2) 
                     bądź wielkość danych startowych (dla -m3). Wartość zmiennej 
					 {ilośc_punktów} powinna być liczbą całkowitą nie mniejszą niż 3.
 -s{skok} - definiuje wielkość skoku między wielkością danych wejściowych w trybie wywołania -m3.
			Zmienna {step} powinna być nieujemną liczbą całkowitą.
 -t{ilość_testów} - definiuje ilość różnych wielkości danych wejściowych, dla których wykonają się testy.
					Zmienna {ilość_testów} powinna być liczbą nieparzystą nie mniejszą niż 1. Liczby parzyste
					zostaną zwiększone w programie o 1.
 -i{iteracje} - ilość iteracji, po której będzie uśredniany czas działania algorytmu dla trybu wywołania -m3.
				Zmienna {iteracje} powinna być dodatnią liczbą całkowitą.
 -si, -so - opcjonalne parametry definiujące, czy tryb wywołania -m2 będzie wypisywał wygenerowane wejście i/lub
			obliczone wyjście na standardowe wyjście.
			
Dane wejściowe:
Wpisywane do programu dane wejściowe powinny być liczbami z przedziału <0, 1440) dla współrzędnej x danego punktu
oraz <0, 810) dla współrzędnej y. Takie zdefiniowanie punktów pozwoli na ładną prezentację graficzną wyników, 
a jednocześnie sprawi, że zbiór możliwych punktów będzie wystarczająco duży. Minimalną ilością punktów, dla której
algorytmy powinny być testowane to 3 - mniejsza liczba nie ma sensu.

Metody rozwiązania: 
1. Metoda otoczek wypukłych:
	dopóki liczba punktów do rozpatrzenia nie mniejsza niż trzy
		wyznacz otoczkę wypukłą z dostępnych punktów, dodaj ją na stos otoczek
	jeśli zostały jakieś punkty to dodaj je do jednego zbioru i dodaj na stos otoczek
	dopóki stos otoczek nie jest pusty
		zdejmij ze stosu nową otoczkę do rozpatrzenia
		jeśli w spirali są już co najmniej 2 punkty
			wyznacz punkt na otoczce, z którym przecina się prosta utworzona z dwóch ostatnio dodanych punktów spirali
			dodaj ten punkt do spirali
		dodaj punkty z otoczki do spirali
		
2. Metoda rosnących promieni
	posortuj punkty według rosnącej odległości od punktu środkowego O
	dodaj pierwszy punkt z listy do spirali, usuń go z listy i ustaw jako punkt poprzedni
	dopóki wszystkie punkty nie zostały dodane do spirali
		weź punkt aktualny jako pierwszy z listy i usuń go z listy
		wyznacz różnicę między promieniami dwóch kolejnych punktów
		wyznacz różnicę między kątami dwóch kolejnych punktów
		dodawaj do spirali punkty leżące na krzywej między dwoma punktami o liniowo rosnącym promieniu i kącie
		dodaj punkt aktualny do spirali i ustaw punkt poprzedni jako aktualny
		
Podział plików:
	algorithms - pakiet z algorytmami i interfejsem definiującym wymagane przez nie metody
		ConvexHullAlgorithm.java
		SortAlgorithm.java
		SpiralAlgorithm.java
	exceptions - pakiet ze zdefiniowanymi wyjątkami
		WrongArgumentException.java
	model - pakiet z klasami stanowiącymi dane
		Point.java
		Vector.java
	utilities - pakiet z klasamo definiującymi metody użytkowe
		AngleComparator.java
		ArgumentParser.java
		Measurement.java
		ModulusComparator.java
		PointGenerator.java
	view - pakiet z klasami definiującymi widok i GUI aplikacji
		View.java
		SpiralView.java
	AAL
		AAL.java
	dokumentacjaKoncowa.pdf
	readme.txt
	
Inne informacje:
- ograniczono rozmiary problemu do punktów nie przekraczających współrzędnej 1440 (x) bądź 810 (y)
  żeby ułatwić wizualizowanie efektów algorytmów
- przed pomiarami czasu algorytmu wykonywany jest warm up mający na celu zapobiec efektom 
  Just-In-Time compilation
- błąd w parsowaniu argumentów zakończy działanie programu i wydrukuje na ekran możliwe wywołania programu
- błąd we wczytywaniu danych w trybie -m1 spowoduje zakończenie działania programu bez informacji o możliwych wywołaniach,
  ale z informacją o błędzie we wczytywaniu danych  