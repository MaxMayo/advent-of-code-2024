package org.example;

import org.example.utils.AbstractDay;

import java.util.*;

public class Day09 extends AbstractDay {
    public Day09(List<String> lines) {
        super(lines);
    }

    record FileBlock(int id, int index) {
    }

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
        for (int i = 0; i < nums.length; i++) {
            int number = nums[i];
            if (i % 2 == 0) {
                for (int j = 0; j < number; j++) {
                    fileBlocks.add(new FileBlock(fileIndex, blockIndex++));
                    finalDisc.add(fileIndex);
                }
                fileIndex++;
            } else {
                for (int k = 0; k < number; k++) {
                    emptyBlocks.add(blockIndex++);
                    finalDisc.add(-1);
                }
            }
        }
        // take from top of fileblocks stack, put in location popped from emptyBlocks queue, and delete from stack
        while (emptyBlocks.peek() != null) {
            int locationToGo = emptyBlocks.poll();
            FileBlock blockToMove = fileBlocks.pop();
            if (
                    blockToMove.index() <= locationToGo
            ) {
                break;
            }
            finalDisc.set(locationToGo, blockToMove.id());
            finalDisc.remove(blockToMove.index());
        }

        //checksum
        long sum = 0;
        for (int i = 0; i < finalDisc.size(); i++) {
            int fileId = finalDisc.get(i);
            if (fileId == -1) {
                return sum;
            }
            sum += (fileId * i);
        }
        return sum;
    }

    record WholeFile(int index, int id, int size) {
    }

    record EmptySpace(int index, int size) {
    }

    @Override
    public long partTwo() {
        String line = lines.getFirst();
        int[] nums = new int[line.length()];
        // loop through initial line, map to array of ints
        for (int i = 0; i < line.length(); i++) {
            nums[i] = line.charAt(i) - '0';
        }

        List<EmptySpace> emptySpaces = new ArrayList<>();
        Stack<WholeFile> unmovedFiles = new Stack<>();
        List<Integer> finalDisc = new ArrayList<>();

        int blockIndex = 0;
        int fileIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            int number = nums[i];
            if (i % 2 == 0) {
                // create file with id and size
                WholeFile file = new WholeFile(blockIndex, fileIndex, number);
                unmovedFiles.push(file);
                for (int j = 0; j < number; j++) {
                    finalDisc.add(fileIndex);
                }
                blockIndex += number;
                fileIndex++;
            } else {
                emptySpaces.add(new EmptySpace(blockIndex, number));
                for (int k = 0; k < number; k++) {
                    finalDisc.add(-1);
                }
                blockIndex += number;
            }
        }

//        System.out.printf(finalDisc);

        // defrag
        // take rightmost file block, look at the spaces starting from the left, and drop it in the first place it fits
        while (!unmovedFiles.empty()) {
            WholeFile unmovedFile = unmovedFiles.pop();
            int sizeOfFile = unmovedFile.size();
            int discIndexOfUnmovedFile = unmovedFile.index();

            boolean moved = false;
            for (int i = 0; i < emptySpaces.size(); i++) {
                if (moved) break;
                // is space big enough?
                EmptySpace emptySpace = emptySpaces.get(i);
                if (emptySpace.index() >= discIndexOfUnmovedFile) break;
                if (emptySpace.size() >= sizeOfFile) {
                    // move file on disc
                    for (int j = 0; j < unmovedFile.size(); j++) {
                        finalDisc.set(j + emptySpace.index(), unmovedFile.id());
                    }
                    // alter entry on the previously empty space
                    // if space completely taken up, remove space from list
                    if (emptySpace.size() == sizeOfFile) {
                        emptySpaces.remove(emptySpace);
                    } else {
                        //if space not completely taken up, adjust. but records are final. replace it.
                        emptySpaces.remove(emptySpace);
                        emptySpaces.add(i, // i is index of current emptySpace
                                new EmptySpace(
                                        emptySpace.index() + sizeOfFile,
                                        emptySpace.size()- sizeOfFile));
                    }
                    // wipe original file from disc
                    for (int k = 0; k < unmovedFile.size(); k++) {
                        finalDisc.set(unmovedFile.index() + k, -1);
                    }
                    moved = true;
                }
            }
        }

        System.out.println(finalDisc);

        //checksum
        long sum = 0;
        for (int i = 0; i < finalDisc.size(); i++) {
            int fileId = finalDisc.get(i);
            if (fileId == -1) {
                return sum;
            }
            sum += (fileId * i);
        }
        return sum;
    }
}
