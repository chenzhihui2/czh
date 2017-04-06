package cat.tbq.hospital.tree;

/**
 * Created by Administrator on 2017/2/21.
 */
public class Tree {
    public static TreeNode createTreeNode(int data){
         return new TreeNode(1);
    }
    public static void main(String args[]){
        TreeNode treeNode1=new TreeNode(1);
        TreeNode treeNode2=new TreeNode(2);
        TreeNode treeNode3=new TreeNode(3);
        TreeNode treeNode4=new TreeNode(4);
        TreeNode treeNode5=new TreeNode(5);

        treeNode3.setLeft(treeNode2);
        treeNode3.setRight(treeNode4);
        treeNode2.setLeft(treeNode1);
        treeNode4.setRight(treeNode5);

        printNodeData(treeNode3);
        System.out.println(nodeCount(treeNode3));
        System.out.println(findTreeNode(treeNode3,5).toString());
        System.out.println(caculateHeight(treeNode3));

    }

    public static TreeNode findTreeNode(TreeNode node,int data){
        if(null==node){
            return null;
        }
        if(data==node.getData()){
            return node;
        }else if(data<node.getData()){
            return findTreeNode(node.getLeft(),data);
        }else {
            return findTreeNode(node.getRight(),data);
        }
    }

    public static int nodeCount(TreeNode node){
        if(node==null){
            return 0;
        }
        return 1+nodeCount(node.getLeft())+nodeCount(node.getRight());
    }

    public static void printNodeData(TreeNode node){
        if(node!=null){
            printNodeData(node.getLeft());
            System.out.println(node.getData());
            printNodeData(node.getRight());
        }
    }

    public static int caculateHeight(TreeNode node){
        int left,right;
        if(null==node){
            return 0;
        }
        left=caculateHeight(node.getLeft());
        right=caculateHeight(node.getRight());
        return (left>right)?(left+1):(right+1);
    }
}
