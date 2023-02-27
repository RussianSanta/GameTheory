public class DataProvider {
    //Вариант №5
    public static double[][] getFirst() {
        return new double[][]{
                {2, 3, 1, 5},
                {3, 5, 4, 6},
                {4, 3, 2, 3},
                {1, 5, 3, 4},
        };
    }

    public static double[][] getSecond() {
        return new double[][]{
                {4, 2, 3},
                {1, -1, -2},
                {0, 3, 5},
        };
    }

    public static double[][] getThird() {
        return getSecond();
    }

    public static void printData(double[][] data) {
        for (double[] doubles : data) {
            for (int j = 0; j < data[0].length; j++) {
                String cell = String.format("%.2f", doubles[j]);
                StringBuilder space = new StringBuilder("  ");
                for (int k = 0; k < (5 - cell.length()); k++) {
                    space.append(" ");
                }
                System.out.print(space + cell);
            }
            System.out.println();
        }
        System.out.println();
    }
}
