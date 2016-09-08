"""This module contains code from
Think Python by Allen B. Downey
http://thinkpython.com

Copyright 2012 Allen B. Downey
License: GNU GPLv3 http://www.gnu.org/licenses/gpl.html

"""

from Card import *
import operator


class PokerHand(Hand):

# premature optimization
#    def __init__(self):
#        super(Hand, self).__init__()
#        self.looked = False

    def rank_hist(self):
        """ Builds hist for the rank of each card """
        self.ranks={}
        for card in self.cards:
            self.ranks[card.rank] = self.ranks.get(card.rank, 0) + 1

    def suit_hist(self):
        """Builds a histogram of the suits that appear in the hand.

        Stores the result in attribute suits.
        """
        self.suits = {}
        for card in self.cards:
            self.suits[card.suit] = self.suits.get(card.suit, 0) + 1

    def has_flush(self):
        """Returns True if the hand has a flush, False otherwise.
      
        Note that this works correctly for hands with more than 5 cards.
        """
        self.suit_hist()
        for val in self.suits.values():
            if val >= 5:
                return True
        return False

#    def look_at_hand(self):
#        """Only count the cards once"""
#        #if self.looked == True:
#            #return
#        self.suit_hist()
#        self.rank_hist()
#        self.looked=True

    def has_pair(self):
        """ Returns True if there are two of the same number """
        self.rank_hist()
        for val in self.ranks.values():
            if val == 2:
                return True
        return False

    def has_twopair(self):
        self.rank_hist()
        found =0
        for val in self.ranks.values():
            if val == 2:
                found +=1
        if found == 2:
            return True
        return False

    def has_threeofakind(self):
        self.rank_hist()
        for val in self.ranks.values():
            if val == 3:
                return True
        return False

    def has_straight(self):
        self.rank_hist()
        lst = self.ranks.keys()
        #print" * * * * "+str(lst)
        for x in lst:
            if (set([x+1, x+2, x+3, x+4]).issubset( set(lst) )) : 
                return True
            #else:
                #print("False for %s in %s" % (x, lst))
        return False

    def has_fullhouse(self):
        self.rank_hist()
        return self.has_pair() and self.has_threeofakind()

    def has_fourofakind(self):
        self.rank_hist()
        for val in self.ranks.values():
            if val == 4:
                return True
        return False

    def has_straightflush(self):
        if(self.has_flush()):
            if(self.has_straight() ):
                #Check each suit
                for s in range(4):
                    #Get the cards for that suit
                    suit_hand = self.get_cards_for_suit(s)
                    #Check for a straight
                    if suit_hand.has_straight():
                        return True
        return False

    #Could be an iterator
    def get_cards_for_suit(self, suit):
        suit_hand = PokerHand()
        for c in self.cards:
            if(c.suit == suit):
                suit_hand.cards.append(c)
        return suit_hand

    # This could be an iterator
    def classify(self):
        if(self.has_straightflush() ):
            self.label = "Straight flush"
            return
        if(self.has_fourofakind() ):
            self.label = "Four of a kind"
            return
        if(self.has_fullhouse() ):
            self.label = "Full house"
            return
        if(self.has_flush() ):
            self.label = "Flush"
            return
        if(self.has_straight() ):
            self.label = "Straight"
            return
        if(self.has_threeofakind() ):
            self.label = "Three of a kind"
            return
        if(self.has_twopair() ):
            self.label = "Two pair"
            return
        if(self.has_pair() ):
            self.label = "One pair"
            return
        self.label = "High Card"
        return



if __name__ == '__main__':
    # make a deck
    results = {}
    random.seed(1)
    cardsPerHand=5
    
    handCount = 100000
    # deal the cards and classify the hands
    for i in range(handCount):
        deck = Deck()
        deck.shuffle()
        hand = PokerHand()
        deck.move_cards(hand, cardsPerHand)
        hand.sort()
        hand.classify()
        #print hand
        #print hand.label
        #print ''
        results[hand.label] = results.get(hand.label, 0 ) + 1

    sorted_results = sorted(results.items(), key=operator.itemgetter(1)) # sort dict by value http://stackoverflow.com/questions/613183/sort-a-python-dictionary-by-value
    for r in sorted_results:
        print("Hand %s %s/%s \t %02.2f %%" % (r[0].ljust(18), r[1], handCount, float(r[1])/handCount* 100 ) )

if False:
    #Testing
    t = PokerHand()
    t.cards.append( Card(1, 10) );
    t.cards.append( Card(1, 9) );
    t.cards.append( Card(1, 8) );
    t.cards.append( Card(2, 7) );
    print("Check flush false = "+str(t.has_flush() ) )
    t.cards.append( Card(1, 2) );
    print("Check flush true = "+str(t.has_flush() ) )
    print("Check pair false = "+str(t.has_pair() ) )
    t.cards.append( Card(1, 10) );
    print("Check pair true = "+str(t.has_pair() ) )
    print("Check twopair false = "+str(t.has_twopair() ) )
    t.cards.append( Card(1, 9) );
    print("Check twopair true = "+str(t.has_twopair() ) )
    print("Check threeofakind false = "+str(t.has_threeofakind() ) )
    print("Check fullhouse false = "+str(t.has_fullhouse() ) )
    t.cards.append( Card(1, 10) );
    print("Check fullhouse true = "+str(t.has_fullhouse() ) )
    print("Check straigt false = "+str(t.has_straight() ) )
    t.cards.append( Card(1, 11) );
    print("Check straigt true = "+str(t.has_straight() ) )
    print("Check get_cards_for_suit  0 = "+str(t.get_cards_for_suit(0) ) )
    print("Check get_cards_for_suit  1 = "+str(t.get_cards_for_suit(1) ) )
    print("Check get_cards_for_suit  2 = "+str(t.get_cards_for_suit(2) ) )
    print("Check get_cards_for_suit  3 = "+str(t.get_cards_for_suit(3) ) )
    sys.exit()
