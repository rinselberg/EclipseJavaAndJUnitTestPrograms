package ronald.assignment2.junit3.binarytree;

import java.util.LinkedList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

/**
 * @author Ronald Inselberg
 * 
 * Unit testing of simple binary tree implementation (class "Bt") using JUnit3.
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
public class JUnitTest extends TestCase {
  
	protected Bt binaryTree;
	protected Bt root, n1, n3, n4, n5, n6, n8, n9;
	protected String namesInAlpha;
	
	public JUnitTest(String name) {
		super(name);
	}  /* I don't know what this is supposed to be used for */
	
	public static Test suite() {
		return new TestSuite(JUnitTest.class);
	}
	
	/*
	 * option of running as Java Application, instead of JUnit Test
	 */
	public static void main(String[] args){
		//junit.textui.TestRunner.run(suite());
		//junit.swingui.TestRunner.run(JUnitTest.class);
		Test runAll = suite();
		TestResult result = new TestResult();
		runAll.run(result);
		System.out.println("Was JUnitTest successful? " + result.wasSuccessful());
		System.out.println("How many tests were there? " + result.runCount());
		System.out.println("Number of errors = " + result.errorCount());
		System.out.println("Number of failures = " + result.failureCount());
	}

	protected void setUp() throws Exception {
		super.setUp();
		binaryTree = new Bt();
		root = null; n1 = null; n3 = null; n4 = null; n5 = null; n6 = null; n8 = null; n9 = null;
		namesInAlpha = null;
		System.out.println("setUp");
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		binaryTree = null;
		root = null; n1 = null; n3 = null; n4 = null; n5 = null; n6 = null; n8 = null; n9 = null;
		namesInAlpha = null;
		System.out.println("tearDown");
	}
	

	public void testBt() {
		/*
		 * test of constructor Bt()
		 */
		n1 = new Bt();
		assertTrue(n1.value.equals("")); assertNull(n1.left); assertNull(n1.right);
		/*
		 * test of constructor Bt(String value)
		 */
		n1 = new Bt("");
		assertTrue(n1.value.equals("")); assertNull(n1.left); assertNull(n1.right);
		/*
		 * test of constructor Bt(String value)
		 */
		n1 = new Bt("zero hour");
		assertTrue(n1.value.equals("zero hour")); assertNull(n1.left); assertNull(n1.right);
		System.out.println("testBt");
	}
	
	public void testStoreStringValueInBt() {
		/*
		 * 
		 */
		n1 = binaryTree.storeStringValueInBt(null, new String());
		assertTrue(n1.value.equals("")); assertNull(n1.left); assertNull(n1.right);
		/*
		 * 
		 */
		n3 = binaryTree.storeStringValueInBt(n1, new String());
		assertTrue(n1.value.equals("")); assertNull(n1.left); assertNull(n1.right);
		assertEquals(n1, n3);
		/*
		 * 
		 */
		n3 = binaryTree.storeStringValueInBt(n1, "");
		assertEquals(n1, n3);
		/*
		 * 
		 */
		n3 = binaryTree.storeStringValueInBt(n1, " ");
		assertTrue(n3==n1); assertNull(n1.left); assertTrue(n1.right.value.equals(" "));
		/*
		 * 
		 */
		n3 = n1.right;
		n5 = binaryTree.storeStringValueInBt(n3, ".");
		assertTrue(n3==n1.right); assertNull(n1.left); assertTrue(n3.value.equals(" "));
		assertTrue(n5==n3); assertNull(n3.left); assertTrue(n3.right.value.equals("."));
		/*
		 * 
		 */
		root = n1;
		n1 = binaryTree.storeStringValueInBt(root, "..");
		assertTrue(n1==root);
		assertTrue(root.right.right.right.value.equals(".."));
		/*
		 * 
		 */
		n1 = binaryTree.storeStringValueInBt(root, " .");
		assertTrue(n1==root);
		assertTrue(root.right.right.left.value.equals(" ."));
		/*
		 * Test the construction of a binary tree from the sequence of values
		 * "5", "1", "3", "8", "3", "6" and "9".
		 * Test that "3" is only stored in the tree the first time that
		 * it is encountered.
		 */
		root = binaryTree.storeStringValueInBt(null, "5");
		root = binaryTree.storeStringValueInBt(root, "1");
		root = binaryTree.storeStringValueInBt(root, "3");
		root = binaryTree.storeStringValueInBt(root, "8");
		root = binaryTree.storeStringValueInBt(root, "3");
		root = binaryTree.storeStringValueInBt(root, "6");
		root = binaryTree.storeStringValueInBt(root, "9");
		assertTrue(root.value.equals("5"));
		assertTrue(root.left.value.equals("1"));
		assertNull(root.left.left);
		assertTrue(root.left.right.value.equals("3"));
		assertNull(root.left.right.left);
		assertNull(root.left.right.right);
		assertTrue(root.right.value.equals("8"));
		assertTrue(root.right.left.value.equals("6"));
		assertNull(root.right.left.left);
		assertNull(root.right.left.right);
		assertTrue(root.right.right.value.equals("9"));
		assertNull(root.right.right.left);
		assertNull(root.right.right.right);
		System.out.println("testBtAddNode");
	}
	
	public void testPopulateBtFromLinkedList() {
		/*
		 * Tests populateBtFromLinkedList, which calls storeStringValueInBt 
		 */
		LinkedList<String> vlist = new LinkedList<String>();
		root = binaryTree.populateBtFromLinkedList(null, vlist);
		assertNull(root);
		/*
		 * 
		 */
		root = binaryTree.populateBtFromLinkedList(new Bt(), vlist);
		assertNull(root.left); assertNull(root.right); assertTrue(root.value.equals(""));
		/*
		 * 
		 */
		vlist.add("a");
		root = binaryTree.populateBtFromLinkedList(root, vlist);
		assertTrue(root.value.equals("")); assertNull(root.left); assertTrue(root.right.value.equals("a"));
		/*
		 * 
		 */
		vlist.add("1");
		root = binaryTree.populateBtFromLinkedList(null, vlist);
		assertTrue(root.left.value.equals("1")); assertNull(root.right); assertTrue(root.value.equals("a"));
		System.out.println("testPopulateFromLinkedList");
	}

	public void testPopulateBtFromSequenceOfValues() {
		/*
		 * Tests populateBtFromSequenceOfValues, which calls populateBtFromLinkedList, which calls storeStringValueInBt 
		 */
		root = binaryTree.populateBtFromSequenceOfValues();
		assertNull(root);
		/*
		 * 
		 */
		root = binaryTree.populateBtFromSequenceOfValues("", " ");
		assertNull(root);
		/*
		 * 
		 */
		root = binaryTree.populateBtFromSequenceOfValues("Sam");
		assertTrue(root.value.equals("Sam"));
		/*
		 * 
		 */
		n1 = new Bt("Sam");
		assertTrue(n1 != null);
		assertTrue(n1 != root);
		assertTrue(n1.value.equals(root.value));
		/*
		 * Test construction of binary tree from the sequence "5", "1", "3", "8", "6", "9".
		 * Test that leading and trailing blanks are removed from strings, and that strings
		 * that equal the null string ("") or reduce to the null string after removing leading
		 * and trailing blanks are not stored in the tree.
		 * 
		 * The same tree structure is created, first with origin of "root", and then with origin
		 * of "n5", and the two trees are verified, node by node, for equivalence.
		 */
		root = binaryTree.populateBtFromSequenceOfValues(" 5", "1 ", "", " 3 ", "8", "   ", "6", "9");
		n5 = new Bt("5");
		n1 = new Bt("1"); n5.left = n1;
		n3 = new Bt("3"); n1.right = n3;
		n8 = new Bt("8"); n5.right = n8;
		n6 = new Bt("6"); n8.left = n6;
		n9 = new Bt("9"); n8.right = n9;
		assertTrue(root.value.equals("5")); assertTrue(root.value.equals(n5.value));
		assertTrue(root.left.value.equals("1")); assertTrue(root.left.value.equals(n5.left.value));
		assertNull(root.left.left); assertNull(n5.left.left);
		assertTrue(root.left.right.value.equals("3")); assertTrue(root.left.right.value.equals(n5.left.right.value));
		assertNull(root.left.right.left); assertNull(n5.left.right.left);
		assertNull(root.left.right.right); assertNull(n5.left.right.right);
		assertTrue(root.right.value.equals("8")); assertTrue(root.right.value.equals(n5.right.value));
		assertTrue(root.right.left.value.equals("6")); assertTrue(root.right.left.value.equals(n5.right.left.value));
		assertNull(root.right.left.left); assertNull(n5.right.left.left);
		assertNull(root.right.left.right); assertNull(n5.right.left.right);
		assertTrue(root.right.right.value.equals("9")); assertTrue(root.right.right.value.equals(n5.right.right.value));
		assertNull(root.right.right.left); assertNull(n5.right.right.left);
		assertNull(root.right.right.right); assertNull(n5.right.right.right);
		System.out.println("testPopulateBtFromSequenceOfValues");
	}
	
	public void testCompareTo() {
		/*
		 * 
		 */
		assertTrue(binaryTree.compareTo(null, null)==0);
		/*
		 * 
		 */
		root = new Bt();
		assertTrue(binaryTree.compareTo(root, null) > 0); assertTrue(binaryTree.compareTo(null, root) < 0);
		assertTrue(binaryTree.compareTo(root, root)==0);
		assertTrue(binaryTree.compareTo(null, new Bt("b")) < 0);
		assertTrue(binaryTree.compareTo(root, new Bt("b")) < 0);
		assertTrue(binaryTree.compareTo(new Bt("Mondo"), null) > 0);
		assertTrue(binaryTree.compareTo(new Bt("Mondo"), root) > 0);
		assertTrue(binaryTree.compareTo(new Bt("b"), new Bt("Mondo")) > 0);
		assertTrue(binaryTree.compareTo(new Bt("Mondo"), new Bt("b")) < 0);
		/*
		 * Set "root" as the origin of a binary tree constructed from the values
		 * "5", "1", "3", "8", "6", "9".
		 * When the tree is compared with itself for lexicographic order,
		 * the return must be zero (0).
		 */
		root = new Bt("5");
		n1 = new Bt("1"); root.left = n1;
		n3 = new Bt("3"); n1.right = n3;
		n8 = new Bt("8"); root.right = n8;
		n6 = new Bt("6"); n8.left = n6;
		n9 = new Bt("9"); n8.right = n9;
		assertTrue(binaryTree.compareTo(root, root)==0);
		/*
		 * Set "n5" as the origin of a binary tree constructed from the values
		 * "5", "3", "1", "8", "6", "9".
		 * When "root" is compared with "n5", verify that the lexicographic order
		 * is determined when the left descendant of root, which stores "1", is compared
		 * with the left descendant of n5, which stores "3".
		 */
		n5 = new Bt("5");
		n3 = new Bt("3"); n5.left = n3;
		n1 = new Bt("1"); n3.right = n1;
		n8 = new Bt("8"); n5.right = n8;
		n6 = new Bt("6"); n8.left = n6;
		n9 = new Bt("9"); n8.right = n9;
		assertTrue(binaryTree.compareTo(root, n5) < 0);
		System.out.println("testCompareTo");
	}
	
	public void testTraverseTree() {
		/*
		 * Construct a binary tree from the sequence of values
		 * "Eli", "Arthur", "Cal", "Hugh", "Carver", "Flynn", "Irsay".
		 * Test that traverseTree collects the values from the tree
		 * in lexicographic order, separated by commas.
		 * 
		 */
		root = new Bt("Eli");
		n1 = new Bt("Arthur"); root.left = n1;
		n3 = new Bt("Cal"); n1.right = n3;
		n4 = new Bt("Hugh"); root.right = n4;
		n5 = new Bt("Carver"); n3.right = n5;
		n6 = new Bt("Flynn"); n4.left = n6;
		n8 = new Bt("Irsay"); n4.right = n8;
		namesInAlpha = "Arthur, Cal, Carver, Eli, Flynn, Hugh, Irsay";
		assertTrue(binaryTree.traverseTree(root).equals(namesInAlpha));
		/*
		 * store a null string ("") in the tree in proper lexicographic order
		 * and retest
		 */
		n1.left = new Bt();
		namesInAlpha = ", " + namesInAlpha;
		assertTrue(binaryTree.traverseTree(root).equals(namesInAlpha));
		/*
		 * 
		 */
		root = new Bt("1"); root.right = new Bt("a");
		assertTrue(binaryTree.traverseTree(root).equals("1, a"));
		root = new Bt("a"); root.left = new Bt("1");
		assertTrue(binaryTree.traverseTree(root).equals("1, a"));
		root = new Bt("onesy");
		assertTrue(binaryTree.traverseTree(root).equals("onesy"));
		root = new Bt();
		assertTrue(binaryTree.traverseTree(root).equals(""));
		root = null;
		assertTrue(binaryTree.traverseTree(root).equals(""));
		System.out.println("testTraverseTree");
	}
	
	public void testCountNode() {
		/*
		 * 
		 */
		root = null;
		assertEquals(binaryTree.countNode(root), 0);
		/*
		 * 
		 */
		root = new Bt();
		assertEquals(binaryTree.countNode(root), 1);
		/*
		 * 
		 */
		root = new Bt("");
		assertEquals(binaryTree.countNode(root), 1);
		/*
		 * Test with tree constructed from the sequence
		 * "5", "1", "3", "8", "6", "9".
		 */
		n5 = new Bt("5");
		n1 = new Bt("1"); n5.left = n1;
		n3 = new Bt("3"); n1.right = n3;
		n8 = new Bt("8"); n5.right = n8;
		n6 = new Bt("6"); n8.left = n6;
		n9 = new Bt("9"); n8.right = n9;
		assertEquals(binaryTree.countNode(n5), 6);
		/*
		 * Test with tree constructed from the sequence
		 * "5", "8", "3", "1", "6", "9", "4".
		 */
		n5 = new Bt("5");
		n8 = new Bt("8"); n5.right = n8;
		n3 = new Bt("3"); n5.left = n3;
		n1 = new Bt("1"); n3.left = n1;
		n6 = new Bt("6"); n8.left = n6;
		n9 = new Bt("9"); n8.right = n9;
		n4 = new Bt("4"); n3.right = n4;
		assertEquals(binaryTree.countNode(n5), 7);
		System.out.println("testCountNode");
	}
	
	public void testCountLeaf() {
		/*
		 * 
		 */
		root = null;
		assertEquals(binaryTree.countLeaf(root), 0);
		/*
		 * 
		 */
		root = new Bt();
		assertEquals(binaryTree.countLeaf(root), 1);
		/*
		 * Test with tree constructed from the sequence
		 * "5", "1", "3", "8", "6", "9".
		 */
		n5 = new Bt("5");
		n1 = new Bt("1"); n5.left = n1;
		n3 = new Bt("3"); n1.right = n3;
		n8 = new Bt("8"); n5.right = n8;
		n6 = new Bt("6"); n8.left = n6;
		n9 = new Bt("9"); n8.right = n9;
		assertEquals(binaryTree.countLeaf(n5), 3);
		/*
		 * Test with tree constructed from the sequence
		 * "5", "8", "3", "1", "6", "9", "4".
		 */
		n5 = new Bt("5");
		n8 = new Bt("8"); n5.right = n8;
		n3 = new Bt("3"); n5.left = n3;
		n1 = new Bt("1"); n3.left = n1;
		n6 = new Bt("6"); n8.left = n6;
		n9 = new Bt("9"); n8.right = n9;
		n4 = new Bt("4"); n3.right = n4;
		assertEquals(binaryTree.countLeaf(n5), 4);
		System.out.println("testCountLeaf");
		
	}
	
	public void testPrintFrontToBack() {
		/*
		 * Test with tree constructed from the sequence
		 * "5", "1", "3", "8", "6", "9".
		 */
		root = new Bt("5");
		n1 = new Bt("1"); root.left = n1;
		n3 = new Bt("3"); n1.right = n3;
		n8 = new Bt("8"); root.right = n8;
		n6 = new Bt("6"); n8.left = n6;
		n9 = new Bt("9"); n8.right = n9;
		assertTrue(binaryTree.printFrontToBack(root, "5").equals("3, 1, 6, 8, 9"));
		assertTrue(binaryTree.printFrontToBack(root, "7").equals("6, 8, 9, 5, 3, 1"));
		assertTrue(binaryTree.printFrontToBack(root, "4").equals("3, 1, 5, 6, 8, 9"));
		assertTrue(binaryTree.printFrontToBack(root, "0").equals("1, 3, 5, 6, 8, 9"));
		assertTrue(binaryTree.printFrontToBack(root, "A").equals("9, 8, 6, 5, 3, 1"));
		assertTrue(binaryTree.printFrontToBack(root, "9").equals("8, 6, 5, 3, 1"));
		assertTrue(binaryTree.printFrontToBack(root, "1").equals("3, 5, 6, 8, 9"));
		assertTrue(binaryTree.printFrontToBack(root, "2").equals("3, 1, 5, 6, 8, 9"));
		assertTrue(binaryTree.printFrontToBack(root, "3").equals("1, 5, 6, 8, 9"));
		assertTrue(binaryTree.printFrontToBack(root, "6").equals("8, 9, 5, 3, 1"));
		assertTrue(binaryTree.printFrontToBack(root, "8").equals("6, 9, 5, 3, 1"));
		/*
		 * 
		 */
		root = new Bt("1");
		assertTrue(binaryTree.printFrontToBack(root, "0").equals("1"));
		assertTrue(binaryTree.printFrontToBack(root, "1").equals(""));
		assertTrue(binaryTree.printFrontToBack(root, "2").equals("1"));
		/*
		 * 
		 */
		root.right = new Bt("2");
		assertTrue(binaryTree.printFrontToBack(root, "0").equals("1, 2"));
		assertTrue(binaryTree.printFrontToBack(root, "1").equals("2"));
		assertTrue(binaryTree.printFrontToBack(root, "2").equals("1"));
		assertTrue(binaryTree.printFrontToBack(root, "3").equals("2, 1"));
		/*
		 * 
		 */
		root = new Bt("2");
		root.left = new Bt("1");
		assertTrue(binaryTree.printFrontToBack(root, "0").equals("1, 2"));
		assertTrue(binaryTree.printFrontToBack(root, "1").equals("2"));
		assertTrue(binaryTree.printFrontToBack(root, "2").equals("1"));
		assertTrue(binaryTree.printFrontToBack(root, "3").equals("2, 1"));
		/*
		 * 
		 */
		root = null;
		assertTrue(binaryTree.printFrontToBack(root, "savvy").equals(""));
		assertTrue(binaryTree.printFrontToBack(root, "").equals(""));
		System.out.println("testPrintFrontToBack");
	}

}
