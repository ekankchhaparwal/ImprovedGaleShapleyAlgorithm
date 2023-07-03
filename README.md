
# Improved Gale-Shapley Algorithm in JAVA

This repository contains an implementation of the Improved Gale-Shapley algorithm in Java. The Improved Gale-Shapley algorithm enhances the original Gale-Shapley algorithm for stable matching by considering priority as a weight from both genders. This modification significantly improves the quality of the matching pairs, increasing the satisfaction of the participants.


## Algorithm Description
The Gale-Shapley algorithm is an algorithm for solving the stable marriage problem. It ensures that each participant is matched with the best possible partner according to their preferences, while avoiding any unstable pairings where both partners would rather be with each other than their current partners.

The Improved Gale-Shapley algorithm builds upon the original algorithm by introducing a weight, based on priority, to each participant's preferences. This weight represents the relative importance of each preference. By considering this weight, the algorithm creates matching pairs that better align with the participants' priorities.


## Time Complexity 
The time complexity of the Improved Gale-Shapley algorithm is O(N^2 * logN), where N is the number of participants. This time complexity arises from the need to iterate over the preference lists multiple times and perform comparisons based on priority.

