package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.utils.AbstractDay;

import java.util.*;
import java.util.stream.Collectors;

public class Day09 extends AbstractDay {
    public Day09(List<String> lines) {
        super(lines);
    }

    private List<Block> unassignedBlocks = new ArrayList<>();
    private List<File> files = new ArrayList<>();

    @Getter
    private class Disc {
        private final Block[] blocks;

        public Disc(int size) {
            blocks = new Block[size];
            for (int i = 0; i < blocks.length; i++) {
                blocks[i] = new Block(i);
            }
        }

        private Block getBlock(int i) {
            return blocks[i];
        }

        public long checksum() {
            long sum = 0;
            for(int i = 0; i < blocks.length; i++) {
                Block block = blocks[i];
                int fileId = block.getAssociatedFileId();
                if(fileId == -1) { return sum; }
                sum += (fileId * i);
            }
            return sum;
        }

        public void defrag() {
            //start from right end of disc
            for(int i = blocks.length - 1; i >= 0; i--) {
                Block rightBlock = blocks[i];
                if(rightBlock.getAssociatedFileId() != -1) {
                    // find leftmost clear block to move it to
                    for (int j = 0; j < i; j++) {
                        Block clearBlock = blocks[j];
                        if(clearBlock.getAssociatedFileId() != 1) {
                            clearBlock.setAssociatedFileId(rightBlock.getAssociatedFileId());
                            rightBlock.setAssociatedFileId(-1);
                        }
                    }
                }
            }
        }
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    private class Block {
        private int associatedFileId = -1;
        private final int discLocation;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    private class File {
        private final int id;
        private final List<FilePart> fileParts = new ArrayList<>();

        public void addPart(Block block) {
            block.setAssociatedFileId(id);
            fileParts.add(new FilePart(id, block));
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class FilePart {
        private final int fileId;
        private Block block;
    }

    record FileBlock(int id, int index){}

    @Override
    public long partOne() {
        String line = lines.getFirst();
        int[] nums = new int[line.length()];
        int totalLength = 0;
        // loop through initial line, map to array of ints
        for (int i = 0; i < line.length(); i++) {
            nums[i] = line.charAt(i) - '0';
            totalLength += nums[i];
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

//        List<Integer> sensibleList = new ArrayList<>(nums);

        // create disc so it can be populated with blocks
//        Disc disc = new Disc(totalLength);
//
//        //write files to disc
//
//        int fileIndex = 0;
//        int currentBlock = 0;
//        for (int i = 0; i < nums.length; i++) {
//            int number = nums[i];
//            // if file
//            if (i % 2 == 0) {
//                File file = new File(fileIndex);
//                int numPartsToFile = number;
//                for (int j = 0; j < numPartsToFile; j++) {
//                    Block block = disc.getBlock(currentBlock++);
//                    file.addPart(block);
//                }
//                files.add(file);
//                fileIndex++;
//            } else {
//                // is num blank spaces
//                int numEmptyBlocks = number;
//                for (int k = 0; k < numEmptyBlocks; k++) {
//                    Block block = new Block(totalLength); // totalLength is also currently the index
//                    unassignedBlocks.add(block);
//                    currentBlock++;
//                }
//            }
//        }
//        //rearrange fileparts
//        disc.defrag();
//
//        return disc.checksum();
    }


    @Override
    public long partTwo() {
        return 0;
    }
}
