
import java.util.LinkedList;

public class TreeNode {
    private StepResult stepResult;
    private LinkedList<TreeNode>  children;

    public TreeNode(StepResult stepResult) {
        this.stepResult = stepResult;
    }

    public StepResult getStepResult() {
        return this.stepResult;
    }

    public void addTreeNode(TreeNode treeNode) {
        children.add(treeNode);
    }

    public sortByStarttime() {

    }
    
    public sortByEndtime() {

    }


}
