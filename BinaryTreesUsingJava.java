package ronald.assignment2.junit3.binarytree;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Ronald Inselberg
 * 
 * This class implements a simple binary tree data structure "Bt".
 * 
 * Each node of the tree stores a String, which may be the null string "";
 * 
 * The JUnit test methods are written (to the best of my awareness) without interdependencies, and
 * I would expect them to be executable in any order.
 * 
 * The Bt class has two constructors and eight other methods.
 * I have eight JUnit tests, one for each of the eight methods of the Bt class, plus another (ninth) test for the two
 * Bt constructors. Aside from that one (for the constructors), each JUnit test calls on one,
 * and only one, of the eight methods of the class Bt. (Bt constructors are used freely throughout.)
 * So I have orthogonalized the testing to the maximum extent possible,
 * which I understand to be the desired objective for proper unit testing.
 * 
 * The unit testing can not be completely orthogonalized, because some Bt methods call
 * other Bt methods in my implementation of binary tree. These are the only couplings:
 * populateBtFromSequenceOfValues calls populateBtFromLinked List, which in turn calls
 * storeStringValueInBt.
 * 
 * I have used the EclEmma test coverage analyzer to assess the completeness of the test
 * coverage. Except for two lines that show yellow (partial coverage), all of the lines in the class
 * under test (Bt) are green (perfect coverage). This means that the test coverage is almost 100 percent.
 */
public class Bt {

  public Bt left;
	public Bt right;
	public String value;

	/**
	 * constructor for new object of class Bt
	 * The null string "" is stored.
	 */
	public Bt() {
		this.left = null;
		this.right = null;
		this.value = new String();
	}

	/**
	 * constructor for new object of class Bt
	 * @param value		String, value to be stored
	 */ 
	public Bt(String value) {
		this.left = null;
		this.right = null;
		this.value = value;
	}

	/**
	 * method for storing a string value in a binary tree
	 * 
	 * @param root		Bt, binary tree (possibly null)
	 * @param value		String, value to be stored
	 * @return			Bt, binary tree with value stored
	 * 
	 * If "root" is null, it returns a new object of class Bt.
	 * If the string is already stored in the tree with origin of "root",
	 * a new node will not be created. So it will not duplicate a string that is
	 * already stored.
	 * Strings are stored in lexicographic order: http://en.wikipedia.org/wiki/C0_Controls_and_Basic_Latin
	 * At any given node in the tree, descend via the "left" subtree to access any/all strings that are
	 * predecessors of the string at the given node; descend via the "right" subtree to access
	 * any/all strings that are successors to the string at the given node.
	 */
	public Bt storeStringValueInBt(Bt root, String value) {
		if (root==null) {
			return new Bt(value);
		} else {
			int stringCompare = value.compareTo(root.value);
			if (stringCompare < 0) {
				if (root.left != null) {
					storeStringValueInBt(root.left, value);
				} else {
					root.left = new Bt(value);
				}
			} else if (stringCompare > 0) {
				if (root.right != null) {
					storeStringValueInBt(root.right, value);
				} else {
					root.right = new Bt(value);
				}
			}
			return root;
		}
	}

	/**
	 * method for populating a tree using a linked list of string values
	 *  
	 * @param root		Bt, binary tree (possibly null)
	 * @param vlist		LinkedList<String>, linked list of string values (possibly an empty list)
	 * @return			Bt, binary tree populated with all string values
	 * 
	 * If "root" is null and "vlist" is empty, it returns null.
	 * If "root" is null and "vlist" is not empty, it returns
	 * a new object of class Bt, which is the origin of a tree that stores the strings that were passed.
	 * Otherwise, it returns the tree with origin of "root", with new strings inserted.
	 */
	public Bt populateBtFromLinkedList(Bt root, LinkedList<String> vlist) {
		Bt btIterator = root;
		Iterator<String > i = vlist.iterator();
		while (i.hasNext()) {
				btIterator = storeStringValueInBt(btIterator, i.next());
		}
		return btIterator;
	}

	/**
	 * method for creating a new tree and populating it using a sequence of String values.
	 *
	 * @param sequenceOfValues		String..., sequence of strings, possibly an empty sequence
	 * @return						Bt, origin of newly created tree, or null, if there were no strings passed
	 * 
	 * If a string is null ("") or reduces to null after being stripped of leading and trailing white space,
	 * it is not used to populate the tree.
	 */
	public Bt populateBtFromSequenceOfValues(String... sequenceOfValues) {
		LinkedList<String> vlist = new LinkedList<String>();
		int length = sequenceOfValues.length;
		for (int i=0; i < length; i++) {
			String value = sequenceOfValues[i].trim();
			if (!value.equals("")) {
				vlist.add(value);
			}
		}
		return populateBtFromLinkedList(null, vlist);
	}

