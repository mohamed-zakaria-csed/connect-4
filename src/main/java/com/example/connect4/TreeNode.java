package com.example.connect4;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    State state;
    boolean isMax;
    boolean isCut;
    List<TreeNode> children;
    TreeNode parent;
    boolean isChildrenCalculated;
    public TreeNode(State state, TreeNode parent, boolean isMax) {
        this.state = state;
        this.parent = parent;
        this.isMax = isMax;
        this.isCut = false;
        this.children = new ArrayList<>();
        isChildrenCalculated = false;
    }

    public List<TreeNode> getChildren(){
        if(isChildrenCalculated){
            return this.children;
        }
        List<TreeNode> children = new ArrayList<>();
        List<State> nextStates = this.state.getNeighbours();
        for (int i = 0; i < nextStates.size(); i++) {
            children.add(new TreeNode(nextStates.get(i), this, !this.isMax));
        }
        this.children = children;
        isChildrenCalculated = true;
        return children;
    }
}
