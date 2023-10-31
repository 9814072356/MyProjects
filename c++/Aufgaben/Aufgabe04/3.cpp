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

    DoubleP pd0(std::make_unique<double>(*dp2));

    DoubleP pd1(dp2);
    std::cout << dp;

    // DoubleP pd2(dp); //--> segmentation fault
    // DoubleP pd3(pd1.get()); //free(): double free detected in tcache 2 Aborted(core dumped)
    // DoubleP pd4(&e); //-> munmap_chunk(): invalid pointer Aborted(core dumped)
    // DoubleP pd5(e); // viele Fehler
    // DoubleP pd6(pd0); // use of deleted function ‘std::unique_ptr<_Tp, _Dp>::unique_ptr(const std::unique_ptr<_Tp, _Dp>&) [with _Tp = double; _Dp = std::default_delete<double>]’
    // DoubleP pd7(&v[0]); //free(): double free detected in tcache 2 Aborted(core dumped)
    // DoubleP pd8(&dr1);
}