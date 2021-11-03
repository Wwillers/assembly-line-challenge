package com.assemblyline.assemblyLine.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecursiveSchedule {

    private static int MIN_WORK_TIME = 180;
    private static int MAX_WORK_TIME = 240;

    static List<Integer> sum_up_recursive(List<Integer> numbers, int target, List<Integer> partial) {
        int s = 0;
        for (int x: partial) s += x;
        if (s == target || s >= MIN_WORK_TIME)
            return partial;
        if (s >= MAX_WORK_TIME)
            return null;
        for(int i = 0 ; i < numbers.size(); i++) {
            ArrayList<Integer> remaining = new ArrayList<Integer>();
            int n = numbers.get(i);
            for (int j = i+1; j < numbers.size(); j++) remaining.add(numbers.get(j));
            ArrayList<Integer> partial_rec = new ArrayList<Integer>(partial);
            partial_rec.add(n);
            List<Integer> sum = sum_up_recursive(remaining,target,partial_rec);
            if (sum != null) {
                return sum;
            }
        }
        return null;
    }

    public List<Integer> findSchedulePossibilities(List<Integer> numbers, int target) {
        List<Integer> sum = sum_up_recursive(numbers, target, new ArrayList<Integer>());
        System.out.println("sum("+sum+")="+target);
        return sum;
    }
}
