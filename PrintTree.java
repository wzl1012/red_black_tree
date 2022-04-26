/*
名称：打印二叉树

排序：
根节点的左子树的所有节点值都不大于本节点值。
根节点的右子树的所有节点值都不小于本节点值。
*/
import java.util.Arrays;
import java.util.Scanner;

class BiTree
{
    private int v;
    private BiTree l;
    private BiTree r;

    public BiTree(int v){
        this.v = v;
    }

    public void add(BiTree the){
        if(the.v < v){
            if(l==null) l = the;
            else l.add(the);
        }
        else{
            if(r==null) r = the;
            else r.add(the);
        }
    }

    public int getHeight(){
        int h = 2;
        int hl = l==null? 0 : l.getHeight();
        int hr = r==null? 0 : r.getHeight();
        return h + Math.max(hl,hr);
    }

    public int getWidth(){
        int w = (""+v).length();
        if(l!=null) w += l.getWidth();
        if(r!=null) w += r.getWidth();
        return w;
    }

    public void show(){
        char[][] buf = new char[getHeight()][getWidth()];
        printInBuf(buf, 0, 0);
        System.out.println();
        showBuf(buf);
    }

    private void showBuf(char[][] x){
        for(int i=0; i<x.length; i++){
            for(int j=0; j<x[i].length; j++) {
                if (x[i][j] == 0 ) {
                    System.out.print(' ');
                } else {
                    System.out.print(x[i][j]);
                }
            }
            for(int j=0; j<x[i].length; j++) {
                if (x[i][j] == 0 ) {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }

    private void printInBuf(char[][] buf, int x, int y){
        String sv = "" + v;

        int p1 = l==null ? x : l.getRootPos(x);
        int p2 = getRootPos(x);
        int p3 = (r == null ? p2 : r.getRootPos(p2 + sv.length()));

        buf[y][p2] = '|';
        for(int i=p1; i<=p3; i++)
            buf[y+1][i]=' ';
        for(int i=0; i<sv.length(); i++) {
            System.out.print(buf[y + 1][p2 + i] = sv.charAt(i));
        }
        System.out.print("   ");
        if(p1<p2)
            buf[y+1][p1] = '/';
        if(p3>p2)
            buf[y+1][p3] = '\\';

        if(l!=null)
            l.printInBuf(buf,x,y+2);
        if(r!=null)
            r.printInBuf(buf,p2+sv.length(),y+2);
    }

    private int getRootPos(int x){
        return l==null? x : x + l.getWidth();
    }
}

public class PrintTree {
    public static void main(String[] args) {
        System.out.println("请输入小于等于16个整数,单个整数用Enter结束");
        Scanner scanner = new Scanner(System.in);
        int iPara[] = new int[16];
        int length = iPara.length;
        //二叉树根节点
        int iRoot = 0;
        //left:小于等于根节点个数, right:大于根节点的个数,
        int left = 0, right = 0;
        //smallLevel小数层级, bigLevel:大数层级
        int smallLevel = 0, bigLevel = 0;

        for(int i=0;i<length;i++) {
            String str = scanner.next();
            if ("end".equals(str.toLowerCase())) {
                length = i;
                break;
            } else {
                iPara[i] = Integer.parseInt(str);
            }
        }

        if (length <= 0){
            System.out.println("输入有效数据必须大于等于1个.");
            System.exit(0);
        }
        int[] lastPara = new int[length];
        System.arraycopy(iPara,0, lastPara, 0, length);
        Arrays.sort(lastPara);

        System.out.println("请输入根节点");
        iRoot = Integer.parseInt(scanner.next());

        //如果根节点有两个或多个,只计一个
        //flag 1: 根节点在数组范围, 0: 根节点不在数组范围
        int flag = 0;
        for(int i=0;i<length;i++){
            if (lastPara[i] < iRoot)
                left++;
            else if (lastPara[i] > iRoot)
                right++;
            else if (lastPara[i] == iRoot)
                flag = 1;
        }

        if (flag == 0){
            System.out.println("根节点不在数组范围内.");
            System.exit(0);
        }

        //计算根节点左边小于根节点的层级
        for(int i=0;i<=left;i++){
            if (Math.pow(2, i) >= left) {
                smallLevel = i;
                break;
            }
        }

        //计算根节点右边大于根节点的层级
        for(int i=0;i<=right;i++){
            if (Math.pow(2, i) >= right) {
                bigLevel = i;
                break;
            }
        }

        //首先导入根节点
        BiTree tree = new BiTree(iRoot);
        //其次导入左边分支
        for(int small=left - 1;small>=0;small--){
            tree.add(new BiTree(lastPara[small]));
        }
        //然后导入右边分支
        for(int big=length - right;big<length;big++){
            tree.add(new BiTree(lastPara[big]));
        }
        tree.show();
    }
}
