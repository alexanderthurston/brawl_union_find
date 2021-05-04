
public class UnionFind {
    int[] parent;
    int size;

    /**
     * Overridden constructor
     * @param size size of array
     */
    public UnionFind(int size) {
        parent = new int[size];
        this.size = size;
        for (int i = 0; i < size; i++) {
            parent[i] = -1;
        }
    }

    /**
     * Union two nodes together
     * @param root1 root node to be unioned
     * @param root2 root node to be unioned
     */
    public void union( int root1, int root2 ) {
        if(root1 == root2) return;
        int size1 = parent[root1];
        int size2 = parent[root2];
        if(size1 < size2){
            parent[root2] = root1;
            parent[root1] = (size1 + size2);
            return;
        }
        parent[root1] = root2;
        parent[root2] = (size1 + size2);

    }

    /**
     * Internal method to find the parent of int i that utilizes path compression
     * @param i
     * @return parent of i
     */
    public int find(int i) {
        if (parent[i] < 0){
            return i;
        }
        parent[i] = find(parent[i]);
        return parent[i];
    }

    /**
     * Generate string containing UnionFind data
     * @return String
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        int max = 0;
        for(int i = 0; i < size; i++){
            if(find(i) != i){ continue;}
            if(Math.abs(parent[i]) > max) max = Math.abs(parent[i]);
            sb.append("Group " + (i + 1) + " has " + Math.abs(parent[i]) + " members " + "\n");
        }
        sb.append("Largest group is of size " + max);
        return sb.toString();
    }
}

