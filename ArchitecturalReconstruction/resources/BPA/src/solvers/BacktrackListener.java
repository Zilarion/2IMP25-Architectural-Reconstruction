package solvers;

import java.util.EventListener;

/**
 * An interface ment to be used by a backtracker to generate solutions
 *
 * @author Ruud Andriessen, 0770663
 */
public interface BacktrackListener extends EventListener {
    /**
     * Reports a solution generated
     * 
     * @param solution The solution generated 
     */
    void solutionGenerated(String solution);
}
