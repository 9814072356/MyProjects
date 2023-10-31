# Aufgabe 1
## 1.3
- Erklären Sie, warum die Methoden der Klasse Calculator virtual sind und
erläutern Sie die Auswirkungen!
  - virtual erlaubt es, dass Funktionen von den Kinder überschrieben werden können und je nach Kind anders funktionieren.
    So hat jedes Kind eigene Funktionen, welche nur die einzelnen Kindobjekte zugänglich sind.

# Aufgabe 4
- Sehen Sie sich die Klasse CalculatorPrinter an! Welche Aufgabe erfüllt diese Klasse?
CalculatorPrinter ruft lediglich eine einzelne Memberfunktion von Calculator auf. 
Warum ist diese Klasse dennoch sinnvoll?
  - Die Klasse überlädt den <<-Operator, um je nach Traveltype eine andere print-Klassenfunktion aufzurufen.