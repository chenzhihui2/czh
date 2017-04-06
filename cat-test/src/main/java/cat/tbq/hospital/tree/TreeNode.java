package cat.tbq.hospital.tree;

import lombok.Data;

/**
 * Created by Administrator on 2017/2/21.
 */
@Data
public class TreeNode {
    public TreeNode(int data){
        this.data=data;
    }
    private int data;
    private TreeNode left;
    private TreeNode right;
}