	/**
	 * method for comparing two trees and determining their relation,
	 * in terms of lexicographic order.
	 * 
	 * Not explicitly called for in the assignment.
	 * 
	 * @param node1		Bt, tree #1
	 * @param node2		Bt, tree #2
	 * @return			int, lexicographic relation of tree #1 vs tree #2
	 */
	public int compareTo(Bt node1, Bt node2) {
		if (node1==null) {
			return node2==null ? 0 : -1;
		} else if (node2==null) {
			return 1;
		} else {
			int result = node1.value.compareTo(node2.value);
			if (result==0) {
				result = compareTo(node1.left, node2.left);
			}
			if (result==0) {
				result = compareTo(node1.right, node2.right);
			}
			return result;
		}
	}

	/**
	 * method for traversing a tree and producing all of its strings,
	 * sorted by ascending lexicographic order and separated by commas.
	 * 
	 * @param node		Bt, origin of tree
	 * @return			String, string comprised by concatenation of all strings in tree,
	 * 					sorted by ascending lexicographic order and separted by commas.
	 */
	public String traverseTree(Bt node) {
		String output = "";
		if (node != null) {
			if (node.left==null) {
				if (node.right==null) {
					output = node.value;
				} else {
					output = node.value + ", " + traverseTree(node.right);
				}
			} else {
				if (node.right==null) {
					output = traverseTree(node.left) + ", " + node.value;
				} else {
					output = traverseTree(node.left) + ", " + node.value + ", " + traverseTree(node.right);
				}
			}
		}
		return output;
	}

	/**
	 * method for counting the nodes of a tree
	 * 
	 * @param node		Bt, origin of tree
	 * @return			int, count of nodes in tree
	 */
	public int countNode(Bt node) {
		int count = 0;
		if (node != null) {
			count += countNode(node.left) + countNode(node.right) + 1;
		}
		return count;
	}

	/**
	 * method for counting the leaf nodes of a tree (nodes without descendants)
	 * 
	 * @param node		Bt, origin of tree
	 * @return			int, count of leaf nodes
	 */
	public int countLeaf(Bt node) {
		int count = 0;
		if (node != null) {
			if (node.left==null & node.right==null) {
				count += 1;
			}
			count += countLeaf(node.left) + countLeaf(node.right);
		}
		return count;
	}

	/**
	 * method for recovering strings from a tree in a special order,
	 * as described below.
	 * 
	 * @param node		Bt, root of tree
	 * @param divide	String, used to divide tree
	 * @return			String, strings recovered from tree, separated by commas
	 * 
	 * The string passed (via "divide") is used to divide the tree.
	 * Start from the root of the tree. Find the node that is closest to the root
	 * that stores a string that is equal to or greater than "divide" in lexicographic
	 * order. Going forward from this node, collect strings in ascending lexicographic order.
	 * Do not collect the string at this node if it is the same as "divide". (That would be equal in
	 * lexicographic order.) Go backward from this node, collecting strings in reverse or descending
	 * lexicographic order. The output is the strings, in the order that they were collected and
	 * separated by commas.
	 */
	public String printFrontToBack(Bt node, String divide) {
		String output = "";
		String checkForNullStringL, checkForNullStringR;
		if (node != null) {
			checkForNullStringL = printFrontToBack(node.left, divide);
			checkForNullStringR = printFrontToBack(node.right, divide);
			int stringCompare = divide.compareTo(node.value);
			if (stringCompare < 0) {
				// collect strings in lexicographic order
				if (checkForNullStringL=="" & checkForNullStringR=="") {
					output = node.value;
				} else if (checkForNullStringL=="" & checkForNullStringR!="") {
					output = node.value + ", " + checkForNullStringR;
				} else if (checkForNullStringL!="" & checkForNullStringR=="") {
					output = checkForNullStringL + ", " + node.value;
				} else {
					output = checkForNullStringL + ", " + node.value + ", " + checkForNullStringR;
				}
			} else if (stringCompare > 0) {
				// collect strings in reverse lexicographic order
				if (checkForNullStringL=="" & checkForNullStringR=="") {
					output = node.value;
				} else if (checkForNullStringL!="" & checkForNullStringR=="") {
					output = node.value + ", " + checkForNullStringL;
				} else if (checkForNullStringL=="" & checkForNullStringR!="") {
					output = checkForNullStringR + ", " + node.value;
				} else {
					output = checkForNullStringR + ", " + node.value + ", " + checkForNullStringL;
				}
			} else {
				// collect strings in lexicographic order, but omit "divide" node
				if (node.left==null) {
					output = printFrontToBack(node.right, divide);
				} else {
					if (node.right!=null) {
						output = printFrontToBack(node.left, divide) + ", " + printFrontToBack(node.right, divide);
					} else {
						output = printFrontToBack(node.left, divide);
					}
				}
			}
		}
		return output;
	}

}
