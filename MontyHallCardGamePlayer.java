/*
Designed and coded by Ronald Inselberg (2010)
A small,interactive card game involving three playing cards.
The interaction is via the Unix command line.
Illustrates the Monty Hall Problem http://en.wikipedia.org/wiki/Monty_Hall_problem.
Also serves as a small example of the Monte Carlo simulation technique,
using psuedo-random number generation.
*/

import java.util.Random;
import java.util.Scanner;

class GamePlayer {

    static boolean debugMode;
    static Scanner consoleInput;
    static int balance, wager;
    
    public static void main(String[] args) {
  
	debugMode = (args.length > 0);
	consoleInput = new Scanner(System.in);
	int playAgain = 0;

	balance = 0; //Initialize player's balance to zero.

	do {
	    main1();
	    System.out.println("Do you want to play again? Input 1 for yes or 0 for no:");
	    playAgain = consoleInput.nextInt();
	} while (playAgain == 1);
	if (playAgain != 0) {
	    System.out.println("Input error. Sorry, this game is over! Please try again.");
	    throw new RuntimeException("main: Input error");

        } else {
	    System.out.println("Your balance is " + balance);
	    System.out.println("Game has ended successfully");

        }
    }

    static void main1() {

	System.out.println("Your balance is " + balance);
	System.out.println("Place your wager. Input any number from 0 upwards:");
	wager = consoleInput.nextInt();
	if (wager < 0) {
	    System.out.println("Input error. Sorry, this game is over! Please try again.");                                                                        
	    throw new RuntimeException("main1: Input error 1");

        }
	System.out.println("If you pick the Joker, you will win " + wager);
	System.out.println("If you pick or settle for the Ace, you will win " + (wager/10));
	System.out.println("If you pick the Queen, you will lose " + wager);

	//GENERATE ONE OF THE SIX (6) POSSIBLE GAMES OR "DEALS" USING A RANDOM NUMBER GENERATOR.
	Random r =  new Random();
	int game = r.nextInt(6) + 1;
	if (debugMode) System.out.println("main1: game=" + game);
        Game cardGame = new Game(game);
	if (debugMode) System.out.print(cardGame.card1);
	if (debugMode) System.out.print(cardGame.card2);
	if (debugMode) System.out.print(cardGame.card3);

	// PRESENT THE GAME PLAYER WITH THREE CARDS, FACE DOWN.
	System.out.println("Card #1 is face down");
	System.out.println("Card #2 is face down");
	System.out.println("Card #3 is face down");
	System.out.println("Try to find the Joker");

	//THE PLAYER MUST SELECT ONE OF THE CARDS.
	System.out.println("Pick a card by entering 1, 2 or 3:");
	int selectCard = consoleInput.nextInt();
       	switch(selectCard) {
	   case 1:
	       initialSelection(cardGame.card1, cardGame.card2, cardGame.card3);
	       break;
	   case 2:
	       initialSelection(cardGame.card2, cardGame.card1, cardGame.card3);
	       break;
	   case 3:
	       initialSelection(cardGame.card3, cardGame.card1, cardGame.card2);
	       break;
	   default:
	       System.out.println("Input error. Sorry, this game is over! Please try again.");
	       throw new RuntimeException("main1: Input error #2");

	}

	//IF THE PLAYER HAS NOT SELECTED THE QUEEN, FACE THE QUEEN.
	//IF THE PLAYER HAS SELECTED THE QUEEN, FACE THE ACE.
	if (cardGame.card1.getFaced()) {
	    System.out.println("Card #1 is the " + cardGame.card1.getCardValue());
        } else {
	    System.out.println("Card #1 is face down");
	}    
	if (cardGame.card2.getFaced()) {
            System.out.println("Card #2 is the " + cardGame.card2.getCardValue());
        } else {
            System.out.println("Card #2 is face down");
        }
        if (cardGame.card3.getFaced()) {
            System.out.println("Card #3 is the " + cardGame.card3.getCardValue());
        } else {
            System.out.println("Card #3 is face down");
        }

	//IF THE ACE HAS BEEN FACED, GIVE THE PLAYER A CHANCE TO SETTLE FOR IT.
	int Ace = 0;
	if (cardGame.card1.getFaced() & (cardGame.card1.getCardValue().equals("Ace"))) Ace = 1;
        if (cardGame.card2.getFaced() & (cardGame.card2.getCardValue().equals("Ace"))) Ace = 2;
	if (cardGame.card3.getFaced() & (cardGame.card3.getCardValue().equals("Ace"))) Ace = 3;
	if (Ace > 0) {
	    System.out.println("Card #" + Ace + " is the Ace. Do you want to settle for it instead of trying to pick the Joker? Input 1 for yes or 0 for no:");
	    int settleForAce = consoleInput.nextInt();
	    if (! ((settleForAce == 0) ^ (settleForAce == 1))) {
		System.out.println("Input error. Sorry, this game is over! Please try again.");
		throw new RuntimeException("main1: Input error #3");

	    } else if (settleForAce == 1) {
		System.out.println("Second Prize--you selected the Ace");
		balance = balance + wager/10;
		System.out.println("Your new balance is " + balance);
		return;

	    }
	}

	//GIVE THE PLAYER A CHANCE TO SWITCH TO THE OTHER CARD THAT IS STILL FACE DOWN.
	System.out.println("You picked Card #" + selectCard);
	if (!cardGame.card1.getSelected() & !cardGame.card1.getFaced()) {
	    System.out.println("Do you want to go with Card #1 instead? Input 1 for yes or 0 for no:");
	} else if (!cardGame.card2.getSelected() & !cardGame.card2.getFaced()) {
	    System.out.println("Do you want to go with Card #2 instead? Input 1 for yes or 0 for no:");
	} else if (!cardGame.card3.getSelected() & !cardGame.card3.getFaced()) {
	    System.out.println("Do you want to go with Card #3 instead? Input 1 for yes or 0 for no:");
        } else {
	    throw new RuntimeException("main1: Logic Error #1");

        }
	int switchCard = consoleInput.nextInt();
	if (! ((switchCard == 0) ^ (switchCard == 1))) {
	    System.out.println("Input error. Sorry, this game is over! Please try again.");
	    throw new RuntimeException("main1: Input error #4");

        }
	if (switchCard == 1) {
	    if (debugMode) System.out.println("Switch Selection");
	    switch(selectCard) {
	        case 1:
		    switchSelection(cardGame.card1, cardGame.card2, cardGame.card3);
		    break;
	        case 2:
		    switchSelection(cardGame.card2, cardGame.card1, cardGame.card3);
		    break;
	        case 3:
		    switchSelection(cardGame.card3, cardGame.card1, cardGame.card2);
		    break;
	    }
	}

	//THE PLAYER HAS MADE HIS FINAL SELECTION. FACE ALL OF THE CARDS AND SHOW HIM HIS GAIN OR LOSS.
        System.out.println("Card #1 is the " + cardGame.card1.getCardValue());
        System.out.println("Card #2 is the " + cardGame.card2.getCardValue());
        System.out.println("Card #3 is the " + cardGame.card3.getCardValue());
	if (cardGame.card1.getSelected() & !cardGame.card2.getSelected() & !cardGame.card3.getSelected()) {
	    faceSelection(cardGame.card1);
	} else if (cardGame.card2.getSelected() & !cardGame.card1.getSelected() & !cardGame.card3.getSelected()) {
	    faceSelection(cardGame.card2);
	} else if (cardGame.card3.getSelected() & !cardGame.card1.getSelected() & !cardGame.card2.getSelected()) {
	    faceSelection(cardGame.card3);
	} else {
	    throw new RuntimeException("main1: Logic Error #2");

        }
       	return;

   }

