import csv
import itertools
import sys

PROBS = {

    # Unconditional probabilities for having gene
    "gene": {
        2: 0.01,
        1: 0.03,
        0: 0.96
    },

    "trait": {

        # Probability of trait given two copies of gene
        2: {
            True: 0.65,
            False: 0.35
        },

        # Probability of trait given one copy of gene
        1: {
            True: 0.56,
            False: 0.44
        },

        # Probability of trait given no gene
        0: {
            True: 0.01,
            False: 0.99
        }
    },

    # Mutation probability
    "mutation": 0.01
}


def main():

    # Check for proper usage
    if len(sys.argv) != 2:
        sys.exit("Usage: python heredity.py data.csv")
    people = load_data(sys.argv[1])

    # Keep track of gene and trait probabilities for each person
    probabilities = {
        person: {
            "gene": {
                2: 0,
                1: 0,
                0: 0
            },
            "trait": {
                True: 0,
                False: 0
            }
        }
        for person in people
    }

    # Loop over all sets of people who might have the trait
    names = set(people)
    for have_trait in powerset(names):

        # Check if current set of people violates known information
        fails_evidence = any(
            (people[person]["trait"] is not None and
             people[person]["trait"] != (person in have_trait))
            for person in names
        )
        if fails_evidence:
            continue

        # Loop over all sets of people who might have the gene
        for one_gene in powerset(names):
            for two_genes in powerset(names - one_gene):

                # Update probabilities with new joint probability
                p = joint_probability(people, one_gene, two_genes, have_trait)
                update(probabilities, one_gene, two_genes, have_trait, p)

    # Ensure probabilities sum to 1
    normalize(probabilities)

    # Print results
    for person in people:
        print(f"{person}:")
        for field in probabilities[person]:
            print(f"  {field.capitalize()}:")
            for value in probabilities[person][field]:
                p = probabilities[person][field][value]
                print(f"    {value}: {p:.4f}")


def load_data(filename):
    """
    Load gene and trait data from a file into a dictionary.
    File assumed to be a CSV containing fields name, mother, father, trait.
    mother, father must both be blank, or both be valid names in the CSV.
    trait should be 0 or 1 if trait is known, blank otherwise.
    """
    data = dict()
    with open(filename) as f:
        reader = csv.DictReader(f)
        for row in reader:
            name = row["name"]
            data[name] = {
                "name": name,
                "mother": row["mother"] or None,
                "father": row["father"] or None,
                "trait": (True if row["trait"] == "1" else
                          False if row["trait"] == "0" else None)
            }
    return data


def powerset(s):
    """
    Return a list of all possible subsets of set s.
    """
    s = list(s)
    return [
        set(s) for s in itertools.chain.from_iterable(
            itertools.combinations(s, r) for r in range(len(s) + 1)
        )
    ]


def joint_probability(people, one_gene, two_genes, have_trait):
    """
    Compute the joint probability of the given genetic configuration and traits.
    """
    probability = 1

    # Loop over every person to calculate their probability
    for person in people:
        mother = people[person]["mother"]
        father = people[person]["father"]

        # Determine the number of genes the person has
        if person in one_gene:
            genes = 1
        elif person in two_genes:
            genes = 2
        else:
            genes = 0

        # Determine if the person has the trait
        trait = person in have_trait

        # Case 1: No parental information (use unconditional probability)
        if mother is None and father is None:
            # Get gene probability from unconditional distribution
            probability *= PROBS["gene"][genes]
        else:
            # Case 2: Parental information available (use conditional probabilities)

            # Calculate probability of inheriting the gene from parents
            probabilities = {}

            for parent in [mother, father]:
                if parent in two_genes:
                    probabilities[parent] = 1 - PROBS["mutation"]
                elif parent in one_gene:
                    probabilities[parent] = 0.5
                else:
                    probabilities[parent] = PROBS["mutation"]

            if genes == 2:
                # Both parents must pass on the gene
                probability *= probabilities[mother] * probabilities[father]
            elif genes == 1:
                # One parent passes the gene, the other does not
                probability *= probabilities[mother] * (1 - probabilities[father]) + (1 - probabilities[mother]) * probabilities[father]
            else:
                # Neither parent passes the gene
                probability *= (1 - probabilities[mother]) * (1 - probabilities[father])

        # Multiply by the probability of showing or not showing the trait
        probability *= PROBS["trait"][genes][trait]

    return probability


def update(probabilities, one_gene, two_genes, have_trait, p):
    """
    Add the new joint probability `p` to the existing probabilities.
    """
    for person in probabilities:
        # Update gene distribution
        if person in one_gene:
            probabilities[person]["gene"][1] += p
        elif person in two_genes:
            probabilities[person]["gene"][2] += p
        else:
            probabilities[person]["gene"][0] += p

        # Update trait distribution
        probabilities[person]["trait"][person in have_trait] += p



def normalize(probabilities):
    """
    Normalize the probabilities so that each distribution sums to 1.
    """
    for person in probabilities:
        # Normalize the gene distribution
        gene_total = sum(probabilities[person]["gene"].values())
        for gene in probabilities[person]["gene"]:
            probabilities[person]["gene"][gene] /= gene_total

        # Normalize the trait distribution
        trait_total = sum(probabilities[person]["trait"].values())
        for trait in probabilities[person]["trait"]:
            probabilities[person]["trait"][trait] /= trait_total


if __name__ == "__main__":
    main()
