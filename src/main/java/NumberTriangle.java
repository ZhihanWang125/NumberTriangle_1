import java.io.*;
import java.util.*;

public class NumberTriangle {

    private int root;
    private NumberTriangle left;
    private NumberTriangle right;

    public NumberTriangle(int root) {
        this.root = root;
    }

    public void setLeft(NumberTriangle left) {
        this.left = left;
    }

    public void setRight(NumberTriangle right) {
        this.right = right;
    }

    public int getRoot() {
        return root;
    }

    public boolean isLeaf() {
        return right == null && left == null;
    }

    /**
     * Follow path through this NumberTriangle structure ('l' = left; 'r' = right) and
     * return the root value at the end of the path. An empty string will return
     * the root of the NumberTriangle.
     */
    public int retrieve(String path) {
        NumberTriangle current = this;
        for (char c : path.toCharArray()) {
            if (c == 'l') {
                current = current.left;
            } else if (c == 'r') {
                current = current.right;
            } else {
                throw new IllegalArgumentException("Path must only contain 'l' or 'r': " + path);
            }
        }
        return current.root;
    }

    /** Read in the NumberTriangle structure from a file. */
    public static NumberTriangle loadTriangle(String fname) throws IOException {
        InputStream inputStream = NumberTriangle.class.getClassLoader().getResourceAsStream(fname);
        if (inputStream == null) {
            throw new FileNotFoundException("File not found: " + fname);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        List<List<NumberTriangle>> levels = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] tokens = line.trim().split("\\s+");
            List<NumberTriangle> row = new ArrayList<>();
            for (String tok : tokens) {
                row.add(new NumberTriangle(Integer.parseInt(tok)));
            }
            levels.add(row);
        }
        br.close();

        // Link parents to children
        for (int i = 0; i < levels.size() - 1; i++) {
            List<NumberTriangle> current = levels.get(i);
            List<NumberTriangle> next = levels.get(i + 1);
            for (int j = 0; j < current.size(); j++) {
                current.get(j).setLeft(next.get(j));
                current.get(j).setRight(next.get(j + 1));
            }
        }

        return levels.get(0).get(0); // root
    }

    public static void main(String[] args) throws IOException {
        NumberTriangle mt = NumberTriangle.loadTriangle("input_tree.txt");
        System.out.println(mt.getRoot());
        System.out.println(mt.retrieve("lr"));
    }
}
