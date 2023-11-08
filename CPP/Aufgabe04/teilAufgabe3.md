- Welche der folgenden unique_ptr-Deklarationen sind illegal oder führen möglicherweise im
Folgenden zu Programmfehlern? <br> Erklären Sie, worin die Probleme jeweils bestehen! <br>
    ```cpp
    #include <vector>
    #include <memory>
    #include <iostream>
    int main()
    {
        double e = 2.7182;
        double *dp = &e;
        double *dp2 = new double(3.1415);
        double *dp3 = new double(1.618);
        double &dr1 = *dp3;
        std::vector<double> v = {1.5, 2.5};
        using DoubleP = std::unique_ptr<double>;

        DoubleP pd0(std::make_unique<double>(3.1415));
        DoubleP pd1(dp2);
        std::cout << *pd1;
        DoubleP pd2(dp); //--> segmentation fault
        DoubleP pd3(pd1.get()); //free(): double free detected in tcache 2 Aborted(core dumped)
        DoubleP pd4(&e); //-> munmap_chunk(): invalid pointer Aborted(core dumped)
        DoubleP pd5(e); // viele Fehler
        DoubleP pd6(pd0); // use of deleted function ‘std::unique_ptr<_Tp, _Dp>::unique_ptr(const std::unique_ptr<_Tp, _Dp>&) [with _Tp = double; _Dp = std::default_delete<double>]’
        DoubleP pd7(&v[0]); //free(): double free detected in tcache 2 Aborted(core dumped)
        DoubleP pd8(&dr1);
    }
    ```
    unique_ptr nicht zuweisbar/kopierbar <br>
    ptr Eigentum kann aber auf einen Anderen übertragen werden, wenn nicht const <br>
    (): Konstruktor = erstellt einen neuen unique_ptr <br>
    DoubleP pd1(dp2) --> erstellt Kopie von pd1,also dp2 <br>
    init unique_ptr mit bestehender Variable: DoubleP pd0(std::make_unique<double>(*dp2));
    - a) DoubleP pd0(std::make_unique<double>(3.1415));
        - legal. 
    - b) DoubleP pd1(dp2);
        - legal
    - c) DoubleP pd2(dp);
        - Führt zu einem segmentation fault, weil nur die Speicheradresse übergeben wird nicht der eigentliche Pointer
    - d) DoubleP pd3(pd1.get());
        - Führt zu einem double free in tcache2, da nur die Speicheradresse angeben wird aber nicht der eigentliche Pointer
        - get() liefert den Zeiger zurück
    - e) DoubleP pd4(&e);
        - das gleiche wie d.), nur die Speicheradresse wird angeben, nicht der eigentliche Pointer
    - f) DoubleP pd5(e);
        - hier wird versucht mit der Übergabe eines doubles ein unique_ptr zuerzeugen, das scheitert, weil kein Pointer dazu übergeben wird
    - g) DoubleP pd6(pd0);
        - unique pointer können nicht kopiert werden
    - h) DoubleP pd7(&v[0]);
        - falsche init eines unique_ptrs, siehe e.).
    - i) DoubleP pd8(&dr1);
        - legal