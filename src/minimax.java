import java.util.*;

/**
 * Class that represents a playing card.
 * 
 * @author gg392
 */

/* The idea of this AI is that you can only see your own cards and you don't know
 * exactly what cards your opponent has. But you know what cards you do not have 
 * so you can calculate how well the opponent could do if you acted like you knew his cards
 * and guessed every pair value. We will evaluate how well we could do and the opponent could do
 * using our scoring system.
 * 
 * But that is too much guessing, so you can use an approximation to guess only a sample
 * of N pairs randomly to represent everything.
 *  
 * So we do minimax N times using the equation.
 * And we could store this into a giant local database.
 *  
 *
 *
 */


// create new deck
// remove your two cards from the deck
// hard code to decide to stay in or stay out.....

// Show 1st river card
// remove that card from the deck
// calculate ur score
// pick X random pairs you know remain in the deck
// calculate average score of the pairs
// if your score >= then stay?
// if your score < then fold?


class PokerNode {	
	public
}



//public void miniMax(node, depth)
//  	if(depth == 0) then --leaf node     
//        local ret = evaluate(node.state)
//        return ret
//    else -- winning node
//        local winner = whoWin(node.state)       
//        if(winner == 1) then -- P1      
//            return math.huge
//        elseif(winner == -1) then -- P2         
//            return math.huge*-1
//        end 
//    end
//
//    local num_of_moves = getNumberOfMoves(node.state)   
//    local v_t = nil
//    local best_move_index = nil 
//    if(getTurn(node.state) == 1) then -- maximizing player      
//    local v = -math.huge 
//        for i=0, num_of_moves-1 do                      
//            local child_node = simulate(node.state, i)) -- simulate move number i
//            v_t = miniMax(child_node, depth-1)
//            if(v_t > v) then 
//                v = v_t 
//                best_move_index = i             
//            end
//        end             
//        if(best_move_index == nil) then best_move_index = random(0, num_of_moves-1) end 
//        return v, best_move_index
//    else -- minimizing player
//    local v = math.huge
//        for i=0, num_of_moves-1 do          
//            local child_node = simulate(node.state, i)
//            v_t = miniMax(child_node, depth-1)
//            if(v_t < v) then                
//                v = v_t             
//                best_move_index = i             
//            end
//        end
//        if(best_move_index == nil) then best_move_index = random(0, num_of_moves-1) end
//        return v, best_move_index                               
//    end 
//end