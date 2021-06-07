
public class Solution {

    public TrieNode root = new TrieNode();

    /*
    By the problem design on binarysearch.com, we have to work
    around the given method 'public int solve(int[] nums)' so that the code
    can be run on the website. Even though the name 'solve' does not make
    a lot of sense, it is left as it is, so that the code can be run directly
    on the website, without any modifications.
     */
    public int solve(int[] nums) {
        return find_maximum_xor(nums);
    }

    public int find_maximum_xor(int[] nums) {

        insert(nums[0]);
        int maximum_xor = 0;

        for (int i = 1; i < nums.length; i++) {
            maximum_xor = Math.max(maximum_xor, do_xor_operation(nums[i]));
            insert(nums[i]);
        }
        return maximum_xor;
    }

    /*
    Starting from the leftmost bit of the integer being checked, we search for
    a mismatch between the bits of the integer and the bits in the Trie. 
    
    The first time we hit a mismatch for integer being checked, we have already 
    established the leading '1' of the binary representation of this particular xor value. 
    Then we search all the way to the rightmost bit, to check for any other mismatches. 
     */
    public int do_xor_operation(int n) {

        TrieNode current = root;
        int shift = 30;
        int xor = 0;

        for (int i = 0; i < 31; i++) {

            int bit = (n >> shift) % 2;
            int opposite_bit = (bit == 1) ? 0 : 1;

            if (current.bitValues[opposite_bit] != null) {
                current = current.bitValues[opposite_bit];
                xor += 1 << shift;
            } else {
                current = current.bitValues[bit];
            }
            shift--;
        }
        return xor;
    }

    /*
    In order to facilitate the operations for searching a maximum xor value,
    the binary representations of the integers are entered with leading '0's,
    if their value warrants so.
     */
    public void insert(int n) {

        TrieNode current = root;

        for (int i = 30; i >= 0; i--) {
            int bit = (n >> i) % 2;
            if (current.bitValues[bit] == null) {
                current.bitValues[bit] = new TrieNode();
            }
            current = current.bitValues[bit];
        }
    }
}

class TrieNode {

    TrieNode[] bitValues;

    public TrieNode() {
        bitValues = new TrieNode[2];
    }
}
