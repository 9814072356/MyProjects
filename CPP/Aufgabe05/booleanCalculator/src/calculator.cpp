#include "calculator.h"

/**
 * Print Calculator to a stream.
 */
std::ostream &operator<<(std::ostream &os, const Calculator &calc)
{
    calc.print(os);
    return os;
}

CalculatorPrinter::CalculatorPrinter(const Calculator &calc, Calculator::TraversalType t) : calculator(&calc), type(t)
{
}

std::ostream &operator<<(std::ostream &os, const CalculatorPrinter &cp)
{
    cp.calculator->print(os, cp.type);
    return os;
}

LeafCalculator::LeafCalculator(size_t i) : idx(i) {}

bool LeafCalculator::calculate(const std::vector<bool> &input) const
{
    return input[this->idx];
}

void LeafCalculator::print(std::ostream &os, TraversalType) const
{
    os << this->idx;
}
//##########################
UnaryCalculator::UnaryCalculator(std::unique_ptr<Calculator> in, std::string opSymbol) : inner(std::move(in)), op(std::move(opSymbol))
{
}

bool UnaryCalculator::calculate(const std::vector<bool> &input) const
{
    return this->operate(this->inner->calculate(input));
}

//#######################
NotCalculator::NotCalculator(std::unique_ptr<Calculator> inner) : UnaryCalculator(std::move(inner), "~")
{
}

// TODO: Implement missing overrides of NotCalculator here
bool NotCalculator::operate(bool input) const
{
    return !input;
}

void NotCalculator::print(std::ostream &os, TraversalType type) const
{
    // TODO: Print according to given TraversalType.
    if (type == TraversalType::Prefix)
    {
        os << "~" << *inner;
    }
    else if (type == TraversalType::Postfix)
    {
        os << *inner << "~";
    }
    else if (type == TraversalType::Infix)
    {
        os << "(~(" << *inner << "))";
    }
}

//#######################
MultiCalculator::MultiCalculator(std::unique_ptr<Calculator> firstElement, std::unique_ptr<Calculator> secondElement) : first(std::move(firstElement)), second(std::move(secondElement))
{
}

bool MultiCalculator::calculate(const std::vector<bool> &input) const
{
    return this->operate(this->first->calculate(input), this->second->calculate(input));
}

//#######################

AndCalculator::AndCalculator(std::unique_ptr<Calculator> first, std::unique_ptr<Calculator> second) : MultiCalculator(std::move(first), std::move(second))
{
}

bool AndCalculator::operate(bool firstArgument, bool secondArgument) const
{
    bool result = false;
    if (firstArgument && secondArgument)
    {
        result = true;
    }
    return result;
}

void AndCalculator::print(std::ostream &os, TraversalType type) const
{
    // TODO: Print according to given TraversalType.
    if (type == TraversalType::Prefix)
    {
        os << "&" << *first << *second;
    }
    else if (type == TraversalType::Postfix)
    {
        os << *first << *second << "&";
    }
    else if (type == TraversalType::Infix)
    {
        os
            << "((" << *first << ")"
            << "&(" << *second << "))";
    }
}

//#######################
OrCalculator::OrCalculator(std::unique_ptr<Calculator> first, std::unique_ptr<Calculator> second) : MultiCalculator(std::move(first), std::move(second))
{
}
bool OrCalculator::operate(bool firstArgument, bool secondArgument) const
{
    bool result = false;
    if (firstArgument || secondArgument)
    {
        result = true;
    }
    return result;
}

void OrCalculator::print(std::ostream &os, TraversalType type) const
{
    // TODO: Print according to given TraversalType.
    if (type == TraversalType::Prefix)
    {
        os << "|" << *first << *second;
    }
    else if (type == TraversalType::Postfix)
    {
        os << *first << *second << "|";
    }
    else if (type == TraversalType::Infix)
    {
        os << "((" << *first << ")"
           << "|(" << *second << "))";
    }
}