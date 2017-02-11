/*
 * Name: Hyun Joong Kim 
 * EID: hk23356
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {
    /**
     * Determines whether a candidate Matching represents a solution to the
     * Stable Marriage problem. Study the description of a Matching in the
     * project documentation to help you with this.
     */

    public boolean isStableMatching(Matching marriage) {
        for(int worker = 0; worker < marriage.getWorkerCount(); worker++){
            //current match for the iteration of worker
            int jobMatch = marriage.getWorkerMatching().get(worker); 
            int indexOfWorker = marriage.getJobPreference().get(jobMatch).indexOf(worker);

            //let worker iterate smaller than the index of job preference of worker
            //and compute other workers to compare to current preference
            for(int worker_iteration = 0; worker_iteration < indexOfWorker ; worker_iteration++){ 
                int iter_worker = marriage.getJobPreference().get(jobMatch).get(worker_iteration);
                int iter_match = marriage.getWorkerMatching().get(iter_worker);
                int current_pref = marriage.getWorkerPreference().get(iter_worker).indexOf(jobMatch);
                int iter_pref = marriage.getWorkerPreference().get(iter_worker).indexOf(iter_match);
                if(iter_pref > current_pref){ return false ;}
            }
        }
        //if there is no problem cross referencing preferences, the solution is stable
        return true;
    }


    /**
     * Determines a solution to the Stable Marriage problem from the given input
     * set. Study the project description to understand the variables which
     * represent the input to your solution.
     * 
     * @return A stable Matching.
     */
    public Matching stableHiringGaleShapley(Matching marriage) {
        /* TODO implement this function */
        Integer worker, job, prefIndex, currentWorker, current_pref, new_pref;
        Queue<Integer> ls = new LinkedList<Integer>();
        ArrayList<Integer> prefIndexList = new ArrayList<Integer>();
        ArrayList<Integer> worker_match = new ArrayList<Integer>();
        ArrayList<Integer> job_match = new ArrayList<Integer>();

        //set the arrays need to compute the Gale Shapley algorithm
        for(Integer worker_iteration = 0; worker_iteration < marriage.getWorkerCount(); worker_iteration++){
            worker_match.add(-1);
            job_match.add(-1);
            ls.add(worker_iteration);
            prefIndexList.add(0);
        }

        //for the iteration created above
        while (ls.size() != 0 ){
            worker = ls.poll();
            prefIndex = prefIndexList.get(worker);
            job = marriage.getWorkerPreference().get(worker).get(prefIndex);

            //for workers that are not matched
            //match with no conditions
            if(job_match.get(job) == -1){
                worker_match.set(worker, job);
                job_match.set(job, worker);
                prefIndexList.set(worker, prefIndex+1);
            }
            //get the preference of current and new to compare
            //check for conditions 
            else{
                currentWorker = job_match.get(job);
                current_pref = marriage.getJobPreference().get(job).indexOf(currentWorker);
                new_pref = marriage.getJobPreference().get(job).indexOf(worker);
                //match them according to preference
                if(new_pref < current_pref){
                    worker_match.set(worker, job);
                    job_match.set(job, worker);
                    worker_match.set(currentWorker, -1);
                    ls.add(currentWorker);   
                }
                //not a match. send the worker back to the Queue
                else{
                    ls.add(worker);
                    prefIndexList.set(worker, prefIndex+1);
                }
            }
        }
        marriage.setWorkerMatching(worker_match);
        return marriage;
    }

}



