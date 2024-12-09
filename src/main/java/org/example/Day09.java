package org.example;

import org.example.utils.AbstractDay;

import java.util.*;

public class Day09 extends AbstractDay {
    public Day09(List<String> lines) {
        super(lines);
    }

    record FileBlock(int id, int index){}

    @Override
    public long partOne() {
        String line = lines.getFirst();
        int[] nums = new int[line.length()];
        // loop through initial line, map to array of ints
        for (int i = 0; i < line.length(); i++) {
            nums[i] = line.charAt(i) - '0';
        }

        List<Integer> finalDisc = new ArrayList<>();
        Queue<Integer> emptyBlocks = new ArrayDeque<>();
        Stack<FileBlock> fileBlocks = new Stack<>();

        int blockIndex = 0;
        int fileIndex = 0;
        for(int i = 0; i < nums.length; i++) {
            int number = nums[i];
            if(i % 2 == 0) {
                for(int j = 0; j < number; j++) {
                    fileBlocks.add(new FileBlock(fileIndex, blockIndex++));
                    finalDisc.add(fileIndex);
                }
                fileIndex++;
            } else {
                for(int k = 0; k < number; k++) {
                    emptyBlocks.add(blockIndex++);
                    finalDisc.add(-1);
                }
            }
        }
        // take from top of fileblocks stack, put in location popped from emptyBlocks queue, and delete from stack
        while(emptyBlocks.peek() != null) {
            int locationToGo = emptyBlocks.poll();
            FileBlock blockToMove = fileBlocks.pop();
            if(
                    blockToMove.index() <= locationToGo
            ) {
                break;
            }
            finalDisc.set(locationToGo, blockToMove.id());
            finalDisc.remove(blockToMove.index());
        }

        //checksum
        long sum = 0;
        for(int i = 0; i < finalDisc.size(); i++) {
            int fileId = finalDisc.get(i);
            if(fileId == -1) { return sum; }
            sum += (fileId * i);
        }
        return sum;
    }


    @Override
    public long partTwo() {
        return 0;
    }
}
