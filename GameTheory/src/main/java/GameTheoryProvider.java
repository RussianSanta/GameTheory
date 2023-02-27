import java.util.ArrayList;
import java.util.Arrays;

public class GameTheoryProvider {
    public static double[] getMinMax(double[][] data) {
        double[] additionalColumn = new double[data.length];
        Arrays.fill(additionalColumn, 9999);
        double[] additionalLine = new double[data[0].length];
        Arrays.fill(additionalLine, -9999);
        int x = -1;
        int y = -1;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (additionalColumn[i] > data[i][j]) {
                    additionalColumn[i] = data[i][j];
                    y = j;
                }
                if (additionalLine[j] < data[i][j]) {
                    additionalLine[j] = data[i][j];
                    x = i;
                }
            }
        }
        return new double[]{
                Arrays.stream(additionalLine).min().getAsDouble(),
                Arrays.stream(additionalColumn).max().getAsDouble(),
                x, y
        };
    }

    public static double[][] checkDomination(double[][] data) {
        ArrayList<Integer> removedLines = new ArrayList<>();
        ArrayList<Integer> removedColumns = new ArrayList<>();
//      Чистим строки
        for (int i = 0; i < data.length; i++) {
            if (removedLines.contains(i)) continue;
            for (int k = i; k < data.length; k++) {
                if (removedLines.contains(k)) continue;
//              0 =, 1 >, -1 <
                int domination = 0;
                for (int j = 0; j < data[i].length; j++) {
                    if (data[i][j] > data[k][j]) {
                        if (domination == 0 || domination == 1) {
                            domination = 1;
                        } else {
                            domination = 0;
                            break;
                        }
                    } else {
                        if (data[i][j] < data[k][j]) {
                            if (domination == 0 || domination == -1) {
                                domination = -1;
                            } else {
                                domination = 0;
                                break;
                            }
                        } else domination = 0;
                    }
                }
                if (domination == 1) {
                    removedLines.add(k);
                } else {
                    if (domination == -1) {
                        removedLines.add(i);
                        break;
                    }
                }
            }
        }
//      Чистим столбцы
        for (int i = 0; i < data[0].length; i++) {
            if (removedColumns.contains(i)) continue;
            for (int k = i; k < data[0].length; k++) {
                if (removedColumns.contains(k)) continue;
//              0 =, 1 >, -1 <
                int domination = 0;
                for (int j = 0; j < data[i].length; j++) {
                    if (removedLines.contains(j)) continue;
                    if (data[j][i] < data[j][k]) {
                        if (domination == 0 || domination == 1) {
                            domination = 1;
                        } else {
                            domination = 0;
                            break;
                        }
                    } else {
                        if (data[j][i] > data[j][k]) {
                            if (domination == 0 || domination == -1) {
                                domination = -1;
                            } else {
                                domination = 0;
                                break;
                            }
                        } else domination = 0;
                    }
                }
                if (domination == 1) {
                    removedColumns.add(k);
                } else {
                    if (domination == -1) {
                        removedColumns.add(i);
                        break;
                    }
                }
            }
        }
        return removeLineAndColumn(data, removedLines, removedColumns);
    }

    private static double[][] removeLineAndColumn(double[][] data, ArrayList<Integer> removedLines, ArrayList<Integer> removedColumns) {
        double[][] newData = new double[data.length - removedLines.size()][data[0].length - removedColumns.size()];
        int k = 0;
        for (int i = 0; i < data.length; i++) {
            if (!removedLines.contains(i)) {
                int p = 0;
                for (int j = 0; j < data[i].length; j++) {
                    if (!removedColumns.contains(j)) {
                        newData[k][p] = data[i][j];
                        p++;
                    }
                }
                k++;
            }
        }
        return newData;
    }

    public static double[] getMesh(double[][] data) {
        double p1 = (data[1][1] - data[0][1]) / (data[0][0] + data[1][1] - data[0][1] - data[1][0]);
        double p2 = (data[0][0] - data[1][0]) / (data[0][0] + data[1][1] - data[0][1] - data[1][0]);
        double q1 = (data[1][1] - data[1][0]) / (data[0][0] + data[1][1] - data[0][1] - data[1][0]);
        double q2 = (data[0][0] - data[0][1]) / (data[0][0] + data[1][1] - data[0][1] - data[1][0]);
        double v = (data[0][0] * data[1][1] - data[0][1] * data[1][0]) / (data[0][0] + data[1][1] - data[0][1] - data[1][0]);
        return new double[]{p1, p2, q1, q2, v};
    }

    public static double[] getBraun(double[][] data, int firstStrategy, int count) {
        double[][] matrix = new double[count][data[0].length + 8];

        double p0count = 0;
        double q0count = 0;

        int a = firstStrategy;
        int b;
        double minWon;
        double maxLost;
//      Хардкод под мое количество столбцов
        matrix[0][0] = 1;
        matrix[0][1] = a;
        matrix[0][2] = data[firstStrategy][0];
        matrix[0][3] = data[firstStrategy][1];
        if (matrix[0][2] > matrix[0][3]) {
            minWon = matrix[0][3];
            b = 1;
        } else {
            minWon = matrix[0][2];
            b = 0;
            q0count++;
        }
        matrix[0][4] = b;
        matrix[0][5] = data[0][b];
        matrix[0][6] = data[1][b];
        if (matrix[0][5] >= matrix[0][6]) {
            maxLost = matrix[0][5];
            a = 0;
            p0count++;
        } else {
            maxLost = matrix[0][6];
            a = 1;
        }
        matrix[0][7] = minWon / matrix[0][0];
        matrix[0][8] = maxLost / matrix[0][0];
        matrix[0][9] = (matrix[0][7] + matrix[0][8]) / 2;

        for (int i = 1; i < count; i++) {
            matrix[i][0] = i + 1;
            matrix[i][1] = a;
            matrix[i][2] = data[a][0] + matrix[i - 1][2];
            matrix[i][3] = data[a][1] + matrix[i - 1][3];
            if (matrix[i][2] > matrix[i][3]) {
                minWon = matrix[i][3];
                b = 1;
            } else {
                minWon = matrix[i][2];
                b = 0;
                q0count++;
            }
            matrix[i][4] = b;
            matrix[i][5] = data[0][b] + matrix[i - 1][5];
            matrix[i][6] = data[1][b] + matrix[i - 1][6];
            if (matrix[i][5] >= matrix[i][6]) {
                maxLost = matrix[i][5];
                a = 0;
                p0count++;
            } else {
                maxLost = matrix[i][6];
                a = 1;
            }
            matrix[i][7] = minWon / matrix[i][0];
            matrix[i][8] = maxLost / matrix[i][0];
            matrix[i][9] = (matrix[i][7] + matrix[i][8]) / 2;
        }
        DataProvider.printData(matrix);
        return new double[]{p0count / count, (count - p0count) / count, q0count / count, (count - q0count) / count, matrix[count - 1][9]};
    }
}
