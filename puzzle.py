from logic import *

AKnight = Symbol("A is a Knight")
AKnave = Symbol("A is a Knave")

BKnight = Symbol("B is a Knight")
BKnave = Symbol("B is a Knave")

CKnight = Symbol("C is a Knight")
CKnave = Symbol("C is a Knave")

# Puzzle 0
# A says "I am both a knight and a knave."
knowledge0 = And(
    # A is either a knight or a knave, but not both.
    Or(AKnight, AKnave),
    Not(And(AKnight, AKnave)),

    # A says "I am both a knight and a knave." If A is a knight, this statement must be true.
    Implication(AKnight, And(AKnight, AKnave)),

    # If A is a knave, the statement is false, meaning A is not both a knight and a knave.
    Implication(AKnave, Not(And(AKnight, AKnave)))
)

# Puzzle 1
# A says "We are both knaves." B says nothing.
knowledge1 = And(
    # A and B are either knights or knaves, but not both.
    Or(AKnight, AKnave),
    Not(And(AKnight, AKnave)),
    Or(BKnight, BKnave),
    Not(And(BKnight, BKnave)),

    # If A is a knight, A's statement is true, meaning both A and B are knaves.
    Implication(AKnight, And(AKnave, BKnave)),

    # If A is a knave, A's statement is false, meaning not both A and B are knaves.
    Implication(AKnave, Not(And(AKnave, BKnave)))
)

# Puzzle 2
# A says "We are the same kind." B says "We are of different kinds."
knowledge2 = And(
    # A and B are either knights or knaves, but not both.
    Or(AKnight, AKnave),
    Not(And(AKnight, AKnave)),
    Or(BKnight, BKnave),
    Not(And(BKnight, BKnave)),

    # If A is a knight, A's statement is true: A and B are the same kind.
    Implication(AKnight, Or(And(AKnight, BKnight), And(AKnave, BKnave))),

    # If A is a knave, A's statement is false: A and B are different kinds.
    Implication(AKnave, Or(And(AKnight, BKnave), And(AKnave, BKnight))),

    # If B is a knight, B's statement is true: A and B are different kinds.
    Implication(BKnight, Or(And(AKnight, BKnave), And(AKnave, BKnight))),

    # If B is a knave, B's statement is false: A and B are the same kind.
    Implication(BKnave, Or(And(AKnight, BKnight), And(AKnave, BKnave)))
)

# Puzzle 3
# A says either "I am a knight." or "I am a knave.", but you don't know which.
# B says "A said 'I am a knave'." B says "C is a knave."
# C says "A is a knight."
knowledge3 = And(
    # A, B, and C are either knights or knaves, but not both.
    Or(AKnight, AKnave),
    Not(And(AKnight, AKnave)),
    Or(BKnight, BKnave),
    Not(And(BKnight, BKnave)),
    Or(CKnight, CKnave),
    Not(And(CKnight, CKnave)),

    # A says either "I am a knight" or "I am a knave."
    Implication(AKnight, AKnight),  # If A is a knight, then A's statement is true (A is a knight).
    Implication(AKnave, AKnave),    # If A is a knave, then A's statement is false (A is a knave).

    # B says "A said 'I am a knave'." and "C is a knave."
    Implication(BKnight, And(Implication(AKnight, AKnave), CKnave)),  # If B is a knight, both statements must be true.
    Implication(BKnave, Or(AKnight, CKnight)),  # If B is a knave, one or both of the statements are false.

    # C says "A is a knight."
    Implication(CKnight, AKnight),  # If C is a knight, A is a knight.
    Implication(CKnave, AKnave)     # If C is a knave, A is a knave.
)


def main():
    symbols = [AKnight, AKnave, BKnight, BKnave, CKnight, CKnave]
    puzzles = [
        ("Puzzle 0", knowledge0),
        ("Puzzle 1", knowledge1),
        ("Puzzle 2", knowledge2),
        ("Puzzle 3", knowledge3)
    ]
    for puzzle, knowledge in puzzles:
        print(puzzle)
        if len(knowledge.conjuncts) == 0:
            print("    Not yet implemented.")
        else:
            for symbol in symbols:
                if model_check(knowledge, symbol):
                    print(f"    {symbol}")


if __name__ == "__main__":
    main()
