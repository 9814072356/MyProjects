#include "parser.h"

#include <string_view>

/**
 * Helper function of parseExpression.
 * Does the parsing of a function expression in prefix notation.
 */
std::unique_ptr<Calculator> parseExpressionRecursive(std::string_view &expr)
{
    char op = expr.at(0);
    expr.remove_prefix(1);

    switch (op)
    {
    // TODO: Insert parsing of '&' and '|'
    case '&':
    {
        auto operand1 = parseExpressionRecursive(expr);
        auto operand2 = parseExpressionRecursive(expr);

        return std::make_unique<AndCalculator>(std::move(operand1), std::move(operand2));
    }
    case '|':
    {
        auto operand1 = parseExpressionRecursive(expr);
        auto operand2 = parseExpressionRecursive(expr);

        return std::make_unique<OrCalculator>(std::move(operand1), std::move(operand2));
    }
    case '~':
    {
        auto operand = parseExpressionRecursive(expr);

        return std::make_unique<NotCalculator>(std::move(operand));
    }
    default:
        return std::make_unique<LeafCalculator>(static_cast<size_t>(std::stoul(std::string(1, op))));
    }
}

/**
 * Parses a boolean function expression in prefix notation.
 * Returns a unique_ptr to a new Calculator for the function expression.
 */
std::unique_ptr<Calculator> parseExpression(const std::string &expr)
{
    std::string_view exprView(expr);
    return parseExpressionRecursive(exprView);
}
