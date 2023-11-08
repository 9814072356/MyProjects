https://pad.finf.uni-hannover.de/ zum Anschauen
- a.) 
    - Was gibt der folgende Code aus? Begründen Sie ihre Antwort!<br>
        ```cpp
        std::vector<int> v = {10, 9}; // Vektor mit 2 int Elementen 
        int* p1 = &v[0]; //Pointer auf den Wert des 1. Elementes
        *p1 = --*p1 * *(p1 + 1); // p1 = 
        std::cout << v[0]<<", "<<v[1] << std::endl;``` // 1. Vektorelement,

    - Der erste Vektorwert wird zunächst dekrementiert und anschließend mit sich selbstmultipliziert --> 9*9 = 81.
    <br>Wenn ein Pointer inkrementiert wird, wird der Wert dahinter inkrementiert, nicht die entsprechende Speicheradresse
    <br>--*p1 dekrementiert den dereferenzierten Pointer: 10 -> 9
    <br>**(p1 + 1) hat den gleichen Effekt, weil durch das Inkrementieren des Pointer, wird auch die Speicheradresse um 4B erhöht.

- b.)
    - Erklären Sie jede der folgenden Definitionen! Ist eine davon illegal?<br> Wenn ja,
warum? <br>int i = 0;
        - a.) short* p1 = &i;
            - illegal, da Shortpointer auf int Wert zeigen soll.
        - b.) int* p2 = 0;
            - legal, der Pointer zeigt auf den Wert 0
        - c.) int* p3 = i;
            - illegal, da versucht wird den Wert von i im Pointer p3 zu speichern, nicht die Speicheradresse
        - d.) int* p4 = &i;
            - legal, Speicheradresse von i wird im Pointer p4 gespeichert
- c.)
    - Sei p ein Pointer auf int. <br>Unter welcher Bedingung wird Code1 und unter welcher
Code2 ausgeführt? <br> Welche Probleme können dabei auftreten?
        <br>
        ```cpp
        if (p) {Code1}
        if (*p) {Code2}
        ```
        - Code1 wird ausgeführt, wenn if(p != NULL), also wenn der Pointer p kein nullptr ist.
            - Wert/Adresse werden nicht überprüft.
        - Code2 wird garnicht ausgeführt 
- d.) 
    -  Es sei ein Pointer p gegeben. Kann man herausfinden, ob p auf ein gültiges Objekt
zeigt? <br>Wenn ja, wie?<br> Wenn nein, warum nicht?     
        - Nein, da ein Pointer auch ein nullptr sein kann, der einfach ins nichts zeigt. <br>Anderes Beispiel: wenn man ein Pointer dupliziert und den Originalpointer löscht, würde beim Versuch das Duplikat zu derefenzieren "undefinied behavior" auftreten.
        - Aber über smart pointer könnte man herausfinden, ob die Ressource auf die der Pointer zeigt noch existiert.  <br>
        Also hängt vom Sachverhalt ab, je nachdem wie ein gültiges Objekt definiert ist.