    static void initialSelection(Card x1, Card x2, Card x3)  {
        x1.setSelected(true);
        if (debugMode) System.out.print(x1);
        if (x2.getCardValue().equals("Queen")) {
            x2.setFaced(true);
            if (debugMode) System.out.print(x2);
        } else if (x3.getCardValue().equals("Queen")) {
            x3.setFaced(true);
            if (debugMode) System.out.print(x3);
        } else if (x2.getCardValue().equals("Ace")) {
            x2.setFaced(true);
            if (debugMode) System.out.print(x2);
        } else {
            x3.setFaced(true);
            if (debugMode) System.out.print(x3);
        }
    }

    static void switchSelection(Card x1, Card x2, Card x3)  {
        x1.setSelected(false);
        if (debugMode) System.out.print(x1);
        if (x2.getFaced()) {
            x3.setSelected(true);
            if (debugMode) System.out.print(x3);
        } else {
            x2.setSelected(true);
            if (debugMode) System.out.print(x2);
        }
    }

    static void faceSelection(Card x1)  {
        x1.setFaced(true);
        if (debugMode) System.out.print(x1);
        if (x1.getCardValue().equals("Joker")) {
            System.out.println("Grand Prize--you selected the Joker!");
	    balance = balance + wager;
        } else if (x1.getCardValue().equals("Ace")) {
            System.out.println("Second Prize--you selected the Ace");
	    balance = balance + wager/10;
        } else {
            System.out.println("Sorry--you selected the Queen");
	    balance = balance - wager;
        }
	System.out.println("Your new balance is " + balance);
    }
}


class Game {

    public Card card1;
    public Card card2;
    public Card card3;

    public Game(int gameNumber) {
	switch(gameNumber) {
	case 1: 
	    card1 = new Card(1, "Joker", false, false);
	    card2 = new Card(2, "Ace", false, false);
	    card3 = new Card(3, "Queen", false, false);
	    //System.out.println("Game #1");
	    break;
        case 2:
	    card1 = new Card(1, "Joker", false, false);
	    card2 = new Card(2, "Queen", false, false);
	    card3 = new Card(3, "Ace", false, false);
            //System.out.println("Game #2");
	    break;
        case 3:
	    card1 = new Card(1, "Ace", false, false);
	    card2 = new Card(2, "Joker", false, false);
	    card3 = new Card(3, "Queen", false, false);
            //System.out.println("Game #3");
	    break;
        case 4:
	    card1 = new Card(1, "Queen", false, false);
	    card2 = new Card(2, "Joker", false, false);
	    card3 = new Card(3, "Ace", false, false);
	    //System.out.println("Game #4");
	    break;
        case 5:
	    card1 = new Card(1, "Ace", false, false);
	    card2 = new Card(2, "Queen", false, false);
	    card3 = new Card(3, "Joker", false, false);
	    //System.out.println("Game #5");
	    break;
	case 6:
	    card1 = new Card(1, "Queen", false, false);
	    card2 = new Card(2, "Ace", false, false);
	    card3 = new Card(3, "Joker", false, false);
            //System.out.println("Game #6");
	    break;
	default:
	    throw new RuntimeException("Game, gameNumber out of bounds");

        }
    }
}

class Card extends Object {

    private int cardNumber;
    private String cardValue;
    private boolean faced;
    private boolean selected;

    public Card (int assignedCardNumber, String assignedCardValue, boolean assignedFaced, boolean assignedSelected) {
	setCardNumber(assignedCardNumber);
	setCardValue(assignedCardValue);
	setFaced(assignedFaced);
	setSelected(assignedSelected);
    }

    public void setCardNumber(int assignedCardNumber) {
        cardNumber = assignedCardNumber;
    }

    public void setCardValue(String assignedCardValue) {
	cardValue = assignedCardValue;
    }

    public void setFaced(boolean assignedFace) {
	faced = assignedFace;
    }

    public void setSelected(boolean assignedSelected) {
	selected = assignedSelected;
    }

    public int getCardNumber() { return cardNumber; }

    public String getCardValue() { return cardValue; }

    public boolean getFaced() { return faced; }

    public boolean getSelected() { return selected; }

    public String toString() {
	return "cardNumber=" + cardNumber + " cardValue=" + cardValue + " faced=" + faced + " selected=" + selected + "\n";
    }
}
